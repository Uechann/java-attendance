package attendance.domain.model;

public enum CrewStatus {

    NORMAL("일반"),
    WARNING("경고"),
    INTERVIEW("면담"),
    DISMISSAL("제적");

    private String name;

    CrewStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
