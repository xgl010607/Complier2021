package error_handler;

import java.util.ArrayList;
import java.util.HashSet;

public class SymbolTable {
    private ArrayList<Symbol> symbols;
    private Integer topSymbol;//符号表栈顶指针,指向第一个空闲位置
    private Integer topLev;//
    private ErrorTable errorTable;

    public SymbolTable(ErrorTable errorTable) {
        this.symbols = new ArrayList<>();
        this.topSymbol = 0;
        this.topLev = 0;
        this.errorTable = errorTable;
    }

    public void addToTable(Symbol symbol, Integer index) {
        //System.out.println("topLev :" + topLev + " , index :" +index );
        if (topLev < index) {
            symbols.add(symbol);
            //System.out.println("topLev<index,adding " + symbol.getName() + ":" + symbol.getLine());
            topSymbol++;
            topLev = index;
        } else if (topLev == index) {
            //考虑同层重定义
            //System.out.println("going rop! " + symbol.getName() + " " + symbol.getLine());
            for (int i = topSymbol - 1; i >= 0; i--) {
                Symbol check = symbols.get(i);
                if (check.getLev() != index) {
                    break;
                }
                if (check.getName().equals(symbol.getName())) {
                    errorTable.addError("b", symbol.getLine());
                    break;
                }
            }
            symbols.add(symbol);
            topSymbol++;
            //System.out.println("adding " + symbol.getName() + ":" + symbol.getLine() + " " + symbol.getLev());
        }
    }

    public void clearLevSymbols(Integer lev) {
        //System.out.println("clear lev " + lev);
        for (int i = topSymbol - 1; i >= 0; i--) {
            if (symbols.get(i).getLev() < lev) {
                break;
            }
            if (symbols.get(i).getLev() == lev) {
                //System.out.println("removing " + symbols.get(i).getName() + ":" + symbols.get(i).getLine());
                symbols.remove(i);
                topSymbol--;
            }
        }
        topLev = lev - 1;
    }

    public Integer getTopSymbol() {
        return topSymbol;
    }

// 看这个变量有没有被定义过 // 它不是函数
    public Symbol checkSymbol(String name, Integer line) {
        for (int i = topSymbol - 1; i >= 0; i--) {
            if (symbols.get(i).getName().equals(name)) {
                if (!symbols.get(i).getKind().equals("FuncDef")) {
                    return symbols.get(i);
                }
            }
        }
        errorTable.addError("c", line);
        return null;
    }

    //主要是找函数，看函数有没有存在
    public Symbol checkFuncSymbol(String name, Integer line, String kind) {
        boolean errorC = true;
        Symbol result = null;
        for (int i = topSymbol - 1; i >= 0; i--) {
            if (symbols.get(i).getName().equals(name)) {
                if (symbols.get(i).getKind().equals(kind)) {
                    result = symbols.get(i);
                    errorC = false;
                }
            }
        }
        if (errorC) {
            errorTable.addError("c", line);
        }
        return result;
    }

    public void getTable() {
        for (Symbol symbol : symbols) {
            System.out.println(symbol.getName() + " " + symbol.getKind() + " " +
                    symbol.getType() + " " + symbol.getLev());
            if (symbol.getKind().equals("FuncDef")) {
                System.out.println(symbol.getParas());
            }
        }
    }

    public void addParas(Integer funcIndex, Symbol FParam) {
        symbols.get(funcIndex).addPara(FParam);
    }

    public String checkFuncType() {
        for (int i = topSymbol - 1; i >= 0; i--) {
            Symbol symbol = symbols.get(i);
            System.out.println("funcdef " + symbol.getKind() + " " + symbol.getLine());
            if (symbol.getKind().equals("FuncDef")) {
                return symbol.getType();
            }
        }
        return "int";
    }
}
