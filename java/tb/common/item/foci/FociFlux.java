package tb.common.item.foci;

import java.util.ArrayList;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import tb.init.TBFociUpgrades;
import tb.utils.TBUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemCrystalEssence;
import thaumcraft.common.items.wands.WandManager;

public class FociFlux extends ItemFocusBasic
{
	
	Object beam = null;

	public int getFocusColor(ItemStack focusstack) {
		return 0x64379d;
	}
	
	public String getSortingHelper(ItemStack focusstack) {		
		String out="FX";
		for (short id:this.getAppliedUpgrades(focusstack)) {
			out = out + id;
		}
		return out;
	}
	
	public boolean isVisCostPerTick(ItemStack focusstack) {
		return true;
	}
	
	public AspectList getVisCost(ItemStack focusstack) {
		return new AspectList().add(Aspect.ORDER, 5);
		
	}
	
	public WandFocusAnimation getAnimation(ItemStack focusstack) {
		return WandFocusAnimation.CHARGE;
	}
	
	public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
		
		switch(rank)
		{
		case 3:
		{
			return new FocusUpgradeType[]{FocusUpgradeType.potency,FocusUpgradeType.frugal,TBFociUpgrades.warping,TBFociUpgrades.crystalization};
		}
		case 5:
		{
			if(this.getUpgradeLevel(focusstack, TBFociUpgrades.warping) > 0)
				return new FocusUpgradeType[]{FocusUpgradeType.potency,FocusUpgradeType.frugal,TBFociUpgrades.calming};
			else if(this.getUpgradeLevel(focusstack, TBFociUpgrades.crystalization) > 0)
				return new FocusUpgradeType[]{FocusUpgradeType.potency,FocusUpgradeType.frugal,TBFociUpgrades.crystalization};
			else
				return new FocusUpgradeType[]{FocusUpgradeType.potency,FocusUpgradeType.frugal,TBFociUpgrades.warping,TBFociUpgrades.crystalization};
		}
		default:
		{
			return new FocusUpgradeType[]{FocusUpgradeType.frugal,FocusUpgradeType.potency};
		}
		}
	}
	
	public ItemStack onFocusRightClick(ItemStack wandstack, World world,EntityPlayer player, MovingObjectPosition movingobjectposition) {
		player.setItemInUse(wandstack, 2147483647);
		return wandstack;
	}
	
	
	public void onUsingFocusTick(ItemStack wandstack, EntityPlayer player,int count) {
		
		ItemStack foci = ItemStack.loadItemStackFromNBT(MiscUtils.getStackTag(wandstack).getCompoundTag("focus"));
		
		int potencyLevel = this.getUpgradeLevel(foci, FocusUpgradeType.potency);
		
		if(!WandManager.consumeVisFromInventory(player, this.getVisCost(foci)))
		{
			player.stopUsingItem();
			return;
		}
		
        float f = 1.0F;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double)f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double)f + (double)(player.worldObj.isRemote ? player.getEyeHeight() - player.getDefaultEyeHeight() : player.getEyeHeight());
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        
        double d3 = 5.0D;
        if (player instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP)player).theItemInWorldManager.getBlockReachDistance();
        }
        
        Vec3 lookVec = player.getLookVec();
        
        if(player.worldObj.isRemote)
			beam = Thaumcraft.proxy.beamCont(player.worldObj, player, player.posX+lookVec.xCoord*2, player.posY+player.getEyeHeight()+lookVec.yCoord*2, player.posZ+lookVec.zCoord*2, 6, 0xff00ff, true, 2, beam, 2);
        
        if(potencyLevel == 5 || player.ticksExisted % 10-potencyLevel*2 == 0)
	        for(int i = 0; i < d3; ++i)
	        {
	        	Vec3 addedVec = vec3.addVector(lookVec.xCoord*i, lookVec.yCoord*i, lookVec.zCoord*i);
	        	
	        	
	        	int x = MathHelper.floor_double(addedVec.xCoord);
	        	int y = MathHelper.floor_double(addedVec.yCoord);
	        	int z = MathHelper.floor_double(addedVec.zCoord);
	        	
	        	Block b = player.worldObj.getBlock(x, y, z);
	        	
	        	if(b != null && !b.isAir(player.worldObj, x, y, z))
	        	{
	        		if(b == ConfigBlocks.blockFluxGoo || b == ConfigBlocks.blockFluxGas)
	        		{
	        			b.onBlockDestroyedByPlayer(player.worldObj, x, y, z, player.worldObj.getBlockMetadata(x, y, z));
        				player.worldObj.setBlock(x, y, z,Blocks.air,0,2);
        				player.worldObj.playSound(x+0.5D, y+0.5D, z+0.5D, b.stepSound.getBreakSound(), 1, 1, false);
        				for(int j = 0; j < 100; ++j)
        					player.worldObj.spawnParticle("blockcrack_"+Block.getIdFromBlock(b)+"_"+player.worldObj.getBlockMetadata(x, y, z), x+player.worldObj.rand.nextDouble(), y+player.worldObj.rand.nextDouble(), z+player.worldObj.rand.nextDouble(), 0, 0, 0);
        				
        				if(!player.worldObj.isRemote)
        				{
	        				if(this.getUpgradeLevel(foci, TBFociUpgrades.calming) > 0)
	        				{
	        					TBUtils.addWarpToPlayer(player, -1, 0);
	        					if(player.worldObj.rand.nextDouble() <= 0.4D)
	        						TBUtils.addWarpToPlayer(player, -1, 1);
	        				}else
	        				{
	              				if(this.getUpgradeLevel(foci, TBFociUpgrades.warping) > 0)
	            				{
	            					TBUtils.addWarpToPlayer(player, 1, 0);
	            					if(player.worldObj.rand.nextDouble() <= 0.1D)
	            						TBUtils.addWarpToPlayer(player, 1, 1);
	            				}
	        				}
        				}
        				
        				int crystalizingLevel = this.getUpgradeLevel(foci, TBFociUpgrades.crystalization);
        				
        				if(crystalizingLevel == 1)
        				{
        					ItemStack potentiaCrystal = new ItemStack(ConfigItems.itemCrystalEssence,1,0);
            				ItemCrystalEssence cEssence = (ItemCrystalEssence) potentiaCrystal.getItem();
            				cEssence.setAspects(potentiaCrystal, new AspectList().add(Aspect.ENERGY,1));
            				EntityItem crystal = new EntityItem(player.worldObj, x+0.5D, y+0.5D, z+0.5D, potentiaCrystal);
            				if(!player.worldObj.isRemote)
            					player.worldObj.spawnEntityInWorld(crystal);
        				}
        				
        				if(crystalizingLevel == 2)
        				{
        					ArrayList<Aspect> allAspects = new ArrayList<Aspect>();
        					
        					allAspects.addAll(Aspect.getPrimalAspects());
        					allAspects.addAll(Aspect.getCompoundAspects());
        					
        					ItemStack potentiaCrystal = new ItemStack(ConfigItems.itemCrystalEssence,1,0);
            				ItemCrystalEssence cEssence = (ItemCrystalEssence) potentiaCrystal.getItem();
            				cEssence.setAspects(potentiaCrystal, new AspectList().add(allAspects.get(player.worldObj.rand.nextInt(allAspects.size())),1));
            				EntityItem crystal = new EntityItem(player.worldObj, x+0.5D, y+0.5D, z+0.5D, potentiaCrystal);
            				if(!player.worldObj.isRemote)
            					player.worldObj.spawnEntityInWorld(crystal);
        				}
        				
	        			return;
	        		}
	        	}
	        }
		
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
    	super.registerIcons(reg);
    	this.icon = reg.registerIcon(getIconString());
    }

	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer p, int count)
	{
		beam = null;
	}
	
}
