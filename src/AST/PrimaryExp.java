package AST;

public class PrimaryExp {
    private Exp exp;
    private LVal lVal;
    private Number number;
    private String type;
    private Integer dimension;
    private String kind;

    public PrimaryExp() {
        this.exp = null;
        this.lVal = null;
        this.number = null;
        this.type = null;
        this.dimension = 0;
        this.kind = null;
    }

    public void setExp(Exp exp) {

        this.exp = exp;
        this.type = exp.getType();
        this.dimension = exp.getDimension();
    }

    public void setlVal(LVal lVal) {
        this.lVal = lVal;
        this.type = lVal.getType();
        this.dimension = lVal.getDimension();
        this.kind = lVal.getKind();
    }

    public void setNumber(Number number) {
        this.number = number;
        this.type = "int";
        this.dimension = 0;
    }

    public Exp getExp() {
        return exp;
    }

    public LVal getlVal() {
        return lVal;
    }

    public Number getNumber() {
        return number;
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
        if (number != null) {
            return number.getIntConst();
        } else if (exp != null) {
            return exp.getConstValue();
        } else if (lVal != null) {
            return lVal.getConstValue();
        }
        return null;
    }
}
