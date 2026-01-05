package attendance.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public InputView() {}

    public String inputFunction() {
        System.out.println(" 기능을 선택해주세요.");
        System.out.println("""
                         1. 출석 확인
                         2. 출석 수정
                         3. 크루별 출석 기록 확인
                         4. 제적 위험자 확인
                         Q. 종료""");
        return Console.readLine();
    }

    public String inputCrewNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return Console.readLine();
    }

    public String inputAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return Console.readLine();
    }

    public String inputModifyingCrewNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return Console.readLine();
    }

    public String inputModifyingDayOfMonth() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Console.readLine();
    }

    public String inputModifyingAttendanceTime() {
        System.out.println("언제로 변경하겠습니까?");
        return Console.readLine();
    }
}
