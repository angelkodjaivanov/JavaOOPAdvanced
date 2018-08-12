package app.models.actions;

import app.contracts.Action;
import app.contracts.Targetable;
import app.models.Config;

import java.util.Comparator;
import java.util.List;

public class BossFight implements Action {

    @Override
    public String executeAction(List<Targetable> participants) {

        Targetable boss = participants.remove(0);

        if(!boss.getClass().getSimpleName().equals("Boss")){
            return "Invalid boss.";
        }

        if(participants.size() < 1){
            return "There should be at least 1 participant for boss fight!";
        }

        StringBuilder sb = new StringBuilder();

        String[] winner = new String[1];
        for (int i = 0; i < participants.size(); i++) {
            participants.get(i).attack(boss);
            if(!(boss.isAlive())){
                sb.append("Boss has been slain by: ").append(System.lineSeparator());
                winner[0] = participants.get(i).getName();
                participants.stream().sorted(Comparator.comparing(Targetable::getName)).forEach(p ->{
                    p.receiveReward(Config.BOSS_INDIVIDUAL_REWARD);
                    p.levelUp();
                    sb.append(p.toString()).append(System.lineSeparator());
                });
                break;
            }else{
                boss.attack(participants.get(i));
                if(!(participants.get(i).isAlive())){
                    participants.remove(i);
                }
            }

            if(i >= participants.size() - 1){
                i = -1;
            }

        }

        if(participants.isEmpty()) {
            boss.levelUp();
            return "Boss has slain them all!";
        }

        return sb.toString().trim();
    }
}
