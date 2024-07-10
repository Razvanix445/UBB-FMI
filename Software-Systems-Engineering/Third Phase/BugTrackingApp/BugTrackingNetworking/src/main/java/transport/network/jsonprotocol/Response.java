package transport.network.jsonprotocol;

import bugtracking.model.*;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private ResponseType type;
    private String errorMessage;
    private Tester tester;
    private Programmer programmer;
    private Bug bug;
    private User user;
    private List<Tester> testers;
    private List<Programmer> programmers;
    private List<Bug> bugs;
    private List<UserWithTypeDTO> users;

    public Response() {
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Tester getTester() {
        return tester;
    }

    public void setTester(Tester tester) {
        this.tester = tester;
    }

    public Programmer getProgrammer() {
        return programmer;
    }

    public void setProgrammer(Programmer programmer) {
        this.programmer = programmer;
    }

    public Bug getBug() {
        return bug;
    }

    public void setBug(Bug bug) {
        this.bug = bug;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tester> getTesters() {
        return testers;
    }

    public void setTesters(List<Tester> testers) {
        this.testers = testers;
    }

    public List<Programmer> getProgrammers() {
        return programmers;
    }

    public void setProgrammers(List<Programmer> programmers) {
        this.programmers = programmers;
    }

    public List<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(List<Bug> bugs) {
        this.bugs = bugs;
    }

    public List<UserWithTypeDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithTypeDTO> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", errorMessage='" + errorMessage + '\'' +
                ", tester=" + tester +
                ", programmer=" + programmer +
                ", bug=" + bug +
                ", testers=" + testers +
                ", programmers=" + programmers +
                ", bugs=" + bugs +
                '}';
    }
}
