package tb.common.tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.MiscUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.entities.monster.EntityWisp;

public class TileAuraLinker extends TileEntity implements IWandable,ITickable{
	
	public Coord3D linkCoord;
	public int syncTimer;
	public int instability;
	Object beam;
	@Override
	public void update() {
		
		if(syncTimer <= 0)
		{
			syncTimer = 100;
			NBTTagCompound tg = new NBTTagCompound();
			if(linkCoord != null)
			{
				tg.setFloat("coordsX", linkCoord.x);
				tg.setFloat("coordsY", linkCoord.y);
				tg.setFloat("coordsZ", linkCoord.z);
			}
			tg.setInteger("x", pos.getX());
			tg.setInteger("y", pos.getY());
			tg.setInteger("z", pos.getZ());
			MiscUtils.syncTileEntity(tg, 0);
		}else
			--syncTimer;
		
		if(linkCoord != null)
		{
			int x = MathHelper.floor_double(linkCoord.x);
			int y = MathHelper.floor_double(linkCoord.y);
			int z = MathHelper.floor_double(linkCoord.z);
			
			if(this.worldObj.getTileEntity(new BlockPos(x, y, z)) instanceof TileAuraLinker && TileAuraLinker.class.cast(this.worldObj.getTileEntity(new BlockPos(x, y, z))).linkCoord == null)
			{
				double sX = linkCoord.x+0.5D;
				double sY = linkCoord.y+0.925D;
				double sZ = linkCoord.z+0.5D;
				
				double eX = pos.getX()+0.5D;
				double eY = pos.getY()+0.925D;
				double eZ = pos.getZ()+0.5D;
				
				int cX = pos.getX() / 16;
				int cZ = pos.getZ() / 16;
				int ecX = x / 16;
				int ecZ = z / 16;
				
				boolean same = cX == ecX && cZ == ecZ;
				
				if(this.worldObj.isRemote)
					beam = Thaumcraft.proxy.getFX().beamBore(sX, sY, sZ, eX, eY, eZ, 1, same ? 0xff0000 : 0xffffff, true, 1, beam, 1);
				
				if(syncTimer % 10 == 0 && !this.worldObj.isRemote && !same && !this.worldObj.isBlockPowered(pos) && !this.worldObj.isBlockPowered(pos.down()))
				{
					int aPos = this.worldObj.rand.nextInt(Aspect.getPrimalAspects().size());
					Aspect a = Aspect.getPrimalAspects().get(aPos);
					int aPullFrom = AuraHelper.getAura(worldObj, getPos(), a);
					BlockPos to = new BlockPos(x,y,z);
					
					if(!this.worldObj.isBlockPowered(to) && !this.worldObj.isBlockPowered(to.down()) && aPullFrom > 0 && AuraHelper.drainAura(worldObj, pos, a, 1))
					{
						int pIndex = a == Aspect.AIR ? 1 : a == Aspect.WATER ? 2 : a == Aspect.EARTH ? 3 : a == Aspect.ENTROPY ? 5 : a == Aspect.ORDER ? 6 : a == Aspect.FIRE ? 4 : 0;
						boolean iB = instabilityCheck();
						if(iB)
							AuraHelper.addAura(worldObj, to, a, 1);
						
						if(iB)
							for(int i = 0; i < 10; ++i)
								Thaumcraft.proxy.getFX().sparkle((float)sX, (float)sY+0.1F, (float)sZ, 3, pIndex, -this.worldObj.rand.nextFloat());
							
						for(int i = 0; i < 10; ++i)
						{
							float addition = this.worldObj.rand.nextFloat();
							Thaumcraft.proxy.getFX().sparkle((float)eX, (float)eY+0.1F+addition, (float)eZ, 3, pIndex, addition);
						}
						
						if(this.worldObj.rand.nextInt(100) > aPullFrom)
							++instability;
					}
				}
			}else
			{
				linkCoord = null;
				beam = null;
			}
		}else
		{
			instability = 0;
			beam = null;
		}
	}
	
	public boolean instabilityCheck()
	{
		if(instability > 0 && this.worldObj.rand.nextInt(50) <= this.instability)
		{
			int rnd = this.worldObj.rand.nextInt(this.instability);
			if(rnd == 49)
			{
				instability -= explodeTransmitter();
			}else
			{
				if(rnd >= 45)
				{
					instability -= harmTransmitter();
				}else
				{
					if(rnd >= 31)
					{
						instability -= wisp();
					}else
					{
						if(rnd >= 20)
						{
							instability -= zap();
						}else
						{
							if(rnd >= 10)
							{
								instability -= fluxNode();
							}else
							{
								instability -= fluxTransmission();
							}
						}
					}
				}
			}
			return false;
		}
		return true;
	}
	
	public int harmTransmitter()
	{
		int destroyed = 0;
		for(Aspect a : Aspect.getPrimalAspects())
		{
			int current = AuraHelper.getAura(worldObj, getPos(), a);
			if(current > 0)
			{
				int reduced = this.worldObj.rand.nextInt(Math.min(current, 6));
				if(reduced > 0)
					AuraHelper.drainAura(worldObj, getPos(), a, reduced);
				
				destroyed += reduced;
			}
			
		}
		return destroyed;
	}
	
