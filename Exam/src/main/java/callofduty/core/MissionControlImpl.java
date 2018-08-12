package callofduty.core;

import callofduty.interfaces.Mission;
import callofduty.interfaces.MissionControl;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MissionControlImpl implements MissionControl {
    private static final Integer MISSION_ID_MAXIMUM_LENGTH = 8;

    private static final Double MISSION_RATING_MINIMUM_VALUE = 0D;

    private static final Double MISSION_RATING_MAXIMUM_VALUE = 100D;

    private static final Double MISSION_BOUNTY_MINIMUM_VALUE = 0D;

    private static final Double MISSION_BOUNTY_MAXIMUM_VALUE = 100000D;

    private Map<String, Class> missionClasses;

    private Iterator<Map.Entry<String, Class>> missionIterator;

    public MissionControlImpl() {
        this.initMissionClasses();
        this.missionIterator = this.missionClasses
                .entrySet()
                .iterator();
    }

    private void initMissionClasses() {
        try {
            this.missionClasses = new LinkedHashMap<String, Class>() {{
                put("EscortMission", Class.forName("callofduty.domain.missions.EscortMission"));
                put("HuntMission", Class.forName("callofduty.domain.missions.HuntMission"));
                put("SurveillanceMission", Class.forName("callofduty.domain.missions.SurveillanceMission"));
            }};
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String checkAndReformMissionId(String missionId) {
        return missionId.length() > MISSION_ID_MAXIMUM_LENGTH
                ? missionId.substring(0, MISSION_ID_MAXIMUM_LENGTH)
                : missionId;
    }

    private Double checkAndReformMissionRating(Double missionRating) {

        if(missionRating < MISSION_RATING_MINIMUM_VALUE){
            return MISSION_RATING_MINIMUM_VALUE;
        }
        else if(missionRating > MISSION_RATING_MAXIMUM_VALUE){
            return MISSION_RATING_MAXIMUM_VALUE;
        }
        else{
            return missionRating;
        }
    }

    private Double checkAndreformMissionBounty(Double missionBounty) {

        if(missionBounty < MISSION_BOUNTY_MINIMUM_VALUE){
            return MISSION_BOUNTY_MINIMUM_VALUE;
        }
        else if(missionBounty > MISSION_BOUNTY_MAXIMUM_VALUE){
            return MISSION_BOUNTY_MAXIMUM_VALUE;
        }
        else{
            return missionBounty;
        }
    }

    private void updateMissionType() {
        this.missionIterator = this
                .missionClasses
                .entrySet()
                .iterator();
    }

    private String currentMission() {
        if (!this.missionIterator.hasNext()) {
            this.updateMissionType();
        }

        return this.missionIterator.next().getKey();
    }

    private Mission instantiateMissionObject(String missionId, Double missionRating, Double missionBounty) {
        Mission missionObject = null;

        try {
            String ref = "callofduty.domain.missions." + this.currentMission();
            missionObject = (Mission)
                    Class.forName(ref)
                            .getDeclaredConstructor(String.class, Double.class, Double.class)
                            .newInstance(missionId, missionRating, missionBounty);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException
                | InvocationTargetException | ClassNotFoundException ignored) {
            ;
        }

        return missionObject;
    }

    @Override
    public Mission generateMission(String missionId, Double missionRating, Double missionBounty) {
        missionId = this.checkAndReformMissionId(missionId);
        missionRating = this.checkAndReformMissionRating(missionRating);
        missionBounty = this.checkAndreformMissionBounty(missionBounty);

        return this.instantiateMissionObject(missionId, missionRating, missionBounty);
    }
}