package tb.common.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tb.api.ITobacco;
import tb.core.TBCore;
import tb.utils.TBUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.config.Config;
import thaumcraft.common.entities.monster.EntityWisp;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;

public class TBTobacco extends Item implements ITobacco{
	
	public TBTobacco()
	{
		this.setHasSubtypes(true);
	}
	
	public static final String names[] = new String[]{
		"tobacco_pile",
		"tobacco_eldritch",
		"tobacco_fighting",
		"tobacco_hunger",
		"tobacco_knowledge",
		"tobacco_mining",
		"tobacco_sanity",
		"tobacco_tainted",
		"tobacco_wispy"
	};
	
	
	public void performTobaccoEffect(EntityPlayer smoker, int metadata, boolean isSilverwood)
	{
		switch(metadata)
		{
			case 0:
			{
				if(isSilverwood && smoker.worldObj.rand.nextFloat() <= 0.3F)
					TBUtils.addWarpToPlayer(smoker, -1, 0);
				
				if(!isSilverwood && smoker.worldObj.rand.nextFloat() <= 0.1F)
					TBUtils.addWarpToPlayer(smoker, -1, 0);
				
				break;
			}
			case 1:
			{
				if(!smoker.worldObj.isRemote)
				{
					smoker.addPotionEffect(new PotionEffect(Config.potionDeathGazeID,2000,0,true));
					if(!isSilverwood)
						TBUtils.addWarpToPlayer(smoker, 3, 0);
				}
				
				break;
			}
			case 2:
			{
				if(!smoker.worldObj.isRemote)
				{
					smoker.addPotionEffect(new PotionEffect(Potion.damageBoost.id,8000,1,true));
					if(isSilverwood && smoker.worldObj.rand.nextFloat() <= 0.1F)
						smoker.addPotionEffect(new PotionEffect(Potion.resistance.id,8000,0,true));
				}
				break;
			}
			case 3:
			{
				if(!smoker.worldObj.isRemote)
				{
					smoker.getFoodStats().addStats(3, 3);
					if(!isSilverwood && smoker.worldObj.rand.nextFloat() <= 0.4F)
						smoker.addPotionEffect(new PotionEffect(Potion.confusion.id,200,0,true));
				}
				break;
			}
			case 4:
			{
				if(!smoker.worldObj.isRemote)
				{
					ArrayList<Aspect> aspects = new ArrayList<Aspect>();
					Collection<Aspect> pa = Aspect.aspects.values();
					for (Aspect aspect:pa) 
					{
						aspects.add(aspect);
					}
					
					for(int i = 0; i < (isSilverwood ? 20 : 10); ++i)
					{
						Aspect a = aspects.get(smoker.worldObj.rand.nextInt(aspects.size()));
						TBUtils.addAspectToKnowledgePool(smoker, a, (short) (isSilverwood ? 2 : 1));
						if(a == Aspect.TAINT && !isSilverwood)
							TBUtils.addWarpToPlayer(smoker, 1, 0);
					}
				}
				break;
			}
			case 5:
			{
				if(!smoker.worldObj.isRemote)
				{
					smoker.addPotionEffect(new PotionEffect(Potion.digSpeed.id,8000,1,true));
					if(isSilverwood && smoker.worldObj.rand.nextFloat() <= 0.3F)
						smoker.addPotionEffect(new PotionEffect(Potion.nightVision.id,8000,0,true));
				}
				break;
			}
			case 6:
			{
				if(!smoker.worldObj.isRemote)
				{
					if(smoker.worldObj.rand.nextFloat() <= (isSilverwood ? 0.6F : 0.4F))
					{
						TBUtils.addWarpToPlayer(smoker, -1, 0);
					}
					if(isSilverwood && smoker.worldObj.rand.nextFloat() <= 0.1F)
						TBUtils.addWarpToPlayer(smoker, -1, 1);
				}
				break;
			}
			case 7:
			{
				if(!smoker.worldObj.isRemote)
				{
					if(!isSilverwood)
					{
						TBUtils.addWarpToPlayer(smoker, 1+smoker.worldObj.rand.nextInt(3), 0);
						if(smoker.worldObj.rand.nextFloat() <= 0.4F)
							TBUtils.addWarpToPlayer(smoker, 1, 1);
					}else
					{
						ItemStack stk = smoker.getCurrentEquippedItem();
						if(stk != null)
						{
							smoker.renderBrokenItemStack(stk);
							smoker.inventory.setInventorySlotContents(smoker.inventory.currentItem, null);
						}
						return;
					}
				}
				break;
			}
			case 8:
			{
				ArrayList<Aspect> aspects = new ArrayList<Aspect>();
				Collection<Aspect> pa = Aspect.aspects.values();
				for (Aspect aspect:pa) 
				{
					aspects.add(aspect);
				}
				
				if(isSilverwood)
					aspects.remove(Aspect.TAINT);
				
				EntityWisp wisp = new EntityWisp(smoker.worldObj);
				wisp.setPositionAndRotation(smoker.posX, smoker.posY, smoker.posZ, 0, 0);
				if(!smoker.worldObj.isRemote)
				{
					wisp.setType(aspects.get(smoker.worldObj.rand.nextInt(aspects.size())).getTag());
					smoker.worldObj.spawnEntityInWorld(wisp);
				}
				break;
			}
		}
	}
	
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
    	return super.getUnlocalizedName(is)+"."+names[is.getItemDamage()].replace('/', '.');
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