	@SuppressWarnings("unchecked")
	public int zap()
	{
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.fromBounds(x-5, y-6, z-5, x+5, y+4, z+5));
		if(!players.isEmpty())
		{
			EntityPlayer target = players.get(worldObj.rand.nextInt(players.size()));
			target.attackEntityFrom(DamageSource.magic, 4 + this.worldObj.rand.nextInt(4));
			return 9;
		}
		return 0;
	}
	
	public int wisp()
	{
		boolean side = this.worldObj.rand.nextBoolean();
		int x = side ? MathHelper.floor_double(linkCoord.x) : pos.getX();
		int y = side ? MathHelper.floor_double(linkCoord.y) : pos.getY();
		int z = side ? MathHelper.floor_double(linkCoord.z) : pos.getZ();
		ArrayList<Aspect> aspects = new ArrayList<Aspect>();
		Collection<Aspect> pa = Aspect.aspects.values();
		aspects.addAll(pa);
		
		EntityWisp wisp = new EntityWisp(worldObj);
		wisp.setPositionAndRotation(x+0.5D, y-0.5D, z+0.5D, 0, 0);
		wisp.setType(aspects.get(worldObj.rand.nextInt(aspects.size())).getTag());
		worldObj.spawnEntityInWorld(wisp);
		
		return 9;
	}
	
	public int fluxNode()
	{
		AuraHelper.pollute(getWorld(), getPos(), 3, true);
		return 5;
	}
	
	public int fluxTransmission()
	{
		AuraHelper.pollute(getWorld(), getPos(), 3, true);
		return 3;
	}
	
	public int explodeTransmitter()
	{
		int x = MathHelper.floor_double(pos.getX());
		int y = MathHelper.floor_double(pos.getY());
		int z = MathHelper.floor_double(pos.getZ());
		this.worldObj.setBlockToAir(pos);
		this.worldObj.createExplosion(null, x+0.5D, y-0.5D, z+0.5D, 2, true);
		return instability;
	}
	
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
    	if(pkt.getNbtCompound().hasKey("coordsX"))
    	{
    		linkCoord = new Coord3D(pkt.getNbtCompound().getFloat("coordsX"),pkt.getNbtCompound().getFloat("coordsY"),pkt.getNbtCompound().getFloat("coordsZ"));
    	}else
    		linkCoord = null;
    }
    
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
    	if(tag.hasKey("coordsX"))
    	{
    		linkCoord = new Coord3D(tag.getFloat("coordsX"),tag.getFloat("coordsY"),tag.getFloat("coordsZ"));
    	}
    	instability = tag.getInteger("instability");
    }
    
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
		if(linkCoord != null)
		{
			tag.setFloat("coordsX", linkCoord.x);
			tag.setFloat("coordsY", linkCoord.y);
			tag.setFloat("coordsZ", linkCoord.z);
		}
		tag.setInteger("instability", instability);
    }

	@Override
	public boolean onWandRightClick(World paramWorld, ItemStack paramItemStack, EntityPlayer paramEntityPlayer,BlockPos paramBlockPos, EnumFacing paramEnumFacing) {
	
		
		paramEntityPlayer.swingItem();
		if(!paramItemStack.hasTagCompound())
			paramItemStack.setTagCompound(new NBTTagCompound());
		
		if(paramItemStack.getTagCompound().hasKey("linkCoordX"))
		{
			float x = paramItemStack.getTagCompound().getFloat("linkCoordX");
			float y = paramItemStack.getTagCompound().getFloat("linkCoordY");
			float z = paramItemStack.getTagCompound().getFloat("linkCoordZ");
			if(this.worldObj.getTileEntity(new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z))) instanceof TileAuraLinker)
			{
				TileAuraLinker tile = (TileAuraLinker) this.worldObj.getTileEntity(new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z)));
				tile.linkCoord = new Coord3D(pos.getX(),pos.getY(),pos.getZ());
				paramItemStack.getTagCompound().removeTag("linkCoordX");
				paramItemStack.getTagCompound().removeTag("linkCoordY");
				paramItemStack.getTagCompound().removeTag("linkCoordZ");
				if(paramWorld.isRemote)
					paramEntityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tb.txt.linkEstabilished")));
			}
		}else
		{
			paramItemStack.getTagCompound().setFloat("linkCoordX", pos.getX());
			paramItemStack.getTagCompound().setFloat("linkCoordY", pos.getY());
			paramItemStack.getTagCompound().setFloat("linkCoordZ", pos.getZ());
			if(paramWorld.isRemote)
				paramEntityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tb.txt.linkStarted")));
		}
		
		return false;
	}

	@Override
	public void onUsingWandTick(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, int paramInt) {
		
	}

	@Override
	public void onWandStoppedUsing(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer,
			int paramInt) {
		
	}

}
