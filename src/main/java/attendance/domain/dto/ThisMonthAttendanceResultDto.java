package attendance.domain.dto;

import java.util.List;

public record ThisMonthAttendanceResultDto(
        String crewNickname,
        List<AttendanceResultDto> attendanceResultDtos,
        int attendanceCount,
        int lateCount,
        int absenceCount,
        String crewStatus
) {
    public static ThisMonthAttendanceResultDto of(
            String crewNickname,
            List<AttendanceResultDto> attendanceResultDtos,
            int attendanceCount,
            int lateCount,
            int absenceCount,
            String crewStatus
    ) {
        return new ThisMonthAttendanceResultDto(crewNickname, attendanceResultDtos, attendanceCount, lateCount, absenceCount, crewStatus);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (AttendanceResultDto attendanceResultDto : attendanceResultDtos) {
            stringBuilder.append(attendanceResultDto.toString()).append("\n");
        }
        stringBuilder.append("출석: ").append(attendanceCount).append("회").append("\n");
        stringBuilder.append("지각: ").append(lateCount).append("회").append("\n");
        stringBuilder.append("결석: ").append(absenceCount).append("회").append("\n");

        stringBuilder.append(crewStatus).append(" 대상자 입니다.");
        return stringBuilder.toString();
    }
}
