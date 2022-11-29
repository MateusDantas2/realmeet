package br.com.sw2you.realmeet.service;

import static java.util.Objects.requireNonNull;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.RoomDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.domain.entity.Room;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import br.com.sw2you.realmeet.mapper.RoomMapper;
import br.com.sw2you.realmeet.validator.RoomValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final RoomValidator roomValidator;

    public RoomService(RoomRepository roomRepository, RoomValidator roomValidator, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomValidator = roomValidator;
        this.roomMapper = roomMapper;
    }

    public RoomDTO getRoom(Long id) {
        requireNonNull(id);
        Room room = getActiveRoomOrThrow(id);
        return roomMapper.fromEntityToDto(room);
    }

    public RoomDTO createRoom(CreateRoomDTO createRoomDTO) {
        roomValidator.validate(createRoomDTO);
        var room = roomMapper.fromCreateRoomDtoToEntity(createRoomDTO);
        roomRepository.save(room);
        return roomMapper.fromEntityToDto(room);
    }

    @Transactional
    public void deletedRoom(Long roomId) {
        getActiveRoomOrThrow(roomId);
        roomRepository.deactivate(roomId);
    }

    @Transactional
    public void updateRoom(Long roomId, UpdateRoomDTO updateRoomDTO) {
        getActiveRoomOrThrow(roomId);
        roomValidator.validate(roomId, updateRoomDTO);
        roomRepository.updateRoom(roomId, updateRoomDTO.getName(), updateRoomDTO.getSeats());
    }

    private Room getActiveRoomOrThrow(Long id) {
        requireNonNull(id);
        return roomRepository
            .findByIdAndActive(id, true)
            .orElseThrow(() -> new RoomNotFoundException("A sala " + id + " não foi encontrada!"));
    }
}
