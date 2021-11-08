package error_handler;

import AST.ConstDef;
import AST.FuncDef;
import AST.FuncFParam;
import AST.VarDef;

import java.util.ArrayList;

public class Symbol {
    private String name;//名字
    private String kind;//语法分析的成分 "nonS"
    private String type;//类型 int, void
    private Integer dimension;
    private Integer lev;//层数
    private Integer line;//行数
    private ArrayList<Symbol> paras;//参数列，存的type

    private ConstDef constDef;
    private VarDef varDef;
    private FuncDef funcDef;
    private FuncFParam funcFParam;

    public Symbol(String name, String kind, String type, Integer lev, Integer line, Integer dimension) {
        this.name = name;
        this.kind = kind;
        this.type = type;
        this.lev = lev;
        this.line = line;
        this.dimension = dimension;
        this.paras = new ArrayList<>();
    }

    public void setConstDef(ConstDef constDef) {
        this.constDef = constDef;
    }

    public void setVarDef(VarDef varDef) {
        this.varDef = varDef;
    }

    public void setFuncDef(FuncDef funcDef) {
        this.funcDef = funcDef;
    }

    public void setFuncFParam(FuncFParam funcFParam) {
        this.funcFParam = funcFParam;
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

    public Object getNode() {
        if (constDef != null) {
            return constDef;
        } else if (varDef != null) {
            return varDef;
        } else if (funcDef != null) {
            return funcDef;
        } else if (funcFParam != null) {
            return funcFParam;
        }
        return null;
    }
}

