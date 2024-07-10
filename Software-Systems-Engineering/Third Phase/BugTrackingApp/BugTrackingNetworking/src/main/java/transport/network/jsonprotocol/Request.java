package transport.network.jsonprotocol;

import bugtracking.model.*;

import java.util.List;

public class Request {
    private RequestType type;
    private Tester tester;
    private Programmer programmer;
    private Bug bug;
    private User user;
    private String aString;
    private String aString2;
    private Long aLong;
    private List<Tester> testers;
    private List<Programmer> programmers;
    private List<Bug> bugs;
    private List<UserWithTypeDTO> users;

    public Request(){}

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
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

    public String getString() {
        return aString;
    }

    public void setString(String aString) {
        this.aString = aString;
    }

    public String getString2() {
        return aString2;
    }

    public void setString2(String aString2) {
        this.aString2 = aString2;
    }

    public Long getLong() {
        return aLong;
    }

    public void setLong(Long aLong) {
        this.aLong = aLong;
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
        return "Request{" +
                "type=" + type +
                ", tester=" + tester +
                ", programmer=" + programmer +
                ", bug=" + bug +
                ", aString='" + aString + '\'' +
                ", aLong=" + aLong +
                ", testers=" + testers +
                ", programmers=" + programmers +
                ", bugs=" + bugs +
                '}';
    }
}
