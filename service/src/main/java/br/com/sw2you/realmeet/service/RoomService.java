package br.com.sw2you.realmeet.service;

import static java.util.Objects.requireNonNull;

import br.com.sw2you.realmeet.domain.entity.Room;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room findById(Long id) {
        requireNonNull(id);
        return roomRepository.findById(id).orElseThrow(RoomNotFoundException::new);
    }
}
