package AST;

public class Exp {
    private AddExp addExp;
    private String type;
    private Integer dimension;
    private String kind;

    public Exp() {
        this.addExp = null;
        this.type = null;//null是读取错误，compError为加和错误，int，void，notDefine是含有未定义的ident
        this.dimension = 0;
        this.kind = null;
    }

    public void addAddExp (AddExp addExp) {
        if (addExp != null) {
            this.addExp = addExp;
            this.type = addExp.getType();
            this.dimension = addExp.getDimension();
            this.kind = addExp.getKind();
        }
    }


    public AddExp getAddExp() {
        return addExp;
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
}
