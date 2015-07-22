package tb.common.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockMetadata extends ItemBlock
{

	public ItemBlockMetadata(Block p_i45328_1_) {
		super(p_i45328_1_);
		this.setHasSubtypes(true);
	}
	
    public int getMetadata(int meta)
    {
        return meta;
    }
	
}
