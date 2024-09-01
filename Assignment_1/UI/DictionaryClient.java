package UI;

// imports
import Helper.ActionType;
import Helper.Actions;
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
    private JTable dictionaryTable;
    private JLabel dictionaryLabel;
    private JLabel logLabel;
    private JTextArea consoleLog;
    private JTextField serverAddressTextField;
    private JTextField serverPortTextField;
    private JButton connectBtn;
    private JLabel newConnectionLabel;
    private JButton addBtn;
    private JTextField addWordHereTextField;
    private JComboBox removeWordComboBox;
    private JButton removeBtn;
    private JTextPane meaningTextPane;
    private JLabel meaningLabel;
    private JLabel searchMeaningLabel;
    private JTextField searchWordTextField;
    private JComboBox updateMeaningComboBox;
    private JLabel updateMeaningLabel;
    private JTextField updateMeaningTextField;
    private JButton updateBtn;
    private JTextField addMeaningTextField;
    private JButton addMeaningButton;
    private JButton searchWordButton;
    private JLabel addMeaningLabel;
    private JLabel addWordLabel;
    private JLabel removeWordLabel;

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

        try {

            // server parameters
            port = Integer.parseInt(args[1]);
            address = args[0];

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
        while(!successRes) {

            if (in.ready()) {           /* Response received */
                String res = in.readLine();
                System.out.println(res);
                successRes = true;
            }
        }

        // Await server response
        String response = in.readLine();  // Read server response
        if (response != null) {
            System.out.println("Server response: " + response);
        } else {
            System.out.println("No response received from server.");
        }

        // Close resources
        in.close();
        out.close();
    }
}
