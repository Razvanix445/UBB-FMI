package transport.network.jsonprotocol;

import bugtracking.model.*;
import bugtracking.services.BugTrackingException;
import bugtracking.services.IBugTrackingObserver;
import bugtracking.services.IBugTrackingServices;
import com.google.gson.*;

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

public class BugTrackingServicesJsonProxy implements IBugTrackingServices {
    private String host;
    private int port;

    private IBugTrackingObserver client;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public class EnumOrdinalTypeAdapter<E extends Enum<E>> implements JsonSerializer<E>, JsonDeserializer<E> {
        private final Class<E> clazz;

        public EnumOrdinalTypeAdapter(Class<E> clazz) {
            this.clazz = clazz;
        }

        @Override
        public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.ordinal());
        }

        @Override
        public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            int ordinal = json.getAsInt();
            return clazz.getEnumConstants()[ordinal];
        }
    }

    public BugTrackingServicesJsonProxy(String host, int port) {
        this.host = host;
        this.port = port;
        gsonFormatter = new GsonBuilder()
//                .registerTypeAdapter(RequestType.class, new EnumOrdinalTypeAdapter<>(RequestType.class))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .create();
        qresponses = new LinkedBlockingQueue<Response>();
    }

    public Tester loginTester(Tester tester, IBugTrackingObserver client) throws BugTrackingException {
        initializeConnection();

        Request req = JsonProtocolUtils.createLoginTesterRequest(tester);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.OK) {
            this.client = client;
        }
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            closeConnection();
            throw new BugTrackingException(err);
        }
        return response.getTester();
    }

    public Programmer loginProgrammer(Programmer programmer, IBugTrackingObserver client) throws BugTrackingException {
        initializeConnection();

        Request req = JsonProtocolUtils.createLoginProgrammerRequest(programmer);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.OK) {
            this.client = client;
        }
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            closeConnection();
            throw new BugTrackingException(err);
        }
        return response.getProgrammer();
    }

    public Tester addTester(Tester tester) throws BugTrackingException {
        initializeConnection();

        Request req = JsonProtocolUtils.createAddTesterRequest(tester);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new BugTrackingException(err);
        }
        return response.getTester();
    }

    public Tester deleteTester(Tester tester) throws BugTrackingException {
        Request req = JsonProtocolUtils.createDeleteTesterRequest(tester);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new BugTrackingException(err);
        }
        return response.getTester();
    }

    public Tester updateTester(Tester tester) throws BugTrackingException {
        Request req = JsonProtocolUtils.createUpdateTesterRequest(tester);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new BugTrackingException(err);
        }
        return response.getTester();
    }

    public Programmer addProgrammer(Programmer programmer) throws BugTrackingException {
        initializeConnection();

        Request req = JsonProtocolUtils.createAddProgrammerRequest(programmer);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new BugTrackingException(err);
        }
        return response.getProgrammer();
    }

    public Programmer deleteProgrammer(Programmer programmer) throws BugTrackingException {
        Request req = JsonProtocolUtils.createDeleteProgrammerRequest(programmer);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new BugTrackingException(err);
        }
        return response.getProgrammer();
    }

    public Programmer updateProgrammer(Programmer programmer) throws BugTrackingException {
        Request req = JsonProtocolUtils.createUpdateProgrammerRequest(programmer);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new BugTrackingException(err);
        }
        return response.getProgrammer();
    }

    public List<Programmer> getAllProgrammers() throws BugTrackingException {
        Request req = JsonProtocolUtils.createGetAllProgrammersRequest();
        sendRequest(req);
        Response response = readResponse();
        return response.getProgrammers();
    }

    public Bug addBug(Bug bug) throws BugTrackingException {
        Request req = JsonProtocolUtils.createAddBugRequest(bug);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new BugTrackingException(err);
        }
        return response.getBug();
    }

    public Bug deleteBug(Bug bug) throws BugTrackingException {
        Request req = JsonProtocolUtils.createDeleteBugRequest(bug);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new BugTrackingException(err);
        }
        return response.getBug();
    }

    public Bug updateBug(Bug bug) throws BugTrackingException {
        Request req = JsonProtocolUtils.createUpdateBugRequest(bug);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new BugTrackingException(err);
        }
        return response.getBug();
    }

    public Tester searchTesterByUsername(String name) throws BugTrackingException {
        initializeConnection();

        Request req = JsonProtocolUtils.createSearchTesterByUsernameRequest(name);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new BugTrackingException(err);
        }
        return response.getTester();
    }

    public Programmer searchProgrammerByUsername(String name) throws BugTrackingException {
        initializeConnection();

        Request req = JsonProtocolUtils.createSearchProgrammerByUsernameRequest(name);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new BugTrackingException(err);
        }
        return response.getProgrammer();
    }

    public Bug searchBugByNameAndDescription(String name, String description) throws BugTrackingException {
        Request req = JsonProtocolUtils.createSearchBugByNameAndDescriptionRequest(name, description);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new BugTrackingException(err);
        }
        return response.getBug();
    }

    public List<Bug> getBugsByProgrammer(Programmer programmer) throws BugTrackingException {
        Request req = JsonProtocolUtils.createGetBugsByProgrammerRequest(programmer);
        sendRequest(req);
        Response response = readResponse();
        return response.getBugs();
    }

    public List<Bug> getAllBugs() throws BugTrackingException {
        Request req = JsonProtocolUtils.createGetAllBugsRequest();
        sendRequest(req);
        Response response = readResponse();
        return response.getBugs();
    }

    public List<UserWithTypeDTO> getAllUsers() throws BugTrackingException {
        Request req = JsonProtocolUtils.createGetAllUsersRequest();
        sendRequest(req);
        Response response = readResponse();
        return response.getUsers();
    }

    public Tester logoutTester(Tester tester, IBugTrackingObserver client) throws BugTrackingException {
        Request req = JsonProtocolUtils.createLogoutTesterRequest(tester);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.OK) {
            this.client = null;
        }
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new BugTrackingException(err);
        }
        return response.getTester();
    }

    public Programmer logoutProgrammer(Programmer programmer, IBugTrackingObserver client) throws BugTrackingException {
        Request req = JsonProtocolUtils.createLogoutProgrammerRequest(programmer);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.OK) {
            this.client = null;
        }
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new BugTrackingException(err);
        }
        return response.getProgrammer();
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

    private void sendRequest(Request request) throws BugTrackingException {
        String requestJson = gsonFormatter.toJson(request);
        System.out.println("Sending request " + requestJson);
        try {
            output.println(requestJson);
            output.flush();
        } catch (Exception e) {
            throw new BugTrackingException("Error sending object " + e);
        }
    }

    private Response readResponse() throws BugTrackingException {
        Response response = null;
        try {
            response = qresponses.take();
        } catch (InterruptedException e) {
            throw new BugTrackingException("Error reading response " + e);
        }
        if (response == null) {
            throw new BugTrackingException("Received null response from server.");
        }
        return response;
    }

    private void initializeConnection() throws BugTrackingException {
        try {
            gsonFormatter = new GsonBuilder()
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
            throw new BugTrackingException("Error initializing connection " + e);
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response) {
        if (response.getType() == ResponseType.ADD_BUG) {
            Bug bug = response.getBug();
            try {
                client.bugAdded(bug);
            } catch (BugTrackingException e) {
                e.printStackTrace();
            }
        } else if (response.getType() == ResponseType.DELETE_BUG) {
            Bug bug = response.getBug();
            try {
                client.bugRemoved(bug);
            } catch (BugTrackingException e) {
                e.printStackTrace();
            }
        } else if (response.getType() == ResponseType.UPDATE_BUG) {
            Bug bug = response.getBug();
            try {
                client.bugUpdated(bug);
            } catch (BugTrackingException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response) {
        return response.getType() == ResponseType.ADD_BUG || response.getType() == ResponseType.DELETE_BUG || response.getType() == ResponseType.UPDATE_BUG;
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
