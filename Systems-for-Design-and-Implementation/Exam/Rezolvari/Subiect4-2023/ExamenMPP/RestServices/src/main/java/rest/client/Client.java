package rest.client;

import app.services.rest.ServiceException;

import java.io.IOException;
import java.util.concurrent.Callable;

import app.model.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class Client {

    RestClient restClient = RestClient.builder().
            requestInterceptor(new CustomRestClientInterceptor()).
            build();

    public static final String URL = "http://localhost:8080/app";
    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Game getGameById(Long id) {
        return execute(() -> restClient.get().uri(String.format(URL + "/game/" + id)).retrieve().body(Game.class));
    }

    public Position[] getPositionsByGame(Game game) {
        return execute(() -> restClient.get().uri(String.format(URL + "/positions/" + game.getId())).retrieve().body(Position[].class));
    }

    public Game[] getGamesByPlayer(Player player) {
        return execute(() -> restClient.get().uri(String.format(URL + "/games/" + player.getAlias())).retrieve().body(Game[].class));
    }

    public Position addPosition(Position position) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return execute(() -> restClient.post().uri(URL + "/position").headers(httpHeaders -> {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        }).body(position).retrieve().body(Position.class));
    }

    public class CustomRestClientInterceptor
            implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept (
                HttpRequest request,
                byte[] body,
                ClientHttpRequestExecution execution) throws IOException {
            System.out.println("Sending a "+request.getMethod()+ " request to "+request.getURI()+ " and body ["+new String(body)+"]");
            ClientHttpResponse response=null;
            try {
                response = execution.execute(request, body);
                System.out.println("Got response code: " + response.getStatusCode());
            } catch (IOException ex){
                System.err.println("Execution error: "+ex);
            }
            return response;
        }
    }
}
