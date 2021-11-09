package AST;

import error_handler.Symbol;

public class UnaryExp {
    private PrimaryExp primaryExp;
    private String kind;

    private Ident ident;
    private FuncRParams funcRParams;

    private UnaryOp unaryOp;
    private UnaryExp unaryExp;

    private String type;
    private Integer dimension;

    public UnaryExp() {
        this.primaryExp = null;
        this.kind = null;
        this.ident = null;
        this.funcRParams = new FuncRParams();
        this.unaryExp = null;
        this.unaryOp = null;
        this.type = null;
        this.dimension = 0;
    }

    public void setPrimaryExp(PrimaryExp primaryExp) {
        if (primaryExp != null) {
            this.primaryExp = primaryExp;
            this.type = primaryExp.getType();
            this.dimension = primaryExp.getDimension();
            this.kind = primaryExp.getKind();
        }
    }

    public void setIdent(Ident ident) {
        this.ident = ident;
        this.type = ident.getType();
        this.dimension = ident.getDimension();
    }

    public void setFuncRParams(FuncRParams funcRParams) {
        this.funcRParams = funcRParams;
    }

    public void setUnaryOp(UnaryOp unaryOp) {
        this.unaryOp = unaryOp;
    }

    public void setUnaryExp(UnaryExp unaryExp) {
        this.unaryExp = unaryExp;
        this.type = unaryExp.getType();
        this.dimension = unaryExp.getDimension();
    }

    public PrimaryExp getPrimaryExp() {
        return primaryExp;
    }

    public Ident getIdent() {
        return ident;
    }

    public FuncRParams getFuncRParams() {
        return funcRParams;
    }

    public UnaryOp getUnaryOp() {
        return unaryOp;
    }

    public UnaryExp getUnaryExp() {
        return unaryExp;
    }

    public String getType() {
        return type;
    }

    public Integer getDimension() {
        return dimension;
    }

    public String getKind() {
        return kind;
    }

    public Integer getConstValue() {
        if (primaryExp != null) {
            return primaryExp.getConstValue();
        }
        return null;
    }

    public ForN getFourNExp(Integer regNow) {
        ForN forN = null;
        if (primaryExp != null) {
            forN = primaryExp.getFourNExp(regNow);
        }
        return forN;
    }
}
