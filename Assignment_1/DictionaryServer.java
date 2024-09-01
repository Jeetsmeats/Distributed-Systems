// imports
import java.io.*;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.net.ServerSocketFactory;

import Exceptions.*;
import Helper.ActionType;
import org.json.*;
import java.lang.Object.*;
import DataStructures.Dictionary;
import ThreadPool.ThreadPool;

/**
 * Dictionary Server
 */
public class DictionaryServer {

    /**
     * Client port
     */
    private static int port;

    /**
     * Dictionary to hold all the data.
     */
    private static Dictionary dictionary;

    public static void main(String[] args) {

        // initialise socket factory
        ServerSocketFactory factory = ServerSocketFactory.getDefault();

        // set up thread pool
        ThreadPool threadPool = new ThreadPool(5, 10);

        // create a new dictionary
        dictionary = new Dictionary();

        // Enter port
        port = Integer.parseInt(args[0]);

        try (ServerSocket server = factory.createServerSocket(port)) {

            // Set the default dictionary values
            readSavedData(args[1]);

            System.out.println("Server Running");

            // Client-server superloop
            while (true) {
                System.out.println("Waiting for connection");
                Socket client = server.accept();

                // add task to queue for execution
                threadPool.addTask(() -> executeTask(client));
            }
        } catch (NumberFormatException e) {

            System.out.println("Input port is NAN: " + args[0]);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Create task per client request.
     * @param client client socket connection.
     */
    private static void executeTask(Socket client) {

        try(Socket clientSocket = client)
        {
            // Buffered reader to read data
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));

            String clientIn = in.readLine();
            JSONObject req = new JSONObject(clientIn);              // Client request

            response(req, out);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Server response to client request.
     * @param req client request.
     * @param outputStream output data stream from socket.
     */
    private synchronized static void response(JSONObject req, BufferedWriter outputStream) throws IOException {

        // JSON packet for sending through TCP buffer
        JSONObject packet = new JSONObject();
        try {
            JSONObject methodJson = (JSONObject) req.get("method");
            String method = methodJson.toString();

            JSONObject wordJson;
            JSONObject meaningJson;
            JSONArray meaningsArray;
            JSONArray wordArray;

            String word;
            String meaning;
            ArrayList<String> meanings;
            ArrayList<String> words;

            switch (method) {

                case "get dictionary":

                    words = dictionary.getWords();
                    wordArray = addList2JSONArray(words);
                    packet.put("words", wordArray);
                    break;
                case "get meaning":

                    wordJson = (JSONObject) req.get("word");
                    word = wordJson.toString();

                    meanings = dictionary.getMeaning(word);
                    meaningsArray = addList2JSONArray(meanings);

                    packet.put("meaning", meaningsArray);
                    packet.put("word", word);
                    break;
                case "add meaning":

                    wordJson = (JSONObject) req.get("word");
                    word = wordJson.toString();

                    meaningJson = (JSONObject) req.get("meaning");
                    meaning = meaningJson.toString();

                    meanings = dictionary.addDescription(word, meaning);
                    meaningsArray = addList2JSONArray(meanings);

                    packet.put("meaning", meaningsArray);
                    packet.put("word", word);
                    break;
                case "update meaning":

                    wordJson = (JSONObject) req.get("word");
                    word = wordJson.toString();

                    meaningJson = (JSONObject) req.get("meaning");
                    meaning = meaningJson.toString();

                    JSONObject prevMeaningJson = (JSONObject) req.get("previous meaning");
                    String prevMeaning = prevMeaningJson.toString();

                    meanings = dictionary.updateDescription(meaning, prevMeaning, word);
                    meaningsArray = addList2JSONArray(meanings);

                    packet.put("meaning", meaningsArray);
                    packet.put("word", word);
                    break;
                case "remove word":

                    wordJson = (JSONObject) req.get("word");
                    word = wordJson.toString();

                    dictionary.removeWord(word);

                    words = dictionary.getWords();
                    wordArray = addList2JSONArray(words);
                    packet.put("word", wordArray);
                    break;
                case "add word":

                    wordJson = (JSONObject) req.get("word");
                    word = wordJson.toString();

                    meaningJson = (JSONObject) req.get("meaning");
                    meaning = meaningJson.toString();

                    dictionary.addWord(word, meaning);

                    words = dictionary.getWords();
                    wordArray = addList2JSONArray(words);
                    packet.put("word", wordArray);
                    break;
                default:
                    break;
            }

            // send data to client
            outputStream.write(packet.toString());
            outputStream.newLine();
            outputStream.flush();                     // Flush buffered writer contents.

        } catch (WordNullException | InvalidDescription |
                 InvalidWordException | DescriptionNullException |
                DictionaryActionException e) {

            // add error to json
            packet.put("error", e.getMessage());

            // send data to client
            outputStream.write(packet.toString());
            outputStream.newLine();
            outputStream.flush();                     // Flush buffered writer contents.
        }
    }

    /**
     * Convert a String ArrayList to a JSON array.
     * @param list String ArrayList of values.
     * @return Formatted JSONArray.
     */
    private static JSONArray addList2JSONArray(ArrayList<String> list) {

        JSONArray jsonArray = new JSONArray();
        for (String item : list) {
            jsonArray.put(item);
        }
        return jsonArray;
    }

    /**
     * Read stored dictionary data from serialised file.
     * @param filePath path of saved dictionary file
     */
    private static void readSavedData(String filePath) {

        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            dictionary = (Dictionary) in.readObject();
            System.out.println(dictionary.getWordDictionary());
        } catch (IOException | ClassNotFoundException  e) {

            e.printStackTrace();
        }
    }
}