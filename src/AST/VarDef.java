package AST;

import java.util.ArrayList;

public class VarDef {
    private Ident ident;
    private ArrayList<ConstExp> constExps = new ArrayList<>();
    private InitVal initVal;
    private ArrayList<Integer> arrSum = new ArrayList<>();

    public VarDef(Ident ident) {
        this.ident = ident;
    }

    public void addConstExp(ConstExp constExp) {
        constExps.add(constExp);
    }

    public void setInitVal(InitVal initVal) {
        this.initVal = initVal;
    }

    public Ident getIdent() {
        return ident;
    }

    public ArrayList<ConstExp> getConstExps() {
        return constExps;
    }

    public InitVal getInitVal() {
        return initVal;
    }

    public void visit() {
        if (constExps.isEmpty()) {
            System.out.println(ident.getSymbol().getName());
        } else {
            System.out.print("arr " + ident.getSymbol().getName());
            for (ConstExp constExp : constExps) {
                Integer temp = constExp.getConstValue();
                System.out.print("[" + temp + "]");
                arrSum.add(temp);
            }
            System.out.println();
        }
        //
        if (initVal != null) {
            String name = ident.getSymbol().getName();
            ArrayList<ForN> forNS = initVal.getInitFour(0);
            if (constExps.isEmpty()) {
                System.out.println(name + " = " + forNS.get(0).getAddr());
            } else if (constExps.size() == 1) {
                for (int i = 0; i < arrSum.get(0); i++) {
                    System.out.println(name + "[" + i + "] = " + forNS.get(i).getAddr());
                }
            } else if (constExps.size() == 2) {
                for (int i = 0; i < arrSum.get(0); i++) {
                    Integer one = i * arrSum.get(0);
                    for (int j = 0; j < arrSum.get(1); j++) {
                        Integer index = one + j;
                        System.out.println(name + "[" + index + "] = " + forNS.get(index).getAddr());
                    }
                }
            }
        }
    }

    public ArrayList<Integer> getArrSum() {
        return arrSum;
    }
}
