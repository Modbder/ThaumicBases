package tb.common.tile;

import java.util.List;

import DummyCore.Utils.MathUtils;
import tb.utils.TBUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.codechicken.lib.math.MathHelper;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.research.ScanManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class TileEntityDeconstructor extends TileEntity{
	
	public int tickTime;
	public String placerName;

	@SuppressWarnings("unchecked")
	public void updateEntity() 
	{
		if(placerName == null || placerName.isEmpty())
			return;
		
		List<Entity> entities = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1));
		if(!entities.isEmpty())
		{
			Entity e = entities.get(0);
			if(e != null && !e.isDead)
			{
				AspectList aspectsCompound = ScanManager.generateEntityAspects(e);
				if(aspectsCompound != null && aspectsCompound.size() > 0)
				{
					++tickTime;
					if(tickTime == 40)
					{
						tickTime = 0;
						e.attackEntityFrom(DamageSource.outOfWorld, 1);
						
						if(e instanceof EntityPlayerMP)
						{
							TBUtils.addWarpToPlayer(EntityPlayerMP.class.cast(e), 1, 0);
						}
						
						Aspect a = aspectsCompound.getAspects()[this.worldObj.rand.nextInt(aspectsCompound.size())];
						
						if(!this.worldObj.isRemote)
						{
							EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(placerName);
							if(player != null)
							{
								double distance = player.getDistance(xCoord+0.5D, yCoord, zCoord+0.5D);
								if(this.worldObj.rand.nextInt(MathHelper.floor_double(Math.max(1, 192-distance))) == 0)
									TBUtils.addWarpToPlayer(EntityPlayerMP.class.cast(e), 1, 0);
								
								TBUtils.addAspectToKnowledgePool(player, a, (short) 1);
							}
						}
					}
					Thaumcraft.proxy.blockRunes(worldObj, xCoord, yCoord+MathUtils.randomDouble(this.worldObj.rand)*0.5D, zCoord, 1, 0.5F, 0.5F, 8, 0);
					return;
				}
			}
		}
		
		tickTime = 0;
	}
	
}
