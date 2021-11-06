package AST;

public class WhileStmt {
    private Cond cond;
    private Stmt stmt;

    public WhileStmt(Cond cond) {
        this.cond = cond;
    }

    public void setStmt(Stmt stmt) {
        this.stmt = stmt;
    }

    public Cond getCond() {
        return cond;
    }

    public Stmt getStmt() {
        return stmt;
    }
}
