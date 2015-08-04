package tb.common.tile;

import java.util.List;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import tb.utils.TBUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.codechicken.lib.math.MathHelper;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.lib.research.ScanManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class TileEntityDeconstructor extends TileEntity{
	
	public int tickTime;
	public String placerName = "no placer";
	
	public boolean hasAir, hasFire, hasWater, hasEarth, hasOrdo, hasEntropy;

    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        
        hasAir = tag.getBoolean("b0");
        hasFire = tag.getBoolean("b1");
        hasWater = tag.getBoolean("b2");
        hasEarth = tag.getBoolean("b3");
        hasOrdo = tag.getBoolean("b4");
        hasEntropy = tag.getBoolean("b5");
        placerName = tag.getString("placer");
    }
    
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        
        tag.setBoolean("b0", hasAir);
        tag.setBoolean("b1", hasFire);
        tag.setBoolean("b2", hasWater);
        tag.setBoolean("b3", hasEarth);
        tag.setBoolean("b4", hasOrdo);
        tag.setBoolean("b5", hasEntropy);
        tag.setString("placer", placerName);
    }
	
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		if(net.getNetHandler() instanceof INetHandlerPlayClient)
			if(pkt.func_148853_f() == -10)
				this.readFromNBT(pkt.func_148857_g());
    }
	
	@Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -10, nbttagcompound);
    }
    
	@SuppressWarnings("unchecked")
	public void updateEntity() 
	{
		if(!this.worldObj.isRemote && tickTime == 0)
			MiscUtils.sendPacketToAllAround(worldObj, getDescriptionPacket(), xCoord, yCoord, zCoord, worldObj.provider.dimensionId, 16);
		
		int additionalStability = 0;
		
		if(hasAir)
			++additionalStability;
		if(hasWater)
			++additionalStability;
		if(hasFire)
			++additionalStability;
		if(hasEarth)
			++additionalStability;
		if(hasOrdo)
			++additionalStability;
		if(hasEntropy)
			++additionalStability;
		
		if(placerName == null || placerName.isEmpty() || placerName.contains("no placer"))
			return;
		
		List<Entity> entities = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1));
		if(!entities.isEmpty())
		{
			Entity e = entities.get(0);
			if(e != null && !e.isDead)
			{
				if(e instanceof EntityItem)
				{
					if(additionalStability >= 6)
					{
						EntityItem itm = (EntityItem) e;
						ItemStack stk = itm.getEntityItem();
						if(stk != null)
						{
							AspectList aspectsCompound = ThaumcraftCraftingManager.getObjectTags(stk);
							aspectsCompound = ThaumcraftCraftingManager.getBonusTags(stk, aspectsCompound);
							if(aspectsCompound != null && aspectsCompound.size() > 0)
							{
								++tickTime;
								if(tickTime == 40)
								{
									tickTime = 0;
									
									--stk.stackSize;
									if(stk.stackSize <= 0)
										itm.setDead();
									
									AspectList primals = ResearchManager.reduceToPrimals(aspectsCompound);
									Aspect a = null;
									if (this.worldObj.rand.nextInt(40) < primals.visSize())
										a = primals.getAspects()[this.worldObj.rand.nextInt(primals.getAspects().length)];
									
									if(!this.worldObj.isRemote && a != null)
									{
										EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(placerName);
										if(player != null)
										{
											double distance = player.getDistance(xCoord+0.5D, yCoord, zCoord+0.5D);
											if(additionalStability < 6)
												if(this.worldObj.rand.nextInt(MathHelper.floor_double(Math.max(1, (128+additionalStability*10)-distance))) == 0)
													TBUtils.addWarpToPlayer(EntityPlayerMP.class.cast(e), 1, 0);
											
											TBUtils.addAspectToKnowledgePool(player, a, (short) 1);
										}
									}
								}
							}
							Thaumcraft.proxy.blockRunes(worldObj, xCoord, yCoord+MathUtils.randomDouble(this.worldObj.rand)*0.5D, zCoord, 1, 0.5F, 0.5F, 8, 0);
							return;
						}
					}
				}else
				{
					AspectList aspectsCompound = ScanManager.generateEntityAspects(e);
					if(aspectsCompound != null && aspectsCompound.size() > 0)
					{
						++tickTime;
						
						tickTime += MathHelper.floor_double(additionalStability/2);
						
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
									if(additionalStability < 6)
										if(this.worldObj.rand.nextInt(MathHelper.floor_double(Math.max(1, (128+additionalStability*10)-distance))) == 0)
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
		}
		
		tickTime = 0;
	}
	
}
