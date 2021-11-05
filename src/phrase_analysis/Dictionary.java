package phrase_analysis;

import java.util.HashMap;

public class Dictionary {
    private HashMap<String, String> reserved_words;

    public Dictionary() {
        this.reserved_words = new HashMap<>();
        reserved_words.put("main", "MAINTK");
        reserved_words.put("const", "CONSTTK");
        reserved_words.put("int", "INTTK");
        reserved_words.put("break", "BREAKTK");
        reserved_words.put("continue", "CONTINUETK");
        reserved_words.put("if", "IFTK");
        reserved_words.put("else", "ELSETK");
        reserved_words.put("while", "WHILETK");
        reserved_words.put("getint", "GETINTTK");
        reserved_words.put("printf", "PRINTFTK");
        reserved_words.put("return", "RETURNTK");
        reserved_words.put("void", "VOIDTK");
        reserved_words.put("!", "NOT");
        reserved_words.put("&&", "AND");
        reserved_words.put("||", "OR");
        reserved_words.put("+", "PLUS");
        reserved_words.put("-", "MINU");
        reserved_words.put("*", "MULT");
        reserved_words.put("/", "DIV");
        reserved_words.put("%", "MOD");
        reserved_words.put("<", "LSS");
        reserved_words.put("<=", "LEQ");
        reserved_words.put(">", "GRE");
        reserved_words.put(">=", "GEQ");
        reserved_words.put("==", "EQL");
        reserved_words.put("!=", "NEQ");
        reserved_words.put("=", "ASSIGN");
        reserved_words.put(";", "SEMICN");
        reserved_words.put(",", "COMMA");
        reserved_words.put("(", "LPARENT");
        reserved_words.put(")", "RPARENT");
        reserved_words.put("[", "LBRACK");
        reserved_words.put("]", "RBRACK");
        reserved_words.put("{", "LBRACE");
        reserved_words.put("}", "RBRACE");
    }

    public HashMap<String, String> getReserved_words() {
        return reserved_words;
    }
}
