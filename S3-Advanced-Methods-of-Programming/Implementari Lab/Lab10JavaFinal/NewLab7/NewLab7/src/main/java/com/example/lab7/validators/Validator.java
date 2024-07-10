package com.example.lab7.validators;

public interface Validator<T> {

    void validate(T entity) throws ValidationException;
}
