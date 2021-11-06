package AST;

import error_handler.Symbol;

public class Ident {
    private Symbol symbol;
    private String type = null;
    private Integer dimension = 0;

    public Ident() {

    }

    public Ident(Symbol symbol) {
        this.symbol = symbol;
        if (symbol != null) {
            this.type = symbol.getType();
            this.dimension = symbol.getDimension();
        } else {
            type = "notDefine";
        }
    }

    public void fillIdent(Symbol symbol) {
        this.symbol = symbol;
        if (symbol != null) {
            this.type = symbol.getType();
            this.dimension = symbol.getDimension();
        } else {
            type = "notDefine";
        }
    }

    public String getType() {
        return type;
    }

    public Integer getDimension() {
        return dimension;
    }

    public Symbol getSymbol() {
        return symbol;
    }

}
