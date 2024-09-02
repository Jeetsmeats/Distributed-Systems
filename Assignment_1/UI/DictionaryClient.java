package UI;

// imports
import Helper.ActionType;
import Helper.Actions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JTextField addWordTextField;
    private JButton removeBtn;
    private JLabel meaningLabel;
    private JLabel searchMeaningLabel;
    private JTextField searchWordTextField;
    private JLabel updateMeaningLabel;
    private JTextField updatedMeaningTextField;
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
    private JButton connectButton;
    private JTextField includeMeaningTextField;

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

    /**
     * Word that was queried.
     */
    private String currentWord;

    /**
     * Address Label text
     */
    private static final String addressLabelText = "DictionaryServer Address: ";

    /**
     * Port label text
     */
    private static final String portLabelText = "DictionaryServer Port: ";

    /**
     * Address Text Field label text
     */
    private static final String enterAddressLabelText = "Enter address Here...";

    /**
     * Port Text Field label text
     */
    private static final String enterPortLabelText = "Enter port Here...";

    /**
     * Search word text field label text
     */
    private static final String searchWordLabelText = "Search word...";

    /**
     * Add word text field label text
     */
    private static final String addWordLabelText = "Add word here...";

    /**
     * Include meaning text field label text
     */
    private static final String includeMeaningLabelText = "Include meaning...";

    /**
     * Remove word text field label text
     */
    private static final String removeWordLabelText = "Remove word...";

    /**
     * Update meaning text field label text
     */
    private static final String meaningToUpdateLabelText = "Meaning to update...";

    /**
     * Updated meaning text field label text
     */
    private static final String updatedMeaningLabelText = "Updated meaning...";

    /**
     * Add meaning text field label text
     */
    private static final String addMeaningLabelText = "Add meaning...";

    /**
     * Current word label text
     */
    private static final String currentWordLabelText = "Current Word:";
    public DictionaryClient(String[] args) {

        try {

            // server parameters
            port = Integer.parseInt(args[1]);
            address = args[0];

            addressLabel.setText(addressLabel.getText() + " " + address);
            portLabel.setText(portLabel.getText() + " " + port);
            Actions startAction = new Actions();

            // initial request
            request(startAction);
        } catch (UnknownHostException e) {

            consoleLog.append(e.getMessage() + " is an unknown host.  Try again!\n");
        } catch (IOException | NumberFormatException e) {

            consoleLog.append(e.getMessage() + "\n");
        } finally {

            // Close the socket
            if (socket != null) {
                try {
                    socket.close();
                }
                catch (IOException e) {

                    consoleLog.append(e.getMessage() + "\n");
                }
            }
        }

        connectButton.addActionListener(e -> {

            try {
                address = serverAddressTextField.getText();
                port = Integer.parseInt(serverPortTextField.getText());

                serverAddressTextField.setText(enterAddressLabelText);
                serverPortTextField.setText(enterPortLabelText);

                socket = new Socket(address, port);
                addressLabel.setText(addressLabelText + " " + address);
                portLabel.setText(portLabelText + " " + port);
                System.out.println("Reached this point");
            } catch (UnknownHostException err) {

                consoleLog.append(err.getMessage() + " is an unknown host.  Try again!\n");
                addressLabel.setText(addressLabelText);
                portLabel.setText(portLabelText);
            } catch (IOException | NumberFormatException err) {

                consoleLog.append(err.getMessage() + "\n");
                addressLabel.setText(addressLabelText);
                portLabel.setText(portLabelText);
            } finally {

                // Close the socket
                if (socket != null) {
                    try {
                        socket.close();
                    }
                    catch (IOException err) {

                        consoleLog.append(err.getMessage() + "\n");
                    }
                }
            }
        });

        getDictionary.addActionListener(e -> {

            Actions act = new Actions();
            requestAction(act);
        });

        searchWordButton.addActionListener(e -> {

            String word = searchWordTextField.getText();
            if (word.equals(searchWordLabelText) || word.isEmpty()) {

                consoleLog.append("Enter a word to search meanings!");
            } else {

                searchWordTextField.setText(searchWordLabelText);
                Actions act = new Actions(word, ActionType.GET_MEANING);
                requestAction(act);
            }
        });

        addBtn.addActionListener(e -> {

            String word = addWordTextField.getText();
            addWordTextField.setText(addWordLabelText);

            if (word.equals(addWordLabelText) || word.isEmpty()) {

                consoleLog.append("Enter a new word!\n");
            } else if (includeMeaningTextField.getText().equals(includeMeaningLabelText)) {

                consoleLog.append("Must add meaning to the new word!\n");
            } else {

                String meaning = includeMeaningTextField.getText();
                includeMeaningTextField.setText(includeMeaningLabelText);
                Actions act = new Actions(word, meaning, ActionType.ADD_WORD);
                requestAction(act);
            }
        });

        removeBtn.addActionListener(e -> {

            String word = removeWordTextField.getText();
            if (word.equals(removeWordLabelText) || word.isEmpty()) {

                consoleLog.append("Enter a word to remove!\n");
            } else {

                removeWordTextField.setText(removeWordLabelText);
                Actions act = new Actions(word, ActionType.REMOVE_WORD);
                requestAction(act);
            }
        });

        addMeaningButton.addActionListener(e -> {

            String word = currentWord;
            String meaning = addMeaningTextField.getText();
            if (meaning.equals(addMeaningLabelText) || meaning.isEmpty()) {

                consoleLog.append("Must add meaning to the current word!\n");
            } else if (word == null) {

                consoleLog.append("Select a new word to add meaning to it!\n");
            } else {

                addMeaningTextField.setText(addMeaningLabelText);
                Actions act = new Actions(word, meaning, ActionType.ADD_MEANING);
                requestAction(act);
            }
            addMeaningTextField.setText(addMeaningLabelText);
        });

        updateBtn.addActionListener(e -> {

            String word = currentWord;
            String prevMeaning = meaningToUpdateTextField.getText();
            String meaning =  updatedMeaningTextField.getText();

            if (meaning.equals(updatedMeaningLabelText) || meaning.isEmpty()) {

                consoleLog.append("Enter a new meaning!\n");
            } else if (prevMeaning.equals(meaningToUpdateLabelText) || prevMeaning.isEmpty()) {

                consoleLog.append("Enter a meaning to update!\n");
            } else if (word == null) {

                consoleLog.append("Select a new word to update a meaning!\n");
            } else {

                updatedMeaningTextField.setText(updatedMeaningLabelText);
                meaningToUpdateTextField.setText(meaningToUpdateLabelText);
                Actions act = new Actions(word, meaning, prevMeaning);
                requestAction(act);
            }
        });
        setContentPane(mainPanel);
        setTitle("Interactive Dictionary");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1800, 800);
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
    }

    public static void main(String[] args) {

        new DictionaryClient(args);
    }

    public void requestAction(Actions action) {

        try {

            request(action);
            System.out.println("Something is happening");
        } catch (UnknownHostException e) {

            consoleLog.append(e.getMessage() + " is an unknown host.  Try again!\n");
            addressLabel.setText(addressLabelText);
            portLabel.setText(portLabelText);
        } catch (IOException | NumberFormatException e) {

            consoleLog.append(e.getMessage() +  "\n");
            addressLabel.setText(addressLabelText);
            portLabel.setText(portLabelText);
        } finally {

            // Close the socket
            if (socket != null) {
                try {
                    socket.close();
                }
                catch (IOException e) {

                    consoleLog.append(e.getMessage() + "\n");
                    addressLabel.setText(addressLabelText);
                    portLabel.setText(portLabelText);
                }
            }
        }
    }

    /**
     * Client request
     * @param serverAction server action object
     * @throws UnknownHostException error connecting to host
     * @throws IOException input output error
     */
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

        switch (action) {               /* Prepare packet for TCP transmission */

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
                packet.put("meaning", serverAction.getMeaning());
                break;
            default:
                break;
        }

        // send packet over TCP
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

        try {               /* Check errors */

            Object err = response.get("error");
            consoleLog.append(err.toString() + "\n");
        } catch (JSONException e) {                 /* No error sent from server */

            System.out.println("No error");

            switch (action) {                   /* Deconstruct response to GUI changes */

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
                    consoleLog.append("Successfully removed word.\n");
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
     * Word actions to update GUI: Add word, Remove word, Get Dictionary
     * @param response server response
     */
    private void wordActions(JSONObject response) {

        Object wordList = response.get("word");
        try {
            String[] words = object2String(wordList);

            // display words on GUI
            wordTextArea.setText("");
            setWordTextArea(words);
        } catch (IllegalArgumentException e) {

            consoleLog.append(e.getMessage() + "\n");
        }
    }

    /**
     * Meaning actions to update GUI:
     * @param response server response
     */
    private void meaningActions(JSONObject response) {

        Object meaningList;
        Object wordJson;
        String word;
        String[] meanings;

        meaningList = response.get("meaning");
        wordJson = response.get("word");

        try {

            word = wordJson.toString();
            meanings = object2String(meaningList);

            meaningTextArea.setText("");
            setMeaningsTextArea(meanings, word);
        } catch (IllegalArgumentException e) {

            consoleLog.append(e.getMessage() + "\n");
        }
    }

    /**
     * Convert JSONArray object to a string array.
     * @param array deconstructed input JSONArray
     * @return string array including deconstructed JSON array items
     * @throws IllegalArgumentException JSONArray was not received
     */
    private String[] object2String(Object array) throws IllegalArgumentException {

        if (array instanceof JSONArray) {
            // Cast the Object to JSONArray
            JSONArray jsonArray = (JSONArray) array;

            // Initialize string array
            String[] stringArray = new String[jsonArray.length()];

            // Convert each element of JSONArray to String and store in the stringArray
            for (int i = 0; i < jsonArray.length(); i++) {
                stringArray[i] = jsonArray.getString(i);
            }

            return stringArray;
        } else {

            throw new IllegalArgumentException("Expected JSONArray but got " + array.getClass().getName());
        }
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

        currentWord = word;
        for (String meaning : meanings) {

            meaningTextArea.append(meaning + "\n");
        }

        currentWordLabel.setText(currentWordLabelText + " " + currentWord);
    }
}
