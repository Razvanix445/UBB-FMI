import app.persistence.IConfigurationRepository;
import app.persistence.IGameRepository;
import app.persistence.IPlayerRepository;
import app.persistence.repository.ConfigurationDBRepository;
import app.persistence.repository.GameDBRepository;
import app.persistence.repository.PlayerDBRepository;
import app.server.ServicesImpl;
import app.network.utils.AbstractServer;
import app.network.utils.ServerException;
import app.network.utils.JsonConcurrentServer;
// TODO 1: IMPORT USED REPOSITORIES
import app.services.IServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class StartJsonServer {

    private static final Logger logger = LogManager.getLogger(StartJsonServer.class);

    private static int defaultPort = 55555;
    public static void main(String[] args) {

        logger.info("Server starting ...");

        Properties serverProps = new Properties();
        try {
            serverProps.load(StartJsonServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }
        IPlayerRepository playerRepository = new PlayerDBRepository();
        IGameRepository gameRepository = new GameDBRepository();
        IConfigurationRepository configurationRepository = new ConfigurationDBRepository();
        IServices serverImpl = new ServicesImpl(playerRepository, gameRepository, configurationRepository);
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        } catch (NumberFormatException nef){
            System.err.println("Wrong Port Number " + nef.getMessage());
            System.err.println("Using default port "+ defaultPort);
        }
        System.out.println("Starting server on port: " + serverPort);
        AbstractServer server = new JsonConcurrentServer(serverPort, serverImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        } finally {
            try {
                server.stop();
            } catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}