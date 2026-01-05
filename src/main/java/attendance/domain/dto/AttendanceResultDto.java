package attendance.domain.dto;

public record AttendanceResultDto(
        String crewNickname,
        int month,
        int day,
        String dayOfWeek,
        int hour,
        int minute
) {

    public static AttendanceResultDto of(String crewNickname, int month, int day, String dayOfWeek, int hour, int minute) {
        return new AttendanceResultDto(crewNickname, month, day, dayOfWeek, hour, minute);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(month).append("월 ");
        stringBuilder.append(day).append("일 ");
        stringBuilder.append(dayOfWeek).append("요일 ");
        stringBuilder.append(hour).append(":").append(minute).append(" ");
        stringBuilder.append("(출석)");
        return stringBuilder.toString();
    }
}
