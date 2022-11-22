package br.com.sw2you.realmeet.exception;

import br.com.sw2you.realmeet.validator.ValidationError;
import br.com.sw2you.realmeet.validator.ValidationErros;

public class InvalidRequestException extends RuntimeException {
    private final ValidationErros validationErrors;

    public InvalidRequestException(ValidationErros validationErrors) {
        super(validationErrors.toString());
        this.validationErrors = validationErrors;
    }

    public InvalidRequestException(ValidationError validationError) {
        this(new ValidationErros().add(validationError));
    }

    public ValidationErros getValidationErrors() {
        return validationErrors;
    }
}
