package attendance.domain.repository;

import attendance.domain.model.Crew;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CrewRepository {

    List<Crew> crews = new ArrayList<>();

    public CrewRepository() {}

    public Crew save(Crew crew) {
        crews.add(crew);
        return crew;
    }

    public Optional<Crew> findByNickname(String name) {
        return crews.stream()
                .filter(crew -> crew.getNickname().equals(name))
                .findFirst();
    }
}
