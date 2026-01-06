package attendance.view;

import attendance.domain.dto.AttendanceModifyingResultDto;
import attendance.domain.dto.AttendanceResultDto;
import attendance.domain.dto.ThisMonthAttendanceResultDto;
import attendance.domain.model.CustomDayOfWeek;
import camp.nextstep.edu.missionutils.DateTimes;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class OutputView {

    public OutputView() {}

    public void outputTodayDate() {
        LocalDateTime now = DateTimes.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        String koreaName = CustomDayOfWeek.getKoreaName(dayOfWeek);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("오늘은").append(" ");
        stringBuilder.append(now.getMonthValue()).append("월").append(" ");
        stringBuilder.append(now.getDayOfMonth()).append("일").append(" ");
        stringBuilder.append(koreaName).append("요일").append("입니다.");
        System.out.print(stringBuilder);
    }

    public void outputCrewAttendanceCheck(AttendanceResultDto attendanceResultDto) {
        System.out.println(attendanceResultDto.toString());
    }

    public void outputModifyingAttendance(AttendanceModifyingResultDto modifyingResultDto) {
        System.out.println(modifyingResultDto.toString());
    }

    public void outputThisMonthAttendance(ThisMonthAttendanceResultDto resultDto) {
        System.out.println("이번달 " + resultDto.crewNickname() + "의 출석 기록입니다.");
        System.out.println(resultDto.toString());
    }
}
