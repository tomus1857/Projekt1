import java.io.*;
import java.net.*;

public class Client {
    private Socket socket = null;
    private BufferedReader inputConsole = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to the chat server");
            inputConsole = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = "";
            while (!line.equals("exit")) {
                line = inputConsole.readLine();
                out.println(line);
                System.out.println(in.readLine());
            }
            socket.close();
            inputConsole.close();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 2000);
    }
}
