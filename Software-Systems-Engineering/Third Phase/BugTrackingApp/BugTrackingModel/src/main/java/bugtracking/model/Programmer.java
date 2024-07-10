package bugtracking.model;

import bugtracking.model.Bug;
import bugtracking.model.BugStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Programmers")
public class Programmer extends User {
    public Programmer(String username, String password, String email, String name) {
        super(username, password, email, name);
    }

    public Programmer() {

    }

    public void eliminateBug(Bug bug) {
        bug.setStatus(BugStatus.SOLVED);
    }
}
