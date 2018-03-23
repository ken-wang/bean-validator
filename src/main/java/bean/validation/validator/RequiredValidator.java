package bean.validation.validator;

public class RequiredValidator implements Validator<Object> {

	public boolean isValid(Object obj) {

		if (String.class.isInstance(obj)) {
			String str = (String) obj;
			return str != null && !str.trim().equals("");
		} else {
			return obj != null;
		}

	}

}
