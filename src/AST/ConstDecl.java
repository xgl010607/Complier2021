package AST;

import java.util.ArrayList;

public class ConstDecl {
    private BType bType;
    private ArrayList<ConstDef> constDefs = new ArrayList<>();

    public ConstDecl(BType bType) {
        this.bType = bType;
    }

    public void addConstDef(ConstDef constDef) {
        constDefs.add(constDef);
    }

    public BType getBType() {
        return bType;
    }

    public ArrayList<ConstDef> getConstDefs() {
        return constDefs;
    }
}
