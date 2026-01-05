package attendance.domain.service;

import attendance.domain.dto.AttendanceResultDto;
import attendance.domain.model.Attendance;
import attendance.domain.model.Crew;
import attendance.domain.model.CustomDayOfWeek;
import attendance.domain.repository.AttendanceRepository;
import attendance.domain.repository.CrewRepository;
import attendance.view.InputView;
import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDateTime;
import java.util.List;

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
        attendanceRepository.save(Attendance.of(crew, attendanceAt));
        return AttendanceResultDto.of(crew.getNickname(), now.getMonthValue(), now.getDayOfMonth(), dayOfWeek, hour, minute);
    }






    // 크루별 출석 기록 확인



    // 제적 위험자 확인


}
