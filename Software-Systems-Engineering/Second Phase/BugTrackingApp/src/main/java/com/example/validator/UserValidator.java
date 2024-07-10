package com.example.validator;

import com.example.domain.User;

import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {

    @Override
    public void validate(User entity) throws ValidationException {
        if (!Pattern.matches("^[A-Za-z]{2,50}$", entity.getName())) {
            throw new ValidationException("User's data is incorrect!");
        }
        if (entity.getName() == null || entity.getUsername() == null || entity.getPassword() == null)
            throw new ValidationException("User's data is incorrect!");
        if (entity.getUsername().isEmpty() || entity.getPassword().isEmpty() || entity.getEmail().isEmpty()) {
            throw new ValidationException("User's data is incorrect!");
        }
    }
}