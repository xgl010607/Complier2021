package AST;

public class Decl {
    private ConstDecl constDecl;
    private VarDecl varDecl;

    public Decl() {
        this.constDecl = null;
        this.varDecl = null;
    }

    public void setConstDecl(ConstDecl constDecl) {
        this.constDecl = constDecl;
    }

    public void setVarDecl(VarDecl varDecl) {
        this.varDecl = varDecl;
    }
}
