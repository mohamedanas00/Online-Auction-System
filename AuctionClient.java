import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class AuctionClient {
    private String IP;
    private int port;

    public AuctionClient(String IP, int port) {
        this.IP = IP;
        this.port = port;
    }

    public void runClient() {
        try {
            Socket socket = new Socket(IP, port);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            // Start a separate thread to listen for server messages
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println("Server Notify you" + message);
                    }
                } catch (Exception e) {
                    System.out.println("Error receiving message from server: " + e.getMessage());
                }
            });
            receiveThread.start();

            String bidding;
            do {
                System.out.println("Enter Your Bid (type 'end' to finish): ");
                bidding = scanner.nextLine();
                writer.write(bidding);
                writer.newLine();
                writer.flush();
            } while (!bidding.equals("end"));

            receiveThread.join(); // Wait for receiving thread to finish
            reader.close();
            writer.close();
            socket.close();
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        AuctionClient client = new AuctionClient("localhost", 6666);
        client.runClient();
    }
}
