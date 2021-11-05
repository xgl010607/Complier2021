package AST;

public class FormatString {
    private Integer paraCount;
    private String string;

    public FormatString(String string) {
        this.paraCount = 0;
        this.string = string;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '%') {
                i++;
                if (i < string.length() && string.charAt(i) == 'd') {
                    paraCount++;
                }
            }
        }
    }

    public Integer getParaCount() {
        return paraCount;
    }

    public String getString() {
        return string;
    }
}
