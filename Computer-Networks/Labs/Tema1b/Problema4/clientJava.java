/*
Un client trimite unui server doua siruri de caractere ordonate. 
Serverul va interclasa cele doua siruri si va returna clientului sirul rezultat interclasat.
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

            System.out.print("Introduceti primul sir de caractere: ");
            String sir1 = reader.readLine();
            System.out.print("Introduceti al doilea sir de caractere: ");
            String sir2 = reader.readLine();

            socketOut.writeInt(sir1.length());
            socketOut.writeBytes(sir1);
            socketOut.writeInt(sir2.length());
            socketOut.writeBytes(sir2);

            int lungimeSirInterclasat = socketIn.readInt();

            byte[] sirInterclasatBytes = new byte[lungimeSirInterclasat];
            socketIn.readFully(sirInterclasatBytes);
            String sirOglindit = new String(sirInterclasatBytes);
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