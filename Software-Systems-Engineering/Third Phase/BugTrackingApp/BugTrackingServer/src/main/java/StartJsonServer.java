import bugtracking.persistence.database.BugDBRepository;
import bugtracking.persistence.database.ProgrammerDBRepository;
import bugtracking.persistence.database.TesterDBRepository;
import bugtracking.persistence.interfaces.IBugRepository;
import bugtracking.persistence.interfaces.IProgrammerRepository;
import bugtracking.persistence.interfaces.ITesterRepository;
import bugtracking.server.BugTrackingServicesImpl;
import bugtracking.services.IBugTrackingServices;
import transport.network.utils.AbstractServer;
import transport.network.utils.BugTrackingJsonConcurrentServer;
import transport.network.utils.ServerException;

import java.io.IOException;
import java.util.Properties;

public class StartJsonServer {
    private static int defaultPort = 55555;
    public static void main(String[] args) {
        Properties serverProps = new Properties();
        try {
            serverProps.load(StartJsonServer.class.getResourceAsStream("/bugtrackingserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find bugtrackingserver.properties "+e);
            return;
        }

//        String url = "jdbc:postgresql://localhost:1234/BugTracking";
//        String username = "postgres";
//        String password = "2003razvi";

        ITesterRepository testerRepository = new TesterDBRepository();
        IProgrammerRepository programmerRepository = new ProgrammerDBRepository();
        IBugRepository bugRepository = new BugDBRepository();
        IBugTrackingServices bugTrackingServerImpl = new BugTrackingServicesImpl(testerRepository, programmerRepository, bugRepository);
        int bugTrackingServerPort = defaultPort;
        try {
            bugTrackingServerPort = Integer.parseInt(serverProps.getProperty("bugtracking.server.port"));
        } catch (NumberFormatException nef){
            System.err.println("Wrong Port Number " + nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }
        System.out.println("Starting server on port: " + bugTrackingServerPort);
        AbstractServer server = new BugTrackingJsonConcurrentServer(bugTrackingServerPort, bugTrackingServerImpl);
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
