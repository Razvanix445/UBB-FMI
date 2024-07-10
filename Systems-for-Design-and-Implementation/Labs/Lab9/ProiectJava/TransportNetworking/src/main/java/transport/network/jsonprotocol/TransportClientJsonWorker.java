package transport.network.jsonprotocol;

import com.google.gson.*;
import transport.model.Reservation;
import transport.model.ReservationManager;
import transport.model.Seat;
import transport.model.Trip;
import transport.services.ITransportObserver;
import transport.services.ITransportServices;
import transport.services.TransportException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransportClientJsonWorker implements Runnable, ITransportObserver {
    private ITransportServices server;
    private Socket connection;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private volatile boolean connected;

    public TransportClientJsonWorker(ITransportServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        gsonFormatter = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .create();
        try {
            output = new PrintWriter(connection.getOutputStream());
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (connected) {
            try {
                String requestLine = input.readLine();
                Request request = gsonFormatter.fromJson(requestLine, Request.class);
                Response response = handleRequest(request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException e) {
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
            e.printStackTrace();
        }
    }

    public void reservationAdded(Reservation reservation) throws TransportException {
        Response response = JsonProtocolUtils.createNewReservationResponse(reservation);
        System.out.println("Reservation added " + reservation);
        try {
            sendResponse(response);
        } catch (IOException e) {
            throw new TransportException("Sending error: " + e);
        }
    }

    private static Response okResponse = JsonProtocolUtils.createOkResponse();

    private Response handleRequest(Request request) {
        //request.setType(RequestType.values()[request.getOrdinalType()]);
        Response response = null;
        if (request.getType() == RequestType.LOGIN) {
            System.out.println("Login request..." + request.getType());
            ReservationManager reservationManager = request.getReservationManager();
            try {
                server.login(reservationManager, this);
                return okResponse;
            } catch (TransportException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.LOGOUT) {
            System.out.println("Logout request..." + request.getType());
            ReservationManager reservationManager = request.getReservationManager();
            try {
                server.logout(reservationManager, this);
                connected = false;
                return okResponse;
            } catch (TransportException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_ALL_TRIPS) {
            System.out.println("Get all trips request..." + request.getType());
            try {
                Trip[] trips = server.getAllTrips();
                System.out.println("Got all trips from Worker HandleRequest: ");
                for (Trip trip: trips)
                    System.out.println(trip);
                return JsonProtocolUtils.createGetAllTripsResponse(trips);
            } catch (TransportException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_RESERVATIONS_BY_TRIP) {
            System.out.println("Get reservations by trip request..." + request.getType());
            if (request.getTrip() == null)
                return JsonProtocolUtils.createErrorResponse("Trip in request is null");
            try {
                List<Reservation> reservations = server.getReservationsByTrip(request.getTrip());
                for (Reservation reservation: reservations)
                    System.out.println(reservation);
                return JsonProtocolUtils.createGetReservationsByTripResponse(reservations);
            } catch (TransportException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_SEATS_BY_TRIP) {
            System.out.println("Get seats by trip request..." + request.getType());
            if (request.getTrip() == null)
                return JsonProtocolUtils.createErrorResponse("Trip in request is null");
            try {
                List<Seat> seats = server.getSeatsByTrip(request.getTrip());
                for (Seat seat: seats)
                    System.out.println(seat);
                return JsonProtocolUtils.createGetSeatsByTripResponse(seats);
            } catch (TransportException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.SEARCH_TRIP_BY_ID) {
            System.out.println("Search trip by id request..." + request.getType());
            if (request.getLong() == null)
                return JsonProtocolUtils.createErrorResponse("Trip id in request is null");
            try {
                Trip trip = server.searchTripById(request.getLong());
                return JsonProtocolUtils.createSearchTripByIdResponse(trip);
            } catch (TransportException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.NEW_RESERVATION) {
            System.out.println("Add reservation request..." + request.getType());
            Reservation reservation = (Reservation) request.getReservation();
            try {
                server.addReservation(reservation);
                return okResponse;
            } catch (TransportException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        //response.setOrdinalType(response.getType().ordinal());
        return response;
    }

    private void sendResponse(Response response) throws IOException {
        String responseLine = gsonFormatter.toJson(response);
        System.out.println("Sending response " + responseLine);
        synchronized (output) {
            output.println(responseLine);
            output.flush();
        }
    }
}