package phrase_analysis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PhraseAnalysis {
    private char ch;
    private StringBuffer string;//all string
    private Integer pointer = 0;
    private String symbol = new String();
    private Dictionary dictionary = new Dictionary();
    private CheckClass checkClass;
    private HashMap<String, String> reserved_words;
    private ArrayList<String> orders = new ArrayList<>();
    //private FileWriter fw = new FileWriter("output.txt");
    //private BufferedWriter bw = new BufferedWriter(fw);
    private ArrayList<String> tokens;
    private ArrayList<String> symbols;
    private ArrayList<Integer> line;
    private ArrayList<Integer> newLine;
    private Integer lineNow;

    public PhraseAnalysis(StringBuffer string, ArrayList<String> tokens,
                          ArrayList<String> symbols, ArrayList<Integer> line) throws IOException {
        this.string = string;
        this.reserved_words = dictionary.getReserved_words();
        this.checkClass = new CheckClass(ch);
        this.tokens = tokens;
        this.symbols = symbols;
        this.line = line;
        this.newLine = new ArrayList<>();
    }

    public ArrayList<Integer> getNewLine() {
        return newLine;
    }

    public void readToken() throws IOException {
        while (pointer < string.length()) {
            StringBuffer token = new StringBuffer();
            getChar();
            while (ch == ' ' || ch == '\n' || ch == '\t' || ch == '\r') {
                getChar();
                if (pointer == string.length() &&
                        (ch == ' ' || ch == '\n' || ch == '\t' || ch == '\r')) {
                    //bw.close();
                    return;
                }
            }
            if (checkClass.isLetter() || ch == '_') {
                checkLetter(token);//组合标识符和保留字
            } else if (checkClass.isDigit()) {
                checkDigit(token);//识别数字
            } else if (ch == '/') {
                checkDivOrNote(token);//识别/和注释
            } else if (ch == '>' || ch == '<' || ch == '!' || ch == '=') {
                checkIfEqual(token);
            } else if (ch == '&' || ch == '|') {
                checkAndOr(token);
            } else if (ch == '"') {
                checkString(token);
            } else {
                checkElse(token);
            }
            orders.add(token.toString());
            System.out.println("add " + token.toString() + " in line " + lineNow);
        }
        //bw.close();
    }

    public void getChar() {
        //System.out.println("pointer is " + pointer);
        if (pointer < string.length()) {
            lineNow = line.get(pointer);
            ch = string.charAt(pointer++);
            checkClass = new CheckClass(ch);
        }
    }

    public void catToken(StringBuffer token) {
        token.append(ch);
    }


    public void retract() {
        ch = string.charAt(--pointer);
        lineNow = line.get(pointer);
        checkClass = new CheckClass(ch);
    }

    public String reserver(StringBuffer token) {
        String string = token.toString();
        if (reserved_words.containsKey(string)) {
            return reserved_words.get(string);
        }
        return null;
    }

    public void checkLetter(StringBuffer token) throws IOException {
        while (checkClass.isLetter() || checkClass.isDigit() || ch == '_') {
            catToken(token);
            getChar();
        }
        retract();
        symbol = reserver(token);
        if (symbol == null) {
            //System.out.println("IDENFR " + token);
            //bw.write("IDENFR " + token + "\n");
            tokens.add(token.toString());
            symbols.add("IDENFR");
        } else {
            //System.out.println(symbol + " " + token);
            //bw.write(symbol + " " + token + "\n");
            tokens.add(token.toString());
            symbols.add(symbol);
        }
        newLine.add(lineNow);
    }

    public void checkDigit(StringBuffer token) throws IOException {
        while (checkClass.isDigit()) {
            catToken(token);
            getChar();
        }
        retract();
        if (token.length() == 1 || token.charAt(0) != '0') {
            //System.out.println("INTCON" + " " + token);
            //bw.write("INTCON" + " " + token + "\n");
            tokens.add(token.toString());
            symbols.add("INTCON");
            newLine.add(lineNow);
        }
    }

    public void checkDivOrNote(StringBuffer token) throws IOException {
        catToken(token);
        getChar();
        if (ch == '*') {
            do {
                do {
                    getChar();
                } while (ch != '*');
                do {
                    getChar();
                    if (ch == '/') return;
                } while (ch == '*');
            } while (ch != '*');
        }
        if (ch == '/') {
            do {
                getChar();
                if (ch == '\n' || ch == '\r' ||
                        pointer == string.length() - 1)
                    return;
            } while (true);
        }
        retract();
        //System.out.println(reserver(token) + " " + token);
        //bw.write(reserver(token) + " " + token + "\n");
        tokens.add(token.toString());
        symbols.add(reserver(token));
        newLine.add(lineNow);
    }

    public void checkIfEqual(StringBuffer token) throws IOException {
        catToken(token);
        getChar();
        if (ch == '=') {
            catToken(token);
        } else {
            retract();
        }
        //System.out.println(reserver(token) + " " + token);
        //bw.write(reserver(token) + " " + token + "\n");
        tokens.add(token.toString());
        symbols.add(reserver(token));
        newLine.add(lineNow);
    }

    public void checkAndOr(StringBuffer token) throws IOException {
        catToken(token);
        getChar();
        if (ch == token.charAt(0)) {
            catToken(token);
            //System.out.println(reserver(token) + " " + token);
            //bw.write(reserver(token) + " " + token + "\n");
            tokens.add(token.toString());
            symbols.add(reserver(token));
            newLine.add(lineNow);
        } else {
            error();
        }
    }

    public void checkString(StringBuffer token) throws IOException {
        boolean flag = true;
        do {
            //System.out.println(ch);
            catToken(token);
            getChar();
            if (ch == '"') {
                break;
            }
            if (ch == '\\') {
                catToken(token);
                getChar();
                if (ch != 'n') {
                    flag = false;
                }
                continue;
            }
            boolean flag1 = (ch >= 40 && ch <= 126) || ch == 32 || ch == 33;
            boolean flag2 = false;
            if (ch == '%') {
                catToken(token);
                getChar();
                if (ch == 'd') {
                    flag2 = true;
                }
            }
            if (!flag1 && !flag2) {
                flag = false;
            }
        } while (ch != '"');
        catToken(token);
        //System.out.println("STRCON" + " " + token);
        //bw.write("STRCON " + token + "\n");
        tokens.add(token.toString());
        if (flag) {
            symbols.add("STRCON");
        } else {
            symbols.add("notSTRCON");
        }
        newLine.add(lineNow);
    }

    public void checkElse(StringBuffer token) throws IOException {
        catToken(token);
        //System.out.println(reserver(token) + " " + token);
        //bw.write(reserver(token) + " " + token + "\n");
        tokens.add(token.toString());
        symbols.add(reserver(token));
        newLine.add(lineNow);
    }

    public void error() {
        return;
    }
}
