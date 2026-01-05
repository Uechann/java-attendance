package attendance.global.config;

import attendance.controller.AttendanceController;
import attendance.domain.repository.AttendanceRepository;
import attendance.domain.repository.CrewRepository;
import attendance.domain.service.AttendanceService;
import attendance.global.util.FileService;
import attendance.view.InputView;
import attendance.view.OutputView;

public final class DIConfig {

    private final CrewRepository crewRepository = new CrewRepository();
    private final AttendanceRepository attendanceRepository = new AttendanceRepository();

    public AttendanceController attendanceController() {
        return new AttendanceController(
                fileService(),
                attendanceService(),
                inputView(),
                outputView()
        );
    }

    public FileService fileService() {
        return new FileService(
                crewRepository(),
                attendanceRepository()
                );
    }

    public AttendanceService attendanceService() {
        return new AttendanceService(
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