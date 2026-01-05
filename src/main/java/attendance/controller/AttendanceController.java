package attendance.controller;

import attendance.global.util.FileService;

public class AttendanceController {

    private final FileService fileService;

    public AttendanceController(FileService fileService) {
        this.fileService = fileService;
    }

    public void run() {
        fileService.initialCrew();
    }
}
