package callofduty.domain.missions;

public class HuntMission extends BaseMission {
    public HuntMission(String id, Double rating, Double bounty) {
        super(id, rating + (rating / 2), bounty * 2);
    }
}
