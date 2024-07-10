package com.example.lab7.controller;

public class ConvertorLuna {
    public static String convertorLuna(int luna) {
        return switch (luna) {
            case 1 -> "Ianuarie";
            case 2 -> "Februarie";
            case 3 -> "Martie";
            case 4 -> "Aprilie";
            case 5 -> "Mai";
            case 6 -> "Iunie";
            case 7 -> "Iulie";
            case 8 -> "August";
            case 9 -> "Septembrie";
            case 10 -> "Octombrie";
            case 11 -> "Noiembrie";
            case 12 -> "Decembrie";
            default -> "Luna Invalida";
        };
    }
}