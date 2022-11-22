package br.com.sw2you.realmeet.validator;

public final class ValidatorConstants {
    public static final String ROOM_NAME = "name";
    public static final int ROOM_NAME_MAX_LENGTH = 20;
    public static final String ROOM_SEATS = "seats";
    public static final int ROOM_SEATS_MIN_VALUE = 2;
    public static final int ROOM_SEATS_MAX_VALUE = 20;

    public static final String MISSING = ".missing";
    public static final String EXCEEDS_MAX_LENGTH = ".exceedsMaxLength";
    public static final String EXCEEDS_MAX_VALUE = ".exceedsMaxValue";
    public static final String BELOW_MIN_VALUE = ".belowMinValue";
    public static final String DUPLICATE = ".duplicate";

    private ValidatorConstants() {}
}
