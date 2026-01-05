package attendance.domain.dto;

import attendance.domain.model.Attendance;
import attendance.domain.model.CustomDayOfWeek;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceResultDto(
        String crewNickname,
        LocalDate date,
        String dayOfWeek,
        LocalTime time,
        String attendanceStatus
) {
    public static AttendanceResultDto of(Attendance attendance) {
        return new AttendanceResultDto(
                attendance.getCrew().getNickname(),
                attendance.getAttendanceDate(),
                CustomDayOfWeek.getKoreaName(attendance.getAttendanceDate().getDayOfWeek()),
                attendance.getAttendanceTime(),
                attendance.getStatus().getName()
        );
    }

    public static AttendanceResultDto of(
            String crewNickname,
            LocalDate date,
            String dayOfWeek,
            LocalTime time,
            String attendanceStatus
    ) {
        return new AttendanceResultDto(
                crewNickname,
                date,
                dayOfWeek,
                null,
                attendanceStatus
        );
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.getMonthValue()).append("월 ").append(String.format("%02d",date.getDayOfMonth())).append("일 ");
        stringBuilder.append(dayOfWeek).append("요일 ");

        if (time != null) {
            stringBuilder.append(String.format("%02d", time.getHour())).append(":").append(String.format("%02d", time.getMinute())).append(" ");
        }

        if (time == null) {
            stringBuilder.append("--:-- ");
        }

        stringBuilder.append("(").append(attendanceStatus).append(")");
        return stringBuilder.toString();
    }
}
