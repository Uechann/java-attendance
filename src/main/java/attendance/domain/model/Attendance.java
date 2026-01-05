package attendance.domain.model;


import java.time.LocalDateTime;

public class Attendance {

    private Crew crew;

    private LocalDateTime attendanceAt;

    private Attendance(Crew crew, LocalDateTime attendanceAt) {
        this.crew = crew;
        this.attendanceAt = attendanceAt;
    }

    public static Attendance of(Crew crew, LocalDateTime attendanceAt) {
        return new Attendance(crew, attendanceAt);
    }

    public Crew getCrew() {
        return crew;
    }

    public LocalDateTime getAttendanceAt() {
        return attendanceAt;
    }
}
