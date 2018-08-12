package app.engines;

import app.contracts.*;

import java.io.IOException;
import java.util.Arrays;

public class EngineImpl implements Engine {

    private Writer writer;
    private Reader reader;
    private Battlefield battlefield;

    public EngineImpl(Writer writer, Reader reader, Battlefield battlefield) {
        this.writer = writer;
        this.reader = reader;
        this.battlefield = battlefield;
    }

    @Override
    public void run() throws IOException {
        String line;
        while (!"Peace".equals(line = reader.readLine())){
            String[] lineTokens = line.split("\\s+");

            switch (lineTokens[0]){
                case "CreateParticipant" :
                    battlefield.createParticipant(lineTokens[1], lineTokens[2]);
                    break;
                case "CreateAction" :
                    battlefield.createAction(lineTokens[1],
                            Arrays.copyOf(Arrays.stream(lineTokens).skip(2).toArray(),
                                    Arrays.stream(lineTokens).skip(2).toArray().length,
                                    String[].class));
                    break;
                case "StatActions":
                    battlefield.reportActions();
                    break;
                case "StatParticipants":
                    battlefield.reportParticipants();
                    break;
                default:
                    writer.writeLine("Invalid command");
                    break;
            }
        }
    }
}
