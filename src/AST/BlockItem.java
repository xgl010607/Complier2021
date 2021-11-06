package AST;

public class BlockItem {
    private Decl decl = null;
    private Stmt stmt = null;
    private boolean checkReturn = false;

    public BlockItem(Decl decl) {
        this.decl = decl;
    }

    public BlockItem(Stmt stmt) {
        this.stmt = stmt;
        if (stmt.getReturnStmt() != null) {
            checkReturn = true;
        }
    }

    public Decl getDecl() {
        return decl;
    }

    public Stmt getStmt() {
        return stmt;
    }

    public boolean isCheckReturn() {
        return checkReturn;
    }
}
