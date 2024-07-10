package ir.map.g222.sem7demo.domain;

import java.time.LocalDateTime;

public class Prietenie extends Entity<Long> {

    LocalDateTime date;

    Long idUtilizator1;
    Long idUtilizator2;

    public Prietenie(Long idUtilizator1, Long idUtilizator2) {
        this.idUtilizator1 = idUtilizator1;
        this.idUtilizator2 = idUtilizator2;
        this.date = LocalDateTime.now();
    }

    public Long getIdUtilizator1() {
        return idUtilizator1;
    }

    public Long getIdUtilizator2() {
        return idUtilizator2;
    }

    /*
    @return the date when the friendship was created
     */
    public LocalDateTime getDate() {
        return date;
    }
}
