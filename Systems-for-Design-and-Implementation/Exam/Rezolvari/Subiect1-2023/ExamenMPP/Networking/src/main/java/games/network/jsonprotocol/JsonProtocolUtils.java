package games.network.jsonprotocol;

import games.model.Configuration;
import games.model.Game;
import games.model.Player;
import games.model.Position;

import java.util.List;

public class JsonProtocolUtils {

    public static Response createAddPositionResponse(Position position){
        Response response = new Response();
        response.setType(ResponseType.ADD_POSITION);
        response.setPosition(position);
        return response;
    }

    public static Response createAddGameResponse(Game game){
        Response response = new Response();
        response.setType(ResponseType.ADD_GAME);
        response.setGame(game);
        return response;
    }

    public static Response createAddConfigurationResponse(Configuration configuration){
        Response response = new Response();
        response.setType(ResponseType.ADD_CONFIGURATION);
        response.setConfiguration(configuration);
        return response;
    }

    public static Response createFindPlayerByAliasResponse(Player player) {
        Response response = new Response();
        response.setType(ResponseType.FIND_PLAYER_BY_ALIAS);
        response.setPlayer(player);
        return response;
    }

    public static Response createGetAllConfigurationsResponse(List<Configuration> configurations) {
        Response response = new Response();
        response.setType(ResponseType.GET_ALL_CONFIGURATIONS);
        response.setConfigurations(configurations);
        return response;
    }

    public static Response createGetAllGamesResponse(List<Game> games) {
        Response response = new Response();
        response.setType(ResponseType.GET_ALL_GAMES);
        response.setGames(games);
        return response;
    }

    public static Response createGetAllGamesForPlayerResponse(List<Game> games) {
        Response response = new Response();
        response.setType(ResponseType.GET_ALL_GAMES_FOR_PLAYER);
        response.setGames(games);
        return response;
    }

    public static Response createOkResponse() {
        Response response = new Response();
        response.setType(ResponseType.OK);
        return response;
    }

    public static Response createErrorResponse(String errorMessage) {
        Response response=new Response();
        response.setType(ResponseType.ERROR);
        response.setErrorMessage(errorMessage);
        return response;
    }

    public static Request createAddPositionRequest(Position position) {
        Request request = new Request();
        request.setType(RequestType.ADD_POSITION);
        request.setPosition(position);
        return request;
    }

    public static Request createAddConfigurationRequest(Configuration configuration) {
        Request request = new Request();
        request.setType(RequestType.ADD_CONFIGURATION);
        request.setConfiguration(configuration);
        return request;
    }

    public static Request createAddGameRequest(Game game) {
        Request request = new Request();
        request.setType(RequestType.ADD_GAME);
        request.setGame(game);
        return request;
    }

    public static Request createFindPlayerByAliasRequest(String alias) {
        Request request = new Request();
        request.setType(RequestType.FIND_PLAYER_BY_ALIAS);
        request.setStringR(alias);
        return request;
    }

    public static Request createGetAllConfigurationsRequest() {
        Request request = new Request();
        request.setType(RequestType.GET_ALL_CONFIGURATIONS);
        return request;
    }

    public static Request createGetAllGamesForPlayerRequest(Player player) {
        Request request = new Request();
        request.setType(RequestType.GET_ALL_GAMES_FOR_PLAYER);
        request.setPlayer(player);
        return request;
    }

    public static Request createGetAllGamesRequest() {
        Request request = new Request();
        request.setType(RequestType.GET_ALL_GAMES);
        return request;
    }

    public static Request createLoginRequest(Player player) {
        Request request = new Request();
        request.setType(RequestType.LOGIN);
        request.setPlayer(player);
        return request;
    }

    public static Request createLogoutRequest(Player player) {
        Request request = new Request();
        request.setType(RequestType.LOGOUT);
        request.setPlayer(player);
        return request;
    }
}