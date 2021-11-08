package AST;

import java.util.ArrayList;

public class AddExp {
    private ArrayList<MulExp> mulExps;
    private ArrayList<String> ops;
    private String type;
    private Integer dimension;
    private Integer addTimes;
    private String kind;

    public AddExp() {
        this.mulExps = new ArrayList<>();
        this.ops = new ArrayList<>();
        this.type = null;
        this.dimension = 0;
        this.addTimes = 0;
        this.kind = null;
    }

    public void addMulExp(MulExp mulExp) {
        String nextType = mulExp.getType();
        Integer nextDim = mulExp.getDimension();
        String nextKind = mulExp.getKind();
        if (nextType == null) {
            //读的是空字字符
            return;
        }

        if (addTimes == 0) {
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
        mulExps.add(mulExp);
        addTimes++;
    }

    public void addOp(String op) {
        ops.add(op);
    }

    public ArrayList<MulExp> getMulExps() {
        return mulExps;
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
        Integer ori = mulExps.get(0).getConstValue();
        for (int i = 0; i < ops.size(); i++) {
            String op = ops.get(i);
            Integer x = mulExps.get(i + 1).getConstValue();
            switch (op) {
                case "+" :
                    ori += x;
                    break;
                case "-" :
                    ori -= x;
                    break;
                default:
            }
        }
        return ori;
    }
}
