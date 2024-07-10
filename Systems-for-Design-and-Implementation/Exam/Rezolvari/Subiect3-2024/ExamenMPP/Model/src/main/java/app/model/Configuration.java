package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "Configurations")
public class Configuration extends Entity<Long> {

    private String letters;
    private String word1;
    private String word2;
    private String word3;
    private String word4;

    public Configuration() {

    }

    public Configuration(String letters, String word1, String word2, String word3, String word4) {
        this.letters = letters;
        this.word1 = word1;
        this.word2 = word2;
        this.word3 = word3;
        this.word4 = word4;
    }

    @Column(name = "letters", nullable = false)
    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    @Column(name = "word1", nullable = false)
    public String getWord1() {
        return word1;
    }

    public void setWord1(String word1) {
        this.word1 = word1;
    }

    @Column(name = "word2", nullable = false)
    public String getWord2() {
        return word2;
    }

    public void setWord2(String word2) {
        this.word2 = word2;
    }

    @Column(name = "word3", nullable = false)
    public String getWord3() {
        return word3;
    }

    public void setWord3(String word3) {
        this.word3 = word3;
    }

    @Column(name = "word4", nullable = false)
    public String getWord4() {
        return word4;
    }

    public void setWord4(String word4) {
        this.word4 = word4;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "letters='" + letters + '\'' +
                ", word1='" + word1 + '\'' +
                ", word2='" + word2 + '\'' +
                ", word3='" + word3 + '\'' +
                ", word4='" + word4 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Configuration that = (Configuration) o;
        return Objects.equals(letters, that.letters) && Objects.equals(word1, that.word1) && Objects.equals(word2, that.word2) && Objects.equals(word3, that.word3) && Objects.equals(word4, that.word4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), letters, word1, word2, word3, word4);
    }
}
