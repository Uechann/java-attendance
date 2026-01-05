package attendance.controller;

import attendance.domain.dto.AttendanceModifyingResultDto;
import attendance.domain.dto.AttendanceResultDto;
import attendance.domain.service.AttendanceService;
import attendance.global.util.FileService;
import attendance.view.InputView;
import attendance.view.OutputView;

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
            String type = inputView.inputFunction();

            // 잘못된 형식 오류

            // 등교일 아닐 때 오류

            if (type.equals("1")) { // 출석 확인
                retry(() -> {
                    String crewNicknameInput = inputView.inputCrewNickname();
                    // TODO: 검증
                    // 없는 크루일때 오류

                    String attendanceTimeInput = inputView.inputAttendanceTime();
                    // TODO: 검증
                    // 등교 시간이 아닐 때 오류
                    // 미래 날짜 일때 오류
                    // 이미 출석한 경우 오류 후 수정으로 안내

                    AttendanceResultDto attendanceResultDto = attendanceService.attendanceCrew(crewNicknameInput, attendanceTimeInput);
                    outputView.outputCrewAttendanceCheck(attendanceResultDto);
                    return null;
                });
            }

            if (type.equals("2")) { // 출석 수정
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

            if (type.equals("3")) { // 크루별 출석 기록 확인
                //TODO
            }

            if (type.equals("4")) { // 제적 위험자 확인
                //TODO
            }

            if (type.equals("Q")) { // 종료
                break;
            }
        }
    }
}
