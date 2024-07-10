/*
Un client trimite unui server un sir de caractere. 
Serverul va returna clientului acest sir oglindit (caracterele sirului in ordine inversa).

NU FUNCTIONEAZA CORESPUNZATOR!!! (NU AFISEAZA CE TREBUIE...)
*/

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

                int lungimeSir = socketIn.readInt();

                byte[] sirBytes = new byte[lungimeSir];
                socketIn.readFully(sirBytes);
                String sirDeCaractere = new String(sirBytes);
                System.out.println("Sirul: " + sirDeCaractere);

                StringBuilder oglinditBuilder = new StringBuilder();
                for (int i = lungimeSir - 1; i >= 0; i --) {
                    oglinditBuilder.append(sirDeCaractere.charAt(i));
                }
                String sirOglindit = oglinditBuilder.toString();

                int lungimeOglindit = sirOglindit.length();
                socketOut.writeInt(lungimeOglindit);
                System.out.println("Am trimis lungimea oglinditului: " + lungimeOglindit);
                socketOut.writeUTF(sirOglindit);
                System.out.println("Am trimis sirul oglindit: " + sirOglindit);
                
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
