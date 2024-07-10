package bugtracking.services.validator;

import bugtracking.model.User;

import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {

    @Override
    public void validate(User entity) throws ValidationException {
        if (!Pattern.matches("^[A-Za-z ]{2,50}$", entity.getName())) {
            throw new ValidationException("User's name is incorrect!");
        }
        if (entity.getName() == null || entity.getUsername() == null || entity.getPassword() == null)
            throw new ValidationException("User's name, username or password is incorrect!");
        if (entity.getUsername().isEmpty() || entity.getPassword().isEmpty() || entity.getEmail().isEmpty()) {
            throw new ValidationException("User's username, password or email are empty!");
        }
        if (!Pattern.matches("^[A-Za-z0-9]{2,50}$", entity.getUsername())) {
            throw new ValidationException("User's username is incorrect!");
        }
        if (!Pattern.matches("^[A-Za-z0-9]{2,50}$", entity.getPassword())) {
            throw new ValidationException("User's password is incorrect!");
        }
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", entity.getEmail())) {
            throw new ValidationException("User's email is incorrect!");
        }
    }
}