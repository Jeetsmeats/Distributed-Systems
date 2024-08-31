// Imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.io.IOException;
import ThreadPool.ThreadPool;

/**
 * Dictionary Server
 */
public class Server {

    public static void main(String[] args) {

        try {

            // Enter port
            int port = Integer.parseInt(args[0]);

            // Open socket
            ServerSocket server = new ServerSocket(port);
        } catch (NumberFormatException e) {

            System.out.println("Input port is NAN: " + args[0]);
        } catch (IOException e) {

            e.printStackTrace();
        }

//        try {
//
//            System.out.println("Awaiting socket connection...");
//
//            // Set up IO streams
////            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
////            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//
//            // Initialise Threadpool
//            ThreadPool threadPool = new ThreadPool(7,  4);
//
//            while (True) {
//
//                for (int i = 0; i < 10; i++) {
//
//                    threadPool.execute(() -> {
//
//
//                    });
//                }
//
//            }
//        } catch (SocketException e) {
//
//
//        } catch (IOException e) {
//
//
//        }

    }

//    private static void serveClient(Socket client)
//    {
//        try(Socket clientSocket = client)
//        {
//            // IO stream set up
//            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
//            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
//
//            System.out.println("CLIENT: "+input.readUTF());
//
//            output.writeUTF("Server: Hi Client "+counter+" !!!");
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//    }
}