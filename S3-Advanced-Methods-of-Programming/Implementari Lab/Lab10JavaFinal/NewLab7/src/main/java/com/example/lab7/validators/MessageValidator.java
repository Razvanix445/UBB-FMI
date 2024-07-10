package com.example.lab7.validators;

import com.example.lab7.domain.Message;

public class MessageValidator implements Validator<Message> {

    @Override
    public void validate(Message entity) throws ValidationException {
        if (entity.getMessage() == null || entity.getDate() == null)
            throw new ValidationException("Invalid data!");
    }
}
