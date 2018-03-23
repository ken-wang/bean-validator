package bean.validation.validator;

public class LengthValidator implements Validator<String>{

	private int minLength = -1;
	private int maxLength = -1;
	
	public boolean isValid(String value) {

        if (value == null) {
            return true;
        }

        int length = value.length();
        boolean isValid = true;
        if (minLength > -1) {
            isValid &= length > minLength;
        }

        if (maxLength > -1) {
            isValid &= length < maxLength;
        }
		
		return isValid;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	
}
