package start;

import games.model.*;
import games.services.rest.ServiceException;
import org.springframework.web.client.RestClientException;
import rest.client.GamesClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class StartRestClientClient {
    private final static GamesClient playersClient = new GamesClient();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedDateTime = LocalDateTime.now().format(formatter);

        Player player = new Player();
        player.setAlias("player1");
        try {
            // Visualize Games for a Player
            System.out.println("\nVisualizing games for player with alias = " + player.getAlias());
            show(() -> {
                Game[] games = playersClient.getGamesByPlayer(player);
                for(Game g: games){
                    Position[] positions = playersClient.getPositionsByGame(g);
                    System.out.println("Game ID: " + g.getId() + ", Number of Tries: " + g.getNoOfTries() + ", Proposed Positions: " + Arrays.toString(positions) + ", Hint Text: " + g.getConfiguration().getHint());
                }
            });

//            // Add Configuration
//            System.out.println("\nAdding configuration ...");
//            Configuration configuration = new Configuration("Hint", 1L, 2L);
//            show(() -> {
//                Configuration savedConfiguration = playersClient.addConfiguration(configuration);
//                System.out.println("Saved configuration: " + savedConfiguration);
//                configuration.setId(savedConfiguration.getId());
//            });

            Configuration configuration = new Configuration("Hint", 1L, 2L);
            configuration.setId(11L);

            // Update Configuration
            System.out.println("\nUpdating configuration ...");
            configuration.setHint("Updated Hint");
            show(() -> {
                Configuration updatedConfiguration = playersClient.updateConfiguration(configuration);
                System.out.println("Updated configuration: " + updatedConfiguration);
            });

            // Delete Configuration
            System.out.println("\nDeleting configuration ...");
            show(() -> {
                playersClient.deleteConfiguration(configuration.getId());
                System.out.println("Configuration deleted");
            });

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
