package AST;

import java.util.ArrayList;

public class VarDecl {
    private BType bType;
    private ArrayList<VarDef> varDefs = new ArrayList<>();

    public VarDecl(BType bType) {
        this.bType = bType;
    }

    public void addVarDef(VarDef varDef) {
        varDefs.add(varDef);
    }
}
