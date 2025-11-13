import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * A simple command-line chat client.
 *
 * @author Jules
 * @author Darshil
 * @version 1.0
 */
public class ChatClient {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    /**
     * The main method that runs the chat client.
     *
     * @param args Command-line arguments (not used).
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        // Create a new socket to connect to the server
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            // Create a thread to read messages from the server and print them to the
            // console
            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (true) {
                        String line = in.readLine();
                        if (line != null && line.startsWith("MESSAGE")) {
                            System.out.println(line.substring(8));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Create a PrintWriter to send messages to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // Create a Scanner to read input from the user
            try (Scanner scanner = new Scanner(System.in)) {
                // Prompt the user for a username
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                out.println(username);


            // Create a PrintWriter to send messages to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // Create a Scanner to read input from the user
            try (Scanner scanner = new Scanner(System.in)) {
                // Loop to read messages from the user and send them to the server
                while (scanner.hasNextLine()) {
                    out.println(scanner.nextLine());
                }
            }
        }
    }
}
