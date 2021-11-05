package AST;

import java.util.ArrayList;

public class FuncFParam {
    private BType bType = null;
    private Ident ident = null;
    private ArrayList<ConstExp> constExps = new ArrayList<>();

    public FuncFParam(BType bType) {
        this.bType = bType;
    }

    public void setIdent(Ident ident) {
        this.ident = ident;
    }

    public void addConstExp(ConstExp constExp) {
        constExps.add(constExp);
    }

    public BType getBType() {
        return bType;
    }

    public Ident getIdent() {
        return ident;
    }

    public ArrayList<ConstExp> getConstExps() {
        return constExps;
    }
}
