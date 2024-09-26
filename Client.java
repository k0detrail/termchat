import java.io.*;
import java.net.*;

public class Client {
    private static PrintWriter out;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) {
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            // Thread to handle incoming messages
            new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        // Print incoming messages
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Thread to handle user input
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                // Send the message to the server
                out.println(userInput);
                // Optionally, you could print something like "Message sent" if desired
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
