package com.restaurantreservation.api.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocaleUtil {
    public static ZoneId zoneId = ZoneId.of("Asia/Seoul");
    public static class DateTime {
        public static LocalDateTime now() {
            return LocalDateTime.now(zoneId);
        }
    }

    public static class Date {
        public static LocalDate now() {
            return LocalDate.now(zoneId);
        }
    }
}
