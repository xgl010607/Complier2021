package AST;

import java.util.ArrayList;

public class PrintStmt {
    private FormatString formatString;
    private ArrayList<Exp> exps = new ArrayList<>();

    public PrintStmt(FormatString formatString) {
        this.formatString = formatString;
    }

    public void addExp(Exp exp) {
        exps.add(exp);
    }

    public FormatString getFormatString() {
        return formatString;
    }

    public ArrayList<Exp> getExps() {
        return exps;
    }
}
