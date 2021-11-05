package grammar_analysis;

import AST.*;
import AST.Number;
import error_handler.ErrorTable;
import error_handler.Symbol;
import error_handler.SymbolTable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Grammar_Analysis {
    private final ArrayList<String> tokens;
    private final ArrayList<String> symbols;
    private Integer i = 0;
    private String token;
    private String symbol;
    private Integer line;
    private Integer lev = 0;
    private final ArrayList<String> output = new ArrayList<>();
    private ArrayList<Integer> lines;
    private ErrorTable errorTable;
    private SymbolTable symbolTable;
    private int listWhilePoint = 0;

    public Grammar_Analysis(ArrayList<String> tokens, ArrayList<String> symbols,
                            ArrayList<Integer> lines, ErrorTable errorTable)
            throws IOException {
        this.tokens = tokens;
        this.symbols = symbols;
        this.lines = lines;
        this.errorTable = errorTable;
        this.symbolTable = new SymbolTable(errorTable);
    }

    public ArrayList<String> getOutput() {
        return output;
    }

    public void grammarAnalysis() throws IOException {
        getToken();
        compUnitParser();
    }

    public void getToken() {
        if (i < tokens.size()) {
            token = tokens.get(i);
            symbol = symbols.get(i);
            output.add(symbol + " " + token);
            line = lines.get(i);
            //System.out.println(symbol + " " + token);
            i++;
            System.out.println("get Token,token:" + token +", symbol:" + symbol +", line:" + line);
        }
    }

    public void reChar() {
        i--;
        token = tokens.get(i - 1);
        symbol = symbols.get(i - 1);
        output.remove(output.get(output.size() - 1));
        line = lines.get(i - 1);
    }

    public void compUnitParser() throws IOException {
        while (symbol.equals("CONSTTK") ||
                (symbol.equals("INTTK") && !symbols.get(i + 1).equals("LPARENT"))) {
            Decl();
            getToken();
        }
        while (symbol.equals("VOIDTK") ||
                (symbol.equals("INTTK") && !symbols.get(i).equals("MAINTK"))) {
            FuncDefParser();
            getToken();
        }
        MainFuncDefParser();
        getToken();
        output.add("<CompUnit>");
    }

    public void Decl() throws IOException {
        if (symbol.equals("CONSTTK")) {
            ConstDeclParser();
        } else if (symbol.equals("INTTK")){
            VarDeclParser();
        }
    }

    public void ConstDeclParser() throws IOException {
        if (symbol.equals("CONSTTK")) {
            getToken();
        }
        BTypeParser();
        getToken();
        ConstDefParser();
        Integer lineError = line;
        getToken();
        while(symbol.equals("COMMA")) {
            getToken();
            lineError = line;
            ConstDefParser();
            getToken();
        }
        if (symbol.equals("SEMICN")) {
            output.add("<ConstDecl>");
        } else {
            error("i", lineError);
            reChar();
        }
    }

    public void ConstDefParser() throws IOException {
        IdentParser();
        String name = token;
        Integer dimension = 0;
        Integer lineIdent = line;
        getToken();
        while(symbol.equals("LBRACK")) {
            dimension++;
            getToken();
            Integer lineError = line;
            ConstExpParser();
            getToken();
            if (symbol.equals("RBRACK")) {
                getToken();
            } else {
                error("k", lineError);
            }
        }
        symbolTable.addToTable(new Symbol(name, "ConstDef", "int", lev, lineIdent, dimension),lev);
        if (symbol.equals("ASSIGN")) {
            getToken();
            ConstInitValParser();
        }
        output.add("<ConstDef>");
    }

    public void ConstInitValParser() throws IOException {
        if (symbol.equals("LBRACE")) {
            getToken();
            if (!symbol.equals("RBRACE")) {
                ConstInitValParser();
                getToken();
                while(symbol.equals("COMMA")) {
                    getToken();
                    ConstInitValParser();
                    getToken();
                }
            }
            if (symbol.equals("RBRACE")) {
                output.add("<ConstInitVal>");
            }
        } else {
            ConstExpParser();
            output.add("<ConstInitVal>");
        }

    }

    public void VarDeclParser() throws IOException {
        BTypeParser();
        getToken();
        VarDefParser();
        Integer lineError = line;
        getToken();
        while(symbol.equals("COMMA")) {
            getToken();
            lineError = line;
            VarDefParser();
            getToken();
        }
        if (symbol.equals("SEMICN")) {
            output.add("<VarDecl>");
        } else {
            errorTable.addError("i", lineError);
            reChar();
        }
    }

    public void VarDefParser() throws IOException {
        IdentParser();
        Integer lineIdent = line;
        String name = token;
        Integer dimension = 0;
        while(tokens.get(i).equals("[")) {
            dimension++;
            getToken();
            getToken();
            Integer lineError = line;
            ConstExpParser();
            getToken();
            if (token.equals("]")) {
                if (!tokens.get(i).equals("["))
                    break;
            } else {
                error("k", lineError);
                reChar();
            }
        }
        symbolTable.addToTable(new Symbol(name, "VarDef", "int", lev, lineIdent, dimension), lev);
        if (tokens.get(i).equals("=")) {
            getToken();
        }
        if (token.equals("=")) {
            getToken();
            InitValParser();
        }
        output.add("<VarDef>");
    }

    public void InitValParser() throws IOException {
        if (token.equals("{")) {
            getToken();
            if (!token.equals("}")){
                InitValParser();
                getToken();
                while(token.equals(",")) {
                    getToken();
                    InitValParser();
                    getToken();
                }
            }
            if (!token.equals("}")) {

            }
        } else {
            ExpParser();
        }
        output.add("<InitVal>");
    }

    public void FuncDefParser() throws IOException {
        FuncTypeParser();
        String type = token;
        getToken();
        Integer lineJ = line;
        IdentParser();
        symbolTable.addToTable(new Symbol(token, "FuncDef", type, lev, line, 0), lev);
        getToken();
        if (token.equals("(")) {
            getToken();
            lineJ = line;
            System.out.println("funcdef now token is " + token);
            FuncFParams funcFParams = FuncFParamsParser(symbolTable.getTopSymbol() - 1);
            System.out.println("形参组是空吗? " + (funcFParams == null));
            if (funcFParams != null) {
                getToken();
            }
            if (token.equals(")")) {
                getToken();
            } else {
                error("j", lineJ);
            }
            if (BlockParser() == false && type.equals("int")) {
                error("g", line);
            }
        }

        output.add("<FuncDef>");
    }

    public void FuncTypeParser() {
        System.out.println("funcType checking! "+ token +" " + symbol);
        if (!symbol.equals("VOIDTK") && !symbol.equals("INTTK")) {

        }
        output.add("<FuncType>");
    }

    public boolean BlockParser() throws IOException {
        boolean checkReturn = false;
        if (token.equals("{")) {
            lev++;
            getToken();
            while(!token.equals("}") && i < tokens.size()) {
                checkReturn = BlockItemParser();
                getToken();
            }
            if (!token.equals("}")) {

            }
            symbolTable.clearLevSymbols(lev);
            lev--;
        }
        output.add("<Block>");
        return  checkReturn;
    }

    public boolean BlockItemParser() throws IOException {
        boolean checkReturn = false;
        if (symbol.equals("CONSTTK") || symbol.equals("INTTK")) {
            Decl();
        } else {
            checkReturn = StmtParser();
        }
        return checkReturn;
    }

    public boolean StmtParser() throws IOException {
        boolean checkReturn = false;
        Integer lineError = line;
        switch (symbol) {
            case "IFTK":
                getToken();
                if (token.equals("(")) {
                    getToken();
                    lineError = line;
                    CondParser();
                    getToken();
                    if (token.equals(")")) {
                        getToken();
                    } else {
                        error("j", lineError);
                    }
                    StmtParser();
                    if (symbols.get(i).equals("ELSETK")) {
                        getToken();
                        getToken();
                        StmtParser();
                    }
                }
                break;
            case "WHILETK":
                listWhilePoint++;
                getToken();
                if (token.equals("(")) {
                    getToken();
                    lineError = line;
                    CondParser();
                    getToken();
                    if (token.equals(")")) {
                        getToken();
                    } else {
                        error("j", lineError);
                    }
                    StmtParser();
                }
                listWhilePoint--;
                break;
            case "BREAKTK":
            case "CONTINUETK":
                lineError = line;
                getToken();
                if (!token.equals(";")) {
                    error("i", lineError);
                    reChar();
                }
                if (listWhilePoint == 0) {
                    error("m", lineError);
                }
                break;
            case "RETURNTK":
                checkReturn = true;
                int lineReturn = line;
                lineError = line;
                getToken();
                //判断这个函数的类型，如果是void，后面只能跟;
                //如果是int,就解析Exp再跟;
                //可以查表,因为重复的元素都加进去了，所以最近的就是那个函数名
                String type = symbolTable.checkFuncType();
                if (type.equals("int")) {
                    lineError = line;
                    Exp exp = ExpParser();
                    if (exp != null) {
                        getToken();
                    }
                    if (!token.equals(";")) {
                        if (token.equals("}")) {
                            lineError = lineReturn;
                        }
                        error("i", lineError);
                        reChar();
                    }
                } else if (type.equals("void")) {
                    Integer lineBefore = line;
                    Exp exp = ExpParser();
                    if (exp != null) {//说明有exp，那么就不匹配。
                        error("f", lineReturn);
                        lineError = lineBefore;
                        getToken();
                    }
                    if (!token.equals(";")) {
                        error("i", lineError);
                        reChar();
                    }
                }

                break;
            case "PRINTFTK":
                int linePrint = line;
                getToken();
                if (token.equals("(")) {
                    getToken();
                    int count = FormatStringParser().getParaCount();
                    int temp = 0;
                    lineError = line;
                    getToken();
                    while (token.equals(",")) {
                        temp++;
                        getToken();
                        lineError = line;
                        ExpParser();
                        getToken();
                    }
                    if (count != temp) {
                        error("l", linePrint);
                    }
                    if (token.equals(")")) {
                        getToken();
                    } else {
                        error("j", lineError);
                    }
                    if (!token.equals(";")) {
                        error("i", lineError);
                        reChar();
                    }
                }
                break;
            case "LBRACE":
                BlockParser();
                break;
            case "SEMICN":
                break;
            default:
                lineError = line;
                Exp exp = ExpParser();
                String kind = exp.getKind();
                getToken();
                if (token.equals("=")) {//LVAl
                    if (kind != null && kind.equals("ConstDef")) {
                        error("h", lineError);
                    }
                    getToken();
                    if (symbol.equals("GETINTTK")) {//LVal = getint();
                        getToken();
                        if (token.equals("(")) {
                            getToken();
                            if (token.equals(")")) {
                                getToken();
                            } else {
                                error("j", lineError);
                            }
                        }
                    } else {// LVal = Exp;
                        lineError = line;
                        ExpParser();
                        getToken();
                    }
                }
                if (!token.equals(";")) {
                    error("i", lineError);
                    reChar();
                }
                break;
        }
        output.add("<Stmt>");
        return checkReturn;
    }

    public LVal LValParser() throws IOException {
        LVal lVal = new LVal();
        IdentParser();
        //查表看它是void 还是 int 数组维度 先看他是否存在
        Integer dimension = 0;
        Integer lineError = line;
        //查表，从内往上，看它是否定义了这个ident
        Symbol preSymbol = symbolTable.checkSymbol(token, line);
        System.out.println("checking LVAL" + preSymbol);
        if (preSymbol != null) {
            dimension = preSymbol.getDimension();
            System.out.println("ident:" + token + ",dimension:" + preSymbol.getDimension() + ",type:" + preSymbol.getType());
        }
        lVal.setIdent(new Ident(preSymbol));
        while(tokens.get(i).equals("[")) {
            dimension--;
            getToken();
            getToken();
            lineError = line;
            lVal.addExp(ExpParser());
            getToken();
            if (!token.equals("]")) {
                error("k", lineError);
                reChar();
            }
        }
        System.out.println("dimension now is " + dimension + ", lVal type is " + lVal.getType());
        lVal.setDimension(dimension);
        output.add("<LVal>");
        return lVal;
    }

    public void CondParser() throws IOException {
        LOrExpParser();
        output.add("<Cond>");
    }

    public void LOrExpParser() throws IOException {
        LAndExpParser();
        while(tokens.get(i).equals("||")) {
            output.add("<LOrExp>");
            getToken();
            getToken();
            LAndExpParser();
        }
        output.add("<LOrExp>");
    }

    public void LAndExpParser() throws IOException {
        EqExpParser();
        while(tokens.get(i).equals("&&")) {
            output.add("<LAndExp>");
            getToken();
            getToken();
            EqExpParser();
        }
        output.add("<LAndExp>");
    }

    public void EqExpParser() throws IOException {
        RelExpParser();
        while(tokens.get(i).equals("==") || tokens.get(i).equals("!=")) {
            output.add("<EqExp>");
            getToken();
            getToken();
            RelExpParser();
        }
        output.add("<EqExp>");
    }

    public void RelExpParser() throws IOException {
        AddExpParser();
        while(tokens.get(i).equals("<") || tokens.get(i).equals(">") ||
                tokens.get(i).equals("<=") || tokens.get(i).equals(">=")) {
            output.add("<RelExp>");
            getToken();
            getToken();
            AddExpParser();
        }
        output.add("<RelExp>");
    }

    public FormatString FormatStringParser() {
        if (!symbol.equals("STRCON")) {
            error("a", line);
        }
        FormatString formatString = new FormatString(token);
        return formatString;
    }

    public void MainFuncDefParser() throws IOException {
        if (symbol.equals("INTTK")) {
            getToken();
            if (symbol.equals("MAINTK")) {
                symbolTable.addToTable(new Symbol("main", "FuncDef", "int", lev, line, 0), lev);
                getToken();
                if (token.equals("(")) {
                    Integer lineError = line;
                    getToken();
                    if (token.equals(")")) {
                        getToken();
                    } else {
                        error("j", lineError);
                    }
                    if (BlockParser() == false) {
                        error("g", line);
                    };
                }
            }
        }

        output.add("<MainFuncDef>");
    }

    public void IdentParser() {
        if (!symbol.equals("IDENFR")) {
            System.out.println("IdentError! " + line + " " + token);
        }
    }

    public AddExp AddExpParser() throws IOException {
        System.out.println("addExp begin! " + token);
        AddExp addExp = new AddExp();
        MulExp mulExp = MulExpParser();
        if (mulExp == null) {
            System.out.println("addExp going null ");
            return null;
        }
        addExp.addMulExp(mulExp);
        while(tokens.get(i).equals("+") || tokens.get(i).equals("-")) {
            output.add("<AddExp>");
            getToken();
            addExp.addOp(token);
            getToken();
            addExp.addMulExp(MulExpParser());
        }
        output.add("<AddExp>");
        System.out.println("addExp over! " + token);
        return addExp;
    }

    public MulExp MulExpParser() throws IOException {
        System.out.println("mulExp begin! " + token);
        MulExp mulExp = new MulExp();
        UnaryExp unaryExp = UnaryExpParser();
        if (unaryExp == null) {
            System.out.println("mueExp going null");
            return null;
        }
        mulExp.addUnaryExp(unaryExp);
        while(tokens.get(i).equals("*") || tokens.get(i).equals("/") || tokens.get(i).equals("%")) {
            output.add("<MulExp>");
            getToken();
            mulExp.addOp(token);
            getToken();
            mulExp.addUnaryExp(UnaryExpParser());
        }
        output.add("<MulExp>");
        System.out.println("mulExp over!" + token);
        return mulExp;
    }

    public UnaryExp UnaryExpParser() throws IOException {
        System.out.println("unaryExp begin " + token);
        UnaryExp unaryExp = new UnaryExp();
        System.out.println("token is " + token);
        // UnaryOp
        if (token.equals("+") || token.equals("-") || token.equals("!")) {
            unaryExp.setUnaryOp(UnaryOpParser());
            getToken();
            unaryExp.setUnaryExp(UnaryExpParser());
        } else if (symbol.equals("INTCON") || token.equals("(")
                    || i == tokens.size() || !tokens.get(i).equals("(") ) {
            unaryExp.setPrimaryExp(PrimaryExpParser());
        } else if (symbol.equals("IDENFR")){
            Integer lineFunc = line;
            IdentParser();
            //查询类别 这里是在找函数 先看它存不存在
            Symbol func = symbolTable.checkFuncSymbol(token, line, "FuncDef");
            System.out.println("checking Unary");
            unaryExp.setIdent(new Ident(func));
            //func 为null说明函数不存在
            ArrayList<Symbol> funcParams = null;
            if (func != null) {
                System.out.println("Line : " + func.getLine() + ",func is " + func.getName());
                System.out.println("now Line is " +line);
                funcParams = func.getParas();//函数的形参表
            }
            getToken();
            FuncRParams funcRParams = null;
            if (token.equals("(")) {
                //System.out.println("正在解析函数 " + func.getName());
                getToken();
                //System.out.println("获取参数表");
                Integer lineJ = line;
                funcRParams = FuncRParamsParser();
                unaryExp.setFuncRParams(funcRParams);//实参
                System.out.println("获取完毕");
                /*
                System.out.println("func Line:" + lineFunc + ", " + func.getName() + " type:" + unaryExp.getType()+", dimension:" + unaryExp.getDimension());
                System.out.println("函数返回值 " + unaryExp.getType() + unaryExp.getDimension());
                if (unaryExp.getFuncRParams() != null) {
                    System.out.println("实参的个数 " + unaryExp.getFuncRParams().getExps().size());
                    System.out.println("实参的type和维度：" + unaryExp.getFuncRParams().getExps().get(0).getType() +" " + unaryExp.getFuncRParams().getExps().get(0).getDimension());
                }
                if (func != null && !funcParams.isEmpty()) {
                    System.out.println("形参长度: " + func.getParas().size() + ",类型:" + func.getParas().get(0).getType() + ", 维度：" + func.getParas().get(0).getDimension());
                }

                 */
                if (funcRParams != null) {
                    getToken();
                }
                if (!token.equals(")")) {
                    error("j", lineJ);
                    reChar();
                }
               // System.out.println("解析完毕 " + func.getName() + " , token is " + token);
            } else {

            }
            //funcRParams为实参的一个类(可以为null)，funcParams为形参的一个arraylist(不为null)
            //
            String errorType = null;
            if (funcRParams == null) {//说明实参没有 funcParams为形参，形参不为空
                if (funcParams != null && funcParams.size() != 0) {
                    errorType = "d";
                }
            } else if (funcParams == null) {
                if (funcRParams != null) {
                    errorType = "d";
                }
            } else {
                errorType = unaryExp.getFuncRParams().compareRF(funcParams);
            }
            System.out.println("ERRORTYPE IS " + errorType);
            if (errorType != null) {
                error(errorType, lineFunc);
                System.out.println("putting " + errorType + " in line " + lineFunc);
            }
        } else {
            System.out.println("Im returning null!");
            return null;
        }
        output.add("<UnaryExp>");
        System.out.println("unaryExp is over " + token);
        if (unaryExp.getType() == null) {
            unaryExp = null;
        }
        return unaryExp;
    }

    public FuncRParams FuncRParamsParser() throws IOException {
        FuncRParams funcRParams = new FuncRParams();
        Exp exp = ExpParser();
        if (exp == null) {
            return null;
        }
        funcRParams.addExp(exp);
        System.out.println("tokens的下一个是？" + tokens.get(i));
        while(tokens.get(i).equals(",")) {
            getToken();
            getToken();
            funcRParams.addExp(ExpParser());
        }
        output.add("<FuncRParams>");
        return funcRParams;
    }


    public UnaryOp UnaryOpParser() {
        if (token.equals("+") || token.equals("-") || token.equals("!")) {
            output.add("<UnaryOp>");
            return new UnaryOp(token);
        }
        return null;
    }

    public PrimaryExp PrimaryExpParser() throws IOException {
        System.out.println("PrimaryExp begin! " + token);
        PrimaryExp primaryExp = new PrimaryExp();
        if (token.equals("(")) {
            getToken();
            Integer lineError = line;
            Exp exp = ExpParser();
            System.out.println("lineError is " + lineError);
            primaryExp.setExp(exp);
            if (exp != null) {
                getToken();
            }
            if (!token.equals(")")) {
                error("j", lineError);
                reChar();
            }
        } else if (token.charAt(0) >= '0' && token.charAt(0) <= '9') {
            primaryExp.setNumber(NumberParser());
            System.out.println("setting number " + token);
        } else if (symbol.equals("IDENFR")) {
            primaryExp.setlVal(LValParser());
        } else {
            System.out.println("PrimaryExp going null! " + token);
            return null;
        }
        output.add("<PrimaryExp>");
        System.out.println("PrimaryExp over !" + token);
        return primaryExp;
    }

    public Exp ExpParser() throws IOException {
        System.out.println("Exp begin!"+token);
        Exp exp = new Exp();
        AddExp addExp = AddExpParser();
        if (addExp == null) {
            System.out.println("Exp going null!");
            return null;
        }
        exp.addAddExp(addExp);
        output.add("<Exp>");
        System.out.println(token);
        System.out.println("exp over!" + token);
        return exp;
    }

    public Number NumberParser() {
        IntConstParser();
        output.add("<Number>");
        return new Number(token);
    }

    public void IntConstParser() {
        if (!symbol.equals("INTCON")) {

        }
        //output.add("<IntConst>");
    }

    public FuncFParams FuncFParamsParser(Integer funcIndex) throws IOException {
        FuncFParam funcFParam = FuncFParam(funcIndex);
        if (funcFParam == null) {
            return null;
        }
        FuncFParams funcFParams = new FuncFParams();
        funcFParams.addFuncParam(funcFParam);
        while (tokens.get(i).equals(",")) {
            getToken();
            getToken();
            funcFParams.addFuncParam(FuncFParam(funcIndex));
        }
        output.add("<FuncFParams>");
        return funcFParams;
    }

    public FuncFParam FuncFParam(Integer funcIndex) throws IOException {
        BType bType = BTypeParser();
        if (bType == null) {
            return null;
        }
        String type = token;
        FuncFParam funcFParam = new FuncFParam(bType);
        getToken();
        IdentParser();//函数形参 加表
        System.out.println("函数形参名字 " + token);
        String name = token;
        Integer dimension = 0;
        Integer lineIdent = line;
        Integer lineError = line;
        if (tokens.get(i).equals("[")) {
            dimension++;
            getToken();
            getToken();
            if (!token.equals("]")) {
                error("k", lineError);
                reChar();
            }
            while (tokens.get(i).equals("[")) {
                dimension++;
                getToken();
                getToken();
                lineError = line;
                funcFParam.addConstExp(ConstExpParser());
                getToken();
                if (!token.equals("]")) {
                    error("k", lineError);
                    reChar();
                }
            }
        }
        Symbol FParam = new Symbol(name, "FuncFParam", type, lev + 1, lineIdent, dimension);
        symbolTable.addParas(funcIndex, FParam);
        symbolTable.addToTable(FParam, lev + 1);
        System.out.println("加了"+token +",在第 " + (lev+1) +" 个层");
        funcFParam.setIdent(new Ident(FParam));
        output.add("<FuncFParam>");
        return funcFParam;
    }

    public ConstExp ConstExpParser() throws IOException {
        ConstExp constExp = new ConstExp(AddExpParser());
        output.add("<ConstExp>");
        return constExp;
    }

    public BType BTypeParser() throws IOException {
        if (!symbol.equals("INTTK")) {
            return null;
        }
        return new BType(token);
    }

    public void error(String errorType, Integer lineNow) {
        System.out.println(lineNow + " " + errorType);
        errorTable.addError(errorType, lineNow);
    }

    /*
    public boolean checkReturn() {
        boolean errorG = false;
        reChar();
        if (token.equals(";")) {
            reChar();
            reChar();
            if (!symbol.equals("RETURNTK")) {
                errorG = true;
            }
            getToken();
            getToken();
        } else {
            reChar();
            if (!symbol.equals("RETURNTK")) {
                errorG = true;
            }
            getToken();
        }
        getToken();
        return errorG;
    }

     */
}
