package tb.common.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import tb.common.block.BlockTBSapling;

public class ItemBlockTBSapling extends ItemBlock{

	public ItemBlockTBSapling(Block b) {
		super(b);
		this.setHasSubtypes(true);
	}
	
    public String getUnlocalizedName(ItemStack stk)
    {
    	return "tile."+BlockTBSapling.names[Math.min(BlockTBSapling.names.length-1,stk.getItemDamage()%8)];
    }
    
    public int getMetadata(int meta)
    {
        return meta;
    }

}
