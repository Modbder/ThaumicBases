package tb.common.tile;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Utils.BlockStateMetadata;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import tb.common.entity.EntityAIAvoidCampfire;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.lib.events.EssentiaHandler;

public class TileBraizer extends TileEntity implements IUpdatePlayerListBox
{
	public int burnTime;
	public int uptime;
	
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
    	return oldState.getBlock() == newSate.getBlock() ? false : super.shouldRefresh(world, pos, oldState, newSate);
    }
	
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
	public void update() 
	{
		++uptime;
		
		if(burnTime > 0)
		{
			--burnTime;
		}else
		{
			if(EssentiaHandler.drainEssentia(this, Aspect.FIRE, null, 6, false))
			{
				burnTime = 1600;
				this.worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).getBlock().getStateFromMeta(1));
			}
		}
		
		if(!this.worldObj.isRemote)
		{
			if(burnTime <= 0)
			{
				int metadata = BlockStateMetadata.getBlockMetadata(worldObj, pos);
				if(metadata == 1)
					this.worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).getBlock().getStateFromMeta(0));
				
				return;
			}
		}

		
		if(!this.worldObj.isRemote)
		{
			List<EntityCreature> creatures = this.worldObj.getEntitiesWithinAABB(EntityCreature.class, AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.getX()+1, pos.getY()+1, pos.getZ()+1).expand(12, 6, 12));
			
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
			      campfireAviodable.campfireX = pos.getX();
			      campfireAviodable.campfireY = pos.getY();
			      campfireAviodable.campfireZ = pos.getZ();
			      
			      if(!hasTask)
			    	  c.tasks.addTask(1, campfireAviodable);
			}
		}
	}
}
