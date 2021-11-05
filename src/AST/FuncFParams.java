package AST;

import java.util.ArrayList;

public class FuncFParams {
    private ArrayList<FuncFParam> funcFParams = new ArrayList<>();

    public FuncFParams() {
    }

    public void addFuncParam (FuncFParam funcFParam) {
        funcFParams.add(funcFParam);
    }
}
