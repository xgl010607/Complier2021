package grammar_analysis;

import AST.*;
import AST.Number;
import error_handler.ErrorTable;
import error_handler.Symbol;
import error_handler.SymbolTable;

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
    private final ArrayList<Integer> lines;
    private final ErrorTable errorTable;
    private final SymbolTable symbolTable;
    private int listWhilePoint = 0;

    public Grammar_Analysis(ArrayList<String> tokens, ArrayList<String> symbols,
                            ArrayList<Integer> lines, ErrorTable errorTable) {
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
        }
    }

    public void reChar() {
        i--;
        token = tokens.get(i - 1);
        symbol = symbols.get(i - 1);
        output.remove(output.get(output.size() - 1));
        line = lines.get(i - 1);
    }

    public CompUnit compUnitParser() throws IOException {
        CompUnit compUnit = new CompUnit();
        while (symbol.equals("CONSTTK") ||
                (symbol.equals("INTTK") && !symbols.get(i + 1).equals("LPARENT"))) {
            compUnit.addDecl(DeclParser());
            getToken();
        }
        while (symbol.equals("VOIDTK") ||
                (symbol.equals("INTTK") && !symbols.get(i).equals("MAINTK"))) {
            compUnit.addFuncDef(FuncDefParser());
            getToken();
        }
        compUnit.setMainFuncDef(MainFuncDefParser());
        getToken();
        output.add("<CompUnit>");
        return compUnit;
    }

    public Decl DeclParser() throws IOException {
        if (symbol.equals("CONSTTK")) {
            return new Decl(ConstDeclParser());
        } else if (symbol.equals("INTTK")){
            return new Decl(VarDeclParser());
        }
        return null;
    }

    public ConstDecl ConstDeclParser() throws IOException {
        if (symbol.equals("CONSTTK")) {
            getToken();
        }
        ConstDecl constDecl = new ConstDecl(BTypeParser());
        getToken();
        constDecl.addConstDef(ConstDefParser());
        Integer lineError = line;
        getToken();
        while(symbol.equals("COMMA")) {
            getToken();
            lineError = line;
            constDecl.addConstDef(ConstDefParser());
            getToken();
        }
        if (symbol.equals("SEMICN")) {
            output.add("<ConstDecl>");
        } else {
            error("i", lineError);
            reChar();
        }
        return constDecl;
    }

    public ConstDef ConstDefParser() throws IOException {
        ConstDef constDef = new ConstDef(IdentParser());
        String name = token;
        Integer dimension = 0;
        Integer lineIdent = line;
        getToken();
        while(symbol.equals("LBRACK")) {
            dimension++;
            getToken();
            Integer lineError = line;
            constDef.addConstExp(ConstExpParser());
            getToken();
            if (symbol.equals("RBRACK")) {
                getToken();
            } else {
                error("k", lineError);
            }
        }
        Symbol symbolInsert = new Symbol(name, "ConstDef", "int", lev, lineIdent, dimension);
        symbolTable.addToTable(symbolInsert,lev);
        constDef.getIdent().fillIdent(symbolInsert);
        if (symbol.equals("ASSIGN")) {
            getToken();
            constDef.setConstInitVal(ConstInitValParser());
        }
        output.add("<ConstDef>");
        return constDef;
    }

    public ConstInitVal ConstInitValParser() throws IOException {
        ConstInitVal constInitVal = null;
        if (symbol.equals("LBRACE")) {
            getToken();
            if (!symbol.equals("RBRACE")) {
                constInitVal = new ConstInitVal(ConstInitValParser());
                getToken();
                while(symbol.equals("COMMA")) {
                    getToken();
                    constInitVal.addConstInitVal(ConstInitValParser());
                    getToken();
                }
            }
            if (symbol.equals("RBRACE")) {
                output.add("<ConstInitVal>");
            }
        } else {
            constInitVal = new ConstInitVal(ConstExpParser());
            output.add("<ConstInitVal>");
        }
        return constInitVal;
    }

    public VarDecl VarDeclParser() throws IOException {
        VarDecl varDecl = new VarDecl(BTypeParser());
        getToken();
        varDecl.addVarDef(VarDefParser());
        Integer lineError = line;
        getToken();
        while(symbol.equals("COMMA")) {
            getToken();
            lineError = line;
            varDecl.addVarDef(VarDefParser());
            getToken();
        }
        if (symbol.equals("SEMICN")) {
            output.add("<VarDecl>");
        } else {
            errorTable.addError("i", lineError);
            reChar();
        }
        return varDecl;
    }

    public VarDef VarDefParser() throws IOException {
        VarDef varDef = new VarDef(IdentParser());
        Integer lineIdent = line;
        String name = token;
        Integer dimension = 0;
        while(tokens.get(i).equals("[")) {
            dimension++;
            getToken();
            getToken();
            Integer lineError = line;
            varDef.addConstExp(ConstExpParser());
            getToken();
            if (token.equals("]")) {
                if (!tokens.get(i).equals("["))
                    break;
            } else {
                error("k", lineError);
                reChar();
            }
        }
        Symbol symbolInsert = new Symbol(name, "VarDef", "int", lev, lineIdent, dimension);
        symbolTable.addToTable(symbolInsert, lev);
        varDef.getIdent().fillIdent(symbolInsert);
        if (tokens.get(i).equals("=")) {
            getToken();
        }
        if (token.equals("=")) {
            getToken();
            varDef.setInitVal(InitValParser());
        }
        output.add("<VarDef>");
        return varDef;
    }

    public InitVal InitValParser() throws IOException {
        InitVal initVal = null;
        if (token.equals("{")) {
            getToken();
            if (!token.equals("}")){
                initVal = new InitVal(InitValParser());
                getToken();
                while(token.equals(",")) {
                    getToken();
                    initVal.addInitVal(InitValParser());
                    getToken();
                }
            }
            if (!token.equals("}")) {

            }
        } else {
            initVal = new InitVal(ExpParser());
        }
        output.add("<InitVal>");
        return initVal;
    }

    public FuncDef FuncDefParser() throws IOException {
        // this is for error "g", 'return'
        FuncDef funcDef = new FuncDef(FuncTypeParser());
        String type = token;
        getToken();
        Integer lineJ;
        funcDef.setIdent(IdentParser());
        Symbol symbolInsert = new Symbol(token, "FuncDef", type, lev, line, 0);
        symbolTable.addToTable(symbolInsert, lev);
        funcDef.getIdent().fillIdent(symbolInsert);
        getToken();
        if (token.equals("(")) {
            getToken();
            lineJ = line;
            // FuncFParams == null 表示 形参是空的，即没有参数
            FuncFParams funcFParams = FuncFParamsParser(symbolTable.getTopSymbol() - 1);
            if (funcFParams != null) {
                getToken();
            }
            if (token.equals(")")) {
                getToken();
            } else {
                error("j", lineJ);
            }
            funcDef.setBlock(BlockParser());
            if (funcDef.getFuncType().getType().equals("int") &&
                    !funcDef.getBlock().isCheckReturn()) {
                error("g", line);
            }
        }
        output.add("<FuncDef>");
        return funcDef;
    }

    public FuncType FuncTypeParser() {
        if (!symbol.equals("VOIDTK") && !symbol.equals("INTTK")) {

        }
        output.add("<FuncType>");
        return new FuncType(token);
    }

    public Block BlockParser() throws IOException {
        Block block = new Block();
        if (token.equals("{")) {
            lev++;
            getToken();
            while(!token.equals("}") && i < tokens.size()) {
                block.addBlockItem(BlockItemParser());
                getToken();
            }
            if (!token.equals("}")) {

            }
            symbolTable.clearLevSymbols(lev);
            lev--;
        }
        output.add("<Block>");
        return block;
    }

    public BlockItem BlockItemParser() throws IOException {
        if (symbol.equals("CONSTTK") || symbol.equals("INTTK")) {
            return new BlockItem(DeclParser());
        } else {
            return new BlockItem(StmtParser());
        }
    }

    public Stmt StmtParser() throws IOException {
        Stmt stmt = null;
        Integer lineError;
        switch (symbol) {
            case "IFTK":
                stmt = new Stmt(IfStmtParser());
                break;
            case "WHILETK":
                stmt = new Stmt(WhileStmtParser());
                break;
            case "BREAKTK":
            case "CONTINUETK":
                stmt = new Stmt(BreakContStmtParser());
                break;
            case "RETURNTK":
                stmt = new Stmt(ReturnStmtParser());
                break;
            case "PRINTFTK":
                stmt = new Stmt(PrintStmtParser());
                break;
            case "LBRACE":
                BlockStmt blockStmt = new BlockStmt(BlockParser());
                stmt = new Stmt(blockStmt);
                break;
            case "SEMICN":
                stmt = new Stmt(new ExpStmt(null));
                break;
            default:
                lineError = line;
                int before = i;
                System.out.println("before is " + before);
                errorTable.stopBefore();
                Exp exp = ExpParser();
                getToken();
                if (token.equals("=")) {
                    //说明是LVAL = 类型，退回解析LVAl
                    while (i != before) {
                        reChar();
                    }
                    errorTable.delError();
                    LVal lVal = LValParser();
                    getToken();
                    String kind = lVal.getKind();
                    if (kind != null && kind.equals("ConstDef")) {
                        error("h", lineError);
                    }
                    getToken();
                    if (symbol.equals("GETINTTK")) {//LVal = getint();
                        stmt = new Stmt(new LValGetIntStmt(lVal));
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
                        stmt = new Stmt(new LValExpStmt(lVal, ExpParser()));
                        getToken();
                    }
                } else {
                    stmt = new Stmt(new ExpStmt(exp));
                }
                if (!token.equals(";")) {
                    error("i", lineError);
                    reChar();
                }
                break;
        }
        output.add("<Stmt>");
        return stmt;
    }

    public LVal LValParser() throws IOException {
        LVal lVal = new LVal();
        IdentParser();
        //查表看它是void 还是 int 数组维度 先看他是否存在
        Integer dimension = 0;
        Integer lineError;
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

    public Cond CondParser() throws IOException {
        Cond cond = new Cond(LOrExpParser());
        output.add("<Cond>");
        return cond;
    }

    public LOrExp LOrExpParser() throws IOException {
        LOrExp lOrExp = new LOrExp(LAndExpParser());
        while(tokens.get(i).equals("||")) {
            output.add("<LOrExp>");
            getToken();
            getToken();
            lOrExp.addLAndExp(LAndExpParser());
        }
        output.add("<LOrExp>");
        return lOrExp;
    }

    public LAndExp LAndExpParser() throws IOException {
        LAndExp lAndExp = new LAndExp(EqExpParser());
        while(tokens.get(i).equals("&&")) {
            output.add("<LAndExp>");
            getToken();
            getToken();
            lAndExp.addEqExp(EqExpParser());
        }
        output.add("<LAndExp>");
        return lAndExp;
    }

    public EqExp EqExpParser() throws IOException {
        EqExp eqExp = new EqExp(RelExpParser());
        while(tokens.get(i).equals("==") || tokens.get(i).equals("!=")) {
            output.add("<EqExp>");
            getToken();
            getToken();
            eqExp.addRelExp(RelExpParser());
        }
        output.add("<EqExp>");
        return eqExp;
    }

    public RelExp RelExpParser() throws IOException {
        RelExp relExp = new RelExp(AddExpParser());
        while(tokens.get(i).equals("<") || tokens.get(i).equals(">") ||
                tokens.get(i).equals("<=") || tokens.get(i).equals(">=")) {
            output.add("<RelExp>");
            getToken();
            getToken();
            relExp.addAddExp(AddExpParser());
        }
        output.add("<RelExp>");
        return relExp;
    }

    public FormatString FormatStringParser() {
        if (!symbol.equals("STRCON")) {
            error("a", line);
        }
        return new FormatString(token);
    }

    public MainFuncDef MainFuncDefParser() throws IOException {
        MainFuncDef mainFuncDef = null;
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
                    mainFuncDef = new MainFuncDef(BlockParser());
                    if (!mainFuncDef.getBlock().isCheckReturn()) {
                        error("g", line);
                    }
                }
            }
        }
        output.add("<MainFuncDef>");
        return mainFuncDef;
    }

    public Ident IdentParser() {
        if (!symbol.equals("IDENFR")) {
            System.out.println("IdentError! " + line + " " + token);
        }
        return new Ident();
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
                errorType = "d";
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
            getToken();
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

    public BType BTypeParser() {
        if (!symbol.equals("INTTK")) {
            return null;
        }
        return new BType(token);
    }

    public IfStmt IfStmtParser() throws IOException {
        IfStmt ifStmt = null;
        Integer lineError;
        getToken();
        if (token.equals("(")) {
            getToken();
            lineError = line;
            ifStmt = new IfStmt(CondParser());
            getToken();
            if (token.equals(")")) {
                getToken();
            } else {
                error("j", lineError);
            }
            ifStmt.setStmt(StmtParser());
            if (symbols.get(i).equals("ELSETK")) {
                getToken();
                getToken();
                ifStmt.setElStmt(StmtParser());
            }
        }
        return ifStmt;
    }

    public WhileStmt WhileStmtParser() throws IOException {
        WhileStmt whileStmt = null;
        Integer lineError;
        listWhilePoint++;
        getToken();
        if (token.equals("(")) {
            getToken();
            lineError = line;
            whileStmt = new WhileStmt(CondParser());
            getToken();
            if (token.equals(")")) {
                getToken();
            } else {
                error("j", lineError);
            }
            whileStmt.setStmt(StmtParser());
        }
        listWhilePoint--;
        return whileStmt;
    }

    public BreakContStmt BreakContStmtParser() {
        BreakContStmt breakContStmt = new BreakContStmt(token);
        Integer lineError = line;
        getToken();
        if (!token.equals(";")) {
            error("i", lineError);
            reChar();
        }
        if (listWhilePoint == 0) {
            error("m", lineError);
        }
        return breakContStmt;
    }

    public ReturnStmt ReturnStmtParser() throws IOException {
        ReturnStmt returnStmt = null;
        int lineReturn = line;
        int lineError = line;
        getToken();
        //判断这个函数的类型，如果是void，后面只能跟;
        //如果是int,就解析Exp再跟;
        //可以查表,因为重复的元素都加进去了，所以最近的就是那个函数名
        String type = symbolTable.checkFuncType();
        if (type.equals("int")) {
            lineError = line;
            Exp exp = ExpParser();
            returnStmt = new ReturnStmt(exp);
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
            returnStmt = new ReturnStmt(exp);
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
        return returnStmt;
    }

    public PrintStmt PrintStmtParser () throws IOException {
        PrintStmt printStmt = null;
        int linePrint = line;
        int lineError;
        getToken();
        if (token.equals("(")) {
            getToken();
            printStmt = new PrintStmt(FormatStringParser());
            int count = printStmt.getFormatString().getParaCount();
            int temp = 0;
            lineError = line;
            getToken();
            while (token.equals(",")) {
                temp++;
                getToken();
                lineError = line;
                printStmt.addExp(ExpParser());
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
        return printStmt;
    }



    public void error(String errorType, Integer lineNow) {
        System.out.println(lineNow + " " + errorType);
        errorTable.addError(errorType, lineNow);
    }

}
