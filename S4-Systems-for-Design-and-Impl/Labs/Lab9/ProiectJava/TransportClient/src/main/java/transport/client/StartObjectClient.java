package transport.client;

import transport.services.ITransportServices;

import java.io.IOException;
import java.util.Properties;

public class StartObjectClient {
    private static int defaultChatPort=55555;
    private static String defaultServer="localhost";
    public static void main(String[] args) {
        Properties clientProps=new Properties();
        try {
            clientProps.load(StartObjectClient.class.getResourceAsStream("/transportclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find transportclient.properties "+e);
            return;
        }
        String serverIP=clientProps.getProperty("transport.server.host",defaultServer);
        int serverPort=defaultChatPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("transport.server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultChatPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);

        System.out.println("Not implemented for object variant...");
        //ITransportServices server = new TransportServicesObjectProxy(serverIP, serverPort);
        //TransportClientCtrl ctrl = new TransportClientCtrl(server);


//        LoginWindow logWin=new LoginWindow("Chat XYZ", ctrl);
//        logWin.setSize(200,200);
//        logWin.setLocation(150,150);
//        logWin.setVisible(true);

    }
}
