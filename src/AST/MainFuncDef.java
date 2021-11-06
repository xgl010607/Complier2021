package AST;

public class MainFuncDef {
    private Block block;

    public MainFuncDef(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }
}
