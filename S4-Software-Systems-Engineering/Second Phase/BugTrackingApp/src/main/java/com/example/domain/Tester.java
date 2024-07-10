package com.example.domain;

public class Tester extends User {
    public Tester(String username, String password, String email, String name) {
        super(username, password, email, name);
    }

    public Bug reportBug(String name, String description, Programmer assignedTo) {
        return new Bug(name, description, this, assignedTo);
    }
}
