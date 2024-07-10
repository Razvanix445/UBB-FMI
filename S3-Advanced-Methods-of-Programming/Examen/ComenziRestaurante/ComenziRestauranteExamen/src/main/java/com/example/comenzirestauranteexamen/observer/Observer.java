package com.example.comenzirestauranteexamen.observer;

import com.example.comenzirestauranteexamen.events.Event;

public interface Observer <E extends Event>{
    void update(E e);
}
