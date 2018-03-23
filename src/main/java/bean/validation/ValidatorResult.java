package bean.validation;

import bean.validation.validator.Validator;

public class ValidatorResult<T> {

    private String name;
    private Class<? extends Validator<T>> validator;
    private T value;
    private boolean isValid;

    public ValidatorResult() {
    }

    public ValidatorResult(String name, Class<? extends Validator<T>> validator, T value, boolean isValid) {
        this.name = name;
        this.validator = validator;
        this.value = value;
        this.isValid = isValid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<? extends Validator<T>> getValidator() {
        return validator;
    }

    public void setValidator(Class<? extends Validator<T>> validator) {
        this.validator = validator;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "ValidatorResult:{" + "name='" + name + '\'' + ", validator=" + validator.getSimpleName() + ", value="
                + value + ", isValid=" + isValid + '}';
    }

}
