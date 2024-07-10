package games.network.jsonprotocol;

import com.google.gson.*;
import games.model.Configuration;
import games.model.Game;
import games.model.Player;
import games.model.Position;
import games.services.AppException;
import games.services.IObserver;
import games.services.IServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesJsonProxy implements IServices {

    private String host;
    private int port;

    private IObserver client;
    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private Socket connection;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServicesJsonProxy(String host, int port) {
        this.host = host;
        this.port = port;
        gsonFormatter = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .create();
        qresponses = new LinkedBlockingQueue<>();
    }

    public Player login(Player player, IObserver client) throws AppException {
        initializeConnection();

        Request request = JsonProtocolUtils.createLoginRequest(player);
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.OK) {
            this.client = client;
        }
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            closeConnection();
            throw new AppException(err);
        }
        return response.getPlayer();
    }

    public Position addPosition(Position position) throws AppException {
        Request request = JsonProtocolUtils.createAddPositionRequest(position);
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new AppException(err);
        }
        return response.getPosition();
    }

    public Game addGame(Game game) throws AppException {
        Request request = JsonProtocolUtils.createAddGameRequest(game);
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new AppException(err);
        }
        return response.getGame();
    }

    public Configuration addConfiguration(Configuration configuration) throws AppException {
        Request request = JsonProtocolUtils.createAddConfigurationRequest(configuration);
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new AppException(err);
        }
        return response.getConfiguration();
    }

    public List<Game> getAllGamesForPlayer(Player player) throws AppException {
        Request request = JsonProtocolUtils.createGetAllGamesForPlayerRequest(player);
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new AppException(err);
        }
        return response.getGames();
    }

    public List<Game> getAllGames() throws AppException {
        Request request = JsonProtocolUtils.createGetAllGamesRequest();
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new AppException(err);
        }
        return response.getGames();
    }

    public Player findPlayerByAlias(String alias) throws AppException {
        Request request = JsonProtocolUtils.createFindPlayerByAliasRequest(alias);
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new AppException(err);
        }
        return response.getPlayer();
    }

    public List<Configuration> getAllConfigurations() throws AppException {
        initializeConnection();

        Request request = JsonProtocolUtils.createGetAllConfigurationsRequest();
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new AppException(err);
        }
        return response.getConfigurations();
    }

    public Player logout(Player player, IObserver client) throws AppException {
        Request request = JsonProtocolUtils.createLogoutRequest(player);
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new AppException(err);
        }
        return response.getPlayer();
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

    private void sendRequest(Request request) throws AppException {
        String requestJson = gsonFormatter.toJson(request);
        System.out.println("Sending request " + requestJson);
        try {
            output.println(requestJson);
            output.flush();
        } catch (Exception e) {
            throw new AppException("Error sending object " + e);
        }
    }

    private Response readResponse() throws AppException {
        Response response = null;
        try {
            response = qresponses.take();
        } catch (InterruptedException e) {
            throw new AppException("Error reading response " + e);
        }
        if (response == null) {
            throw new AppException("Received null response from server.");
        }
        return response;
    }

    private void initializeConnection() throws AppException {
        try {
            gsonFormatter = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                    .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .create();
            connection = new Socket(host, port);
            output = new PrintWriter(connection.getOutputStream(), true);
            output.flush();
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            finished = false;
            startReader();
        } catch (IOException e) {
            throw new AppException("Error initializing connection " + e);
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response) {
        if (response.getType() == ResponseType.ADD_GAME) {
            Game game = response.getGame();
            try {
                client.gameAdded(game);
            } catch (AppException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response) {
        return response.getType() == ResponseType.ADD_GAME;
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
