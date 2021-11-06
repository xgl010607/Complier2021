package AST;

import java.util.ArrayList;

public class InitVal {
    private Exp exp;
    private ArrayList<InitVal> initVals = new ArrayList<>();

    public InitVal(Exp exp) {
        this.exp = exp;
    }

    public InitVal(InitVal initVal) {
        initVals.add(initVal);
    }

    public void addInitVal(InitVal initVal) {
        initVals.add(initVal);
    }

    public Exp getExp() {
        return exp;
    }

    public ArrayList<InitVal> getInitVals() {
        return initVals;
    }
}
