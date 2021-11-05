package AST;

import error_handler.Symbol;

import java.util.ArrayList;

public class LVal {
    private Ident ident;
    private ArrayList<Exp> exps;
    private String type;
    private Integer dimension;
    private String kind;

    public LVal() {
        this.ident = null;
        this.exps = new ArrayList<>();
        this.type = null;
        this.dimension = 0;
        this.kind = null;
    }

    public void setIdent(Ident ident) {
        //ident 为 null 说明 在 表里面没有找到这个 元素
        this.ident = ident;
        Symbol symbol = ident.getSymbol();
        if (symbol != null) {
            this.type = symbol.getType();
            this.kind = symbol.getKind();
        } else {
            this.type = "notDefine";
        }
    }

    public void addExp(Exp exp) {
        exps.add(exp);
        //check Exp type
        dimension++;
    }

    public void setDimension(Integer dimension) {
        this.dimension = dimension;
    }

    public Ident getIdent() {
        return ident;
    }

    public ArrayList<Exp> getExps() {
        return exps;
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
}
