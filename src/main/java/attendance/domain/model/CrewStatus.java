package attendance.domain.model;

public enum CrewStatus {

    NORMAL("일반", 1),
    WARNING("경고", 2),
    INTERVIEW("면담", 3),
    DISMISSAL("제적", 4);

    private final String name;
    private final int code;

    CrewStatus(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
