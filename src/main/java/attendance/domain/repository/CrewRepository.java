package attendance.domain.repository;

import attendance.domain.model.Crew;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrewRepository {

    List<Crew> crews = new ArrayList<>();

    public CrewRepository() {}

    public void save(Crew crew) {
        crews.add(crew);
    }

    public Optional<Crew> findByName(String name) {
        return crews.stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst();
    }
}
