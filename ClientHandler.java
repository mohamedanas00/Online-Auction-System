import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedWriter writer;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String bidding;
            while ((bidding = reader.readLine()) != null && !bidding.equals("end")) {
                // Process bidding if needed
                AuctionServer.notifyOtherClients(this, bidding);
            }

            // Close resources
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }
}
