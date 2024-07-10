/*
Un client trimite unui server un sir de caractere si un caracter.
Serverul va returna clientului toate pozitiile
pe care caracterul primit se regaseste in sir.
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
            String inputString = reader.readLine();
            System.out.print("Introduceti caracterul: ");
            char ch = reader.readLine().charAt(0);

            String combinedString = inputString + ch;

            socketOut.writeInt(combinedString.length());
            socketOut.writeBytes(combinedString);
            System.out.println("Sirul cu caracterul: " + combinedString);

            System.out.println("Se citeste numarul de pozitii: ");
            int numarPozitii = socketIn.readInt();
            System.out.println("Numarul de pozitii de la server: " + numarPozitii);

            System.out.print("Pozitii: ");
            if (numarPozitii > 0) {
                int pozitie;
                for (int i = 0; i < numarPozitii; i++) {
                    pozitie = socketIn.readInt();
                    System.out.print(pozitie + " ");
                }

                System.out.println();
                //System.out.println(Arrays.toString(pozitii));
            } else {
                System.out.println("Caracterul nu a fost gasit in sir!");
            }
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