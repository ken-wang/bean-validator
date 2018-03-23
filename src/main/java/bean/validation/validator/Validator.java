package bean.validation.validator;

public interface Validator<T> {

    boolean isValid(T value);

}
