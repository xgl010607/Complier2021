package AST;

public class ConstExp {
    private AddExp addExp = null;

    public ConstExp(AddExp addExp) {
        this.addExp = addExp;
    }

    public AddExp getAddExp() {
        return addExp;
    }
}
