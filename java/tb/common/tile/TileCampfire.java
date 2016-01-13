package tb.common.tile;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import tb.common.entity.EntityAIAvoidCampfire;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.potions.PotionFluxTaint;
import thaumcraft.common.config.Config;

public class TileCampfire extends TileEntity implements ITickable
{
	public int burnTime;
	public int logTime;
	public int uptime;
	public boolean tainted;
	public int syncTimer;
	
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        burnTime = tag.getInteger("burnTime");
        logTime = tag.getInteger("logTime");
        tainted = tag.getBoolean("tainted");
    }
    
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setInteger("burnTime", burnTime);
        tag.setInteger("logTime", logTime);
        tag.setBoolean("tainted", tainted);
    }
    
    public boolean addLog(ItemStack log)
    {
    	if(log == null)
    		return false;
    	
    	if(logTime > 0)
    		return false;
    	
    	if(log.getItem() == Item.getItemFromBlock(BlocksTC.log))
    	{
    		if(log.getItemDamage()%3==0)
    		{
    			logTime = 1600*4;
    			this.worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).getBlock().getStateFromMeta(1));
    			syncTimer = 1;
    			return true;
    		}
    	}
    	
    	if(log.getItem() == Item.getItemFromBlock(BlocksTC.taintLog))
    	{
    		logTime = 1600*8;
    		tainted = true;
    		this.worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).getBlock().getStateFromMeta(1));
    		syncTimer = 1;
    		return true;
    	}
    	
    	if(Block.getBlockFromItem(log.getItem()) instanceof BlockLog)
    	{
			logTime = 1600;
			this.worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).getBlock().getStateFromMeta(1));
			syncTimer = 1;
			return true;
    	}
    	
    	if(OreDictionary.getOreIDs(log).length > 0)
    	{
    		int[] ints = OreDictionary.getOreIDs(log);
    		boolean isLog = false;
    		for(int i = 0; i < ints.length; ++i)
    		{
    			String str = OreDictionary.getOreName(ints[i]);
    			if(str.equals("logWood"))
    			{
    				isLog = true;
    				break;
    			}
    		}
    		if(isLog)
    		{
    			logTime = 1600;
    			this.worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).getBlock().getStateFromMeta(1));
    			syncTimer = 1;
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public boolean addFuel(ItemStack item)
    {
    	if(item == null)
    		return false;
    	
    	if(logTime <= 0)
    		return false;
    	
    	if(burnTime > 0)
    		return false;
    	
    	int burnTime = TileEntityFurnace.getItemBurnTime(item);
    	
    	if(burnTime <= 0)
    		return false;
    	
    	this.burnTime = burnTime;
    	this.worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).getBlock().getStateFromMeta(2));
    	
    	syncTimer = 1;
    	
    	return true;
    }
    
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
    	boolean b = pkt.getNbtCompound().getBoolean("2");
    	if(tainted != b)
    	{
    		this.worldObj.markBlockRangeForRenderUpdate(getPos().down().west().north(), getPos().up().east().south());
    		tainted = b;
    	}
    }
    
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
    	return oldState.getBlock() == newSate.getBlock() ? false : super.shouldRefresh(world, pos, oldState, newSate);
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void update() 
	{
		if(syncTimer <= 0)
		{
			syncTimer = 100;
			NBTTagCompound tg = new NBTTagCompound();
			tg.setBoolean("2", tainted);
			tg.setInteger("x", this.pos.getX());
			tg.setInteger("y", this.pos.getY());
			tg.setInteger("z", this.pos.getZ());
			MiscUtils.syncTileEntity(tg, 0);
		}else
			--syncTimer;
		
		++uptime;
		
		if(burnTime > 0)
		{
			--burnTime;
			
			if(logTime > 0)
				--logTime;
		}
		
		if(!this.worldObj.isRemote)
		{
			if(burnTime <= 0)
			{
				int metadata = BlockStateMetadata.getBlockMetadata(worldObj, getPos());
				if(metadata == 2)
					this.worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).getBlock().getStateFromMeta(1));
				
				return;
			}
			if(logTime <= 0)
			{
				int metadata = BlockStateMetadata.getBlockMetadata(worldObj, getPos());
				if(metadata > 0)
					this.worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).getBlock().getStateFromMeta(0));
				tainted = false;
				return;
			}
		}

		
		if(!this.worldObj.isRemote)
		{
			List<EntityLivingBase> creatures = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.getX()+1, pos.getY()+1, pos.getZ()+1).expand(12, 6, 12));
			
			for(EntityLivingBase elb : creatures)
			{
				if(this.tainted && this.worldObj.rand.nextDouble() < 0.003D)
					elb.addPotionEffect(new PotionEffect(PotionFluxTaint.instance.getId(),200,0,true,false));
				
				if(!(elb instanceof EntityCreature))
					continue;
				
				EntityCreature c = EntityCreature.class.cast(elb);
				
				if(!(c instanceof IMob))
					continue;

			      ArrayList entries = (ArrayList) c.tasks.taskEntries;
			      
			      EntityAIAvoidCampfire campfireAviodable = null;
			      
			      boolean hasTask = false;
			      for (int i = 0; i < entries.size(); ++i)
			      {
			    	  EntityAITasks.EntityAITaskEntry task = (EntityAITaskEntry) entries.get(i);
			    	  if ((task.action instanceof EntityAIAvoidCampfire)) 
			    	  {
			    		  campfireAviodable = (EntityAIAvoidCampfire) task.action;
			    		  hasTask = true;
			    		  break;
			    	  }
			      }
			      if(campfireAviodable == null)
			    	  campfireAviodable = new EntityAIAvoidCampfire(c);
			      
			      campfireAviodable.isScared = true;
			      campfireAviodable.scareTimer = 100;
			      campfireAviodable.campfireX = pos.getX();
			      campfireAviodable.campfireY = pos.getY();
			      campfireAviodable.campfireZ = pos.getZ();
			      
			      if(!hasTask)
			    	  c.tasks.addTask(1, campfireAviodable);
			}
		}
	}
}
