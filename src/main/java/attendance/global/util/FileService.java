package attendance.global.util;

import attendance.domain.model.Attendance;
import attendance.domain.model.Crew;
import attendance.domain.repository.AttendanceRepository;
import attendance.domain.repository.CrewRepository;

import java.io.*;
import java.time.LocalDateTime;

public class FileService {
    private final CrewRepository crewRepository;
    private final AttendanceRepository attendanceRepository;

    public FileService(CrewRepository crewRepository, AttendanceRepository attendanceRepository) {
        this.crewRepository = crewRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public void initialCrew() {
        File attendances = new File("src/main/resources/attendances.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(attendances))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.startsWith("nickname")) continue;

                String[] crewAttendances = line.split(",");
                String crewName = crewAttendances[0];
                String attendanceTime = crewAttendances[1];

                Crew crew = crewRepository.findByNickname(crewName)
                                .orElseGet(() -> crewRepository.save(Crew.create(crewName)));

                String[] dateTime = attendanceTime.split(" ");
                String date = dateTime[0];
                String time = dateTime[1];

                String[] yearMonthDay = date.split("-");
                int year = Integer.parseInt(yearMonthDay[0]);
                int month = Integer.parseInt(yearMonthDay[1]);
                int day = Integer.parseInt(yearMonthDay[2]);

                String[] hourMinute = time.split(":");
                int hour = Integer.parseInt(hourMinute[0]);
                int minute = Integer.parseInt(hourMinute[1]);
                LocalDateTime attendanceAt = LocalDateTime.of(year, month, day, hour, minute);
                attendanceRepository.save(Attendance.of(crew, attendanceAt));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}