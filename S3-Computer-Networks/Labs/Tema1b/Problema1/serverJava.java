import java.net.*;
import java.io.*;

public class serverJava {

    public static void main(String args[]) throws Exception {
        ServerSocket s = new ServerSocket(53298);

        while(true) {
            Socket c = s.accept();
            System.out.println("S-a conectat un client!");

            ClientHandler clientHandler = new ClientHandler(c);
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket c;

        public ClientHandler(Socket socket) {
            this.c = socket;
        }

        @Override
        public void run() {
            try {
                DataInputStream socketIn = new DataInputStream(c.getInputStream());
                DataOutputStream socketOut = new DataOutputStream(c.getOutputStream());
                
                int a = socketIn.readInt();
                int b = socketIn.readInt();

                int suma = a + b;
                socketOut.writeInt(suma);

            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    c.close();
                } catch (IOException e) {
                    System.err.println("Could not close client socket!");
                }
            }
        }
    }
}
