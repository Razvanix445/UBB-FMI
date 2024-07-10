package ir.map.g222.sem7demo.validators;

public interface Validator<T> {

    void validate(T entity) throws ValidationException;
}
