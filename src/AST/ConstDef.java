package AST;

import java.util.ArrayList;

public class ConstDef {
    private Ident ident;
    private ArrayList<ConstExp> constExps = new ArrayList<>();
    private ConstInitVal constInitVal;

    public ConstDef(Ident ident) {
        this.ident = ident;
    }

    public void addConstExp(ConstExp constExp) {
        constExps.add(constExp);
    }


    public void setConstInitVal(ConstInitVal constInitVal) {
        this.constInitVal = constInitVal;
    }

    public Ident getIdent() {
        return ident;
    }

    public ArrayList<ConstExp> getConstExps() {
        return constExps;
    }

    public ConstInitVal getConstInitVal() {
        return constInitVal;
    }
}
