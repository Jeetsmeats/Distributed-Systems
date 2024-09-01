package UI;

// imports
import Helper.ActionType;
import Helper.Actions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class DictionaryClient extends JFrame {

    /* UI Elements */
    private JPanel mainPanel;
    private JLabel addressLabel;
    private JLabel portLabel;
    private JLabel dictionaryLabel;
    private JLabel logLabel;
    private JTextArea consoleLog;
    private JTextField serverAddressTextField;
    private JTextField serverPortTextField;
    private JButton connectBtn;
    private JLabel newConnectionLabel;
    private JButton addBtn;
    private JTextField addWordHereTextField;
    private JButton removeBtn;
    private JLabel meaningLabel;
    private JLabel searchMeaningLabel;
    private JTextField searchWordTextField;
    private JLabel updateMeaningLabel;
    private JTextField updateMeaningTextField;
    private JButton updateBtn;
    private JTextField addMeaningTextField;
    private JButton addMeaningButton;
    private JButton searchWordButton;
    private JLabel addMeaningLabel;
    private JLabel addWordLabel;
    private JLabel removeWordLabel;
    private JButton getDictionary;
    private JTextField removeWordTextField;
    private JTextField meaningToUpdateTextField;
    private JLabel currentWordLabel;
    private JTextArea meaningTextArea;
    private JTextArea wordTextArea;

    /**
     * Socket Address
     */
    private String address;

    /**
     * Server Port
     */
    private int port;

    /**
     * Socket connection object.
     */
    private Socket socket;

    /**
     * Buffered Data Reader for JSON data into the client.
     */
    private BufferedReader in;

    /**
     * Buffered Data Writer for JSON data to the server.
     */
    private BufferedWriter out;

    public DictionaryClient(String[] args) {

        setContentPane(mainPanel);
        setTitle("Interactive Dictionary");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        // initialise word text area to show data.
        wordTextArea.setEditable(false);  // Make it non-editable
        wordTextArea.setLineWrap(true);   // Optional: wrap lines
        wordTextArea.setWrapStyleWord(true); // Optional: wrap at word boundaries

        // Initialize JTextArea as the console
        consoleLog.setEditable(false);  // Make it non-editable
        consoleLog.setLineWrap(true);   // Optional: wrap lines
        consoleLog.setWrapStyleWord(true); // Optional: wrap at word boundaries

        meaningTextArea.setEditable(false);
        meaningTextArea.setLineWrap(true);
        meaningTextArea.setWrapStyleWord(true);

        try {

            // server parameters
            port = Integer.parseInt(args[1]);
            address = args[0];

            addressLabel.setText(addressLabel.getText() + " " + address);
            portLabel.setText(portLabel.getText() + " " + port);
            Actions startAction = new Actions();

            // initial request
            request(startAction);
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (NumberFormatException e){

            System.out.println("Incorrect port number.");
        } finally {

            // Close the socket
            if (socket != null) {
                try {
                    socket.close();
                }
                catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        new DictionaryClient(args);
    }

    public void request(Actions serverAction) throws UnknownHostException, IOException {

        // Socket connection to server
        socket = new Socket(address, port);
        System.out.println("Connection established");

        // Create new IO stream for client request.
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), "UTF-8"));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

        // package message into json based message protocol
        JSONObject packet = new JSONObject();
        ActionType action = serverAction.getActionType();
        switch (action) {

            case GET_DICTIONARY:
                packet.put("method", "get dictionary");
                break;
            case GET_MEANING:
                packet.put("method", "get meaning");
                packet.put("word", serverAction.getWord());
                break;
            case ADD_MEANING:
                packet.put("method", "add meaning");
                packet.put("word", serverAction.getWord());
                packet.put("meaning", serverAction.getMeaning());
                break;
            case UPDATE_MEANING:
                packet.put("method", "update meaning");
                packet.put("word", serverAction.getWord());
                packet.put("meaning", serverAction.getMeaning());
                packet.put("previous meaning", serverAction.getPrevMeaning());
                break;
            case REMOVE_WORD:
                packet.put("method", "remove word");
                packet.put("word", serverAction.getWord());
                break;
            case ADD_WORD:
                packet.put("method", "add word");
                packet.put("word", serverAction.getWord());
                break;
            default:
                break;
        }
        out.write(packet.toString());
        out.newLine();
        out.flush();                     // Flush buffered writer contents.

        // await server response
        boolean successRes = false;
        String res = null;

        while(!successRes) {

            if (in.ready()) {           /* Response received */
                res = in.readLine();
                System.out.println(res);
                successRes = true;
            }
        }

        // Close resources
        in.close();
        out.close();

        JSONObject response = new JSONObject(res);
        // check for errors
        try {

            JSONObject err = (JSONObject) response.get("error");
            consoleLog.append(err.toString() + "\n");
        } catch (JSONException e) {

            JSONArray wordList;
            JSONArray meaningList;
            JSONObject wordJson;
            String word;
            String[] words;
            String[] meanings;

            System.out.println("No error");
            // package message into json based message protocol
            switch (action) {

                case GET_DICTIONARY:

                    wordActions(response);
                    consoleLog.append("Successfully retrieved dictionary.\n");
                    break;
                case GET_MEANING:

                    meaningActions(response);
                    consoleLog.append("Successfully retrieved word meanings.\n");
                    break;
                case ADD_MEANING:

                    meaningActions(response);
                    consoleLog.append("Successfully added meaning to word.\n");
                    break;
                case UPDATE_MEANING:

                    meaningActions(response);
                    consoleLog.append("Successfully updated meaning for word.\n");
                    break;
                case REMOVE_WORD:

                    wordActions(response);
                    consoleLog.append("Successfully removed word.\n" + "\n");
                    break;
                case ADD_WORD:

                    wordActions(response);
                    consoleLog.append("Successfully added word.\n");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Convert JSONArray to a string.
     * @param jsonArray JSONArray retrieved from server.
     * @return String array containing item list.
     */
    private String[] jsonArrayToString(JSONArray jsonArray) {

        // initialise string array
        String[] stringArray = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {

            // set string at index
            stringArray[i] = jsonArray.getString(i);
        }
        return stringArray;
    }

    /**
     * Set the word text area to display dictionary words.
     * @param words words to display on text area.
     */
    private void setWordTextArea(String[] words) {

        for (String word : words) {

            wordTextArea.append(word + "\n");
        }
    }

    /**
     * Set the meanings text area to display word meanings
     * @param meanings list of meanings associated to a selected word.
     * @param word word corresponding to meanings.
     */
    private void setMeaningsTextArea(String[] meanings, String word) {

        for (String meaning : meanings) {

            meaningTextArea.append(meaning + "\n");
        }

        currentWordLabel.setText(currentWordLabel.getText() + " " + word);
    }

    /**
     * Word actions to update GUI: Add word, Remove word, Get Dictionary
     * @param response server response
     */
    private void wordActions(JSONObject response) {

        JSONArray wordList = (JSONArray) response.get("words");
        String[] words = jsonArrayToString(wordList);

        // display words on GUI
        wordTextArea.setText("");
        setWordTextArea(words);
    }

    /**
     * Meaning actions to update GUI:
     * @param response server response
     */
    private void meaningActions(JSONObject response) {

        JSONArray meaningList;
        JSONObject wordJson;
        String word;
        String[] meanings;

        meaningList = (JSONArray) response.get("meanings");
        wordJson = (JSONObject) response.get("word");

        word = wordJson.toString();
        meanings = jsonArrayToString(meaningList);

        meaningTextArea.setText("");
        setMeaningsTextArea(meanings, word);
    }
}
