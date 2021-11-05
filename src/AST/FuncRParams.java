package AST;

import error_handler.Symbol;

import java.util.ArrayList;

public class FuncRParams {
    ArrayList<Exp> exps;

    public FuncRParams() {
        this.exps = new ArrayList<>();
    }

    public void addExp(Exp exp) {
        if (exp != null && exp.getType() != null) {
            exps.add(exp);
        }
    }

    public String compareRF(ArrayList<Symbol> funcParams) {
        int sizeDefine = funcParams.size();
        int sizeRParams = exps.size();
        System.out.println("sizeDefine:" + sizeDefine+",sizeRParams:" + sizeRParams);
        if (sizeRParams != sizeDefine) {
            return "d";
        } else {
            for (int i = 0; i < sizeDefine; i++) {
                Exp exp = exps.get(i);
                Symbol symbol = funcParams.get(i);
                System.out.println("Line "+ symbol.getLine() + ": "+symbol. getName()+" "+ symbol.getType()+" " + symbol.getDimension());
                System.out.println(exp.getType() + exp.getDimension());
                if (exp.getType() == null ) {
                    return "e";
                }
                if (exp.getType().equals(symbol.getType()) && (exp.getDimension() == symbol.getDimension())) {
                    continue;
                }
                return "e";
            }
        }
        return null;
    }

    public ArrayList<Exp> getExps() {
        return exps;
    }
}
