package com.example.domain;

public class Programmer extends User {
    public Programmer(String username, String password, String email, String name) {
        super(username, password, email, name);
    }

    public void eliminateBug(Bug bug) {
        bug.setStatus(BugStatus.SOLVED);
    }
}
