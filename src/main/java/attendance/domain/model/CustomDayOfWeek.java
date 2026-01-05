package attendance.domain.model;

import java.time.DayOfWeek;
import java.util.Arrays;

import static attendance.global.exception.ErrorMessage.INVALID_WEEKDAY_KOREA_NAME;
import static attendance.global.exception.ErrorMessage.WEEKDAY_NOT_FOUND;

public enum CustomDayOfWeek {

    MON(DayOfWeek.MONDAY, "월"),
    TUE(DayOfWeek.TUESDAY, "화"),
    WED(DayOfWeek.WEDNESDAY, "수"),
    THU(DayOfWeek.THURSDAY, "목"),
    FRI(DayOfWeek.FRIDAY, "금"),
    SAT(DayOfWeek.SATURDAY, "토"),
    SUN(DayOfWeek.SUNDAY, "일");

    CustomDayOfWeek(DayOfWeek dayOfWeek, String koreaName) {
        this.dayOfWeek = dayOfWeek;
        this.koreaName = koreaName;
    }

    private final DayOfWeek dayOfWeek;
    private final String koreaName;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public String getKoreaName() {
        return koreaName;
    }

    public static CustomDayOfWeek of(String dayOfWeekKoreaName) {
        return Arrays.stream(CustomDayOfWeek.values())
                .filter(customDayOfWeek -> customDayOfWeek.getKoreaName().equals(dayOfWeekKoreaName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_WEEKDAY_KOREA_NAME.getMessage()));
    }

    public static String getKoreaName(DayOfWeek dayOfWeek) {
        return Arrays.stream(CustomDayOfWeek.values())
                .filter(customDayOfWeek -> customDayOfWeek.dayOfWeek.equals(dayOfWeek))
                .findFirst()
                .map(CustomDayOfWeek::getKoreaName)
                .get();
    }

    public CustomDayOfWeek getNext() {
        return Arrays.stream(CustomDayOfWeek.values())
                .filter(customDayOfWeek -> customDayOfWeek.getDayOfWeek().equals(dayOfWeek.plus(1)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(WEEKDAY_NOT_FOUND.getMessage()));
    }
}

