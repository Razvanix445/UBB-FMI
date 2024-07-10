package app.model;

import java.util.List;
import java.util.Objects;

public class GameDetailsDTO {

    private Long gameId;
    private List<PositionDTO> positions;
    private String playerAlias;
    private List<Word> words;
    private Long score;

    public GameDetailsDTO(Long gameId, List<PositionDTO> positions, String playerAlias, List<Word> words, Long score) {
        this.gameId = gameId;
        this.positions = positions;
        this.playerAlias = playerAlias;
        this.words = words;
        this.score = score;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public List<PositionDTO> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionDTO> positions) {
        this.positions = positions;
    }

    public String getPlayerAlias() {
        return playerAlias;
    }

    public void setPlayerAlias(String playerAlias) {
        this.playerAlias = playerAlias;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "GameDetailsDTO{" +
                "gameId=" + gameId +
                ", positions=" + positions +
                ", playerAlias='" + playerAlias + '\'' +
                ", words=" + words +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDetailsDTO that = (GameDetailsDTO) o;
        return Objects.equals(gameId, that.gameId) && Objects.equals(positions, that.positions) && Objects.equals(playerAlias, that.playerAlias) && Objects.equals(words, that.words) && Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, positions, playerAlias, words, score);
    }
}
