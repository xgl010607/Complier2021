package AST;

import error_handler.Symbol;
import error_handler.SymbolTable;

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

    public Integer getConstValue() {
        if (ident.getSymbol().getNode() instanceof ConstDef) {
            ConstDef constDef = (ConstDef) ident.getSymbol().getNode();
            ArrayList<Integer> arrValues = constDef.getArrValues();
            if (exps.isEmpty()) {
                return arrValues.get(0);
            } else {
                Integer index = 1;
                for (Exp exp : exps) {
                    index *= exp.getConstValue();
                }
                return arrValues.get(index);
            }
        }
        return null;
    }

    public ForN getFourNExp(Integer regNow) {
        ForN forN = null;
        Integer indexArr1;
        if (exps.isEmpty()) {
            forN = new ForN(ident.getSymbol().getName(), regNow);
        } else if (exps.size() == 1) {
            ForN forN1 = exps.get(0).getFourNExp(regNow);
            regNow = forN1.getRegNow();
            forN = new ForN(ident.getSymbol().getName()+"[" + forN1.getAddr() + "]", regNow);
        } else if (exps.size() == 2) {
            ForN forN1 = exps.get(0).getFourNExp(regNow);
            regNow = forN1.getRegNow();
            System.out.print("t" + (++regNow) + " = " + forN1.getAddr() + " * ");
            indexArr1 = regNow;
            if (ident.getSymbol().getNode() instanceof ConstDef) {
                System.out.println(((ConstDef) ident.getSymbol().getNode()).getArrSum().get(0));
            } else if (ident.getSymbol().getNode() instanceof VarDef) {
                System.out.println(((VarDef) ident.getSymbol().getNode()).getArrSum().get(0));
            }
            ForN forN2 = exps.get(1).getFourNExp(regNow);
            regNow = forN2.getRegNow();
            System.out.println("t" + (++regNow) + " = " + forN2.getAddr() + " + t" + indexArr1);
            forN = new ForN(ident.getSymbol().getName() + "[t" + regNow + "]", regNow);
        }
        return forN;
    }
}
