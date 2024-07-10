package com.example.validator;

public interface Validator<T> {

    void validate(T entity) throws ValidationException;
}