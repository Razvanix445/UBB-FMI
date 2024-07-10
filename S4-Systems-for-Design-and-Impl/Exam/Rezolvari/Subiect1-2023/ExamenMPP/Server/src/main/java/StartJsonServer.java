import games.persistence.repository.ConfigurationDBRepository;
import games.persistence.repository.GameDBRepositoryHibernate;
import games.persistence.repository.PlayerDBRepository;
import games.persistence.repository.PositionDBRepository;
import games.network.utils.AbstractServer;
import games.network.utils.ServerException;
import games.network.utils.JsonConcurrentServer;
import games.persistence.IConfigurationRepository;
import games.persistence.IGameRepository;
import games.persistence.IPlayerRepository;
import games.persistence.IPositionRepository;
import games.server.ServicesImpl;
import games.services.IServices;

import java.io.IOException;
import java.util.Properties;

public class StartJsonServer {
    private static int defaultPort = 55555;
    public static void main(String[] args) {

        Properties serverProps = new Properties();
        try {
            serverProps.load(StartJsonServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }
        IGameRepository gameRepository = new GameDBRepositoryHibernate();
        IConfigurationRepository configurationRepository = new ConfigurationDBRepository(serverProps);
        IPositionRepository positionRepository = new PositionDBRepository(serverProps);
        IPlayerRepository playerRepository = new PlayerDBRepository(serverProps);
        IServices serverImpl = new ServicesImpl(playerRepository, gameRepository, configurationRepository, positionRepository);
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