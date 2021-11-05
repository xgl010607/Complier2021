package error_handler;

import java.util.ArrayList;

public class Symbol {
    private String name;//名字
    private String kind;//语法分析的成分 "nonS"
    private String type;//类型 int, void
    private Integer dimension;
    private Integer lev;//层数
    private Integer line;//行数
    private ArrayList<Symbol> paras;//参数列，存的type

    public Symbol(String name, String kind, String type, Integer lev, Integer line, Integer dimension) {
        this.name = name;
        this.kind = kind;
        this.type = type;
        this.lev = lev;
        this.line = line;
        this.dimension = dimension;
        this.paras = new ArrayList<>();
    }


    public void addPara(Symbol symbol) {
            paras.add(symbol);
    }

    public String getName() {
        return name;
    }

    public String getKind() {
        return kind;
    }

    public String getType() {
        return type;
    }

    public Integer getLev() {
        return lev;
    }

    public ArrayList<Symbol> getParas() {
        return paras;
    }

    public Integer getDimension() {
        return dimension;
    }

    public Integer getLine() {
        return line;
    }
}

