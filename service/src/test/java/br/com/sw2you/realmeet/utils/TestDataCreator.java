package br.com.sw2you.realmeet.utils;

import br.com.sw2you.realmeet.domain.entity.Room;

import static br.com.sw2you.realmeet.utils.TestConstants.DEFAULT_ROOM_NAME;
import static br.com.sw2you.realmeet.utils.TestConstants.DEFAULT_ROOM_SEATS;

public final class TestDataCreator {

    private TestDataCreator() {

    }

    public static Room.Builder newRoomBuilder() {
        return Room.newBuilder().name(DEFAULT_ROOM_NAME).seats(DEFAULT_ROOM_SEATS);
    }
}
