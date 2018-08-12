package callofduty;

import callofduty.core.MissionControlImpl;
import callofduty.interfaces.InputReader;
import callofduty.interfaces.MissionControl;
import callofduty.interfaces.MissionManager;
import callofduty.interfaces.OutputWriter;
import callofduty.io.InputReaderImpl;
import callofduty.io.OutputWriterImpl;
import callofduty.managers.MissionManagerImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args)  {
        InputReader reader = new InputReaderImpl();
        OutputWriter writer = new OutputWriterImpl();
        MissionControl missionControl = new MissionControlImpl();
        MissionManager manager = new MissionManagerImpl(missionControl);

        String line = null;

        while (true){

            if("Over".equals(line = reader.readLine())){
                writer.println(manager.over(new ArrayList<>()));
                break;
            }

            String[] tokens = line.split("\\s+");
            String command = tokens[0];
            List<String> arguments = Arrays.stream(tokens).skip(1).collect(Collectors.toList());

            switch (command){
                case "Agent":
                    writer.println(manager.agent(arguments));
                    break;
                case "Request":
                    writer.println(manager.request(arguments));
                    break;
                case "Complete":
                    writer.println(manager.complete(arguments));
                    break;
                case "Status":
                    writer.println(manager.status(arguments));
                    break;
            }

        }
    }
}




