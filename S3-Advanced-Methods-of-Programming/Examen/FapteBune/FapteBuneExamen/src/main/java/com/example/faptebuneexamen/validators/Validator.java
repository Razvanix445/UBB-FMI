package com.example.faptebuneexamen.validators;

public interface Validator<T> {

    void validate(T entity) throws ValidationException;
}
