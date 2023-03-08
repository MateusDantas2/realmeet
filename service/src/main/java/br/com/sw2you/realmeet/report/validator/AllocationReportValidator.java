package br.com.sw2you.realmeet.report.validator;

import static br.com.sw2you.realmeet.util.Constants.ALLOCATION_REPORT_MAX_MONTHS_INTERVAL;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.validateRequired;

import br.com.sw2you.realmeet.report.model.AbstractReportData;
import br.com.sw2you.realmeet.report.model.AllocationReportData;
import br.com.sw2you.realmeet.validator.ValidationErros;
import java.time.Period;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AllocationReportValidator extends AbstractReportValidator {
    private final int maxMonthInterval;

    public AllocationReportValidator(@Value(ALLOCATION_REPORT_MAX_MONTHS_INTERVAL) int maxMonthInterval) {
        this.maxMonthInterval = maxMonthInterval;
    }

    @Override
    protected void validate(AbstractReportData reportData, ValidationErros validationErros) {
        var allocationReportData = (AllocationReportData) reportData;

        validateRequired(allocationReportData.getDateFrom(), DATE_FROM, validationErros);
        validateRequired(allocationReportData.getDateTo(), DATE_TO, validationErros);

        if (allocationReportData.getDateFrom().isAfter(allocationReportData.getDateTo())) {
            validationErros.add(DATE_FROM, DATE_FROM + INCONSISTENT);
        } else if (
            Period.between(allocationReportData.getDateFrom(), allocationReportData.getDateTo()).getMonths() >
            maxMonthInterval
        ) {
            validationErros.add(DATE_TO, DATE_TO + EXCEEDS_INTERVAL);
        }
    }
}
