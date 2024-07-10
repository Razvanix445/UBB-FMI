/*
Un client trimite unui server un numar. Serverul va returna clientului 
un boolean care sa indice daca numarul respectiv este prim sau nu.
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

            System.out.println("Introduceti numarul: ");
            int n = Integer.parseInt(reader.readLine());

            socketOut.writeInt(n);
            socketOut.flush();

            int estePrim = socketIn.readInt();
            if (estePrim == 0)
                System.out.println("Numarul este prim.\n");
            else
                System.out.println("Numarul nu este prim.\n");
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
