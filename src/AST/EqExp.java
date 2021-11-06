package AST;

import java.util.ArrayList;

public class EqExp {
    private ArrayList<RelExp> relExps = new ArrayList<>();
    private ArrayList<String> ops = new ArrayList<>();

    public EqExp(RelExp relExp) {
        relExps.add(relExp);
    }

    public void addRelExp(RelExp relExp) {
        relExps.add(relExp);
    }

    public void addOp(String op) {
        ops.add(op);
    }

    public ArrayList<RelExp> getRelExps() {
        return relExps;
    }

    public ArrayList<String> getOps() {
        return ops;
    }
}
