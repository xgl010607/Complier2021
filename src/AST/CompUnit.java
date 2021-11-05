package AST;

import java.util.ArrayList;

public class CompUnit {
    private ArrayList<Decl> declLists;
    private ArrayList<FuncDef> funcDefLists;
    private MainFuncDef mainFuncDef;

    public CompUnit() {
        this.declLists = new ArrayList<>();
        this.funcDefLists = new ArrayList<>();
        this.mainFuncDef = null;
    }

    public void addDecl(Decl declNode) {
        declLists.add(declNode);
    }

    public void addFuncDef(FuncDef funcDef) {
        funcDefLists.add(funcDef);
    }

    public void setMainFuncDef(MainFuncDef mainFuncDef) {
        this.mainFuncDef = mainFuncDef;
    }
}
