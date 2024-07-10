package com.example.lab7.utils.events;

import com.example.lab7.domain.Utilizator;

public class UtilizatorChangeEvent implements Event {
    private ChangeEventType type;
    private Utilizator data, oldData;

    public UtilizatorChangeEvent(ChangeEventType type, Utilizator data) {
        this.type = type;
        this.data = data;
    }

    public UtilizatorChangeEvent(ChangeEventType type, Utilizator data, Utilizator oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Utilizator getData() {
        return data;
    }

    public Utilizator getOldData() {
        return oldData;
    }
}
