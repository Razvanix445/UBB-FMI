package com.example.faptebuneexamen.observer;

import com.example.faptebuneexamen.events.Event;

public interface Observer <E extends Event>{
    void update(E e);
}
