package callofduty.io;

import callofduty.interfaces.OutputWriter;

public class OutputWriterImpl implements OutputWriter {

    public OutputWriterImpl(){};

    @Override
    public void print(String output) {
        System.out.print(output);
    }

    @Override
    public void println(String output) {
        System.out.println(output);
    }
}
