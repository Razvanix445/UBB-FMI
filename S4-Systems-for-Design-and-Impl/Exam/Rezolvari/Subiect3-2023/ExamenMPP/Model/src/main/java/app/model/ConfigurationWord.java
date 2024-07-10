package app.model;

import jakarta.persistence.*;

import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "ConfigurationsWords")
public class ConfigurationWord extends Entity<Long> {

    private Configuration configuration;
    private Word word;
    private Long word_number;

    public ConfigurationWord() {
    }

    public ConfigurationWord(Configuration configuration, Word word, Long word_number) {
        this.configuration = configuration;
        this.word = word;
        this.word_number = word_number;
    }

    @ManyToOne
    @JoinColumn(name = "configuration_id", nullable = false)
    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @ManyToOne
    @JoinColumn(name = "word_id", nullable = false)
    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    @Column(name = "word_number", nullable = false)
    public Long getWordNumber() {
        return word_number;
    }

    public void setWordNumber(Long wordNumber) {
        this.word_number = wordNumber;
    }

    @Override
    public String toString() {
        return "ConfigurationWord{" +
                "configuration=" + configuration +
                ", word=" + word +
                ", word_number=" + word_number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurationWord that = (ConfigurationWord) o;
        return Objects.equals(configuration, that.configuration) && Objects.equals(word, that.word) && Objects.equals(word_number, that.word_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configuration, word, word_number);
    }
}
