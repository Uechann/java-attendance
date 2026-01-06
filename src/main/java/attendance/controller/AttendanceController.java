package attendance.controller;

import attendance.domain.dto.AttendanceModifyingResultDto;
import attendance.domain.dto.AttendanceResultDto;
import attendance.domain.dto.ThisMonthAttendanceResultDto;
import attendance.domain.service.AttendanceService;
import attendance.global.util.FileService;
import attendance.global.validator.InputValidator;
import attendance.view.InputView;
import attendance.view.OutputView;

import javax.xml.validation.Validator;

import static attendance.global.util.Retry.retry;

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

            // 등교일 아닐 때 오류

            if (function.equals("1")) { // 출석 확인
                String crewNicknameInput = inputView.inputCrewNickname();
                attendanceService.validateIsExist(crewNicknameInput);
                String attendanceTimeInput = inputView.inputAttendanceTime();
                InputValidator.validateInputTime(attendanceTimeInput);

                // 등교일이 아닐 때
                // 이미 출석한 경우
                // 미래 시간일 때
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
                //TODO
                String crewNickname = inputView.inputCrewNickname();
                ThisMonthAttendanceResultDto resultDto = attendanceService.getAttendancesByCrew(crewNickname);
                outputView.outputThisMonthAttendance(resultDto);
            }

            if (function.equals("4")) { // 제적 위험자 확인
                //TODO
            }

            if (function.equals("Q")) { // 종료
                break;
            }
        }
    }
}
