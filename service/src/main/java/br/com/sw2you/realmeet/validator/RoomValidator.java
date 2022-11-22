package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {

    public void validate(CreateRoomDTO createRoomDTO) {
        var validationErros = new ValidationErros();

        // Room Name
        validateRequired(createRoomDTO.getName(), ROOM_NAME, validationErros);
        validateMaxLength(createRoomDTO.getName(), ROOM_NAME, ROOM_NAME_MAX_LENGTH, validationErros);

        // Room Seats
        validateRequired(createRoomDTO.getSeats(), ROOM_SEATS, validationErros);
        validateMinValue(createRoomDTO.getSeats(), ROOM_SEATS, ROOM_SEATS_MIN_VALUE, validationErros);
        validateMaxValue(createRoomDTO.getSeats(), ROOM_SEATS, ROOM_SEATS_MAX_VALUE, validationErros);

        throwOnError(validationErros);
    }
}
