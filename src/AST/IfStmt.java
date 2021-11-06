package AST;

public class IfStmt {
    private Cond cond;
    private Stmt stmt;
    private Stmt elStmt;

    public IfStmt(Cond cond) {
        this.cond = cond;
    }

    public void setStmt(Stmt stmt) {
        this.stmt = stmt;
    }

    public void setElStmt(Stmt elStmt) {
        this.elStmt = elStmt;
    }

    public Cond getCond() {
        return cond;
    }

    public Stmt getStmt() {
        return stmt;
    }

    public Stmt getElStmt() {
        return elStmt;
    }
}
