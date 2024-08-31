// imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ServerSocketFactory;
import org.json.*;

import DataStructures.Dictionary;
import ThreadPool.ThreadPool;
import org.stjs.javascript.JSON;

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

        try (ServerSocket server = factory.createServerSocket(port)) {

            // Enter port
            port = Integer.parseInt(args[0]);

            // Client-server superloop
            while (true) {

                Socket client = server.accept();

                // add task to queue for execution
                threadPool.addTask(() -> createTask(client));
            }
        } catch (NumberFormatException e) {

            System.out.println("Input port is NAN: " + args[0]);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private static void createTask(Socket client) {
        try(Socket clientSocket = client)
        {
            // Input stream
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());


        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}