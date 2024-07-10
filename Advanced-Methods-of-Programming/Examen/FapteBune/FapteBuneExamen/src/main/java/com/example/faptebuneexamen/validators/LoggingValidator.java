package com.example.faptebuneexamen.validators;

import com.example.faptebuneexamen.domain.Oras;
import com.example.faptebuneexamen.domain.Persoana;

import java.util.regex.Pattern;

public class LoggingValidator implements Validator<Persoana> {

    @Override
    public void validate(Persoana entity) throws ValidationException {
        if (!Pattern.matches("^[A-Za-z]{2,50}$", entity.getPrenume()) || !Pattern.matches("^[A-Za-z]{2,50}$", entity.getNume())) {
            throw new ValidationException("Datele utilizatorului sunt incorecte!");
        }
        if (entity.getPrenume() == null || entity.getNume() == null)
            throw new ValidationException("Datele utilizatorului sunt incorecte!");
        if (entity.getUsername().isEmpty() || entity.getParola().isEmpty() || entity.getStrada().isEmpty() || entity.getNumarStrada().isEmpty() || entity.getTelefon().isEmpty()) {
            throw new ValidationException("Datele utilizatorului sunt incorecte!");
        }
    }
}
