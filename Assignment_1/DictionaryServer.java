// imports
import java.io.*;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.net.ServerSocketFactory;

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

            response(req, out, in);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Server response to client request.
     * @param req client request.
     * @param outputStream output data stream from socket.
     * @return dictionary data response
     */
    private static void response(JSONObject req, BufferedWriter outputStream, BufferedReader inputStream) throws IOException {

        // read client request data
//        switch (action) {
//
//            case GET_DICTIONARY:
//                packet.put("method", "get dictionary");
//                break;
//            case GET_MEANING:
//                packet.put("method", "get meaning");
//                packet.put("word", serverAction.getWord());
//                break;
//            case ADD_MEANING:
//                packet.put("method", "add meaning");
//                packet.put("word", serverAction.getWord());
//                packet.put("meaning", serverAction.getMeaning());
//                break;
//            case UPDATE_MEANING:
//                packet.put("method", "update meaning");
//                packet.put("word", serverAction.getWord());
//                packet.put("meaning", serverAction.getMeaning());
//                packet.put("previous meaning", serverAction.getPrevMeaning())
//                break;
//            case REMOVE_WORD:
//                packet.put("method", "remove word");
//                packet.put("word", serverAction.getWord());
//                break;
//            case ADD_WORD:
//                packet.put("method", "add word");
//                packet.put("word", serverAction.getWord());
//                break;
//            default:
//                break;
//        }
//        out.write(packet.toString());
//        out.newLine();
//        out.flush();                     // Flush buffered writer contents.
//
//        // await server response
//        boolean successRes = false;
//        while(!successRes) {
//
//            if (in.ready()) {           /* Response received */
//                String res = in.readLine();
//                System.out.println(res);
//                successRes = true;
//            }
//        }
//
//        // Await server response
//        String response = in.readLine();  // Read server response
//        if (response != null) {
//            System.out.println("Server response: " + response);
//        } else {
//            System.out.println("No response received from server.");
//        }
//
//        // Close resources
//        in.close();
//        out.close();
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