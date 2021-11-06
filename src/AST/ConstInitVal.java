package AST;

import java.util.ArrayList;

public class ConstInitVal {
    private ConstExp constExp;
    private ArrayList<ConstInitVal> constInitVals = new ArrayList<>();

    public ConstInitVal(ConstExp constExp) {
        this.constExp = constExp;
    }

    public ConstInitVal(ConstInitVal constInitVal) {
        constInitVals.add(constInitVal);
    }

    public void addConstInitVal(ConstInitVal constInitVal) {
        constInitVals.add(constInitVal);
    }
}
