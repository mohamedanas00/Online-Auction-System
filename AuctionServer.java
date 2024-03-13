import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AuctionServer {
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            int port = 6666;
            System.out.println("Multi Threaded Server started...");
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(socket);
                    clients.add(clientHandler);
                    Thread t = new Thread(clientHandler);
                    t.start();
                    System.out.println("Thread #" + t.getId());
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    public static void notifyOtherClients(ClientHandler sender, String bid) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                try {
                    BufferedWriter writer = client.getWriter();
                    writer.write("New bid: " + bid);
                    writer.newLine();
                    writer.flush();
                } catch (IOException e) {
                    System.out.println("Failed to notify client: " + e.getMessage());
                }
            }
        }
    }
}
