package tb.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tb.utils.TBUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemKnoseFragment extends Item
{
	
	public static final String names[] = new String[]{
		"air",
		"fire",
		"aqua",
		"terra",
		"order",
		"entropy",
		"mixed",
		"tainted"
	};
	
	public static final AspectList[] addedAspects = new AspectList[]{
		lst(8,Aspect.AIR),
		lst(8,Aspect.FIRE),
		lst(8,Aspect.WATER),
		lst(8,Aspect.EARTH),
		lst(8,Aspect.ORDER),
		lst(8,Aspect.ENTROPY),
		lst(2,Aspect.FIRE,Aspect.WATER,Aspect.EARTH,Aspect.AIR,Aspect.ORDER,Aspect.ENTROPY)
	};
	
	public static IIcon[] icons = new IIcon[names.length];

	public ItemKnoseFragment()
	{
		this.setHasSubtypes(true);
	}
	
	public static final AspectList lst(int num,Aspect... aspect)
	{
		AspectList aLst = new AspectList();
		
		for(int i = 0; i < aspect.length; ++i)
			aLst.add(aspect[i], num);
		
		return aLst;
	}
	
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
    		icons[i] = reg.registerIcon(iconString+names[i]+"Fragment");
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item itm, CreativeTabs tab, List lst)
    {
    	for(int i = 0; i < names.length; ++i)
    		lst.add(new ItemStack(itm,1,i));
    }
    
    public ItemStack onItemRightClick(ItemStack stk, World w, EntityPlayer player)
    {
    	int meta = stk.getItemDamage();
    	if(!player.worldObj.isRemote)
	    	if(meta < 7)
	    	{
	    		for(int i = 0; i < addedAspects[meta].size(); ++i)
	    			TBUtils.addAspectToKnowledgePool(player, addedAspects[meta].getAspects()[i], (short) addedAspects[meta].getAmount(addedAspects[meta].getAspects()[i]));
	    	}else
	    	{
	    		//if(!ResearchManager.isResearchComplete(player.getCommandSenderName(), "TB.TaintMinor"))
	    		//	PacketHandler.INSTANCE.sendTo(new PacketResearchComplete("@TB.TaintMinor"), (EntityPlayerMP)player);
	    		int overhaulAddedAspects = 0;
	    		for(int i = 0; i < Aspect.getCompoundAspects().size(); ++i)
	    		{
	    			Aspect a = Aspect.getCompoundAspects().get(i);
	    			if(a == Aspect.TAINT)
	    			{
	    				TBUtils.addAspectToKnowledgePool(player, a, (short) 8);
	    				TBUtils.addWarpToPlayer(player, 2, 0);
	    				++overhaulAddedAspects;
	    			}else
	    			{
	    				if(player.worldObj.rand.nextBoolean())
	    				{
	    					TBUtils.addAspectToKnowledgePool(player, a, (short) 1);
	    					++overhaulAddedAspects;
	    				}
	    				if(player.worldObj.rand.nextInt(24) == 0)
	    					break;
	    			}
	    		}
	    		
	    		for(int i = 0; i < overhaulAddedAspects; ++i)
	    			if(w.rand.nextFloat() <= 0.1F)
	    				TBUtils.addWarpToPlayer(player, 1, w.rand.nextFloat() <= 0.15F ? 0 : 1);
	    	}
    	player.inventory.decrStackSize(player.inventory.currentItem, 1);
    	
    	return stk.stackSize <= 0 ? null : stk;
    }
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List lst, boolean held) 
    {
    	lst.add(EnumChatFormatting.AQUA+StatCollector.translateToLocal("tb.txt.knoseFragment"));
    }
}
