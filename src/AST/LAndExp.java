package AST;

import java.util.ArrayList;

public class LAndExp {
    private ArrayList<EqExp> eqExps = new ArrayList<>();
    private ArrayList<String> ops = new ArrayList<>();

    public LAndExp(EqExp eqExp) {
        eqExps.add(eqExp);
    }

    public void addEqExp(EqExp eqExp) {
        eqExps.add(eqExp);
    }

    public void addOp(String op) {
        ops.add(op);
    }

    public ArrayList<EqExp> getEqExps() {
        return eqExps;
    }

    public ArrayList<String> getOps() {
        return ops;
    }
}
