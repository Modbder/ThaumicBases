package tb.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemNodeFoci extends Item{

	public static final String names[] = new String[]{
		"brightness",
		"destruction",
		"efficiency",
		"hunger",
		"instability",
		"purity",
		"sinister",
		"speed",
		"stability",
		"taint"
	};
	
	public ItemNodeFoci()
	{
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
	}
	
    public int getMetadata(int meta)
    {
    	return meta;
    }
    
    public String getUnlocalizedName(ItemStack is)
    {
    	return super.getUnlocalizedName(is)+names[is.getItemDamage()].replace('/', '.');
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item itm, CreativeTabs tab, List lst)
    {
    	for(int i = 0; i < names.length; ++i)
    		lst.add(new ItemStack(itm,1,i));
    }
}
