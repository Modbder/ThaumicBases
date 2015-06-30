package tb.common.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockPyrofluid extends ItemBlock{

	public ItemBlockPyrofluid(Block b) {
		super(b);
		this.setHasSubtypes(true);
	}
	
    public int getMetadata(int meta)
    {
        return meta;
    }

}
