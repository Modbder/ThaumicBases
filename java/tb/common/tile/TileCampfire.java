package tb.common.tile;

import java.util.ArrayList;
import java.util.List;

import tb.common.entity.EntityAIAvoidCampfire;
import thaumcraft.common.config.ConfigBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.oredict.OreDictionary;

public class TileCampfire extends TileEntity
{
	public int burnTime;
	public int logTime;
	public int uptime;
	
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        burnTime = tag.getInteger("burnTime");
        logTime = tag.getInteger("logTime");
    }
    
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setInteger("burnTime", burnTime);
        tag.setInteger("logTime", logTime);
    }
    
    public boolean addLog(ItemStack log)
    {
    	if(log == null)
    		return false;
    	
    	if(logTime > 0)
    		return false;
    	
    	if(log.getItem() == Item.getItemFromBlock(ConfigBlocks.blockMagicalLog))
    	{
    		if(log.getItemDamage()%3==0)
    		{
    			logTime = 1600*4;
    			this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
    			return true;
    		}
    	}
    	
    	if(Block.getBlockFromItem(log.getItem()) instanceof BlockLog)
    	{
			logTime = 1600;
			this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
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
    			this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
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
		this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 3);
    	
    	return true;
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateEntity() 
	{
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
				int metadata = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
				if(metadata == 2)
					this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
				
				return;
			}
			if(logTime <= 0)
			{
				int metadata = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
				if(metadata > 0)
					this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
				
				return;
			}
		}

		
		if(!this.worldObj.isRemote)
		{
			List<EntityCreature> creatures = this.worldObj.getEntitiesWithinAABB(EntityCreature.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1).expand(12, 6, 12));
			
			for(EntityCreature c : creatures)
			{
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
			      campfireAviodable.campfireX = xCoord;
			      campfireAviodable.campfireY = yCoord;
			      campfireAviodable.campfireZ = zCoord;
			      
			      if(!hasTask)
			    	  c.tasks.addTask(1, campfireAviodable);
			}
		}
	}
}
