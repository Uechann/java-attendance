package attendance.domain.dto;

import attendance.domain.model.Attendance;
import attendance.domain.model.CustomDayOfWeek;

public record AttendanceModifyingResultDto(
        String crewNickname,
        int month,
        int day,
        String dayOfWeek,
        int previousHour,
        int previousMinute,
        String previousStatus,

        int modifyingHour,
        int modifyingMinute,
        String modifyingStatus
) {
    public static AttendanceModifyingResultDto of(Attendance previousAttendance, Attendance modifyingAttendance) {
        return new AttendanceModifyingResultDto(
                previousAttendance.getCrew().getNickname(),
                previousAttendance.getAttendanceDate().getMonthValue(),
                previousAttendance.getAttendanceDate().getDayOfMonth(),
                CustomDayOfWeek.getKoreaName(previousAttendance.getAttendanceDate().getDayOfWeek()),
                previousAttendance.getAttendanceTime().getHour(),
                previousAttendance.getAttendanceTime().getMinute(),
                previousAttendance.getStatus().getName(),

                modifyingAttendance.getAttendanceTime().getHour(),
                modifyingAttendance.getAttendanceTime().getMinute(),
                modifyingAttendance.getStatus().getName()
        );
    }

    //12월 03일 화요일 10:07 (지각) -> 09:58 (출석) 수정 완료!
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(month).append("월 ").append(String.format("%02d", day)).append("일 ").append(dayOfWeek).append("요일 ");
        stringBuilder.append(previousHour).append(":").append(String.format("%02d",previousMinute)).append(" (").append(previousStatus).append(")");
        stringBuilder.append(" -> ");
        stringBuilder.append(modifyingHour).append(":").append(String.format("%02d",modifyingMinute)).append(" (").append(modifyingStatus).append(")");
        stringBuilder.append(" 수정 완료!");
        return stringBuilder.toString();
    }
}
