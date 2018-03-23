package bean.validation.provider;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import bean.validation.validator.Email;
import bean.validation.validator.EmailValidator;
import bean.validation.validator.Length;
import bean.validation.validator.LengthValidator;
import bean.validation.validator.Required;
import bean.validation.validator.RequiredValidator;
import bean.validation.validator.Validator;

public class ValidatorProvider {

    private final Map<Class<? extends Annotation>, Class<? extends Validator<?>>> map = new HashMap<>();

    public ValidatorProvider() {
        init();
    }

    private void init() {
        map.put(Required.class, RequiredValidator.class);
        map.put(Length.class, LengthValidator.class);
        map.put(Email.class, EmailValidator.class);
    }

    public <M extends Validator<?>> M newInstance(Annotation annotation) throws Exception {
        if (annotation != null) {
            Class<? extends Validator<?>> validatorClass = map.get(annotation.annotationType());
            return newInstance(annotation, validatorClass);
        }
        return null;
    }

    private <M extends Validator<?>> M newInstance(Annotation annotation, Class<? extends Validator<?>> validatorClass)
            throws Exception {

        @SuppressWarnings("unchecked")
        M validator = (M) validatorClass.newInstance();
        Method[] methods = annotation.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (isSkippedMethod(method)) {
                continue;
            }

            Object value = method.invoke(annotation);
            BeanInfo bean = Introspector.getBeanInfo(validatorClass);
            PropertyDescriptor[] pds = bean.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                if (pd.getName().equals(method.getName())) {
                    Method setter = pd.getWriteMethod();
                    setter.invoke(validator, value);
                }
            }
        }
        return validator;
    }

    private boolean isSkippedMethod(final Method method) {
        final String methodName = method.getName();
        boolean skipped = false;
        skipped |= methodName.equals("equals");
        skipped |= methodName.equals("toString");
        skipped |= methodName.equals("hashCode");
        skipped |= methodName.equals("annotationType");
        return skipped;
    }

}
