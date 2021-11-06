package error_handler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ErrorTable {
    private ArrayList<String> errorOut;
    private ArrayList<Integer> errorLine;
    private Integer size;
    private Integer before;

    public ErrorTable() throws IOException {
        this.errorOut = new ArrayList<>();
        this.errorLine = new ArrayList<>();
        this.size = 0;
    }

    public void addError(String error, Integer line) {
        System.out.println("adding " + error + " " + line);
        errorOut.add(error);
        errorLine.add(line);
        size++;
        for (int i = size - 1; i >= 1; i--) {
            int temp = errorLine.get(i);
            int temp1 = errorLine.get(i - 1);
            if (temp1 > temp) {
                String str = errorOut.get(i);
                String str1 = errorOut.get(i - 1);
                errorOut.set(i, str1);
                errorOut.set(i - 1, str);
                errorLine.set(i, temp1);
                errorLine.set(i - 1, temp);
            }
        }
    }

    public void getErrorTable(BufferedWriter bw) throws IOException {
        for (int i = 0; i < size; i++) {
            System.out.println(errorLine.get(i) + " " + errorOut.get(i));
            bw.write(errorLine.get(i) + " " + errorOut.get(i) + "\n");
        }
    }

    public void stopBefore() {
        before = size;
    }

    public void delError() {
        for (int i = size - before; i > 0; i--) {
            errorLine.remove(size - 1);
            errorOut.remove(size - 1);
            size--;
        }
    }
}
