package attendance.controller;

import attendance.domain.dto.AttendanceModifyingResultDto;
import attendance.domain.dto.AttendanceResultDto;
import attendance.domain.dto.CrewStatusResultDto;
import attendance.domain.dto.ThisMonthAttendanceResultDto;
import attendance.domain.service.AttendanceService;
import attendance.global.util.FileService;
import attendance.global.validator.InputValidator;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.util.List;

public class AttendanceController {

    private final FileService fileService;
    private final AttendanceService attendanceService;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(
            FileService fileService,
            AttendanceService attendanceService,
            InputView inputView,
            OutputView outputView
    ) {
        this.fileService = fileService;
        this.attendanceService = attendanceService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        fileService.initialCrew();

        while (true) {
            outputView.outputTodayDate();
            String function = inputView.inputFunction();
            InputValidator.validateInputFunction(function);

            if (function.equals("1")) { // 출석 확인
                //입력 받기 전에 등교일 검증
                attendanceService.validateNowDate();

                String crewNicknameInput = inputView.inputCrewNickname();
                attendanceService.validateIsExist(crewNicknameInput);
                String attendanceTimeInput = inputView.inputAttendanceTime();
                InputValidator.validateInputTime(attendanceTimeInput);
                AttendanceResultDto attendanceResultDto = attendanceService.attendanceCrew(crewNicknameInput, attendanceTimeInput);
                outputView.outputCrewAttendanceCheck(attendanceResultDto);
            }

            if (function.equals("2")) { // 출석 수정
                // TODO:
                String crewNickname = inputView.inputModifyingCrewNickname();
                // TODO 검증

                String dayOfMonth = inputView.inputModifyingDayOfMonth();
                // TODO 검증

                String attendanceTime = inputView.inputModifyingAttendanceTime();
                // TODO 검증
                AttendanceModifyingResultDto modifyingResultDto = attendanceService.modifyCrewAttendance(crewNickname, dayOfMonth, attendanceTime);
                outputView.outputModifyingAttendance(modifyingResultDto);
            }

            if (function.equals("3")) { // 크루별 출석 기록 확인
                // TODO 검증
                String crewNickname = inputView.inputCrewNickname();
                ThisMonthAttendanceResultDto resultDto = attendanceService.getAttendancesByCrew(crewNickname);
                outputView.outputThisMonthAttendance(resultDto);
            }

            if (function.equals("4")) { // 제적 위험자 확인
                List<CrewStatusResultDto> crewStatus = attendanceService.getCrewStatus();
                outputView.outputCrewStatus(crewStatus);
            }

            if (function.equals("Q")) { // 종료
                break;
            }
        }
    }
}
