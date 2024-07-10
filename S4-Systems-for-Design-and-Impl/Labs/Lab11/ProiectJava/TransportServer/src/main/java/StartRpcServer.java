import transport.network.utils.AbstractServer;
import transport.network.utils.ServerException;
import transport.network.utils.TransportRpcConcurrentServer;
import transport.persistence.IReservationManagerRepository;
import transport.persistence.IReservationRepository;
import transport.persistence.ISeatRepository;
import transport.persistence.ITripRepository;
import transport.persistence.repository.*;
import transport.server.TransportServicesImpl;
import transport.services.ITransportServices;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort = 55555;
    public static void main(String[] args) {

        Properties serverProps = new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/transportserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find transportserver.properties "+e);
            return;
        }
        IReservationManagerRepository reservationManagerRepository = new ReservationManagerDBRepository(serverProps);
        IReservationRepository reservationRepository = new ReservationDBRepository(serverProps);
        ITripRepository tripRepository = new TripDBRepository(serverProps);
//        ITripRepository tripRepository = new TripDBRepositoryHibernate();
        ISeatRepository seatRepository = new SeatDBRepository(serverProps);
        ITransportServices transportServerImpl = new TransportServicesImpl(reservationManagerRepository, reservationRepository, tripRepository, seatRepository);
        int transportServerPort = defaultPort;
        try {
            transportServerPort = Integer.parseInt(serverProps.getProperty("transport.server.port"));
        } catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number" + nef.getMessage());
            System.err.println("Using default port "+ defaultPort);
        }
        System.out.println("Starting server on port: " + transportServerPort);
        AbstractServer server = new TransportRpcConcurrentServer(transportServerPort, transportServerImpl);
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