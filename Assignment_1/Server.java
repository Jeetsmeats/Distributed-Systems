// Imports
import java.net.*;
import java.io.IOException;

/**
 * Dictionary Server
 */
public class Server {

    public static void main(String[] args) {

        try {

            // Enter port
            int port = Integer.parseInt(args[0]);

            // Open socket
            ServerSocket server = new ServerSocket(port)
        } catch (NumberFormatException e) {

            System.out.println("Input port is NAN: " + args[0])
        }

        try {

            System.out.println("Awaiting socket connection...);

            while (True) {

            }
        } catch (SocketException e) {


        } catch (IOException e) {


        }

    }

}