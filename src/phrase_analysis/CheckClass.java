package phrase_analysis;

public class CheckClass {
    private char ch;

    public CheckClass(char ch) {
        this.ch = ch;
    }

    public boolean isSpace() {
        return ch == ' ';
    }

    public boolean isNewline() {
        return ch == '\n';
    }

    public boolean isTab() {
        return ch == '\t';
    }

    public boolean isLetter() {
        return (ch >= 'A' && ch <= 'Z') ||
                (ch >= 'a' && ch <= 'z');
    }

    public boolean isNonDigit() {
        return ch >= '1' && ch <= '9';
    }

    public boolean isDigit() {
        return ch >= '0' && ch <= '9';
    }

    public boolean isComma() {
        return ch == ',';
    }

    public boolean isSemi() {
        return ch == ';';
    }

    public boolean isEqu() {
        return ch == '=';
    }

    public boolean isPlus() {
        return ch == '+';
    }

    public boolean isMinus() {
        return ch == '-';
    }

    public boolean isDiv() {
        return ch == '/';
    }

    public boolean isStar() {
        return ch == '*';
    }

    public boolean isLpar() {
        return ch == '(';
    }

    public boolean isRpar() {
        return ch == ')';
    }

    public boolean isAND() {
        return ch == '&';
    }

    public boolean isOR() {
        return ch == '|';
    }

    public boolean isLess() {
        return ch == '<';
    }

    public boolean isGreat() {
        return ch == '>';
    }

    public boolean isNot() {
        return ch == '!';
    }

    public boolean isLbra() {
        return ch == '{';
    }

    public boolean isRbra() {
        return ch == '}';
    }

}
