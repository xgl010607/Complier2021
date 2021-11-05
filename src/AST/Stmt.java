package AST;

public class Stmt {
    // 1 LVal = Exp ;
    private LVal lVal = null;
    private Exp exp = null;

    //2 单纯一个exp [Exp];

    //3 Block
    private Block block = null;

    //4 if ( Cond ) Stmt [ else Stmt]
    private Cond cond = null;
    private Stmt stmt = null;

    //
}
