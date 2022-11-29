package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;
import static java.util.Objects.isNull;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
            validateNameDuplicate(null, createRoomDTO.getName(), validationErros);
        }

        throwOnError(validationErros);
    }

    public void validate(Long roomId, UpdateRoomDTO updateRoomDTO) {
        var validationErros = new ValidationErros();

        if (
            validateRequired(roomId, ROOM_ID, validationErros) &&
            validateName(updateRoomDTO.getName(), validationErros) &&
            validateSeats(updateRoomDTO.getSeats(), validationErros)
        ) {
            validateNameDuplicate(roomId, updateRoomDTO.getName(), validationErros);
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

    private void validateNameDuplicate(Long roomIdToExclude, String name, ValidationErros validationErros) {
        roomRepository
            .findByNameAndActive(name, true)
            .ifPresent(room -> {
                if (!isNull(roomIdToExclude) && !Objects.equals(room.getId(), roomIdToExclude)) {
                    validationErros.add(ROOM_NAME, ROOM_SEATS + DUPLICATE);
                }
            });
    }
}
