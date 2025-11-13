import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A simple multi-threaded chat server.
 * When a client connects, it spawns a new thread to handle them.
 *
 * @author Darshil
 * @version 1.0
 */
public class ChatServer {
    // Port number for the server
    private static final int PORT = 12345;
    // Set of all print writers for connected clients, used to broadcast messages.
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    /**
     * The main method that runs the chat server.
     *
     * @param args Command-line arguments (not used).
     * @throws Exception If an error occurs.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running on port " + PORT);
        // Create a new server socket
        ServerSocket listener = new ServerSocket(PORT);

        try {
            while (true) {
                // Wait for a client to connect and create a new thread for them
                new Handler(listener.accept()).start();
            }
        } finally {
            // Close the server socket
            listener.close();
        }
    }

    /**
     * A handler thread class. Handlers are spawned from the listening loop and
     * are responsible for a single client's communication.
     */
    private static class Handler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        /**
         * Constructor for the Handler class.
         *
         * @param socket The client socket.
         */
        public Handler(Socket socket) {
            this.socket = socket;
        }

        /**
         * The main method for the handler thread.
         */
        public void run() {
            try {
                // Initialize input and output streams for the client socket
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Add the client's print writer to the set of all writers so they can receive
                // messages.
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                // Accept messages from this client and broadcast them.
                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        return; // Client disconnected
                    }
                    // Broadcast the received message to all other clients
                    for (PrintWriter writer : clientWriters) {
                        writer.println("MESSAGE " + input);
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                // This client is going down! Remove its print writer and close its socket.
                if (out != null) {
                    synchronized (clientWriters) {
                        clientWriters.remove(out);
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }
}
