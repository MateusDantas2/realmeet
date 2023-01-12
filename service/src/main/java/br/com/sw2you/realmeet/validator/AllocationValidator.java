package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.util.DateUtils.now;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;

import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.api.model.UpdateAllocationDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import java.time.Duration;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;

@Component
public class AllocationValidator {
    private final AllocationRepository allocationRepository;

    public AllocationValidator(AllocationRepository allocationRepository) {
        this.allocationRepository = allocationRepository;
    }

    public void validate(CreateAllocationDTO createAllocationDTO) {
        var validationErros = new ValidationErros();
        validateSubject(createAllocationDTO.getSubject(), validationErros);
        validateEmployeeName(createAllocationDTO.getEmployeeName(), validationErros);
        validateEmployeeEmail(createAllocationDTO.getEmployeeEmail(), validationErros);
        validateDates(createAllocationDTO.getStartAt(), createAllocationDTO.getEndAt(), validationErros);

        throwOnError(validationErros);
    }

    public void validate(Long allocationId, UpdateAllocationDTO updateAllocationDTO) {
        var validationErros = new ValidationErros();

        validateRequired(allocationId, ALLOCATION_ID, validationErros);
        validateSubject(updateAllocationDTO.getSubject(), validationErros);
        validateDates(updateAllocationDTO.getStartAt(), updateAllocationDTO.getEndAt(), validationErros);

        throwOnError(validationErros);
    }

    private void validateSubject(String subject, ValidationErros validationErros) {
        validateRequired(subject, ALLOCATION_SUBJECT, validationErros);
        validateMaxLength(subject, ALLOCATION_SUBJECT, ALLOCATION_SUBJECT_MAX_LENGTH, validationErros);
    }

    private void validateEmployeeName(String employeeName, ValidationErros validationErros) {
        validateRequired(employeeName, ALLOCATION_EMPLOYEE_NAME, validationErros);
        validateMaxLength(employeeName, ALLOCATION_EMPLOYEE_NAME, ALLOCATION_EMPLOYEE_NAME_MAX_LENGTH, validationErros);
    }

    private void validateEmployeeEmail(String employeeEmail, ValidationErros validationErros) {
        validateRequired(employeeEmail, ALLOCATION_EMPLOYEE_EMAIL, validationErros);
        validateMaxLength(
            employeeEmail,
            ALLOCATION_EMPLOYEE_EMAIL,
            ALLOCATION_EMPLOYEE_EMAIL_MAX_LENGTH,
            validationErros
        );
    }

    private void validateDates(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErros validationErros) {
        if (validateDatesPresent(startAt, endAt, validationErros)) {
            validateDateOrdering(startAt, endAt, validationErros);
            validateDateInTheFuture(startAt, validationErros);
            validateDuration(startAt, endAt, validationErros);
            validateIfTimeAvailable(startAt, endAt, validationErros);
        }
    }

    private boolean validateDatesPresent(
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        ValidationErros validationErros
    ) {
        return (
            validateRequired(startAt, ALLOCATION_START_AT, validationErros) &&
            validateRequired(endAt, ALLOCATION_END_AT, validationErros)
        );
    }

    private void validateDateOrdering(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErros validationErros) {
        if (startAt.isEqual(endAt) || startAt.isAfter(endAt)) {
            validationErros.add(ALLOCATION_START_AT, ALLOCATION_START_AT + INCONSISTENT);
        }
    }

    private void validateDateInTheFuture(OffsetDateTime date, ValidationErros validationErros) {
        if (date.isBefore(now())) {
            validationErros.add(ALLOCATION_START_AT, ALLOCATION_START_AT + IN_THE_PAST);
        }
    }

    private void validateDuration(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErros validationErros) {
        if (Duration.between(startAt, endAt).getSeconds() > ALLOCATION_MAX_DURATION_SECONDS) {
            validationErros.add(ALLOCATION_END_AT, ALLOCATION_END_AT + EXCEEDS_DURATION);
        }
    }

    private void validateIfTimeAvailable(
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        ValidationErros validationErros
    ) {}
}
