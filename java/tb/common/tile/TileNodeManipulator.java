package tb.common.tile;

import java.util.List;

import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.MiscUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tb.common.entity.EntityAspectOrb;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.aura.EntityAuraNode;
import thaumcraft.common.lib.aura.NTAstral;
import thaumcraft.common.lib.aura.NTDark;
import thaumcraft.common.lib.aura.NTHungry;
import thaumcraft.common.lib.aura.NTNormal;
import thaumcraft.common.lib.aura.NTTaint;
import thaumcraft.common.lib.aura.NTUnstable;
import thaumcraft.common.lib.aura.NodeType;

public class TileNodeManipulator extends TileEntity implements IUpdatePlayerListBox{
	public int workTime = 0;
	public EntityAuraNode node = null;
	public boolean firstTick = true;
	public int ticksExisted;
	Object beam;
	
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        workTime = tag.getInteger("workTime");
    }
	
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setInteger("workTime", workTime);
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(){
		++ticksExisted;
		if(ticksExisted % 20 == 0)
			beam = null;
		if((firstTick || ticksExisted % 20 == 0) && node == null)
		{
			List<Entity> nodes = this.worldObj.getEntitiesWithinAABB(EntityAuraNode.class, AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ()).expand(3, 3, 3));
			if(!nodes.isEmpty())
				node = (EntityAuraNode) MiscUtils.getClosestEntity(nodes, pos.getX(), pos.getY(), pos.getZ());
		}
		
		if(node != null && (node.isDead || MathHelper.sqrt_double(node.getDistanceSq(getPos())) > 3))
			node = null;
		
		if(node != null)
		{
			int meta = BlockStateMetadata.getBlockMetadata(worldObj, getPos());
			EnumEffect ee = EnumEffect.fromMetadata(meta);
			NodeType nt = NodeType.nodeTypes[node.getNodeType()];
			switch(ee)
			{
				case BRIGHTEN:
				{
					if(nt instanceof NTNormal)
					{
						if(workTime > 20*60*20)
						{
							workTime = 0;
							node.setNodeType(6);
						}else
						{
							++workTime;
						}
					}
					break;
				}
				
				case DESTRUCT:
				{
					int maxTimeRequired = nt instanceof NTAstral ? 1*60*20 : 2*60*20;
					if(workTime >= maxTimeRequired)
					{
						workTime = 0;
						if(!(nt instanceof NTAstral))
						{
							node.setDead();
							node = null;
							return;
						}
						node.setNodeType(0);
						
					}else
					{
						++workTime;
						if(ticksExisted % 10 == 0)
						{
							Aspect asp = node.getAspect();
							AspectList al = AspectHelper.reduceToPrimals(new AspectList().add(asp,1));
							Aspect a = al.getAspects()[this.worldObj.rand.nextInt(al.size())];
							EntityAspectOrb aspect = new EntityAspectOrb(worldObj, node.posX,node.posY,node.posZ, a, 1);
							if(!this.worldObj.isRemote)
								this.worldObj.spawnEntityInWorld(aspect);
						}
					}
					break;
				}
				
				case EFFICIENCY:
				{
					if(this.worldObj.rand.nextDouble() < 0.05D)
					{
						Aspect asp = node.getAspect();
						AspectList al = AspectHelper.reduceToPrimals(new AspectList().add(asp,1));
						Aspect a = al.getAspects()[this.worldObj.rand.nextInt(al.size())];
						AuraHelper.addAura(getWorld(), node.getPosition(), a, 1+this.worldObj.rand.nextInt(5));
					}
					break;
				}
				
				case HUNGER:
				{
					if(nt instanceof NTNormal)
					{
						if(workTime > 5*60*20)
						{
							workTime = 0;
							node.setNodeType(2);
						}else
							++workTime;
					}
					break;
				}
				
				case INSTABILITY:
				{
					if(nt instanceof NTNormal)
					{
						if(workTime > 7*60*20)
						{
							workTime = 0;
							node.setNodeType(5);
						}else
							++workTime;
					}
					break;
				}
				
				case PURITY:
				{
					if(nt instanceof NTNormal)
					{
						if(workTime > 3*60*20)
						{
							workTime = 0;
							node.setNodeType(3);
						}else
							++workTime;
					}
					break;
				}
				
				case SINISTER:
				{
					if(nt instanceof NTNormal)
					{
						if(workTime > 6*60*20)
						{
							workTime = 0;
							node.setNodeType(1);
						}else
							++workTime;
					}
					break;
				}
				
				case SPEED:
				{
					for(int i = 0; i < 4; ++i)
						node.onEntityUpdate();
					
					break;
				}
				
				case STABILITY:
				{
					int maxTimeRequired = 0;
					
					if(nt instanceof NTDark)
						maxTimeRequired = 2*60*20;
					
					if(nt instanceof NTHungry)
						maxTimeRequired = 30*20;
					
					if(nt instanceof NTUnstable)
						maxTimeRequired = 7*60*20;
					
					if(nt instanceof NTTaint)
						maxTimeRequired = 3*60*20;
					
					if(nt instanceof NTAstral)
						maxTimeRequired = 60*20;
					
					if(workTime >= maxTimeRequired)
					{
						workTime = 0;
						
						if(nt instanceof NTDark || nt instanceof NTUnstable || nt instanceof NTHungry || nt instanceof NTTaint || nt instanceof NTAstral)
							node.setNodeType(0);
					}else
						++workTime;
					
					break;
				}
				
				case TAINT:
				{
					if(nt instanceof NTNormal)
					{
						if(workTime > 6*60*20)
						{
							workTime = 0;
							node.setNodeType(4);
						}else
							++workTime;
					}
					break;
				}
			
				case NONE:
				default:
					break;
			}
			
			if(!firstTick && this.worldObj.isRemote && ee != EnumEffect.NONE)
				beam = Thaumcraft.proxy.getFX().beamBore(node.posX, node.posY, node.posZ, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, 2, ee.getColor(), true, 0.5F, beam, 1);
		
		}
		
		if(firstTick)
			firstTick = false;
	}
	
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
    	return oldState.getBlock() == newSate.getBlock() ? false : super.shouldRefresh(world, pos, oldState, newSate);
    }
	
    public enum EnumEffect
    {
    	NONE, //Do nothing
    	BRIGHTEN, //Turn into etherial
    	DESTRUCT, //Destroy aspects and node, but create tons of aspect orbs
    	EFFICIENCY, //Recharge aura with current aspect faster
    	HUNGER, //Turn into Hungry
    	INSTABILITY, //Turn into unstable
    	PURITY, //Turn into pure
    	SINISTER, //Turn into sinister
    	SPEED, //+4 additional ticks each tick
    	STABILITY, //Turn onto normal
    	TAINT; //Turn into tainted
    	
    	public static EnumEffect fromMetadata(int meta)
    	{
    		return EnumEffect.values()[Math.min(EnumEffect.values().length-1, meta)];
    	}
    	
    	public int getColor()
    	{
    		return colors[ordinal()];
    	}
    	
    	private static final int[] colors = new int[]{
    		0xffffff,
    		0xffffff,
    		0x4e4756,
    		0xd2d200,
    		0xaf7c23,
    		0x0b4d42,
    		0xccc8f7,
    		0x643c5b,
    		0xeaeaea,
    		0xd0e0f8,
    		0x713496
    	};
    }
}
