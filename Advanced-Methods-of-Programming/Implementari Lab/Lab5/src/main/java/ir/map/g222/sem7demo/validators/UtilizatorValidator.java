package ir.map.g222.sem7demo.validators;

import ir.map.g222.sem7demo.domain.Utilizator;

import java.util.regex.Pattern;

public class UtilizatorValidator implements Validator<Utilizator> {

    @Override
    public void validate(Utilizator entity) throws ValidationException {
        if (!Pattern.matches("^[A-Za-z]{2,50}$", entity.getPrenume()) || !Pattern.matches("^[A-Za-z]{2,50}$", entity.getNume())) {
            throw new ValidationException("Datele utilizatorului sunt incorecte!");
        }
        if (entity.getPrenume() == null || entity.getNume() == null)
            throw new ValidationException("Datele utilizatorului sunt incorecte!");
    }
}
