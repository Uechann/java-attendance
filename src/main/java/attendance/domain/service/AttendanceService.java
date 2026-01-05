package attendance.domain.service;

import attendance.domain.dto.AttendanceModifyingResultDto;
import attendance.domain.dto.AttendanceResultDto;
import attendance.domain.model.Attendance;
import attendance.domain.model.AttendanceStatus;
import attendance.domain.model.Crew;
import attendance.domain.model.CustomDayOfWeek;
import attendance.domain.repository.AttendanceRepository;
import attendance.domain.repository.CrewRepository;
import attendance.view.InputView;
import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static attendance.global.exception.ErrorMessage.ATTENDANCE_NOT_FOUND;
import static attendance.global.exception.ErrorMessage.CREW_NOT_FOUND;

public class AttendanceService {

    private final CrewRepository crewRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(CrewRepository crewRepository, AttendanceRepository attendanceRepository) {
        this.crewRepository = crewRepository;
        this.attendanceRepository = attendanceRepository;
    }

    // 출석 체크
    public AttendanceResultDto attendanceCrew(String crewNickname, String attendanceTime) {
        Crew crew = crewRepository.findByNickname(crewNickname)
                .orElseThrow(() -> new IllegalArgumentException(CREW_NOT_FOUND.getMessage()));

        // TODO: Parser 인터페이스로 분리
        String[] hourMinute = attendanceTime.split(":");
        int hour = Integer.parseInt(hourMinute[0]);
        int minute = Integer.parseInt(hourMinute[1]);

        LocalDateTime now = DateTimes.now();
        String dayOfWeek = CustomDayOfWeek.getKoreaName(now.getDayOfWeek());
        LocalDateTime attendanceAt = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), hour, minute);

        // TODO: 출석 상태 검사 기능 추가
        Attendance attendance = Attendance.of(crew, attendanceAt);
        attendanceRepository.save(attendance); // 임시로 출석 처리
        return AttendanceResultDto.of(crew.getNickname(), now.getMonthValue(), now.getDayOfMonth(), dayOfWeek, hour, minute, attendance.getStatus().getName());
    }



    // 출석 수정
    //12월 03일 화요일 10:07 (지각) -> 09:58 (출석) 수정 완료!
    public AttendanceModifyingResultDto modifyCrewAttendance(String modifyingCrewNickname, String modifyingDayOfMonth, String modifyingAttendanceTime) {
        Crew crew = crewRepository.findByNickname(modifyingCrewNickname)
                .orElseThrow(() -> new IllegalArgumentException(CREW_NOT_FOUND.getMessage()));

        int day = Integer.parseInt(modifyingDayOfMonth);

        Attendance attendance = attendanceRepository.findByCrewNickNameAndDay(modifyingCrewNickname, day)
                .orElseThrow(() -> new IllegalArgumentException(ATTENDANCE_NOT_FOUND.getMessage()));
        Attendance previousAttendance = Attendance.copy(attendance);

        String[] attendanceTime = modifyingAttendanceTime.split(":");
        int hour = Integer.parseInt(attendanceTime[0]);
        int minute = Integer.parseInt(attendanceTime[1]);
        attendance.modifyAttendanceTime(LocalTime.of(hour, minute));

        return AttendanceModifyingResultDto.of(previousAttendance, attendance);
    }



    // 크루별 출석 기록 확인



    // 제적 위험자 확인


}
