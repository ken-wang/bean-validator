package bean.validation;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import bean.validation.provider.ValidatorProvider;
import bean.validation.validator.Validator;

public class ValidationExecutor {

    private ValidatorProvider provider;

    public ValidationExecutor() {
        this.provider = new ValidatorProvider();
    }

    public ValidationExecutor(ValidatorProvider provider) {
        this.provider = provider;
    }

    public ValidatorResults validate(Object obj) throws Exception {
        ValidatorResults results = new ValidatorResults();
        Field[] fields = obj.getClass().getDeclaredFields();
        BeanInfo bean = Introspector.getBeanInfo(obj.getClass());

        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            results.addAll(validate(annotations, field.getName(), obj));
        }
        for (PropertyDescriptor pd : bean.getPropertyDescriptors()) {
            Method getter = pd.getReadMethod();
            Annotation[] annotations = getter.getDeclaredAnnotations();
            results.addAll(validate(annotations, pd.getName(), obj));
        }
        return results;
    }

    private <T> List<ValidatorResult<?>> validate(Annotation[] annotations, String name, Object obj) throws Exception {
        List<ValidatorResult<?>> results = new ArrayList<>();
        for (Annotation annotation : annotations) {
            @SuppressWarnings("unchecked")
            T value = (T) getPropertyValue(obj, name);
            Validator<T> validator = provider.newInstance(annotation);
            boolean isValid = validate(validator, value);
            ValidatorResult<T> result = new ValidatorResult<>(name, (Class<? extends Validator<T>>) validator.getClass(), value, isValid);
            results.add(result);
        }
        return results;
    }

    private Object getPropertyValue(Object obj, String name) {
        try {
            BeanInfo bean = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor des = getPropertyDescriptor(name, bean);
            Method getter = des.getReadMethod();
            return getter.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private PropertyDescriptor getPropertyDescriptor(String key, BeanInfo bean) {
        PropertyDescriptor[] pds = bean.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().equals(key)) {
                return pd;
            }
        }
        return null;
    }

    private <T> boolean validate(Validator<T> validator, T value) {
        try {
            return validator.isValid(value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
