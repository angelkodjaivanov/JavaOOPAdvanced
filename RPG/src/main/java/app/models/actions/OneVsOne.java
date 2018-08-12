package app.models.actions;

import app.contracts.Action;
import app.contracts.Targetable;

import java.util.List;

public class OneVsOne implements Action {


    public String executeAction(List<Targetable> participants) {

        String error = "There should be exactly 2 participants for OneVsOne!";

        if(participants.size() != 2){
            return error;
        }


        StringBuilder sb = new StringBuilder();

        Targetable firstHero = participants.get(0);
        Targetable secondHero = participants.get(1);

        while (firstHero.isAlive()){
            sb.append(firstHero.attack(secondHero)).append(System.lineSeparator());
            if (secondHero.isAlive()) {
                sb.append(secondHero.attack(firstHero)).append(System.lineSeparator());
            }else {
                break;
            }
        }

        if (firstHero.isAlive()){
            firstHero.levelUp();
            sb.append(String.format("%s is victorious!%s%s", firstHero.getName(), System.lineSeparator(),firstHero.toString()));
        }else {
            secondHero.levelUp();
            sb.append(String.format("%s is victorious!%s%s", secondHero.getName(), System.lineSeparator(),firstHero.toString()));
        }

        return sb.toString();
    }
}
