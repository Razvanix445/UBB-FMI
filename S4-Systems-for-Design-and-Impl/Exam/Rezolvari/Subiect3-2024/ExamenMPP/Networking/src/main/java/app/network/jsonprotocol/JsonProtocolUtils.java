package app.network.jsonprotocol;

import app.model.Configuration;
import app.model.Game;
import app.model.Player;

import java.util.List;

public class JsonProtocolUtils {

    public static Response createAddGameResponse(Game game) {
        Response resp = new Response();
        resp.setType(ResponseType.ADD_GAME);
        resp.setGame(game);
        return resp;
    }

    public static Response createFindPlayerByAliasResponse(Player player) {
        Response resp = new Response();
        resp.setType(ResponseType.FIND_PLAYER_BY_ALIAS);
        resp.setPlayer(player);
        return resp;
    }

    public static Response createGetAllGamesResponse(List<Game> games) {
        Response resp = new Response();
        resp.setType(ResponseType.GET_ALL_GAMES);
        resp.setGames(games);
        return resp;
    }

    public static Response createGetAllConfigurationsResponse(List<Configuration> configurations) {
        Response resp = new Response();
        resp.setType(ResponseType.GET_ALL_CONFIGURATIONS);
        resp.setConfigurations(configurations);
        return resp;
    }

    public static Response createOkResponse(){
        Response response = new Response();
        response.setType(ResponseType.OK);
        return response;
    }

    public static Response createErrorResponse(String errorMessage){
        Response response=new Response();
        response.setType(ResponseType.ERROR);
        response.setErrorMessage(errorMessage);
        return response;
    }

    public static Request createAddGameRequest(Game game) {
        Request req = new Request();
        req.setType(RequestType.ADD_GAME);
        req.setGame(game);
        return req;
    }

    public static Request createFindPlayerByAliasRequest(String alias) {
        Request req = new Request();
        req.setType(RequestType.FIND_PLAYER_BY_ALIAS);
        req.setStringR(alias);
        return req;
    }

    public static Request createGetAllGamesRequest() {
        Request req = new Request();
        req.setType(RequestType.GET_ALL_GAMES);
        return req;
    }

    public static Request createGetAllConfigurationsRequest() {
        Request req = new Request();
        req.setType(RequestType.GET_ALL_CONFIGURATIONS);
        return req;
    }

    public static Request createLoginRequest(Player player){
        Request request = new Request();
        request.setType(RequestType.LOGIN);
        request.setPlayer(player);
        return request;
    }

    public static Request createLogoutRequest(Player player){
        Request request = new Request();
        request.setType(RequestType.LOGOUT);
        request.setPlayer(player);
        return request;
    }
}