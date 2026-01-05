package attendance.global.config;

import attendance.controller.AttendanceController;
import attendance.domain.repository.AttendanceRepository;
import attendance.domain.repository.CrewRepository;
import attendance.global.util.FileService;
import attendance.view.InputView;
import attendance.view.OutputView;

public final class DIConfig {

    private final CrewRepository crewRepository = new CrewRepository();
    private final AttendanceRepository attendanceRepository = new AttendanceRepository();

    public AttendanceController attendanceController() {
        return new AttendanceController(
                fileService()
        );
    }

    public FileService fileService() {
        return new FileService(
                crewRepository(),
                attendanceRepository()
                );
    }

    public InputView inputView() {
        return new InputView();
    }

    public OutputView outputView() {
        return new OutputView();
    }

    public CrewRepository crewRepository() {
        return crewRepository;
    }

    public AttendanceRepository attendanceRepository() {
        return attendanceRepository;
    }
}