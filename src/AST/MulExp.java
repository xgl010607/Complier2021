package AST;

import java.util.ArrayList;

public class MulExp {
    private ArrayList<UnaryExp> unaryExps;
    private ArrayList<String> ops;
    private String type;
    private Integer dimension;
    private Integer mulTimes;
    private String kind;

    public MulExp() {
        this.unaryExps = new ArrayList<>();
        this.ops = new ArrayList<>();
        this.type = null;
        this.dimension = 0;
        this.mulTimes = 0;
        this.kind = null;
    }

    public void addUnaryExp(UnaryExp unaryExp) {
        if (unaryExp == null) {
            return;
        }

        String nextType = unaryExp.getType();
        Integer nextDim = unaryExp.getDimension();
        String nextKind = unaryExp.getKind();
        if (nextType == null) {
            return;
        }

        if (mulTimes == 0) {
            type = nextType;
            dimension = nextDim;
            kind = nextKind;
        } else {
            switch (type) {
                case "int":
                    if (nextType.equals(type) && nextDim == dimension) {
                        break;
                    } else{
                        type = "compError";
                    }
                    break;
                case "void"://可能有void用[]的情况，暂时先不考虑
                    if (!nextType.equals(type)) {
                        type = "compError";
                    }
                    break;
                case "notDefine":
                    break;
                default:
                    type = "compError";
            }
        }

        unaryExps.add(unaryExp);
        mulTimes++;
    }

    public void addOp(String op) {
        ops.add(op);
    }

    public ArrayList<UnaryExp> getUnaryExps() {
        return unaryExps;
    }

    public ArrayList<String> getOps() {
        return ops;
    }

    public String getType() {
        return type;
    }

    public Integer getDimension() {
        return dimension;
    }

    public String getKind() {
        return kind;
    }

    public Integer getConstValue() {
        Integer ori = unaryExps.get(0).getConstValue();
        for (int i = 0; i < ops.size(); i++) {
            String op = ops.get(i);
            Integer x = unaryExps.get(i + 1).getConstValue();
            switch (op) {
                case "*" :
                    ori *= x;
                    break;
                case "/" :
                    ori /= x;
                    break;
                case "%" :
                    ori %= x;
                    break;
                default:
            }
        }
        return ori;
    }
}
