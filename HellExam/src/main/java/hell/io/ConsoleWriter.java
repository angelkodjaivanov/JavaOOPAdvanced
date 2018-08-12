package hell.io;

import hell.interfaces.OutputWriter;

public class ConsoleWriter implements OutputWriter {
    public ConsoleWriter(){
    }

    @Override
    public void writeLine(String text) {
        System.out.println(text);
    }

    @Override
    public void writeLine(String format, Object... params) {
        System.out.println(String.format(format, params));
    }
}
