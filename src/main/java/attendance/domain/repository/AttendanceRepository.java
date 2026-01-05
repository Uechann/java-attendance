package attendance.domain.repository;

import attendance.domain.model.Attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceRepository {

    private Map<String, List<Attendance>> attendances = new HashMap<>();

    public void save(Attendance attendance) {
        attendances.computeIfAbsent(attendance.getCrew().getNickname(), k -> new ArrayList<>()).add(attendance);
    }

    public List<Attendance> findByCrewNickName(String crewNickname) {
        return attendances.get(crewNickname);
    }
}
