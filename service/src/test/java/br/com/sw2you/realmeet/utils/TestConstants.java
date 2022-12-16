package br.com.sw2you.realmeet.utils;

import br.com.sw2you.realmeet.util.DateUtils;

import java.time.OffsetDateTime;

import static br.com.sw2you.realmeet.util.DateUtils.now;

public final class TestConstants {
    public static final long DEFAULT_ROOM_ID = 1L;
    public static final String DEFAULT_ROOM_NAME = "Room A";
    public static final int DEFAULT_ROOM_SEATS = 7;

    public static final String DEFAULT_ALLOCATION_SUBJECT = "Some Subject";
    public static final String DEFAULT_EMPLOYEE_NAME = "Mateus Dantas";
    public static final String DEFAULT_EMPLOYEE_EMAIL = "mateusdantas.dev@gmail.com";
    public static final OffsetDateTime DEFAULT_ALLOCATION_START_AT = now().plusDays(1);
    public static final OffsetDateTime DEFAULT_ALLOCATION_END_AT = DEFAULT_ALLOCATION_START_AT.plusHours(1);

    private TestConstants() {}
}
