package ir.map.g222.validators;

public interface Validator<T> {

    void validate(T entity) throws ValidationException;
}
