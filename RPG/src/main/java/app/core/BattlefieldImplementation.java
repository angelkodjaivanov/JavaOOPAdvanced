package app.core;

import app.contracts.*;
import app.io.ConsoleWriter;
import app.models.actions.OneVsOne;
import app.models.participants.Warrior;
import app.models.participants.Wizard;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BattlefieldImplementation implements Battlefield {

    private Map<String, Targetable> participants;
    private List<Action> executedActions;
    private ConsoleWriter writer;
    private TargetableFactory targetableFactory;
    private ActionFactory actionFactory;

    public BattlefieldImplementation(ConsoleWriter writer, TargetableFactory targetableFactory,
                                     ActionFactory actionFactory) {
        this.executedActions = new ArrayList<>();
        this.participants = new TreeMap<>();
        this.writer = writer;
        this.targetableFactory = targetableFactory;
        this.actionFactory = actionFactory;
    }

    @Override
    public void createAction(String actionName, String... participantNames) {
        try {
            Action action = this.actionFactory.create(actionName, participantNames);

            List<Targetable> actionParticipants = new ArrayList<>();
            for (String name : participantNames){
                if (this.participants.containsKey(name)){
                    actionParticipants.add(this.participants.get(name));
                } else {
                    this.writer.writeLine(String.format("%s is not on the battlefield. %s failed.", name, actionName));
                    return;
                }
            }

            this.writer.writeLine(action.executeAction(actionParticipants));
            checkForDeadParticipants();
            this.executedActions.add(action);
        } catch (Exception e) {
            this.writer.writeLine("Action does not exist.");
        }
    }

    @Override
    public void createParticipant(String name, String className) {

        if (this.participants.containsKey(name)){
            this.writer.writeLine("Participant with that name already exists.");
            return;
        }

        Targetable targetable = null;
        try {
            targetable = this.targetableFactory.create(name, className);
            this.participants.put(targetable.getName(), targetable);
            this.writer.writeLine(
                    String.format("%s %s entered the battlefield.",
                            targetable.getClass().getSimpleName(),
                            targetable.getName()));
        } catch (ClassNotFoundException | IllegalAccessException |
                InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void createSpecial(String name, String specialName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reportParticipants(){
        if (this.participants.size() < 1) {
            this.writer.writeLine("There are no participants on the battlefield.");
            return;
        }

        for (Targetable targetable : this.participants.values()) {
            this.writer.writeLine(targetable.toString());
            this.writer.writeLine("* * * * * * * * * * * * * * * * * * * *");
        }
    }

    @Override
    public void reportActions(){
        if (this.executedActions.size() < 1) {
            this.writer.writeLine("There are no actions on the battlefield.");
            return;
        }

        for (Action executedAction : executedActions) {
            this.writer.writeLine(executedAction.getClass().getSimpleName());
        }
    }

    private void checkForDeadParticipants(){
        Map<String, Targetable> aliveParticipants = new TreeMap<>();

        for (Targetable targetable : this.participants.values()) {
            if (!targetable.isAlive()){
                this.writer.writeLine(String.format("%s has been removed from the battlefield.", targetable.getName()));
            }else {
                aliveParticipants.put(targetable.getName(), this.participants.get(targetable.getName()));
            }
        }

        this.participants = aliveParticipants;
    }
}
