package transport.network.rpcprotocol;

import transport.model.Reservation;
import transport.model.ReservationManager;
import transport.model.Seat;
import transport.model.Trip;
import transport.services.ITransportObserver;
import transport.services.ITransportServices;
import transport.services.TransportException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TransportServicesRpcProxy implements ITransportServices {
    private String host;
    private int port;

    private ITransportObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public TransportServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    public ReservationManager login(ReservationManager reservationManager, ITransportObserver client) throws TransportException {
        initializeConnection();
        Request req = new Request.Builder().type(RequestType.LOGIN).data(reservationManager).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new TransportException(err);
        }
        return (ReservationManager) response.data();
    }

    public ReservationManager searchReservationManagerByName(String name) throws TransportException {
        Request req = new Request.Builder().type(RequestType.SEARCH_RESERVATION_MANAGER_BY_NAME).data(name).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR){
            String err=response.data().toString();
            throw new TransportException(err);
        }
        return (ReservationManager) response.data();
    }

    public Trip[] getAllTrips() throws TransportException {
        Request req = new Request.Builder().type(RequestType.GET_ALL_TRIPS).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR){
            String err=response.data().toString();
            throw new TransportException(err);
        }
        //if (response.data() instanceof ArrayList){
        System.out.println("Response data: " + response.data().getClass());
        Trip[] trips = (Trip[]) response.data();
        return trips;
    }

    public Trip searchTripById(Long id) throws TransportException {
        Request req = new Request.Builder().type(RequestType.SEARCH_TRIP_BY_ID).data(id).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR){
            String err=response.data().toString();
            throw new TransportException(err);
        }
        return (Trip) response.data();
    }

    public Reservation addReservation(Reservation reservation) throws TransportException {
        Request req = new Request.Builder().type(RequestType.ADD_RESERVATION).data(reservation).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new TransportException(err);
        }
        return (Reservation) response.data();
    }

    public List<Reservation> getReservationsByTrip(Trip trip) throws TransportException {
        Request req = new Request.Builder().type(RequestType.GET_RESERVATIONS_BY_TRIP).data(trip).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            throw new TransportException(err);
        }
        if (response.data() instanceof List){
            List<Reservation> reservations = (List<Reservation>) response.data();
            return reservations;
        } else {
            throw new TransportException("Unexpected data type in getReservationsByTrip() method: " + response.data().getClass());
        }
    }

    public List<Seat> getSeatsByReservation(Long reservationId) throws TransportException {
        Request req = new Request.Builder().type(RequestType.GET_SEATS_BY_RESERVATION).data(reservationId).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new TransportException(err);
        }
        if (response.data() instanceof List) {
            List<Seat> seats = (List<Seat>) response.data();
            return seats;
        } else {
            throw new TransportException("Unexpected data type in getSeatsByReservation() method: " + response.data().getClass());
        }
    }

    public List<Seat> getSeatsByTrip(Trip trip) throws TransportException {
        Request req = new Request.Builder().type(RequestType.GET_SEATS_BY_TRIP).data(trip).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new TransportException(err);
        }
        if (response.data() instanceof List) {
            List<Seat> seats = (List<Seat>) response.data();
            return seats;
        } else {
            throw new TransportException("Unexpected data type in getSeatsByReservation() method: " + response.data().getClass());
        }
    }

    public ReservationManager logout(ReservationManager reservationManager, ITransportObserver client) throws TransportException {
        Request req = new Request.Builder().type(RequestType.LOGOUT).data(reservationManager).build();
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if (response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            throw new TransportException(err);
        }
        return (ReservationManager) response.data();
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(Request request) throws TransportException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new TransportException("Error sending object "+e);
        }

    }

    private Response readResponse() throws TransportException {
        Response response=null;
        try{
            response=qresponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws TransportException {
        try {
            System.out.println("Connecting to server on port " + port + " and host " + host);
            connection=new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            //throw new TransportException("Error connecting to server: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response){
        if (response.type()== ResponseType.NEW_RESERVATION){
            Reservation message= (Reservation) response.data();
            try {
                client.reservationAdded(message);
            } catch (TransportException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response){
        return response.type()== ResponseType.NEW_RESERVATION;
    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
