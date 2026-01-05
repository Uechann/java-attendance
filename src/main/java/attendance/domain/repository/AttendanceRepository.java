package attendance.domain.repository;

import attendance.domain.model.Attendance;

import java.util.*;

public class AttendanceRepository {

    private Map<String, List<Attendance>> attendances = new HashMap<>();

    public void save(Attendance attendance) {
        attendances.computeIfAbsent(attendance.getCrew().getNickname(), k -> new ArrayList<>()).add(attendance);
    }

    public Optional<Attendance> findByCrewNickNameAndDay(String crewNickname, int dayOfMonth) {
        return attendances.get(crewNickname).stream()
                .filter(attendance -> attendance.getAttendanceDate().getDayOfMonth() == dayOfMonth)
                .findFirst();
    }
}
