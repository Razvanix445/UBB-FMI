package transport.network.utils;

import transport.network.rpcprotocol.TransportClientRpcReflectionWorker;
import transport.services.ITransportServices;

import java.net.Socket;

public class TransportRpcConcurrentServer extends AbsConcurrentServer {
    private ITransportServices transportServer;
    public TransportRpcConcurrentServer(int port, ITransportServices transportServer) {
        super(port);
        this.transportServer = transportServer;
        System.out.println("Transport - TransportRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        TransportClientRpcReflectionWorker worker = new TransportClientRpcReflectionWorker(transportServer, client);

        Thread tw = new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}