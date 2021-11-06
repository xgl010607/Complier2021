package AST;

import java.util.ArrayList;

public class Block {
    private ArrayList<BlockItem> blockItems;
    private boolean checkReturn;

    public Block() {
        blockItems = new ArrayList<>();
        checkReturn = false;
    }

    public void addBlockItem(BlockItem blockItem) {
        blockItems.add(blockItem);
        if (blockItem.isCheckReturn()) {
            checkReturn = true;
        }
    }

    public ArrayList<BlockItem> getBlockItems() {
        return blockItems;
    }

    public boolean isCheckReturn() {
        return checkReturn;
    }
}
