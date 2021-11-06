package AST;

public class LValExpStmt {
    private LVal lVal;
    private Exp exp;

    public LValExpStmt(LVal lVal, Exp exp) {
        this.lVal = lVal;
        this.exp = exp;
    }

    public LVal getlVal() {
        return lVal;
    }

    public Exp getExp() {
        return exp;
    }
}
