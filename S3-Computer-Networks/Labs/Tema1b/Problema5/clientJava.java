/*
Un client trimite unui server un numar. Serverul va returna clientului sirul divizorilor acestui numar.
*/

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class clientJava {

    public static void main(String args[]) {
        String SERVER_ADDRESS = args[1];
        int SERVER_PORT = Integer.parseInt(args[0]);
        Socket socket = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            DataInputStream socketIn = new DataInputStream(socket.getInputStream());
            DataOutputStream socketOut = new DataOutputStream(socket.getOutputStream());

            System.out.print("Introduceti numarul: ");
            int numar = Integer.parseInt(reader.readLine());

            socketOut.writeInt(numar);

            int suma = socketIn.readInt();
            System.out.println("Suma divizorilor este: " + suma);
            
        } catch (IOException e) {
            System.err.println("Caught exception " + e.getMessage());
        } finally {
            closeStreams(socket, reader);
        }
    }

    private static void closeStreams(Socket socket, BufferedReader reader) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Could not close socket!");
            }
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("Could not close reader!");
            }
        }
    }
}