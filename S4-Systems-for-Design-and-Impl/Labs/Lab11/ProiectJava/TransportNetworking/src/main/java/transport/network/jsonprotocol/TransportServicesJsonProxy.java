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
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TransportServicesJsonProxy implements ITransportServices {
    private String host;
    private int port;

    private ITransportObserver client;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public TransportServicesJsonProxy(String host, int port) {
        this.host = host;
        this.port = port;
        gsonFormatter = new GsonBuilder()
//                .registerTypeAdapter(RequestType.class, new EnumOrdinalTypeAdapter<>(RequestType.class))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .create();
        qresponses = new LinkedBlockingQueue<Response>();
    }

    public ReservationManager login(ReservationManager reservationManager, ITransportObserver client) throws TransportException {
        initializeConnection();

        Request req = JsonProtocolUtils.createLoginRequest(reservationManager);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.OK) {
            this.client = client;
        }
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            closeConnection();
            throw new TransportException(err);
        }
        return response.getReservationManager();
    }

    public Reservation addReservation(Reservation reservation) throws TransportException {
        Request req = JsonProtocolUtils.createAddReservationRequest(reservation);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new TransportException(err);
        }
        return response.getReservation();
    }

    public ReservationManager logout(ReservationManager reservationManager, ITransportObserver client) throws TransportException {
        Request req = JsonProtocolUtils.createLogoutRequest(reservationManager);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new TransportException(err);
        }
        return response.getReservationManager();
    }

    public Trip[] getAllTrips() throws TransportException {
        Request req = JsonProtocolUtils.createGetAllTripsRequest();
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new TransportException(err);
        }
        return response.getTrips();
    }

    public Trip searchTripById(Long id) throws TransportException {
        Request req = JsonProtocolUtils.createSearchTripByIdRequest(id);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new TransportException(err);
        }
        return response.getTrip();
    }

    public ReservationManager searchReservationManagerByName(String name) throws TransportException {
        Request req = JsonProtocolUtils.createSearchReservationManagerByNameRequest(name);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new TransportException(err);
        }
        return response.getReservationManager();
    }

    public List<Reservation> getReservationsByTrip(Trip trip) throws TransportException {
        Request req = JsonProtocolUtils.createGetReservationsByTripRequest(trip);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new TransportException(err);
        }
        return response.getReservations();
    }

    public List<Seat> getSeatsByReservation(Long reservationId) throws TransportException {
        Request req = JsonProtocolUtils.createGetSeatsByReservationRequest(reservationId);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new TransportException(err);
        }
        return response.getSeats();
    }

    public List<Seat> getSeatsByTrip(Trip trip) throws TransportException {
        Request req = JsonProtocolUtils.createGetSeatsByTripRequest(trip);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new TransportException(err);
        }
        return response.getSeats();
    }

    public void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    private void sendRequest(Request request) throws TransportException {
        String requestJson = gsonFormatter.toJson(request);
        System.out.println("Sending request " + requestJson);
        try {
            output.println(requestJson);
            output.flush();
        } catch (Exception e) {
            throw new TransportException("Error sending object " + e);
        }
    }

    private Response readResponse() throws TransportException {
        Response response = null;
        try {
            response = qresponses.take();
        } catch (InterruptedException e) {
            throw new TransportException("Error reading response " + e);
        }
        if (response == null) {
            throw new TransportException("Received null response from server.");
        }
        return response;
    }

    private void initializeConnection() throws TransportException {
        try {
            gsonFormatter = new GsonBuilder()
//                    .registerTypeAdapter(RequestType.class, new EnumOrdinalTypeAdapter<>(RequestType.class))
                    .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                    .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .create();
            connection = new Socket(host, port);
            output = new PrintWriter(connection.getOutputStream());
            output.flush();
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            finished = false;
            startReader();
        } catch (IOException e) {
            throw new TransportException("Error initializing connection " + e);
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response) {
        if (response.getType() == ResponseType.NEW_RESERVATION) {
            Reservation reservation = response.getReservation();
            try {
                client.reservationAdded(reservation);
            } catch (TransportException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response) {
        return response.getType() == ResponseType.NEW_RESERVATION;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    String responseLine = input.readLine();
                    System.out.println("Received response " + responseLine);
                    Response response = gsonFormatter.fromJson(responseLine, Response.class);
                    if (response == null) {
                        System.out.println("Invalid response from the server (Proxy)!");
                        continue;
                    }
                    if (response.getTrip() != null) {
                        response.getTrip().getDateDeparture();
                    }
                    if (isUpdate(response)) {
                        handleUpdate(response);
                    } else {
                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}
