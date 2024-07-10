package com.example.comenzirestauranteexamen.observer;

import com.example.comenzirestauranteexamen.events.Event;

public interface Observable <E extends Event>{
    void addObserver(Observer<E> e);

    void removeObserver(Observer<E> e);

    void notifyObservers(E t);
}
