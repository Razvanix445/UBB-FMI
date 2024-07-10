package start;

import app.model.*;
import app.services.rest.ServiceException;
import org.springframework.web.client.RestClientException;
import rest.client.Client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StartRestClient {
    private final static Client client = new Client();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedDateTime = LocalDateTime.now().format(formatter);

        try {
            // Visualize Multiple Things for a Game
            System.out.println("\nVisualizing things for game with id = " + 1);
            Game game = client.getGameById(1L);
            show(() -> {
                System.out.println("Game ID: " + game.getId());
                System.out.println("Player Alias: " + game.getPlayer().getAlias());
                Position[] positions = client.getPositionsByGame(game);
                for (Position position : positions)
                    System.out.println("Position " + position.getPositionIndex() + ": (" + position.getCoordinateX() + ", " + position.getCoordinateY() + ")");

                Word[] words = client.getWordsByConfiguration(1L);
                System.out.println("Game Configuration: " + Arrays.toString(words));

                System.out.println("Total Score: " + game.getScore());
            });

            // Update Configuration
            List<Long> numbers = Arrays.asList(10L, 3L, 5L, 4L, 2L, 9L, 8L, 6L, 7L, 1L);
            ConfigurationWord[] configurationWords = client.getConfigurationWordsByConfiguration(1L);
            System.out.println("\nUpdating configuration ...");
            for (int i = 0; i < configurationWords.length; i++) {
                configurationWords[i].setWordNumber(numbers.get(i));
            }
            for (ConfigurationWord configurationWord: configurationWords) {
                client.updateConfigurationWord(configurationWord);
            }
//            client.updateConfigurationWords(configurationWords);
            show(() -> {
                Word[] words = client.getWordsByConfiguration(1L);
                System.out.println("Updated configuration: " + Arrays.toString(words));
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
