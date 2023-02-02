package br.com.sw2you.realmeet.exception;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.INVALID;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.ORDER_BY;

import br.com.sw2you.realmeet.validator.ValidationError;
import br.com.sw2you.realmeet.validator.ValidationErros;

public class InvalidOrderByFieldException extends InvalidRequestException {

    public InvalidOrderByFieldException() {
        super(new ValidationError(ORDER_BY, ORDER_BY + INVALID));
    }
}
