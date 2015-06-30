package tb.common.item;

import java.util.List;

import tb.core.TBCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class TBResource extends Item{
	
	public TBResource()
	{
		this.setHasSubtypes(true);
	}
	
	public static final String names[] = new String[]{
		"nuggetthauminite",
		"thauminite/thauminite_ingot",
		"thauminite/wand_cap_thauminite",
		"thaumium_wand_core",
		"void_wand_core",
		"aurelia_petal",
		"briar_seedbag",
		"tobacco_leaves",
		"bloodycloth"
	};
	
	public static IIcon[] icons = new IIcon[names.length];
	
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
    	return icons[meta];
    }
    
    public int getMetadata(int meta)
    {
    	return meta;
    }
    
    public String getUnlocalizedName(ItemStack is)
    {
    	return super.getUnlocalizedName(is)+names[is.getItemDamage()].replace('/', '.');
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
    	for(int i = 0; i < names.length; ++i)
    		icons[i] = reg.registerIcon(TBCore.modid+":"+names[i]);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item itm, CreativeTabs tab, List lst)
    {
    	for(int i = 0; i < names.length; ++i)
    		lst.add(new ItemStack(itm,1,i));
    }

}
