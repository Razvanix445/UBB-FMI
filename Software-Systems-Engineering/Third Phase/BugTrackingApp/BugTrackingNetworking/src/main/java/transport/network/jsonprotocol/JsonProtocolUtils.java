package transport.network.jsonprotocol;

import bugtracking.model.*;

import java.util.List;

public class JsonProtocolUtils {
    public static Response createOkResponse() {
        Response resp = new Response();
        resp.setType(ResponseType.OK);
        return resp;
    }

    public static Response createErrorResponse(String errorMessage) {
        Response resp = new Response();
        resp.setType(ResponseType.ERROR);
        resp.setErrorMessage(errorMessage);
        return resp;
    }

    public static Response createAddTesterResponse(Tester tester) {
        Response resp = new Response();
        resp.setType(ResponseType.ADD_TESTER);
        resp.setTester(tester);
        return resp;
    }

    public static Response createDeleteTesterResponse(Tester tester) {
        Response resp = new Response();
        resp.setType(ResponseType.DELETE_TESTER);
        resp.setTester(tester);
        return resp;
    }

    public static Response createUpdateTesterResponse(Tester tester) {
        Response resp = new Response();
        resp.setType(ResponseType.UPDATE_TESTER);
        resp.setTester(tester);
        return resp;
    }

    public static Response createAddProgrammerResponse(Programmer programmer) {
        Response resp = new Response();
        resp.setType(ResponseType.ADD_PROGRAMMER);
        resp.setProgrammer(programmer);
        return resp;
    }

    public static Response createDeleteProgrammerResponse(Programmer programmer) {
        Response resp = new Response();
        resp.setType(ResponseType.DELETE_PROGRAMMER);
        resp.setProgrammer(programmer);
        return resp;
    }

    public static Response createUpdateProgrammerResponse(Programmer programmer) {
        Response resp = new Response();
        resp.setType(ResponseType.UPDATE_PROGRAMMER);
        resp.setProgrammer(programmer);
        return resp;
    }

    public static Response createGetAllProgrammersResponse(List<Programmer> programmers) {
        Response resp = new Response();
        resp.setType(ResponseType.GET_ALL_PROGRAMMERS);
        resp.setProgrammers(programmers);
        return resp;
    }

    public static Response createAddBugResponse(Bug bug) {
        Response resp = new Response();
        resp.setType(ResponseType.ADD_BUG);
        resp.setBug(bug);
        return resp;
    }

    public static Response createDeleteBugResponse(Bug bug) {
        Response resp = new Response();
        resp.setType(ResponseType.DELETE_BUG);
        resp.setBug(bug);
        return resp;
    }

    public static Response createUpdateBugResponse(Bug bug) {
        Response resp = new Response();
        resp.setType(ResponseType.UPDATE_BUG);
        resp.setBug(bug);
        return resp;
    }

    public static Response createSearchTesterByUsernameResponse(Tester tester) {
        Response resp = new Response();
        resp.setType(ResponseType.SEARCH_TESTER_BY_USERNAME);
        resp.setTester(tester);
        return resp;
    }

    public static Response createSearchProgrammerByUsernameResponse(Programmer programmer) {
        Response resp = new Response();
        resp.setType(ResponseType.SEARCH_PROGRAMMER_BY_USERNAME);
        resp.setProgrammer(programmer);
        return resp;
    }

    public static Response createSearchBugByNameAndDescriptionResponse(Bug bug) {
        Response resp = new Response();
        resp.setType(ResponseType.SEARCH_BUG_BY_NAME_AND_DESCRIPTION);
        resp.setBug(bug);
        return resp;
    }

    public static Response createGetBugsByProgrammerResponse(List<Bug> bugs) {
        Response resp = new Response();
        resp.setType(ResponseType.GET_BUGS_BY_PROGRAMMER);
        resp.setBugs(bugs);
        return resp;
    }

    public static Response createGetAllBugsResponse(List<Bug> bugs) {
        Response resp = new Response();
        resp.setType(ResponseType.GET_ALL_BUGS);
        resp.setBugs(bugs);
        return resp;
    }

