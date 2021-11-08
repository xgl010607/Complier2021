package AST;

import java.util.ArrayList;

public class ConstDef {
    private Ident ident;
    private ArrayList<ConstExp> constExps = new ArrayList<>();
    private ConstInitVal constInitVal;
    private ArrayList<Integer> arrSum = new ArrayList<>();
    private ArrayList<Integer> arrValues;

    public ConstDef(Ident ident) {
        this.ident = ident;
    }

    public void addConstExp(ConstExp constExp) {
        constExps.add(constExp);
    }


    public void setConstInitVal(ConstInitVal constInitVal) {
        this.constInitVal = constInitVal;
    }

    public Ident getIdent() {
        return ident;
    }

    public ArrayList<ConstExp> getConstExps() {
        return constExps;
    }

    public ConstInitVal getConstInitVal() {
        return constInitVal;
    }

    public ArrayList<Integer> getArrSum() {
        return arrSum;
    }

    public ArrayList<Integer> getArrValues() {
        return arrValues;
    }

    public void visit() {
        arrValues = constInitVal.getConstInit();
        if (constExps.isEmpty()) {
            System.out.println(ident.getSymbol().getName() + " = "
                    + arrValues.get(0));
        } else {
            System.out.print("arr " + ident.getSymbol().getName());
            for (ConstExp constExp : constExps) {
                Integer temp = constExp.getConstValue();
                System.out.print("[" + temp + "]");
                arrSum.add(temp);
            }
            System.out.println();
            if (constExps.size() == 1) {
                for (int i = 0; i < arrSum.get(0); i++) {
                    System.out.println(ident.getSymbol().getName() + "[" + i + "] = " +
                            arrValues.get(i));
                }
            } else if (constExps.size() == 2) {
                for (int i = 0, k = 0; i < arrSum.get(0); i++) {
                    for (int j = 0; j < arrSum.get(1); j++) {
                        System.out.println(ident.getSymbol().getName() + "[" + i + "]["
                                + j + "] = " + arrValues.get(k++));
                    }
                }
            }
        }
    }
}
