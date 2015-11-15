package tb.common.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import tb.common.block.BlockSpike;

public class ItemBlockSpike extends ItemBlock{

	public ItemBlockSpike(Block b) {
		super(b);
		this.setHasSubtypes(true);
	}
	
    public String getUnlocalizedName(ItemStack stk)
    {
    	return super.getUnlocalizedName(stk)+ (stk.getItemDamage() >= 6 ? BlockSpike.spikeNames[5] : BlockSpike.spikeNames[stk.getItemDamage()]);
    }
    
    public int getMetadata(int meta)
    {
        return meta;
    }

}
