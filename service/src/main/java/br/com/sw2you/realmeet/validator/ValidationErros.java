package br.com.sw2you.realmeet.validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.data.util.Streamable;

public class ValidationErros implements Streamable<ValidationError> {
    private final List<ValidationError> validationErrorList;

    public ValidationErros(List<ValidationError> validationErrorList) {
        this.validationErrorList = new ArrayList<>();
    }

    public ValidationErros add(String field, String errorCode) {
        return add(new ValidationError(field, errorCode));
    }

    public ValidationErros add(ValidationError validationError) {
        validationErrorList.add(validationError);
        return this;
    }

    public ValidationError getError(int index) {
        return validationErrorList.get(index);
    }

    public int getNumberOfErros() {
        return validationErrorList.size();
    }

    public boolean hasErros() {
        return !validationErrorList.isEmpty();
    }

    @Override
    public String toString() {
        return "ValidationErros{" + "validationErrorList=" + validationErrorList + '}';
    }

    @Override
    public Iterator<ValidationError> iterator() {
        return validationErrorList.iterator();
    }
}
