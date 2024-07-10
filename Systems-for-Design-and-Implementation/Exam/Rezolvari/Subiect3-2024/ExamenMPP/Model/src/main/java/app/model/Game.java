package app.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "Games")
public class Game extends Entity<Long> {

    private Player player;
    private Configuration configuration;
    private LocalDateTime startingTime;
    private Long score;
    private Long noOfGuessedWords;

    public Game() {
    }

    public Game(Player player, Configuration configuration, LocalDateTime startingTime, Long score, Long noOfGuessedWords) {
        this.player = player;
        this.configuration = configuration;
        this.startingTime = startingTime;
        this.score = score;
        this.noOfGuessedWords = noOfGuessedWords;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "player_id", nullable = false)
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @ManyToOne
    @JoinColumn(name = "configuration_id", nullable = false)
    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Column(name = "startingTime", nullable = false)
    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }

    @Column(name = "score", nullable = false)
    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Column(name = "noOfGuessedWords", nullable = false)
    public Long getNoOfGuessedWords() {
        return noOfGuessedWords;
    }

    public void setNoOfGuessedWords(Long noOfGuessedWords) {
        this.noOfGuessedWords = noOfGuessedWords;
    }

    @Override
    public String toString() {
        return "Game{" +
                "player=" + player +
                ", configuration=" + configuration +
                ", startingTime=" + startingTime +
                ", score=" + score +
                ", noOfGuessedWords=" + noOfGuessedWords +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Game game = (Game) o;
        return Objects.equals(player, game.player) && Objects.equals(configuration, game.configuration) && Objects.equals(startingTime, game.startingTime) && Objects.equals(score, game.score) && Objects.equals(noOfGuessedWords, game.noOfGuessedWords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player, configuration, startingTime, score, noOfGuessedWords);
    }
}
