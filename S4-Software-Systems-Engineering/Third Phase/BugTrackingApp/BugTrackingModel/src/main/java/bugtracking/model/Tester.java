package bugtracking.model;

import bugtracking.model.Bug;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Testers")
public class Tester extends User {
    public Tester(String username, String password, String email, String name) {
        super(username, password, email, name);
    }

    public Tester() {

    }

    public Bug reportBug(String name, String description, Programmer assignedTo) {
        return new Bug(name, description, this, assignedTo);
    }
}
