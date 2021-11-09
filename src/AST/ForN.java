package AST;

public class ForN {
    private String addr;
    private Integer regNow;

    public ForN(String addr, Integer regNow) {
        this.addr = addr;
        this.regNow = regNow;
    }

    public String getAddr() {
        return addr;
    }

    public Integer getRegNow() {
        return regNow;
    }
}
