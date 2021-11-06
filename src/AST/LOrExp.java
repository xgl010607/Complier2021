package AST;

import java.util.ArrayList;

public class LOrExp {
    private ArrayList<LAndExp> lAndExps = new ArrayList<>();
    private ArrayList<String> ops = new ArrayList<>();

    public LOrExp(LAndExp lAndExp) {
        lAndExps.add(lAndExp);
    }

    public void addLAndExp(LAndExp lAndExp) {
        lAndExps.add(lAndExp);
    }

    public void addOp(String op) {
        ops.add(op);
    }

    public ArrayList<LAndExp> getlAndExps() {
        return lAndExps;
    }

    public ArrayList<String> getOps() {
        return ops;
    }
}