    public static Response createGetAllUsersResponse(List<UserWithTypeDTO> users) {
        Response resp = new Response();
        resp.setType(ResponseType.GET_ALL_USERS);
        resp.setUsers(users);
        return resp;
    }

    public static Request createLoginTesterRequest(Tester tester) {
        Request req = new Request();
        req.setType(RequestType.LOGIN_TESTER);
        req.setTester(tester);
        return req;
    }

    public static Request createLoginProgrammerRequest(Programmer programmer) {
        Request req = new Request();
        req.setType(RequestType.LOGIN_PROGRAMMER);
        req.setProgrammer(programmer);
        return req;
    }

    public static Request createAddTesterRequest(Tester tester) {
        Request req = new Request();
        req.setType(RequestType.ADD_TESTER);
        req.setTester(tester);
        return req;
    }

    public static Request createDeleteTesterRequest(Tester tester) {
        Request req = new Request();
        req.setType(RequestType.DELETE_TESTER);
        req.setTester(tester);
        return req;
    }

    public static Request createUpdateTesterRequest(Tester tester) {
        Request req = new Request();
        req.setType(RequestType.UPDATE_TESTER);
        req.setTester(tester);
        return req;
    }

    public static Request createAddProgrammerRequest(Programmer programmer) {
        Request req = new Request();
        req.setType(RequestType.ADD_PROGRAMMER);
        req.setProgrammer(programmer);
        return req;
    }

    public static Request createDeleteProgrammerRequest(Programmer programmer) {
        Request req = new Request();
        req.setType(RequestType.DELETE_PROGRAMMER);
        req.setProgrammer(programmer);
        return req;
    }

    public static Request createUpdateProgrammerRequest(Programmer programmer) {
        Request req = new Request();
        req.setType(RequestType.UPDATE_PROGRAMMER);
        req.setProgrammer(programmer);
        return req;
    }

    public static Request createGetAllProgrammersRequest() {
        Request req = new Request();
        req.setType(RequestType.GET_ALL_PROGRAMMERS);
        return req;
    }

    public static Request createAddBugRequest(Bug bug) {
        Request req = new Request();
        req.setType(RequestType.ADD_BUG);
        req.setBug(bug);
        return req;
    }

    public static Request createDeleteBugRequest(Bug bug) {
        Request req = new Request();
        req.setType(RequestType.DELETE_BUG);
        req.setBug(bug);
        return req;
    }

    public static Request createUpdateBugRequest(Bug bug) {
        Request req = new Request();
        req.setType(RequestType.UPDATE_BUG);
        req.setBug(bug);
        return req;
    }

    public static Request createSearchTesterByUsernameRequest(String username) {
        Request req = new Request();
        req.setType(RequestType.SEARCH_TESTER_BY_USERNAME);
        req.setString(username);
        return req;
    }

    public static Request createSearchProgrammerByUsernameRequest(String username) {
        Request req = new Request();
        req.setType(RequestType.SEARCH_PROGRAMMER_BY_USERNAME);
        req.setString(username);
        return req;
    }

    public static Request createSearchBugByNameAndDescriptionRequest(String name, String description) {
        Request req = new Request();
        req.setType(RequestType.SEARCH_BUG_BY_NAME_AND_DESCRIPTION);
        req.setString(name);
        req.setString2(description);
        return req;
    }

    public static Request createGetBugsByProgrammerRequest(Programmer programmer) {
        Request req = new Request();
        req.setType(RequestType.GET_BUGS_BY_PROGRAMMER);
        req.setProgrammer(programmer);
        return req;
    }

    public static Request createGetAllBugsRequest() {
        Request req = new Request();
        req.setType(RequestType.GET_ALL_BUGS);
        return req;
    }

    public static Request createGetAllUsersRequest() {
        Request req = new Request();
        req.setType(RequestType.GET_ALL_USERS);
        return req;
    }

    public static Request createLogoutTesterRequest(Tester tester) {
        Request req = new Request();
        req.setType(RequestType.LOGOUT_TESTER);
        req.setTester(tester);
        return req;
    }

    public static Request createLogoutProgrammerRequest(Programmer programmer) {
        Request req = new Request();
        req.setType(RequestType.LOGOUT_PROGRAMMER);
        req.setProgrammer(programmer);
        return req;
    }
}
