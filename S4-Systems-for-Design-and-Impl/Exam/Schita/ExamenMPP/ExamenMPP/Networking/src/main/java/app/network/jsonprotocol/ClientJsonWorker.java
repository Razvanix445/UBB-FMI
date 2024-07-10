package app.network.jsonprotocol;

import com.google.gson.*;
import app.services.IObserver;
import app.services.IServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientJsonWorker implements Runnable, IObserver {

    private IServices server;
    private Socket connection;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private volatile boolean connected;

    public ClientJsonWorker(IServices server, Socket connection) {
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

    // TODO 2: ADD OBSERVER METHODS IMPLEMENTATIONS

    private static Response okResponse = JsonProtocolUtils.createOkResponse();

    private Response handleRequest(Request request) {
        Response response = null;
//        if (request.getType() == RequestType.LOGIN) {
//            System.out.println("Login request..." + request.getType());
//            Something something = request.getSomething();
//            try {
//                server.login(something, this);
//                return okResponse;
//            } catch (AppException e) {
//                return JsonProtocolUtils.createErrorResponse(e.getMessage());
//            }
//        }
//        if (request.getType() == RequestType.LOGOUT) {
//            System.out.println("Logout request..." + request.getType());
//            Something something = request.getSomething();
//            try {
//                server.logout(something, this);
//                connected = false;
//                return okResponse;
//            } catch (AppException e) {
//                return JsonProtocolUtils.createErrorResponse(e.getMessage());
//            }
//        }
        // TODO 3: ADD OTHER REQUESTS HANDLING FROM SERVICES
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
