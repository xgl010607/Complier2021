import error_handler.ErrorTable;
import grammar_analysis.Grammar_Analysis;
import org.omg.PortableInterceptor.SUCCESSFUL;
import phrase_analysis.PhraseAnalysis;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Compiler {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("testfile.txt");
        //FileWriter fw = new FileWriter("output.txt");
        //BufferedWriter bw = new BufferedWriter(fw);

        FileWriter efr = new FileWriter("error.txt");
        BufferedWriter ebw = new BufferedWriter(efr);

        StringBuffer stringBuffer = new StringBuffer();
        ArrayList<String> tokens = new ArrayList<>();
        ArrayList<String> symbols = new ArrayList<>();
        ArrayList<Integer> line = new ArrayList<>();
        ArrayList<Integer> newLine;

        ErrorTable errorTable = new ErrorTable();
        Integer i = 1;

        int ch_int;
        while ((ch_int = fr.read()) > 0) {
            char ch = (char) ch_int;
            stringBuffer.append(ch);
            line.add(i);
            if (ch == '\n') {
                i++;
            }
        }

        PhraseAnalysis phraseAnalysis = new PhraseAnalysis(stringBuffer, tokens, symbols, line);
        phraseAnalysis.readToken();
        newLine = phraseAnalysis.getNewLine();

        Grammar_Analysis grammar_analysis = new Grammar_Analysis(tokens, symbols, newLine, errorTable);
        grammar_analysis.grammarAnalysis();

/*
        for (String s : grammar_analysis.getOutput()) {
            bw.write(s + "\n");
        }
        bw.close();

 */

        errorTable.getErrorTable(ebw);
        //fw.close();
        fr.close();
        ebw.close();
        efr.close();
        System.out.println();
        for (int j = 0; j < stringBuffer.length(); j++) {
            System.out.println(line.get(j) + " " + stringBuffer.charAt(j));
        }
    }
}
