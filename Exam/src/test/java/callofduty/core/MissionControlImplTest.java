package callofduty.core;

import callofduty.interfaces.Mission;
import callofduty.interfaces.MissionControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class MissionControlImplTest {

    private MissionControl missionControl;

    @Before
    public void setUp() throws Exception {
        missionControl = new MissionControlImpl();
    }

    @Test
    public void checkIfTheIdLengthIsBiggerThanEightSymbols(){
        Mission mission = missionControl.generateMission("123456789", 10.0D, 10.0D);
        Assert.assertEquals(mission.getId(), "12345678");
    }

    @Test
    public void changeRatingIfBelowZero() {
        Mission mission = missionControl.generateMission("mission", -2.0D, 3.0D);
        Assert.assertEquals(mission.getRating(), 0.0D, Double.MIN_VALUE);
    }

    @Test
    public void changeRatingIfOverTheMaxValue() {
        Mission mission = missionControl.generateMission("mission", 101.0D, 3.0D);
        Assert.assertEquals(mission.getRating(), 75.0D, Double.MIN_VALUE);
    }

    @Test
    public void changeBountyIfBelowZero() {
        Mission mission = missionControl.generateMission("mission", 10.0D, -3.0D);
        Assert.assertEquals(mission.getBounty(), 0.0D, Double.MIN_VALUE);
    }

    @Test
    public void changeBountyIfOverTheMaxValue() {
        Mission mission = missionControl.generateMission("mission", 90.0D, 1000000.0D);
        Assert.assertEquals(mission.getBounty(), 125000.0D, Double.MIN_VALUE);
    }

    @Test
    public void generatesCorrectMissions(){
        Mission mission1 = missionControl.generateMission("firstMission", 10.0D, 10.0D);
        Mission mission2 = missionControl.generateMission("secondMission", 10.0D, 10.0D);
        Mission mission3 = missionControl.generateMission("thirdMission", 10.0D, 10.0D);
        Mission mission4 = missionControl.generateMission("fourthMission", 10.0D, 10.0D);
        Assert.assertEquals(mission1.getClass().getSimpleName(), "EscortMission");
        Assert.assertEquals(mission2.getClass().getSimpleName(), "HuntMission");
        Assert.assertEquals(mission3.getClass().getSimpleName(), "SurveillanceMission");
        Assert.assertEquals(mission4.getClass().getSimpleName(), "EscortMission");
    }
}