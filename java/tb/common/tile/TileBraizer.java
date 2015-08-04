package tb.common.tile;

import java.util.ArrayList;
import java.util.List;

import tb.common.entity.EntityAIAvoidCampfire;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.lib.events.EssentiaHandler;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileBraizer extends TileEntity
{
	public int burnTime;
	public int uptime;
	
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        burnTime = tag.getInteger("burnTime");
    }
    
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setInteger("burnTime", burnTime);
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateEntity() 
	{
		++uptime;
		
		if(burnTime > 0)
		{
			--burnTime;
		}else
		{
			if(EssentiaHandler.drainEssentia(this, Aspect.FIRE, ForgeDirection.UNKNOWN, 6, false))
			{
				burnTime = 1600;
				this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
			}
		}
		
		if(!this.worldObj.isRemote)
		{
			if(burnTime <= 0)
			{
				int metadata = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
				if(metadata == 1)
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
