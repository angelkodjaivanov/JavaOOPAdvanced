package callofduty.domain.agents;

import callofduty.interfaces.Agent;
import callofduty.interfaces.Mission;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseAgent implements Agent {

    private String id;
    private String name;
    private double rating;
    private List<Mission> acceptedMissions;
    private List<Mission> completedMissions;

    protected BaseAgent(String id, String name, double rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        acceptedMissions = new ArrayList<>();
        completedMissions = new ArrayList<>();
    }

    @Override
    public void acceptMission(Mission mission) {
        this.acceptedMissions.add(mission);
    }

    @Override
    public void completeMissions() {

        for (Mission mission:acceptedMissions) {
            this.rating += mission.getRating();
            if(this.getClass().getSimpleName().equals("MasterAgent")) {
                try {
                    Field declaredField = this.getClass().getDeclaredField("bounty");
                    declaredField.setAccessible(true);
                    declaredField.set(this, (double) declaredField.get(this) + mission.getBounty());

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            completedMissions.add(mission);
        }

        acceptedMissions = new ArrayList<>();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Double getRating() {
        return this.rating;
    }

    @Override
    public String toString() {
        return String.format("%s Agent - %s\n" +
                "Personal Code: %s\n" +
                "Assigned Missions: %d\n" +
                "Completed Missions: %d\n" +
                "Rating: %.2f",
        this.getClass().getSimpleName().substring(0,this.getClass().getSimpleName().length() - 5),
                this.getName(), this.getId(), this.acceptedMissions.size(),
                this.completedMissions.size(), this.getRating());
    }
}
