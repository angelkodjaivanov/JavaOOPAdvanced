package app;

import app.contracts.*;
import app.core.BattlefieldImplementation;
import app.engines.EngineImpl;
import app.factory.ActionFactoryImpl;
import app.factory.TargetableFactoryImpl;
import app.io.ConsoleReader;
import app.io.ConsoleWriter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        ConsoleReader reader = new ConsoleReader();
        ConsoleWriter writer = new ConsoleWriter();
        TargetableFactory targetableFactory = new TargetableFactoryImpl();
        ActionFactory actionFactory = new ActionFactoryImpl();
        Battlefield battleField = new BattlefieldImplementation(writer, targetableFactory, actionFactory);

        Engine engine = new EngineImpl(writer, reader, battleField);

        engine.run();

    }
}
