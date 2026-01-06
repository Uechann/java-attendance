package attendance.domain.model;

public class Crew {

    private String nickname;
    private int attendanceCount;
    private int lateCount;
    private int absenceCount;
    private CrewStatus crewStatus ;

    private Crew(String nickname, int attendanceCount, int lateCount, int absenceCount, CrewStatus crewStatus) {
        this.nickname = nickname;
        this.attendanceCount = attendanceCount;
        this.lateCount = lateCount;
        this.absenceCount = absenceCount;
        this.crewStatus = crewStatus;
    }

    public static Crew create(String nickname) {
        return new Crew(nickname, 0, 0, 0, CrewStatus.NORMAL);
    }

    public String getNickname() {
        return nickname;
    }

    public void countAttendance(AttendanceStatus attendanceStatus) {
        if (attendanceStatus.equals(AttendanceStatus.ATTENDANCE)) {
            attendanceCount++;
        }

        if (attendanceStatus.equals(AttendanceStatus.LATE)) {
            lateCount++;
        }

        if (attendanceStatus.equals(AttendanceStatus.ABSENCE)) {
            absenceCount++;
        }
        judgeCrewStatus();
    }

    public void decreaseAttendance(AttendanceStatus attendanceStatus) {
        if (attendanceStatus.equals(AttendanceStatus.ATTENDANCE)) {
            attendanceCount--;
        }

        if (attendanceStatus.equals(AttendanceStatus.LATE)) {
            lateCount--;
        }

        if (attendanceStatus.equals(AttendanceStatus.ABSENCE)) {
            absenceCount--;
        }
        judgeCrewStatus();
    }

    private void judgeCrewStatus() {
        int totalAbsenceCount = lateCount / 3 + absenceCount;
        if (totalAbsenceCount == 2) {
            crewStatus = CrewStatus.WARNING;
        }

        if (totalAbsenceCount == 3 || totalAbsenceCount == 4) {
            crewStatus = CrewStatus.INTERVIEW;
        }

        if (totalAbsenceCount >= 5) {
            crewStatus = CrewStatus.DISMISSAL;
        }
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public CrewStatus getCrewStatus() {
        return crewStatus;
    }

    public boolean isNotNormal() {
        return crewStatus != CrewStatus.NORMAL;
    }

    public int getLateAbsenceCount() {
        return absenceCount + lateCount;
    }

    public int getCrewStatusCode() {
        return crewStatus.getCode();
    }
}
