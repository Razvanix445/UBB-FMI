package app.client;

import app.client.gui.LoginController;
import app.client.gui.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import app.network.jsonprotocol.ServicesJsonProxy;
import app.services.IServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class StartJsonClient extends Application {
    private static final Logger logger = LogManager.getLogger(StartJsonClient.class);
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";


    public void start(Stage primaryStage) throws Exception {
        logger.info("In start");
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartJsonClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IServices server = new ServicesJsonProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/login-view.fxml"));
        Parent root=loader.load();


        LoginController ctrl =
                loader.<LoginController>getController();
        ctrl.setServer(server);


        FXMLLoader cloader = new FXMLLoader(
                getClass().getResource("/views/main-view.fxml"));
        Parent croot=cloader.load();


        MainController mainController =
                cloader.<MainController>getController();
        mainController.setServer(server);

        ctrl.setMainController(mainController);
        ctrl.setParent(croot);

        primaryStage.setTitle("Exam app");
        primaryStage.setScene(new Scene(root, 700, 350));
        primaryStage.show();
    }
}