package com.example.faptebuneexamen.events;

public class NevoieChangeEvent implements Event {

    private final ChangeEventType changeEventType;

    public NevoieChangeEvent(ChangeEventType changeEventType) {
        this.changeEventType = changeEventType;
    }

    public ChangeEventType getChangeEventType() {
        return changeEventType;
    }
}
