package tb.common.entity;

import java.util.ArrayList;

import DummyCore.Utils.Pair;
import tb.api.RevolverUpgrade;
import tb.common.item.ItemRevolver;
import thaumcraft.common.Thaumcraft;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityRevolverBullet extends EntityThrowable
{
	public Entity shooter;
	public ItemStack revolver;
	ArrayList<Pair<RevolverUpgrade,Integer>> upgrades;
	boolean isPrimal;
	
	public EntityRevolverBullet(World w)
	{
		super(w);
	}
	
	public EntityRevolverBullet(World w, EntityLivingBase shooter)
	{
		super(w, shooter);
		revolver = shooter.getHeldItem();
		this.shooter = shooter;
		if(revolver != null && revolver.getItem() instanceof ItemRevolver)
		{
			upgrades = ItemRevolver.getAllUpgradesFor(revolver);
			boolean allowNoclip = false;
			float speedIndex = 1;
			for(Pair<RevolverUpgrade,Integer> p : upgrades)
			{
				if(!allowNoclip)
					allowNoclip = p.getFirst().bulletNoclip((EntityPlayer) shooter, revolver, p.getSecond());
				
				speedIndex = (float) p.getFirst().modifySpeed((EntityPlayer) shooter, revolver, speedIndex, p.getSecond());
				
				if(p.getFirst() == RevolverUpgrade.primal)
					isPrimal = true;
			}
			if(allowNoclip)
				noClip = true;
			
			this.motionX *=3;
			this.motionY *=3;
			this.motionZ *=3;
			
			this.motionX *= speedIndex;
			this.motionY *= speedIndex;
			this.motionZ *= speedIndex;
		}
	}
	
    public void onUpdate()
    {
    	if(this.worldObj.isRemote)
    		Thaumcraft.proxy.sparkle((float)posX, (float)posY, (float)posZ, 4);
    	
    	if(this.worldObj.isRemote)
    		Thaumcraft.proxy.sparkle((float)(posX-motionX/20), (float)(posY-motionY/20), (float)(posZ-motionZ/20), 4);
    	
    	if(this.ticksExisted >= 200) //<- loop exit for primal
    		this.setDead();
    	
    	super.onUpdate();
    	
    	if(isPrimal && !isDead)
    	{
    		++ticksExisted; 
    		onUpdate(); //<- loop to turn into a hit-scan
    	}
    }
	
    protected float getGravityVelocity()
    {
        return isPrimal ? 0 : 0.01F;
    }

	@Override
	protected void onImpact(MovingObjectPosition object)
	{
		if(this.isDead)
			return;
		
		if(!this.worldObj.isRemote && object.typeOfHit == MovingObjectType.BLOCK)
		{
			if(noClip)
				return;
			if(this.worldObj.isBlockNormalCubeDefault(object.blockX, object.blockY, object.blockZ, true))
				this.setDead();
			else
			{
				Block b = this.worldObj.getBlock(object.blockX, object.blockY, object.blockZ);
				int meta = this.worldObj.getBlockMetadata(object.blockX, object.blockY, object.blockZ);
				
				for(int i = 0; i < 100; ++i)
					this.worldObj.spawnParticle("blockcrack_"+Block.getIdFromBlock(b)+"_"+meta, object.blockX+worldObj.rand.nextDouble(), object.blockY+worldObj.rand.nextDouble(), object.blockZ+worldObj.rand.nextDouble(), 0, 0, 0);
				
				worldObj.playSound(object.blockX+0.5D, object.blockY+0.5D, object.blockZ+0.5D, b.stepSound.getBreakSound(), 1, 1, false);
				
				this.worldObj.setBlockToAir(object.blockX, object.blockY, object.blockZ);
			}
		}
		if(object.typeOfHit == MovingObjectType.ENTITY)
		{
			Entity e = object.entityHit;
			if(e instanceof EntityLivingBase && e != this.shooter && !(e instanceof EntityRevolverBullet))
			{
				EntityLivingBase elb = (EntityLivingBase) e;
				float initialDamage = 14;
				for(Pair<RevolverUpgrade,Integer> p : upgrades)
					initialDamage = p.getFirst().modifyDamage_start(elb, revolver, initialDamage, p.getSecond());
				
				for(Pair<RevolverUpgrade,Integer> p : upgrades)
					initialDamage = p.getFirst().modifyDamage_end(elb, revolver, initialDamage, p.getSecond());
				
				elb.attackEntityFrom(new RevolverDamage("revolver"), initialDamage);
				
				boolean destroy = true;
				
				for(Pair<RevolverUpgrade,Integer> p : upgrades)
				{
					if(destroy)
						destroy = p.getFirst().afterhit(elb, (EntityPlayer) shooter, revolver, initialDamage, p.getSecond());
					else
						p.getFirst().afterhit(elb, (EntityPlayer) shooter, revolver, initialDamage, p.getSecond());
				}
				
				if(destroy)
					this.setDead();
			}
		}
	}
	
	public static class RevolverDamage extends DamageSource
	{

		public RevolverDamage(String damage) {
			super(damage);
		}
		
	}

    public void writeEntityToNBT(NBTTagCompound tag)
    {
    	super.writeEntityToNBT(tag);
    	if(revolver != null)
    	{
	    	NBTTagCompound revolverTag = new NBTTagCompound();
	    	revolver.writeToNBT(revolverTag);
	    	tag.setTag("revolverTag", revolverTag);
    	}
    	tag.setBoolean("noclip", noClip);
    	tag.setBoolean("primal", isPrimal);
    }
    
    public void readEntityFromNBT(NBTTagCompound tag)
    {
    	super.readEntityFromNBT(tag);
    	
    	if(tag.hasKey("revolverTag"))
    	{
    		revolver = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("revolverTag"));
			upgrades = ItemRevolver.getAllUpgradesFor(revolver);
    	}
    	noClip = tag.getBoolean("noclip");
    	isPrimal = tag.getBoolean("primal");
    }
}
