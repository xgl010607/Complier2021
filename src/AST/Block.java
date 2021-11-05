package AST;

import java.util.ArrayList;

public class Block {
    ArrayList<BlockItem> blockItems;
    public Block() {
        blockItems = new ArrayList<>();
    }

    public void addBlockItem(BlockItem blockItem) {
        blockItems.add(blockItem);
    }

    public ArrayList<BlockItem> getBlockItems() {
        return blockItems;
    }
}
