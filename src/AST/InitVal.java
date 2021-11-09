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


    public ArrayList<ForN> getInitFour(Integer regNow) {
        ArrayList<ForN> forNS = new ArrayList<>();
        if (exp != null) {
            forNS.add(exp.getFourNExp(regNow));
        } else {
            for (InitVal initVal : initVals) {
                forNS.addAll(initVal.getInitFour(regNow));
                regNow = forNS.get(forNS.size() - 1).getRegNow();
            }
        }
        return forNS;
    }

}
