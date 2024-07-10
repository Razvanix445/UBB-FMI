package rest.client;

import java.io.IOException;

import games.model.Configuration;
import games.model.Game;
import games.model.Player;
import games.model.Position;
import games.services.rest.ServiceException;
import java.util.concurrent.Callable;

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

public class GamesClient {
    RestClient restClient = RestClient.builder().
            requestInterceptor(new CustomRestClientInterceptor()).
            build();

    public static final String URL = "http://localhost:8080/games/players";
    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Player getByAlias(String alias) {
        return execute(() -> restClient.get(). uri(String.format("%s/%s", URL, alias)).retrieve().body(Player.class));
    }

    public Game[] getGamesByPlayer(Player player) {
        return execute(() -> restClient.get().uri(String.format("%s/%s/games", URL, player.getAlias())).retrieve().body(Game[].class));
    }

    public Position[] getPositionsByGame(Game game) {
        return execute(() -> restClient.get().uri(String.format("%s/%s/positions", URL, game.getId())).retrieve().body(Position[].class));
    }

    public Configuration addConfiguration(Configuration configuration) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return execute(() -> restClient.post().uri(URL + "/configurations").headers(httpHeaders -> {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        }).body(configuration).retrieve().body(Configuration.class));
    }

    public Configuration updateConfiguration(Configuration configuration) {
        return execute(() -> restClient.put().uri(String.format(URL + "/configurations/" + configuration.getId())).contentType(APPLICATION_JSON).body(configuration).retrieve().body(Configuration.class));
    }

    public void deleteConfiguration(Long id) {
        execute(() -> {
            restClient.delete().uri(URL + "/configurations/" + id).retrieve().body(Void.class);
            return null;
        });
    }

    public class CustomRestClientInterceptor
            implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(
                HttpRequest request,
                byte[] body,
                ClientHttpRequestExecution execution) throws IOException {
            System.out.println("Sending a "+request.getMethod()+ " request to "+request.getURI()+ " and body ["+new String(body)+"]");
            ClientHttpResponse response=null;
            try {
                response = execution.execute(request, body);
                System.out.println("Got response code " + response.getStatusCode());
            }catch(IOException ex){
                System.err.println("Eroare executie "+ex);
            }
            return response;
        }
    }
}
