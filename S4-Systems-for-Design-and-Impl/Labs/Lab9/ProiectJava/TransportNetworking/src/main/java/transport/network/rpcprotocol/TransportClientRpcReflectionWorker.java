package transport.network.rpcprotocol;

import transport.model.Reservation;
import transport.model.ReservationManager;
import transport.model.Trip;
import transport.services.ITransportObserver;
import transport.services.ITransportServices;
import transport.services.TransportException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.sql.SQLOutput;

public class TransportClientRpcReflectionWorker implements Runnable, ITransportObserver {
    private ITransportServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public TransportClientRpcReflectionWorker(ITransportServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    public void reservationAdded(Reservation reservation) {
        Response resp = new Response.Builder().type(ResponseType.NEW_RESERVATION).data(reservation).build();
        System.out.println("Reservation added: " + reservation);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).type();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login Request ... " + request.type());
        ReservationManager reservationManager = (ReservationManager)request.data();
        try {
            server.login(reservationManager, this);
            return okResponse;
        } catch (TransportException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleSEARCH_RESERVATION_MANAGER_BY_NAME(Request request){
        System.out.println("Search Reservation Manager By Name Request ...");
        String name=(String)request.data();
        try {
            return new Response.Builder().type(ResponseType.SEARCH_RESERVATION_MANAGER_BY_NAME).data(server.searchReservationManagerByName(name)).build();
        } catch (TransportException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_ALL_TRIPS(Request request){
        System.out.println("Get All Trips Request ...");
        try {
            return new Response.Builder().type(ResponseType.GET_ALL_TRIPS).data(server.getAllTrips()).build();
        } catch (TransportException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleSEARCH_TRIP_BY_ID(Request request) {
        System.out.println("Search Trip By Id Request ...");
        Long id = (Long) request.data();
        try {
            return new Response.Builder().type(ResponseType.SEARCH_TRIP_BY_ID).data(server.searchTripById(id)).build();
        } catch (TransportException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleADD_RESERVATION(Request request){
        System.out.println("Add Reservation Request ...");
        Reservation reservation = (Reservation)request.data();
        new Thread(() -> {
            try {
                server.addReservation(reservation);
            } catch (TransportException e) {
                e.printStackTrace();
            }
        }).start();
        return okResponse;
    }

    private Response handleGET_RESERVATIONS_BY_TRIP(Request request){
        System.out.println("Get Reservations By Trip Request ...");
        Trip trip = (Trip)request.data();
        try {
            return new Response.Builder().type(ResponseType.GET_RESERVATIONS_BY_TRIP).data(server.getReservationsByTrip(trip)).build();
        } catch (TransportException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_SEATS_BY_RESERVATION(Request request){
        System.out.println("Get Seats By Reservation Request ...");
        Long reservationId = (Long)request.data();
        try {
            return new Response.Builder().type(ResponseType.GET_SEATS_BY_RESERVATION).data(server.getSeatsByReservation(reservationId)).build();
        } catch (TransportException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_SEATS_BY_TRIP(Request request) {
        System.out.println("Get Seats By Trip Request ...");
        Trip trip = (Trip)request.data();
        try {
            return new Response.Builder().type(ResponseType.GET_SEATS_BY_TRIP).data(server.getSeatsByTrip(trip)).build();
        } catch (TransportException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request){
        System.out.println("Logout Request...");
        ReservationManager reservationManager=(ReservationManager)request.data();
        try {
            server.logout(reservationManager, this);
            connected=false;
            return okResponse;
        } catch (TransportException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }
}
