package com.example.lab7;

import com.example.lab7.domain.Utilizator;

public class Session {

    private static Utilizator loggedUser;

    public static Utilizator getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(Utilizator user) {
        loggedUser = user;
    }

    public static void clearSession() {
        loggedUser = null;
    }

    public static boolean isLoggedIn() {
        return loggedUser != null;
    }
}
