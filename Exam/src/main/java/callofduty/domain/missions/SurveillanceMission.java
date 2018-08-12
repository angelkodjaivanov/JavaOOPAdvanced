package callofduty.domain.missions;

public class SurveillanceMission extends BaseMission {
    public SurveillanceMission(String id, Double rating, Double bounty) {
        super(id, (rating / 2) - (rating / 4), bounty + (bounty / 2));
    }
}
