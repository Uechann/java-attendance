package attendance.domain.repository;

import attendance.domain.model.Attendance;

import java.util.LinkedHashMap;
import java.util.Map;

public class AttendanceRepository {

    private Map<String, Attendance> attendances = new LinkedHashMap<>();

    public void save(Attendance attendance) {
        attendances.put(attendance.getCrew().getName(), attendance);
    }
}
