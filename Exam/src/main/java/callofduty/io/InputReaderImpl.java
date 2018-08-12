package callofduty.io;

import callofduty.interfaces.InputReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReaderImpl implements InputReader {

    private BufferedReader bf;

    public InputReaderImpl() {
        this.bf = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String readLine() {
        try {
            return bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
