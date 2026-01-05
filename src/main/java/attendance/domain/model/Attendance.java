package attendance.domain.model;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private Crew crew;

    private LocalDate attendanceDate;

    private LocalTime attendanceTime;

    // 출석 상태
    private AttendanceStatus status;

    private Attendance(Crew crew, LocalDate attendanceDate, LocalTime attendanceTime, AttendanceStatus status) {
        this.crew = crew;
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
        this.status = status;
    }

    public static Attendance of(Crew crew, LocalDateTime attendanceAt) {
        DayOfWeek dayOfWeek = attendanceAt.getDayOfWeek();
        LocalTime attendanceTime = attendanceAt.toLocalTime();
        AttendanceStatus status = judgeAttendanceStatus(dayOfWeek, attendanceTime);
        crew.countAttendance(status);
        return new Attendance(crew, attendanceAt.toLocalDate(), attendanceTime, status);
    }

    public static Attendance of(Crew crew, LocalDate attendanceDate, LocalTime attendanceTime, AttendanceStatus status) {
        crew.countAttendance(status);
        return new Attendance(crew, attendanceDate, attendanceTime, status);
    }

    private static AttendanceStatus judgeAttendanceStatus(DayOfWeek dayOfWeek, LocalTime attendanceTime) {
        AttendanceStatus status = AttendanceStatus.ATTENDANCE;
        if (dayOfWeek.equals(DayOfWeek.MONDAY)) {
            LocalTime lateCriteria = LocalTime.of(13, 5);
            LocalTime absenceCriteria = LocalTime.of(13, 30);
            if (attendanceTime.isAfter(lateCriteria) && attendanceTime.isBefore(absenceCriteria)) {
                status = AttendanceStatus.LATE;
            }

            if (attendanceTime.isAfter(absenceCriteria)) {
                status = AttendanceStatus.ABSENCE;
            }
        }

        // 화수목금 10:00
        if (dayOfWeek.equals(DayOfWeek.TUESDAY) || dayOfWeek.equals(DayOfWeek.WEDNESDAY)
                || dayOfWeek.equals(DayOfWeek.THURSDAY) || dayOfWeek.equals(DayOfWeek.FRIDAY)
        ) {
            LocalTime lateCriteria = LocalTime.of(10, 5);
            LocalTime absenceCriteria = LocalTime.of(10, 30);
            if (attendanceTime.isAfter(lateCriteria) && attendanceTime.isBefore(absenceCriteria)) {
                status = AttendanceStatus.LATE;
            }

            if (attendanceTime.isAfter(absenceCriteria)) {
                status = AttendanceStatus.ABSENCE;
            }
        }
        return status;
    }

    public static Attendance copy(Attendance attendance) {
        return new Attendance(attendance.crew, attendance.getAttendanceDate(), attendance.getAttendanceTime(), attendance.getStatus());
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
        crew.decreaseAttendance(this.status);
        this.status = judgeAttendanceStatus(attendanceDate.getDayOfWeek(), attendanceTime);
        crew.countAttendance(this.status);
    }

    public AttendanceStatus getStatus() {
        return status;
    }
}
