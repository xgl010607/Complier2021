package AST;

import java.util.ArrayList;

public class RelExp {
    private ArrayList<AddExp> addExps = new ArrayList<>();
    private ArrayList<String> ops = new ArrayList<>();

    public RelExp(AddExp addExp) {
        addExps.add(addExp);
    }

    public void addOP(String op) {
        ops.add(op);
    }

    public void addAddExp(AddExp addExp) {
        addExps.add(addExp);
    }

    public ArrayList<AddExp> getAddExps() {
        return addExps;
    }

    public ArrayList<String> getOps() {
        return ops;
    }
}
