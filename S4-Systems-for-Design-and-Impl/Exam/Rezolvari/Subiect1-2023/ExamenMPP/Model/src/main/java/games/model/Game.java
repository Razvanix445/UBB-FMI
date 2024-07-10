package games.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@jakarta.persistence.Entity
@Table(name = "Games")
public class Game extends Entity<Long> {

    private Player player;
    private Boolean isWon;
    private Long NoOfTries;
    private LocalDateTime startingTime;
    private Configuration configuration;

    public Game() {
    }

    public Game(Player player, Boolean isWon, Long NoOfTries, LocalDateTime startingTime, Configuration configuration) {
        this.player = player;
        this.isWon = isWon;
        this.NoOfTries = NoOfTries;
        this.startingTime = startingTime;
        this.configuration = configuration;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "player_id", nullable = false)
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Column(name = "won", nullable = false)
    public Boolean getIsWon() {
        return isWon;
    }

    public void setIsWon(Boolean isWon) {
        this.isWon = isWon;
    }

    @Column(name = "no_of_tries", nullable = false)
    public Long getNoOfTries() {
        return NoOfTries;
    }

    public void setNoOfTries(Long NoOfTries) {
        this.NoOfTries = NoOfTries;
    }

    @Column(name = "starting_date", nullable = false)
    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }

    @ManyToOne
    @JoinColumn(name = "configuration_id", nullable = false)
    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return "Game: " +
                "Player: " + player +
                "; IsWon: " + isWon +
                "; NoOfTries: " + NoOfTries +
                "; StartingTime: " + startingTime +
                "; Configuration: " + configuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game that = (Game) o;
        return player.equals(that.player) &&
                isWon.equals(that.isWon) &&
                NoOfTries.equals(that.NoOfTries) &&
                startingTime.equals(that.startingTime) &&
                configuration.equals(that.configuration);
    }

    @Override
    public int hashCode() {
        int result = player.hashCode();
        result = 31 * result + isWon.hashCode();
        result = 31 * result + NoOfTries.hashCode();
        result = 31 * result + startingTime.hashCode();
        result = 31 * result + configuration.hashCode();
        return result;
    }
}
