package attendance;

import attendance.controller.AttendanceController;
import attendance.global.config.DIConfig;

public class Application {
    public static void main(String[] args) {

        DIConfig diConfig = new DIConfig();
        AttendanceController controller = diConfig.attendanceController();
        controller.run();
    }
}
