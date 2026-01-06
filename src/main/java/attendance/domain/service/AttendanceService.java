package attendance.domain.service;

import attendance.domain.dto.AttendanceModifyingResultDto;
import attendance.domain.dto.AttendanceResultDto;
import attendance.domain.dto.ThisMonthAttendanceResultDto;
import attendance.domain.model.Attendance;
import attendance.domain.model.AttendanceStatus;
import attendance.domain.model.Crew;
import attendance.domain.model.CustomDayOfWeek;
import attendance.domain.repository.AttendanceRepository;
import attendance.domain.repository.CrewRepository;
import camp.nextstep.edu.missionutils.DateTimes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static attendance.global.exception.ErrorMessage.*;

public class AttendanceService {

    private final CrewRepository crewRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(CrewRepository crewRepository, AttendanceRepository attendanceRepository) {
        this.crewRepository = crewRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public void validateIsExist(String crewNickName) {
        crewRepository.findByNickname(crewNickName)
                .orElseThrow(() -> new IllegalArgumentException(CREW_NOT_FOUND.getMessage()));
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
        LocalDateTime attendanceAt = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), hour, minute);

        validateIsFutureTime(attendanceAt, now);
        validateNowDate();

        // TODO: 출석 상태 검사 기능 추가
        attendanceRepository.findByCrewNicknameAndDate(crewNickname, now.toLocalDate())
                .ifPresent(att -> {
                    throw new IllegalArgumentException(ATTENDANCE_ALREADY_EXIST.getMessage());
                });

        Attendance attendance = Attendance.of(crew, attendanceAt);
        attendanceRepository.save(attendance);
        return AttendanceResultDto.of(attendance);
    }

    public void validateNowDate() {
        LocalDateTime now = DateTimes.now();
        String dayOfWeek = CustomDayOfWeek.getKoreaName(now.getDayOfWeek());
        if (dayOfWeek.equals("토") || dayOfWeek.equals("일")) {
            throw new IllegalArgumentException(ERROR_MESSAGE.getMessage() +
                    String.format(" %02d월 %02d일 %s요일은 등교일이 아닙니다.", now.getMonthValue(), now.getDayOfMonth(), dayOfWeek));
        }
    }

    // 출석 수정
    //12월 03일 화요일 10:07 (지각) -> 09:58 (출석) 수정 완료!
    public AttendanceModifyingResultDto modifyCrewAttendance(String modifyingCrewNickname, String modifyingDayOfMonth, String modifyingAttendanceTime) {
        Crew crew = crewRepository.findByNickname(modifyingCrewNickname)
                .orElseThrow(() -> new IllegalArgumentException(CREW_NOT_FOUND.getMessage()));

        int day = Integer.parseInt(modifyingDayOfMonth);

        Attendance attendance = attendanceRepository.findByCrewNicknameAndDay(modifyingCrewNickname, day)
                .orElseThrow(() -> new IllegalArgumentException(ATTENDANCE_NOT_FOUND.getMessage()));
        Attendance previousAttendance = Attendance.copy(attendance);

        String[] attendanceTime = modifyingAttendanceTime.split(":");
        int hour = Integer.parseInt(attendanceTime[0]);
        int minute = Integer.parseInt(attendanceTime[1]);
        attendance.modifyAttendanceTime(LocalTime.of(hour, minute));

        return AttendanceModifyingResultDto.of(previousAttendance, attendance);
    }

    // 크루별 출석 기록 확인
    public ThisMonthAttendanceResultDto getAttendancesByCrew(String crewNickname) {
        Crew crew = crewRepository.findByNickname(crewNickname)
                .orElseThrow(() -> new IllegalArgumentException(CREW_NOT_FOUND.getMessage()));

        List<Attendance> attendances = attendanceRepository.findByCrewNickname(crewNickname);
        LocalDateTime now = DateTimes.now();
        List<AttendanceResultDto> attendanceResultDtos = new ArrayList<>();
        for (int i = 1; i <= now.getDayOfMonth() - 1; i++) {
            LocalDate date = LocalDate.of(now.getYear(), now.getMonthValue(), i);
            if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                continue;
            }

            Attendance crewAttendance = attendances.stream()
                    .filter(attendance -> attendance.getAttendanceDate().equals(date))
                    .findFirst()
                    .orElseGet(() -> Attendance.of(crew, date, null, AttendanceStatus.ABSENCE));
            // orElse 이거만 하면 미리 만들어놓는다 이거 주의 !
            attendanceResultDtos.add(AttendanceResultDto.of(crewAttendance));
        }
        return ThisMonthAttendanceResultDto.of(
                crewNickname, attendanceResultDtos,
                crew.getAttendanceCount(), crew.getLateCount(), crew.getAbsenceCount(),
                crew.getCrewStatus().getName()
        );
    }

    // 제적 위험자 확인



    public void validateIsFutureTime(LocalDateTime attendanceAt, LocalDateTime now) {
        if (attendanceAt.toLocalDate().isAfter(now.toLocalDate())) {
            throw new IllegalArgumentException(INVALID_FUTURE_TIME.getMessage());
        }
    }
}
