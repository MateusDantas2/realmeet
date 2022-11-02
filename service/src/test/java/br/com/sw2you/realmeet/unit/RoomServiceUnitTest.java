package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.utils.MapperUtils.roomMapper;
import static br.com.sw2you.realmeet.utils.TestConstants.DEFAULT_ROOM_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import br.com.sw2you.realmeet.core.BaseUnitTeste;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.service.RoomService;
import br.com.sw2you.realmeet.utils.TestDataCreator;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class RoomServiceUnitTest extends BaseUnitTeste {
    private RoomService victim;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void steupEach() {
        victim = new RoomService(roomRepository, roomMapper());
    }

    @Test
    void testeGetRoom() {
        var room = TestDataCreator.newRoomBuilder().id(DEFAULT_ROOM_ID).build();
        when(roomRepository.findById(DEFAULT_ROOM_ID)).thenReturn(Optional.of(room));

        var dto = victim.getRoom(DEFAULT_ROOM_ID);
        assertEquals(room.getId(), dto.getId());
        assertEquals(room.getName(), dto.getName());
        assertEquals(room.getSeats(), dto.getSeats());
    }
}
