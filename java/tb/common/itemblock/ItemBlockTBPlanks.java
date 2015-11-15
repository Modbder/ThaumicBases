package tb.common.itemblock;

import tb.common.block.BlockTBPlanks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockTBPlanks extends ItemBlock{

	public ItemBlockTBPlanks(Block b) {
		super(b);
		this.setHasSubtypes(true);
	}
	
    public String getUnlocalizedName(ItemStack stk)
    {
    	return "tile."+BlockTBPlanks.names[Math.min(BlockTBPlanks.names.length-1,stk.getItemDamage())];
    }
    
    public int getMetadata(int meta)
    {
        return meta;
    }

}
