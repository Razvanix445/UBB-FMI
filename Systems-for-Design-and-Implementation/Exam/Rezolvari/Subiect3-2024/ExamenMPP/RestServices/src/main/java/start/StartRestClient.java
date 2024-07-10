package start;

import app.model.*;
import app.services.rest.ServiceException;
import org.springframework.web.client.RestClientException;
import rest.client.Client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class StartRestClient {
    private final static Client client = new Client();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedDateTime = LocalDateTime.now().format(formatter);

        // Add Configuration
        System.out.println("\nAdding configuration ...");
        Configuration configuration = new Configuration("arescd", "acasa", "aers", "daes", "reas");
        show(() -> {
            Configuration savedConfiguration = client.addConfiguration(configuration);
            System.out.println("Saved configuration: " + savedConfiguration);
        });
//
//            // Update Configuration
//            System.out.println("\nUpdating configuration ...");
//            configuration.setHint("Updated Hint");
//            show(() -> {
//                Configuration updatedConfiguration = playersClient.updateConfiguration(configuration);
//                System.out.println("Updated configuration: " + updatedConfiguration);
//            });
//
//            // Delete Configuration
//            System.out.println("\nDeleting configuration ...");
//            show(() -> {
//                playersClient.deleteConfiguration(configuration.getId());
//                System.out.println("Configuration deleted");
//            });

//        } catch (RestClientException ex) {
//            System.out.println("Exception ... " + ex.getMessage());
//        }
    }

    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}
