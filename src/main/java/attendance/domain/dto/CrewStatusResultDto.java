package attendance.domain.dto;

import attendance.domain.model.Crew;

public record CrewStatusResultDto(
        String crewNickname,
        int absenceCount,
        int lateCount,
        String crewStatus
) {
    public static CrewStatusResultDto of(Crew crew) {
        return new CrewStatusResultDto(crew.getNickname(), crew.getAbsenceCount(), crew.getLateCount(), crew.getCrewStatus().getName());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("- ").append(crewNickname).append(": ");
        stringBuilder.append("결석 ").append(absenceCount).append("회, ");
        stringBuilder.append("지각 ").append(lateCount).append("회 ");
        stringBuilder.append("(").append(crewStatus).append(")");
        return stringBuilder.toString();
    }
}
