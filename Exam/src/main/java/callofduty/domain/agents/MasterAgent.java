package callofduty.domain.agents;

import callofduty.interfaces.BountyAgent;
import callofduty.interfaces.Mission;

import java.lang.reflect.Field;
import java.util.List;

public class MasterAgent extends BaseAgent implements BountyAgent {

    private double bounty;

    public MasterAgent(String id, String name, double rating) {
        super(id, name, rating);
    }

    @Override
    public Double getBounty() {
        return this.bounty;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(System.lineSeparator())
                .append(String.format("Bounty Earned: $%.2f", this.bounty));
        return sb.toString();
    }
}
