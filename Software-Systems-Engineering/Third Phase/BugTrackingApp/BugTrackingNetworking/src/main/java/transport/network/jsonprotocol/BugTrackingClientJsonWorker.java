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
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BugTrackingClientJsonWorker implements Runnable, IBugTrackingObserver {
    private IBugTrackingServices server;
    private Socket connection;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private volatile boolean connected;

    public BugTrackingClientJsonWorker(IBugTrackingServices server, Socket connection) {
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

    public void bugAdded(Bug bug) throws BugTrackingException {
        Response response = JsonProtocolUtils.createAddBugResponse(bug);
        System.out.println("Bug added " + bug);
        try {
            sendResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bugRemoved(Bug bug) throws BugTrackingException {
        Response response = JsonProtocolUtils.createDeleteBugResponse(bug);
        System.out.println("Bug removed " + bug);
        try {
            sendResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bugUpdated(Bug bug) throws BugTrackingException {
        Response response = JsonProtocolUtils.createUpdateBugResponse(bug);
        System.out.println("Bug updated " + bug);
        try {
            sendResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Response okResponse = JsonProtocolUtils.createOkResponse();

    private Response handleRequest(Request request) {
        Response response = null;
        if (request.getType() == RequestType.LOGIN_TESTER) {
            System.out.println("Login request..." + request.getType());
            Tester tester = request.getTester();
            try {
                server.loginTester(tester, this);
                return okResponse;
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.LOGIN_PROGRAMMER) {
            System.out.println("Login request..." + request.getType());
            Programmer programmer = request.getProgrammer();
            try {
                server.loginProgrammer(programmer, this);
                return okResponse;
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.LOGOUT_TESTER) {
            System.out.println("Logout request..." + request.getType());
            Tester tester = request.getTester();
            try {
                server.logoutTester(tester, this);
                connected = false;
                return okResponse;
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.LOGOUT_PROGRAMMER) {
            System.out.println("Logout request..." + request.getType());
            Programmer programmer = request.getProgrammer();
            try {
                server.logoutProgrammer(programmer, this);
                connected = false;
                return okResponse;
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_ALL_BUGS) {
            System.out.println("Get all bugs request..." + request.getType());
            try {
                List<Bug> bugs = server.getAllBugs();
                return JsonProtocolUtils.createGetAllBugsResponse(bugs);
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_ALL_PROGRAMMERS) {
            System.out.println("Get all programmers request..." + request.getType());
            try {
                List<Programmer> programmers = server.getAllProgrammers();
                return JsonProtocolUtils.createGetAllProgrammersResponse(programmers);
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_ALL_USERS) {
            System.out.println("Get all users request..." + request.getType());
            try {
                List<UserWithTypeDTO> users = server.getAllUsers();
                return JsonProtocolUtils.createGetAllUsersResponse(users);
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_BUGS_BY_PROGRAMMER) {
            System.out.println("Get bugs by programmer request..." + request.getType());
            Programmer programmer = request.getProgrammer();
            try {
                List<Bug> bugs = server.getBugsByProgrammer(programmer);
                return JsonProtocolUtils.createGetBugsByProgrammerResponse(bugs);
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.SEARCH_PROGRAMMER_BY_USERNAME) {
            System.out.println("Search programmer by username request..." + request.getType());
            String username = request.getString();
            try {
                Programmer foundProgrammer = server.searchProgrammerByUsername(username);
                return JsonProtocolUtils.createSearchProgrammerByUsernameResponse(foundProgrammer);
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.SEARCH_TESTER_BY_USERNAME) {
            System.out.println("Search tester by username request..." + request.getType());
            String username = request.getString();
            try {
                Tester foundTester = server.searchTesterByUsername(username);
                return JsonProtocolUtils.createSearchTesterByUsernameResponse(foundTester);
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.SEARCH_BUG_BY_NAME_AND_DESCRIPTION) {
            System.out.println("Search bug by name and description request..." + request.getType());
            String name = request.getString();
            String description = request.getString2();
            try {
                Bug foundBug = server.searchBugByNameAndDescription(name, description);
                return JsonProtocolUtils.createSearchBugByNameAndDescriptionResponse(foundBug);
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.ADD_TESTER) {
            System.out.println("Add tester request..." + request.getType());
            Tester tester = request.getTester();
            try {
                server.addTester(tester);
                return okResponse;
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.DELETE_TESTER) {
            System.out.println("Delete tester request..." + request.getType());
            Tester tester = request.getTester();
            try {
                server.deleteTester(tester);
                return okResponse;
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.UPDATE_TESTER) {
            System.out.println("Update tester request..." + request.getType());
            Tester tester = request.getTester();
            try {
                server.updateTester(tester);
                return okResponse;
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.ADD_PROGRAMMER) {
            System.out.println("Add programmer request..." + request.getType());
            Programmer programmer = request.getProgrammer();
            try {
                server.addProgrammer(programmer);
                return okResponse;
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.DELETE_PROGRAMMER) {
            System.out.println("Delete programmer request..." + request.getType());
            Programmer programmer = request.getProgrammer();
            try {
                server.deleteProgrammer(programmer);
                return okResponse;
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.UPDATE_PROGRAMMER) {
            System.out.println("Update programmer request..." + request.getType());
            Programmer programmer = request.getProgrammer();
            try {
                server.updateProgrammer(programmer);
                return okResponse;
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.ADD_BUG) {
            System.out.println("Add bug request..." + request.getType());
            Bug bug = request.getBug();
            try {
                server.addBug(bug);
                return okResponse;
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.DELETE_BUG) {
            System.out.println("Delete bug request..." + request.getType());
            Bug bug = request.getBug();
            try {
                server.deleteBug(bug);
                return okResponse;
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.UPDATE_BUG) {
            System.out.println("Update bug request..." + request.getType());
            Bug bug = request.getBug();
            try {
                server.updateBug(bug);
                return okResponse;
            } catch (BugTrackingException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
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
