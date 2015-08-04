package tb.common.item.foci;

import java.util.ArrayList;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import tb.init.TBFociUpgrades;
import tb.init.TBThaumonomicon;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.codechicken.lib.math.MathHelper;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemCrystalEssence;
import thaumcraft.common.items.wands.WandManager;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

public class FociExperience extends ItemFocusBasic
{

	public int getFocusColor(ItemStack focusstack) {
		return 0xf8ffdc;
	}
	
	public String getSortingHelper(ItemStack focusstack) {		
		String out="XP";
		for (short id:this.getAppliedUpgrades(focusstack)) {
			out = out + id;
		}
		return out;
	}
	
	public boolean isVisCostPerTick(ItemStack focusstack) {
		return true;
	}
	
	public AspectList getVisCost(ItemStack focusstack) {
		return new AspectList().add(Aspect.ORDER, 500).add(Aspect.ENTROPY, 500);
		
	}
	
	public WandFocusAnimation getAnimation(ItemStack focusstack) {
		return WandFocusAnimation.CHARGE;
	}
	
	public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
		
		switch(rank)
		{
			case 3:
			{
				return new FocusUpgradeType[]{FocusUpgradeType.potency,FocusUpgradeType.frugal,TBFociUpgrades.vaporizing,TBFociUpgrades.decomposing};
			}
			case 5:
			{
				if(this.getUpgradeLevel(focusstack, TBFociUpgrades.decomposing) > 0)
					return new FocusUpgradeType[]{FocusUpgradeType.potency,FocusUpgradeType.frugal,TBFociUpgrades.decomposing};
				else if(this.getUpgradeLevel(focusstack, TBFociUpgrades.vaporizing) > 0)
					return new FocusUpgradeType[]{FocusUpgradeType.potency,FocusUpgradeType.frugal,TBFociUpgrades.vaporizing};
				else
					return new FocusUpgradeType[]{FocusUpgradeType.potency,FocusUpgradeType.frugal,TBFociUpgrades.vaporizing,TBFociUpgrades.decomposing};
			}
			default:
			{
				return new FocusUpgradeType[]{FocusUpgradeType.frugal,FocusUpgradeType.potency};
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public ItemStack onFocusRightClick(ItemStack wandstack, World world,EntityPlayer player, MovingObjectPosition movingobjectposition) {
		
		ItemStack foci = ItemStack.loadItemStackFromNBT(MiscUtils.getStackTag(wandstack).getCompoundTag("focus"));
		
		int vaporisingLevel = this.getUpgradeLevel(foci, TBFociUpgrades.vaporizing);
		int decomposingLevel = this.getUpgradeLevel(foci, TBFociUpgrades.decomposing);
		
		if(decomposingLevel > 0)
		{

			Vec3 vec = player.getLookVec();
			ArrayList<Entity> mobs = (ArrayList<Entity>) world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(player.posX+vec.xCoord-0.3D, player.posY+player.getEyeHeight()+vec.yCoord-0.3D, player.posZ+vec.zCoord-0.3D, player.posX+vec.xCoord+0.3D, player.posY+player.getEyeHeight()+vec.yCoord+0.3D, player.posZ+vec.zCoord+0.3D));
					
			if(mobs != null && !mobs.isEmpty())
			{
				Entity e = mobs.get(world.rand.nextInt(mobs.size()));
				if(e instanceof EntityLivingBase)
				{
					EntityLivingBase eaten = (EntityLivingBase) e;
					if(e instanceof EntityPlayer)
						return wandstack;
					
					if(e instanceof IBossDisplayData)
						return wandstack;
					
					double hp = eaten.getHealth();
					
					if(WandManager.consumeVisFromInventory(player, TBThaumonomicon.primals(MathHelper.floor_double(hp*100/decomposingLevel))))
					{
						eaten.attackEntityFrom(DamageSource.outOfWorld, Integer.MAX_VALUE);
						int xp = MathHelper.floor_double(hp*3);
						
						EntityXPOrb orb = new EntityXPOrb(world, eaten.posX, eaten.posY, eaten.posZ, xp);
						if(!world.isRemote)
							world.spawnEntityInWorld(orb);
						
						return wandstack;
					}
				}
			}
		}
		
		if(movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectType.BLOCK)
		{
			Block b = world.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
			int meta = world.getBlockMetadata(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
			int xp = b.getExpDrop(world, meta, this.getUpgradeLevel(foci, FocusUpgradeType.potency));
			if(xp <= 0)
				xp = this.getUpgradeLevel(foci, FocusUpgradeType.potency)+1;
			
			MovingObjectPosition pos = movingobjectposition;
			
			if(b.getBlockHardness(world, pos.blockX, pos.blockY, pos.blockZ) <= 0)
				return wandstack;
			
			if(!WandManager.consumeVisFromInventory(player, TBThaumonomicon.primals(MathHelper.floor_double(b.getBlockHardness(world, pos.blockX, pos.blockY, pos.blockZ)*100))))
				return wandstack;
			
			if(vaporisingLevel > 0)
			{
				boolean doWork = vaporisingLevel == 1 ? world.rand.nextBoolean() : true;
				if(doWork)
				{
					if(ThaumcraftApi.exists(Item.getItemFromBlock(b), meta))
					{
						AspectList aspects = ThaumcraftCraftingManager.getObjectTags(new ItemStack(Item.getItemFromBlock(b),1, meta));
						if(aspects != null && aspects.size() > 0)
						{
							for(int i = 0; i < aspects.getAspects().length; ++i)
							{
								Aspect a = aspects.getAspects()[i];
								int amount = aspects.getAmount(a);
								
								ItemStack crystalStack = new ItemStack(ConfigItems.itemCrystalEssence,1,0);
	            				ItemCrystalEssence cEssence = (ItemCrystalEssence) crystalStack.getItem();
	            				cEssence.setAspects(crystalStack, new AspectList().add(a,amount));
	            				
	            				EntityItem crystal = new EntityItem(player.worldObj, pos.blockX+0.5D, pos.blockY+0.5D, pos.blockZ+0.5D, crystalStack);
	            				if(!player.worldObj.isRemote)
	            					player.worldObj.spawnEntityInWorld(crystal);
							}
						}
					}
				}
			}
			
			b.onBlockDestroyedByPlayer(player.worldObj, pos.blockX, pos.blockY, pos.blockZ, player.worldObj.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ));
			player.worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ,Blocks.air,0,3);
			player.worldObj.playSound(pos.blockX+0.5D, pos.blockY+0.5D, pos.blockZ+0.5D, b.stepSound.getBreakSound(), 1, 1, false);
			for(int i = 0; i < 100; ++i)
				player.worldObj.spawnParticle("blockcrack_"+Block.getIdFromBlock(b)+"_"+meta, pos.blockX+player.worldObj.rand.nextDouble(), pos.blockY+player.worldObj.rand.nextDouble(), pos.blockZ+player.worldObj.rand.nextDouble(), 0, 0, 0);
			
			xp *= 2;
			
			EntityXPOrb orb = new EntityXPOrb(world, pos.blockX+0.5D, pos.blockY+0.5D, pos.blockZ+0.5D, xp);
			if(!world.isRemote)
				world.spawnEntityInWorld(orb);
			
			player.swingItem();
		}
		
		return wandstack;
	}
	
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
    	super.registerIcons(reg);
    	this.icon = reg.registerIcon(getIconString());
    }

	
}
