package tb.common.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIAvoidCampfire extends EntityAIBase
{
	public boolean isScared;
	public int scareTimer;
	public int campfireX;
	public int campfireY;
	public int campfireZ;
	public World worldObj;
	public EntityCreature entityObj;
    /** The PathEntity of our entity */
    private PathEntity entityPathEntity;
    /** The PathNavigate of our entity */
    private PathNavigate entityPathNavigate;
    private double farSpeed;
    private double nearSpeed;

	public EntityAIAvoidCampfire(EntityCreature creature)
	{
		scareTimer = 100;
		worldObj = creature.worldObj;
		entityObj = creature;
		this.setMutexBits(1);
		this.entityPathNavigate = creature.getNavigator();
		nearSpeed = 2;
		farSpeed = nearSpeed*2;
	}
	
	@Override
	public boolean shouldExecute()
	{
		if(isScared)
		{
			Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entityObj, 16, 10, Vec3.createVectorHelper(campfireX+0.5D, campfireY, campfireZ+0.5D));
			
			if(vec3 == null)
				return false;
			
            this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord);
            return this.entityPathEntity == null ? false : this.entityPathEntity.isDestinationSame(vec3);
		}
		return false;
	}
	
    public boolean continueExecuting()
    {
        return !this.entityObj.getNavigator().noPath() && scareTimer > 0;
    }
    
    public void resetTask()
    {
    }
    
    public void updateTask()
    {
    	if(entityPathNavigate != null && (entityPathNavigate.getPath() == null || entityPathNavigate.getPath() != entityPathEntity))
    	{
    		this.entityPathNavigate.setPath(this.entityPathEntity, nearSpeed);
    	}
    	
    	--scareTimer;
    	if(scareTimer <= 0)
    		isScared = false;
    	
        if (this.entityObj.getDistanceSq(campfireX+0.5D,campfireY,campfireZ+0.5D) < 49.0D)
        {
            this.entityObj.getNavigator().setSpeed(this.nearSpeed);
        }
        else
        {
            this.entityObj.getNavigator().setSpeed(this.farSpeed);
        }
    }
    
    public void startExecuting()
    {
    	this.entityPathNavigate.setPath(this.entityPathEntity, nearSpeed);
    }

}
