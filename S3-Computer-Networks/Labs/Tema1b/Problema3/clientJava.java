/*
Un client trimite unui server un sir de caractere. 
Serverul va returna clientului acest sir oglindit (caracterele sirului in ordine inversa).
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

            System.out.print("Introduceti sirul de caractere: ");
            String sirDeCaractere = reader.readLine();

            socketOut.writeInt(sirDeCaractere.length());
            socketOut.writeBytes(sirDeCaractere);

            int lungimeOglindit = socketIn.readInt();

            byte[] oglinditBytes = new byte[lungimeOglindit];
            socketIn.readFully(oglinditBytes);
            String sirOglindit = new String(oglinditBytes);
            System.out.println("Sirul oglindit: " + sirOglindit);
            
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