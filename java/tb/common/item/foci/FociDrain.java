package tb.common.item.foci;

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
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import tb.init.TBFociUpgrades;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemCrystalEssence;
import thaumcraft.common.items.wands.WandManager;

public class FociDrain extends ItemFocusBasic
{

	Object beam = null;
	
	public int getFocusColor(ItemStack focusstack) {
		return 0x191770;
	}
	
	public String getSortingHelper(ItemStack focusstack) {		
		String out="DR";
		for (short id:this.getAppliedUpgrades(focusstack)) {
			out = out + id;
		}
		return out;
	}
	
	public boolean isVisCostPerTick(ItemStack focusstack) {
		return true;
	}
	
	public AspectList getVisCost(ItemStack focusstack) {
		return new AspectList().add(Aspect.WATER, 5);
		
	}
	
	public WandFocusAnimation getAnimation(ItemStack focusstack) {
		return WandFocusAnimation.CHARGE;
	}
	
	public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
		
		switch(rank)
		{
			case 3:
			{
				return new FocusUpgradeType[]{FocusUpgradeType.frugal,TBFociUpgrades.aquatic,TBFociUpgrades.netheric};
			}
			default:
			{
				return new FocusUpgradeType[]{FocusUpgradeType.frugal};
			}
		}
	}
	
	public ItemStack onFocusRightClick(ItemStack wandstack, World world,EntityPlayer player, MovingObjectPosition movingobjectposition) {
		player.setItemInUse(wandstack, 2147483647);
		return wandstack;
	}
	
	
	public void onUsingFocusTick(ItemStack wandstack, EntityPlayer player,int count) {
		
		ItemStack foci = ItemStack.loadItemStackFromNBT(MiscUtils.getStackTag(wandstack).getCompoundTag("focus"));
		
		if(!WandManager.consumeVisFromInventory(player, this.getVisCost(foci)))
		{
			player.stopUsingItem();
			return;
		}
		
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double)f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double)f + (double)(player.worldObj.isRemote ? player.getEyeHeight() - player.getDefaultEyeHeight() : player.getEyeHeight());
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        if (player instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP)player).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        MovingObjectPosition pos =  player.worldObj.func_147447_a(vec3, vec31, true, false, true);
        
        if(pos != null && pos.typeOfHit == MovingObjectType.BLOCK)
        {
        	Block b = player.worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ);
        	int meta = player.worldObj.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ);
        	if(b != null)
        	{
        		if(b == Blocks.water)
        		{
        			if(player.worldObj.isRemote)
        				beam = Thaumcraft.proxy.beamCont(player.worldObj, player, pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, 1, 0x5555ff, true, 2, beam, 2);
        			
        			if(this.getUpgradeLevel(foci, TBFociUpgrades.aquatic) > 0 || player.ticksExisted % 5 == 0)
        			{
        				b.onBlockDestroyedByPlayer(player.worldObj, pos.blockX, pos.blockY, pos.blockZ, player.worldObj.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ));
        				player.worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ,Blocks.air,0,2);
        				player.worldObj.playSound(pos.blockX+0.5D, pos.blockY+0.5D, pos.blockZ+0.5D, b.stepSound.getBreakSound(), 1, 1, false);
        				for(int i = 0; i < 100; ++i)
        					player.worldObj.spawnParticle("blockcrack_"+Block.getIdFromBlock(b)+"_"+meta, pos.blockX+player.worldObj.rand.nextDouble(), pos.blockY+player.worldObj.rand.nextDouble(), pos.blockZ+player.worldObj.rand.nextDouble(), 0, 0, 0);
            			
        				if(this.getUpgradeLevel(foci, TBFociUpgrades.aquatic) > 0 && player.worldObj.rand.nextDouble() < 0.4D)
            			{
            				ItemStack waterCrystal = new ItemStack(ConfigItems.itemCrystalEssence,1,0);
            				ItemCrystalEssence cEssence = (ItemCrystalEssence) waterCrystal.getItem();
            				cEssence.setAspects(waterCrystal, new AspectList().add((player.worldObj.rand.nextDouble() < 0.1D ? Aspect.POISON : Aspect.WATER),1));
            				EntityItem crystal = new EntityItem(player.worldObj, pos.blockX+0.5D, pos.blockY+0.5D, pos.blockZ+0.5D, waterCrystal);
            				if(!player.worldObj.isRemote)
            					player.worldObj.spawnEntityInWorld(crystal);
            			}
        			}
        		}
        		
           		if(b == Blocks.lava && this.getUpgradeLevel(foci, TBFociUpgrades.netheric) > 0)
        		{
        			if(player.worldObj.isRemote)
        				beam = Thaumcraft.proxy.beamCont(player.worldObj, player, pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, 1, 0xff5555, true, 2, beam, 2);
           			
        			if(player.ticksExisted % 5 == 0 && WandManager.consumeVisFromInventory(player, new AspectList().add(Aspect.FIRE, 3)))
        			{
        				b.onBlockDestroyedByPlayer(player.worldObj, pos.blockX, pos.blockY, pos.blockZ, player.worldObj.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ));
        				player.worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ,Blocks.air,0,2);
        				player.worldObj.playSound(pos.blockX+0.5D, pos.blockY+0.5D, pos.blockZ+0.5D, b.stepSound.getBreakSound(), 1, 1, false);
        				for(int i = 0; i < 100; ++i)
        					player.worldObj.spawnParticle("blockcrack_"+Block.getIdFromBlock(b)+"_"+meta, pos.blockX+player.worldObj.rand.nextDouble(), pos.blockY+player.worldObj.rand.nextDouble(), pos.blockZ+player.worldObj.rand.nextDouble(), 0, 0, 0);
        			}
        		}
        	}
        }
		
	}
	
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer p, int count)
	{
		beam = null;
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
    	super.registerIcons(reg);
    	this.icon = reg.registerIcon(getIconString());
    }

	
}
