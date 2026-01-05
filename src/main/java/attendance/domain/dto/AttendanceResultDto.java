package attendance.domain.dto;

public record AttendanceResultDto(
        String crewNickname,
        int month,
        int day,
        String dayOfWeek,
        int hour,
        int minute,
        String attendanceStatus
) {

    public static AttendanceResultDto of(String crewNickname, int month, int day, String dayOfWeek, int hour, int minute, String attendanceStatus) {
        return new AttendanceResultDto(crewNickname, month, day, dayOfWeek, hour, minute, attendanceStatus);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(month).append("월 ");
        stringBuilder.append(day).append("일 ");
        stringBuilder.append(dayOfWeek).append("요일 ");
        stringBuilder.append(hour).append(":").append(minute).append(" ");
        stringBuilder.append("(").append(attendanceStatus).append(")");
        return stringBuilder.toString();
    }
}
