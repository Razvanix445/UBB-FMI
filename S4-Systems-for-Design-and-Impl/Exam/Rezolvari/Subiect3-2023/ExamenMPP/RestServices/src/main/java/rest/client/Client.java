package rest.client;

import app.services.rest.ServiceException;

import java.io.IOException;
import java.util.concurrent.Callable;

import app.model.*;

import org.springframework.http.HttpRequest;
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
        return execute(() -> restClient.get(). uri(String.format(URL + "/games/" + id)).retrieve().body(Game.class));
    }

    public Position[] getPositionsByGame(Game game) {
        return execute(() -> restClient.get().uri(String.format(URL + "/positions/" + game.getId())).retrieve().body(Position[].class));
    }

    public Configuration getConfigurationById(Long id) {
        return execute(() -> restClient.get(). uri(String.format(URL + "/configurations/" + id)).retrieve().body(Configuration.class));
    }

    public Word[] getWordsByConfiguration(Long configuration_id) {
        return execute(() -> restClient.get(). uri(String.format(URL + "/words/" + configuration_id)).retrieve().body(Word[].class));
    }

    public ConfigurationWord[] getConfigurationWordsByConfiguration(Long configuration_id) {
        return execute(() -> restClient.get(). uri(String.format(URL + "/configurationwords/" + configuration_id)).retrieve().body(ConfigurationWord[].class));
    }

    public ConfigurationWord updateConfigurationWord(ConfigurationWord configurationWord) {
        return execute(() -> restClient.put().uri(String.format(URL + "/configurations/" + configurationWord.getId())).contentType(APPLICATION_JSON).body(configurationWord).retrieve().body(ConfigurationWord.class));
    }

//    public void updateConfigurationWords(ConfigurationWord[] configurationWords) {
//        execute(() -> restClient.put().uri(String.format(URL + "/configurations/" + configurationWords[0].getId())).contentType(APPLICATION_JSON).body(configurationWords).retrieve().body(ConfigurationWord[].class));
//    }

//    public Configuration updateConfiguration(Configuration configuration) {
//        return execute(() -> restClient.put().uri(String.format(URL + "/configurations/" + configuration.getId())).contentType(APPLICATION_JSON).body(configuration).retrieve().body(Configuration.class));
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
