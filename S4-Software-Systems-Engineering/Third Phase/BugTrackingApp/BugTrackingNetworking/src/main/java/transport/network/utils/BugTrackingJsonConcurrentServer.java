package transport.network.utils;

import bugtracking.services.IBugTrackingServices;
import transport.network.jsonprotocol.BugTrackingClientJsonWorker;

import java.net.Socket;

public class BugTrackingJsonConcurrentServer extends AbsConcurrentServer {
    private IBugTrackingServices bugTrackingServer;
    public BugTrackingJsonConcurrentServer(int port, IBugTrackingServices bugTrackingServer) {
        super(port);
        this.bugTrackingServer = bugTrackingServer;
        System.out.println("BugTracking - BugTrackingJsonConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        BugTrackingClientJsonWorker worker = new BugTrackingClientJsonWorker(bugTrackingServer, client);

        Thread tw = new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}