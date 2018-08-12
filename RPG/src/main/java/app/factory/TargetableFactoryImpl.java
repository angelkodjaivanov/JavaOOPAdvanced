package app.factory;

import app.contracts.Targetable;
import app.contracts.TargetableFactory;
import app.models.participants.Boss;
import app.models.participants.Necromancer;
import app.models.participants.Warrior;
import app.models.participants.Wizard;

import java.lang.reflect.InvocationTargetException;

public class TargetableFactoryImpl implements TargetableFactory {
    @Override
    public Targetable create(String name, String className) throws
            ClassNotFoundException {
        switch (className) {
            case "Boss":
                return new Boss(name);
            case "Wizard":
                return new Wizard(name);
            case "Warrior":
                return new Warrior(name);
            case "Necromancer":
                return new Necromancer(name);
            default:
                throw new ClassNotFoundException();
        }
    }
}
