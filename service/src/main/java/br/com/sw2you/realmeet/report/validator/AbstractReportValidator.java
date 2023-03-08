package br.com.sw2you.realmeet.report.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.DATE_FROM;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.EMAIL;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.throwOnError;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.validateRequired;

import br.com.sw2you.realmeet.report.model.AbstractReportData;
import br.com.sw2you.realmeet.validator.ValidationErros;

public abstract class AbstractReportValidator {

    public void validate(AbstractReportData abstractReportData) {
        var validationErros = new ValidationErros();

        validateRequired(abstractReportData.getEmail(), EMAIL, validationErros);
        validate(abstractReportData, validationErros);

        throwOnError(validationErros);
    }

    protected abstract void validate(AbstractReportData reportData, ValidationErros validationErros);
}
