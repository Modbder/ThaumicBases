package tb.common.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Utils.IOldItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tb.api.ITobacco;
import tb.core.TBCore;
import tb.utils.TBUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.config.Config;
import thaumcraft.common.entities.monster.EntityWisp;

public class TBTobacco extends Item implements ITobacco, IOldItem{
	
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
					smoker.addPotionEffect(new PotionEffect(Config.potionDeathGazeID,2000,0,true,false));
					if(!isSilverwood)
						TBUtils.addWarpToPlayer(smoker, 3, 0);
				}
				
				break;
			}
			case 2:
			{
				if(!smoker.worldObj.isRemote)
				{
					smoker.addPotionEffect(new PotionEffect(Potion.damageBoost.id,8000,1,true,false));
					if(isSilverwood && smoker.worldObj.rand.nextFloat() <= 0.1F)
						smoker.addPotionEffect(new PotionEffect(Potion.resistance.id,8000,0,true,false));
				}
				break;
			}
			case 3:
			{
				if(!smoker.worldObj.isRemote)
				{
					smoker.getFoodStats().addStats(3, 3);
					if(!isSilverwood && smoker.worldObj.rand.nextFloat() <= 0.4F)
						smoker.addPotionEffect(new PotionEffect(Potion.confusion.id,200,0,true,false));
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
						EntityXPOrb xp = new EntityXPOrb(smoker.worldObj,smoker.posX,smoker.posY,smoker.posZ,a.isPrimal() ? 4 : a == Aspect.FLUX ? 8 : 2);
						smoker.worldObj.spawnEntityInWorld(xp);
						if(a == Aspect.FLUX && !isSilverwood)
							TBUtils.addWarpToPlayer(smoker, 1, 0);
					}
				}
				break;
			}
			case 5:
			{
				if(!smoker.worldObj.isRemote)
				{
					smoker.addPotionEffect(new PotionEffect(Potion.digSpeed.id,8000,1,true,false));
					if(isSilverwood && smoker.worldObj.rand.nextFloat() <= 0.3F)
						smoker.addPotionEffect(new PotionEffect(Potion.nightVision.id,8000,0,true,false));
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
					aspects.remove(Aspect.FLUX);
				
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
	
	public static Icon[] icons = new Icon[names.length];
	
    public Icon getIconFromDamage(int meta)
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
    public void registerIcons(IconRegister reg)
    {
    	for(int i = 0; i < names.length; ++i)
    		icons[i] = reg.registerItemIcon(TBCore.modid+":"+names[i]);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item itm, CreativeTabs tab, List lst)
    {
    	for(int i = 0; i < names.length; ++i)
    		lst.add(new ItemStack(itm,1,i));
    }

	@Override
	public Icon getIconFromItemStack(ItemStack stk) {
		return getIconFromDamage(stk.getMetadata());
	}

	@Override
	public int getRenderPasses(ItemStack stk) {
		return 0;
	}

	@Override
	public Icon getIconFromItemStackAndRenderPass(ItemStack stk, int pass) {
		return getIconFromItemStack(stk);
	}

	@Override
	public boolean recreateIcon(ItemStack stk) {
		return false;
	}

	@Override
	public boolean render3D(ItemStack stk) {
		return false;
	}

}
