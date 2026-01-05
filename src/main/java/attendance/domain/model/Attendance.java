package attendance.domain.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private Crew crew;

    private LocalDate attendanceDate;

    private LocalTime attendanceTime;

    // 출석 상태
    private 

    private Attendance(Crew crew, LocalDate attendanceDate, LocalTime attendanceTime) {
        this.crew = crew;
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public static Attendance of(Crew crew, LocalDateTime attendanceAt) {
        return new Attendance(crew, attendanceAt.toLocalDate(), attendanceAt.toLocalTime());
    }

    public Crew getCrew() {
        return crew;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public void modifyAttendanceTime(LocalTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }
}
