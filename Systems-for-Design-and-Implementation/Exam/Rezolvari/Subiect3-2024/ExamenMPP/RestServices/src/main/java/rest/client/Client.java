package rest.client;

import app.model.Configuration;
import app.model.Game;
import app.model.Player;
import app.services.rest.ServiceException;

import java.io.IOException;
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

    public Game[] getGamesByPlayer(Player player) {
        return execute(() -> restClient.get().uri(String.format(URL + "/games/" + player.getAlias())).retrieve().body(Game[].class));
    }

    public Configuration addConfiguration(Configuration configuration) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return execute(() -> restClient.post().uri(URL + "/configurations").headers(httpHeaders -> {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        }).body(configuration).retrieve().body(Configuration.class));
    }

//    TODO 5: Add the method to update a "something"
//    public Configuration updateConfiguration(Configuration configuration) {
//        return execute(() -> restClient.put().uri(String.format(URL + "/configurations/" + configuration.getId())).contentType(APPLICATION_JSON).body(configuration).retrieve().body(Configuration.class));
//    }

//    TODO 6: Add the method to delete a "something"
//    public void deleteConfiguration(Long id) {
//        execute(() -> {
//            restClient.delete().uri(URL + "/configurations/" + id).retrieve().body(Void.class);
//            return null;
//        });
//    }

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
