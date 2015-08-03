package tb.common.itemblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tb.common.block.BlockTBSapling;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

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
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        return this.field_150939_a.getIcon(2, meta);
    }

}
