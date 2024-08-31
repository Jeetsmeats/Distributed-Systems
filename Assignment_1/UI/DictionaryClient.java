package UI;

// imports
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
    private JLabel serverConnectionLabel;
    private JLabel portLabel;
    private JTable dictionaryTable;
    private JLabel dictionaryLabel;
    private JLabel actionLabel;
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
    private static String address;

    /**
     * Server Port
     */
    private static int port;

    /**
     * Socket connection object.
     */
    private static Socket socket;

    /**
     * Buffered Data Reader for JSON data into the client.
     */
    private static BufferedReader in;

    /**
     * Buffered Data Writer for JSON data to the server.
     */
    private static BufferedWriter out;

    public DictionaryClient() {

        setContentPane(mainPanel);
        setTitle("Interactive Dictionary");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new DictionaryClient();

        try {

            setUpClient2Server(args);
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

    /**
     * Set up the client - server connection.
     * @param args initial arguments for server setup.
     * @throws UnknownHostException undetermined host ip.
     * @throws IOException failed IO stream set up.
     */
    public static void setUpClient2Server(String[] args) throws UnknownHostException, IOException {

        port = Integer.parseInt(args[1]);
        address = args[0];

        // Create a stream socket bounded to any port and connect it to the
        socket = new Socket(address, port);
        System.out.println("Connection established");

        // Set the IO stream for client - server communication.
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
    }

//    public static void sendTask()
}
