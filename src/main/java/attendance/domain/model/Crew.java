package attendance.domain.model;

public class Crew {

    private String nickname;

    private Crew(String nickname) {
        this.nickname = nickname;
    }

    public static Crew create(String nickname) {
        return new Crew(nickname);
    }

    public String getNickname() {
        return nickname;
    }
}
