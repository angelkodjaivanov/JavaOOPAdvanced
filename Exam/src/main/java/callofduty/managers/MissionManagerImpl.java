package callofduty.managers;

import callofduty.domain.agents.MasterAgent;
import callofduty.domain.agents.NoviceAgent;
import callofduty.interfaces.Agent;
import callofduty.interfaces.Mission;
import callofduty.interfaces.MissionControl;
import callofduty.interfaces.MissionManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissionManagerImpl implements MissionManager {

    private MissionControl missionControl;
    private Map<String, Agent> agents;
    private int totalMissions = 0;
    private int totalCompletedMissions = 0;
    private int noviceAgents = 0;
    private int masterAgents = 0;

    public MissionManagerImpl( MissionControl missionControl) {
        this.missionControl = missionControl;
        this.agents = new HashMap<>();
    }

    @Override
    public String agent(List<String> arguments) {
        Agent agent = new NoviceAgent(arguments.get(0), arguments.get(1));
        this.agents.put(arguments.get(0), agent);
        this.noviceAgents++;

        return String.format("Registered Agent - %s:%s", agent.getName(), agent.getId());
    }

    @Override
    public String request(List<String> arguments) {
        Mission mission = this.missionControl.generateMission(arguments.get(1),
                Double.parseDouble(arguments.get(2)), Double.parseDouble(arguments.get(3)));

        this.totalMissions++;

        this.agents.get(arguments.get(0)).acceptMission(mission);

        return String.format("Assigned %s Mission - %s to Agent - %s",
                mission.getClass().getSimpleName()
                        .substring(0, mission.getClass().getSimpleName().length() - 7),
                mission.getId(), this.agents.get(arguments.get(0)).getName());
    }

    @Override
    public String complete(List<String> arguments) {

        Agent agent = this.agents.get(arguments.get(0));

        try {
            Field field = agent.getClass().getSuperclass().getDeclaredField("acceptedMissions");
            field.setAccessible(true);
            List<Mission> missionList = (List<Mission>) field.get(agent);
            this.totalCompletedMissions += missionList.size();

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        agent.completeMissions();

        try {
            Field declaredField = agent.getClass().getSuperclass().getDeclaredField("completedMissions");
            declaredField.setAccessible(true);
            List<Mission> missions = (List<Mission>) declaredField.get(agent);

            if(missions.size() >= 3 && agent.getClass().getSimpleName().equals("NoviceAgent")){
                this.masterAgents++;
                this.noviceAgents--;
                this.agents.put(agent.getId(), new MasterAgent(agent.getId(), agent.getName(), agent.getRating()));
                Agent masterAgent = this.agents.get(agent.getId());
                Field declaredField2 = masterAgent.getClass().getSuperclass().getDeclaredField("completedMissions");
                declaredField2.setAccessible(true);
                declaredField2.set(masterAgent, missions);
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return String.format("Agent - %s:%s has completed all assigned missions.",
                this.agents.get(arguments.get(0)).getName(), this.agents.get(arguments.get(0)).getId());
    }

    @Override
    public String status(List<String> arguments) {
        for (Agent agent:agents.values()) {
            if(agent.getId().equals(arguments.get(0))){
                return agent.toString();
            }
            else{
                try {
                    Field declaredField = agent.getClass().getSuperclass().getDeclaredField("acceptedMissions");
                    declaredField.setAccessible(true);
                    List<Mission> acceptedMissions = (List<Mission>) declaredField.get(agent);

                    Field declaredField2 = agent.getClass().getSuperclass().getDeclaredField("completedMissions");
                    declaredField2.setAccessible(true);
                    List<Mission> completedMissions = (List<Mission>) declaredField2.get(agent);

                    for (Mission mission:acceptedMissions) {
                        if (mission.getId().equals(arguments.get(0))) {
                            return String.format("%s Mission - %s\n" +
                                            "Status: %s\n" +
                                            "Rating: %.2f\n" +
                                            "Bounty: %.2f",
                                    mission.getClass().getSimpleName()
                                            .substring(0, mission.getClass().getSimpleName().length() - 7),
                                    mission.getId(), "Open", mission.getRating(), mission.getBounty());
                        }
                    }
                    for (Mission mission:completedMissions) {
                        if (mission.getId().equals(arguments.get(0))) {
                            return String.format("%s Mission - %s\n" +
                                            "Status: %s\n" +
                                            "Rating: %.2f\n" +
                                            "Bounty: %.2f",
                                    mission.getClass().getSimpleName()
                                            .substring(0, mission.getClass().getSimpleName().length() - 7),
                                    mission.getId(), "Completed", mission.getRating(), mission.getBounty());
                        }
                    }

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }

    @Override
    public String over(List<String> arguments) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Novice Agents: %d", noviceAgents)).append(System.lineSeparator())
                .append(String.format("Master Agents: %d", masterAgents)).append(System.lineSeparator())
                .append(String.format("Assigned Missions: %d", totalMissions)).append(System.lineSeparator())
                .append(String.format("Completed Missions: %d", totalCompletedMissions)).append(System.lineSeparator())
                .append(String.format("Total Rating Given: %.2f", totalRating())).append(System.lineSeparator())
                .append(String.format("Total Bounty Given: $%.2f", totalBountyGiven()));

        return sb.toString();
    }

    private double totalBountyGiven() {
        double bounty = 0.0;

        for (Agent agent:agents.values()) {
            if(agent.getClass().getSimpleName().equals("MasterAgent")){
                try {
                    Field declaredField = agent.getClass().getDeclaredField("bounty");
                    declaredField.setAccessible(true);
                    double bountyAdd = (double) declaredField.get(agent);
                    bounty += bountyAdd;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

        return bounty;
    }

    private double totalRating() {
        double rating = 0.0;
        for (Agent agent:agents.values()) {
            rating+= agent.getRating();
        }
        return rating;
    }
}
