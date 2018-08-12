package callofduty.domain.missions;

public class EscortMission extends BaseMission {
    public EscortMission(String id, Double rating, Double bounty) {
        super(id, rating - (rating / 4), bounty + (bounty / 4));
    }
}
