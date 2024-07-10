package rest.client;

import transport.model.ReservationManager;
import transport.services.rest.ServiceException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class TripsClient {
    public static final String URL = "http://localhost:8080/transport/trips";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public ReservationManager[] getAll() {
        return execute(() -> restTemplate.getForObject(URL, ReservationManager[].class));
    }

    public ReservationManager getById(String id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), ReservationManager.class));
    }

    public ReservationManager create(ReservationManager user) {
        return execute(() -> restTemplate.postForObject(URL, user, ReservationManager.class));
    }

    public void update(ReservationManager user) {
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, user.getId()), user);
            return null;
        });
    }

    public void delete(String id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }
}