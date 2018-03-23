package bean.validation.exception;

import bean.validation.ValidatorResults;

@SuppressWarnings("serial")
public class ValidatorException extends RuntimeException{

    private ValidatorResults results;
    
    public ValidatorException(ValidatorResults results) {
        this.results = results;
    }

    public ValidatorResults getResults() {
        return results;
    }

    public void setResults(ValidatorResults results) {
        this.results = results;
    }
    
}
