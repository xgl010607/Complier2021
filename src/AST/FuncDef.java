package AST;

public class FuncDef {
    private FuncType funcType;
    private Ident ident;
    private FuncFParams funcFParams;
    private Block block;

    public FuncDef(FuncType funcType) {
        this.funcType = funcType;
    }

    public void setIdent(Ident ident) {
        this.ident = ident;
    }

    public void setFuncFParams(FuncFParams funcFParams) {
        this.funcFParams = funcFParams;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public FuncType getFuncType() {
        return funcType;
    }

    public Ident getIdent() {
        return ident;
    }

    public FuncFParams getFuncFParams() {
        return funcFParams;
    }

    public Block getBlock() {
        return block;
    }
}
