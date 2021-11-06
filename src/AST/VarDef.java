package AST;

import java.util.ArrayList;

public class VarDef {
    private Ident ident;
    private ArrayList<ConstExp> constExps = new ArrayList<>();
    private InitVal initVal;

    public VarDef(Ident ident) {
        this.ident = ident;
    }

    public void addConstExp(ConstExp constExp) {
        constExps.add(constExp);
    }

    public void setInitVal(InitVal initVal) {
        this.initVal = initVal;
    }

    public Ident getIdent() {
        return ident;
    }

    public ArrayList<ConstExp> getConstExps() {
        return constExps;
    }

    public InitVal getInitVal() {
        return initVal;
    }
}
