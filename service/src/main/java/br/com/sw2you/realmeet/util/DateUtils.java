package br.com.sw2you.realmeet.util;

import static java.time.temporal.ChronoUnit.MILLIS;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public final class DateUtils {
    public static final ZoneOffset DEFAULT_TIMEZONE = ZoneOffset.of("-03:00");

    private DateUtils() {}

    public static OffsetDateTime now() {
        return OffsetDateTime.now(DEFAULT_TIMEZONE).truncatedTo(MILLIS);
    }

    public static boolean isOverlapping(
        OffsetDateTime start1,
        OffsetDateTime end1,
        OffsetDateTime start2,
        OffsetDateTime end2
    ) {
        return start1.compareTo(end2) < 0 && end1.compareTo(start2) > 0;
    }
}
