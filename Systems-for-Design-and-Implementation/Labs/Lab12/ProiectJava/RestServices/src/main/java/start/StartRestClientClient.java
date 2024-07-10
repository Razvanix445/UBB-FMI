package start;

import transport.model.Trip;
import transport.model.TripDTO;
import transport.services.rest.ServiceException;
import org.springframework.web.client.RestClientException;
import rest.client.NewTripsClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class StartRestClientClient {
    private final static NewTripsClient tripsClient = new NewTripsClient();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedDateTime = LocalDateTime.now().format(formatter);

        TripDTO tripDTO = new TripDTO();
        tripDTO.setDestination("destinationJava");
        tripDTO.setDateDeparture(formattedDateTime);
        tripDTO.setNoOfAvailableSeats("5");
        try {
            // Add Functionality
            System.out.println("Adding a new trip " + tripDTO);
            TripDTO addedTrip = tripsClient.create(tripDTO);
            show(() -> System.out.println(addedTrip));
            scanner.nextLine();

            // FindAll Functionality
            System.out.println("\nPrinting all users ...");
            show(() -> {
                TripDTO[] res = tripsClient.getAll();
                for(TripDTO t: res){
                    System.out.println(t.getId() + ": " + t.getDestination());
                }
            });
            scanner.nextLine();

            // FindOne Functionality
            System.out.println("\nInfo for user with id = 32");
            show(() -> System.out.println(tripsClient.getById(32L)));
            scanner.nextLine();

            // Update Functionality
            System.out.println("\nUpdating trip with id = " + addedTrip.getId());
            TripDTO updatedTripDTO = new TripDTO();
            updatedTripDTO.setId(addedTrip.getId());
            updatedTripDTO.setDestination("updatedDestination");
            updatedTripDTO.setDateDeparture(formattedDateTime);
            updatedTripDTO.setNoOfAvailableSeats("6");
            TripDTO updatedTrip = tripsClient.update(updatedTripDTO);
            show(() -> System.out.println(updatedTrip));
            scanner.nextLine();

            // Delete Functionality
            System.out.println("\nDeleting user with id = " + addedTrip.getId());
            show(() -> tripsClient.delete(updatedTripDTO.getId().toString()));
        } catch (RestClientException ex) {
            System.out.println("Exception ... " + ex.getMessage());
        }
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
