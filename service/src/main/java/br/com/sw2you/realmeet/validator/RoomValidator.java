package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {
    private final RoomRepository roomRepository;

    public RoomValidator(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void validate(CreateRoomDTO createRoomDTO) {
        var validationErros = new ValidationErros();

        if (
            validateName(createRoomDTO.getName(), validationErros) &&
            validateSeats(createRoomDTO.getSeats(), validationErros)
        ) {
            validateNameDuplicate(createRoomDTO.getName(), validationErros);
        }
        throwOnError(validationErros);
    }

    private boolean validateName(String name, ValidationErros validationErros) {
        return (
            validateRequired(name, ROOM_NAME, validationErros) &&
            validateMaxLength(name, ROOM_NAME, ROOM_NAME_MAX_LENGTH, validationErros)
        );
    }

    private boolean validateSeats(Integer seats, ValidationErros validationErros) {
        return (
            validateRequired(seats, ROOM_SEATS, validationErros) &&
            validateMinValue(seats, ROOM_SEATS, ROOM_SEATS_MIN_VALUE, validationErros) &&
            validateMaxValue(seats, ROOM_SEATS, ROOM_SEATS_MAX_VALUE, validationErros)
        );
    }

    private void validateNameDuplicate(String name, ValidationErros validationErros) {
        roomRepository
            .findByNameAndActive(name, true)
            .ifPresent(__ -> validationErros.add(ROOM_NAME, ROOM_NAME + DUPLICATE));
    }
}
