package app.network.utils;

import app.network.jsonprotocol.ClientJsonWorker;
import app.services.IServices;

import java.net.Socket;

public class JsonConcurrentServer extends AbsConcurrentServer {
    private IServices server;
    public JsonConcurrentServer(int port, IServices server) {
        super(port);
        this.server = server;
        System.out.println("App - ConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientJsonWorker worker = new ClientJsonWorker(server, client);

        Thread tw = new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
