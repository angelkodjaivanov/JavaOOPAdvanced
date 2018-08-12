package callofduty.domain.agents;

import callofduty.interfaces.Agent;
import callofduty.interfaces.Mission;

import java.lang.reflect.Field;
import java.util.List;

public class NoviceAgent extends BaseAgent {
    public NoviceAgent(String id, String name) {
        super(id, name, 0.0);
    }


}
