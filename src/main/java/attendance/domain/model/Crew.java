package attendance.domain.model;

public class Crew {

    private String name;

    private Crew(String name) {
        this.name = name;
    }

    public static Crew create(String name) {
        return new Crew(name);
    }

    public String getName() {
        return name;
    }
}
