package AST;

public class Stmt {
    private LValExpStmt lValExpStmt;
    private ExpStmt expStmt;
    private BlockStmt blockStmt;
    private IfStmt ifStmt;
    private WhileStmt whileStmt;
    private BreakContStmt breakContStmt;
    private ReturnStmt returnStmt;
    private LValGetIntStmt lValGetIntStmt;
    private PrintStmt printStmt;

    public Stmt(LValExpStmt lValExpStmt) {
        this.lValExpStmt = lValExpStmt;
    }

    public Stmt(ExpStmt expStmt) {
        this.expStmt = expStmt;
    }

    public Stmt(BlockStmt blockStmt) {
        this.blockStmt = blockStmt;
    }

    public Stmt(IfStmt ifStmt) {
        this.ifStmt = ifStmt;
    }

    public Stmt(WhileStmt whileStmt) {
        this.whileStmt = whileStmt;
    }

    public Stmt(BreakContStmt breakContStmt) {
        this.breakContStmt = breakContStmt;
    }

    public Stmt(ReturnStmt returnStmt) {
        this.returnStmt = returnStmt;
    }

    public Stmt(PrintStmt printStmt) {
        this.printStmt = printStmt;
    }

    public Stmt(LValGetIntStmt lValGetIntStmt) {
        this.lValGetIntStmt = lValGetIntStmt;
    }
/////////////////////////////////////////////////////////////
    public LValExpStmt getlValExpStmt() {
        return lValExpStmt;
    }

    public ExpStmt getExpStmt() {
        return expStmt;
    }

    public BlockStmt getBlockStmt() {
        return blockStmt;
    }

    public IfStmt getIfStmt() {
        return ifStmt;
    }

    public WhileStmt getWhileStmt() {
        return whileStmt;
    }

    public BreakContStmt getBreakContStmt() {
        return breakContStmt;
    }

    public ReturnStmt getReturnStmt() {
        return returnStmt;
    }

    public LValGetIntStmt getlValGetIntStmt() {
        return lValGetIntStmt;
    }

    public PrintStmt getPrintStmt() {
        return printStmt;
    }
}
