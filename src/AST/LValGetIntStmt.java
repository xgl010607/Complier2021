package AST;

public class LValGetIntStmt {
    private LVal lVal;

    public LValGetIntStmt(LVal lVal) {
        this.lVal = lVal;
    }

    public LVal getlVal() {
        return lVal;
    }
}
