package tb.common.itemblock;

import tb.common.block.BlockCrystalBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockCrystal extends ItemBlock{

	public ItemBlockCrystal(Block b) {
		super(b);
		this.setHasSubtypes(true);
	}
	
    public String getUnlocalizedName(ItemStack stk)
    {
    	return super.getUnlocalizedName(stk)+ (stk.getItemDamage() >= 8 ? BlockCrystalBlock.names[7] : BlockCrystalBlock.names[stk.getItemDamage()]);
    }
    
    public int getMetadata(int meta)
    {
        return meta;
    }

}
