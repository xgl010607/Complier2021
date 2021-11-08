package AST;

public class Number {
    private Integer IntConst;

    public Number() {
    }

    public Number(String intConst) {
        IntConst = new Integer(intConst);
    }

    public Integer getIntConst() {
        return IntConst;
    }
}
