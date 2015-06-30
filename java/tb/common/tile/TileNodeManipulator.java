package tb.common.tile;

import java.util.Hashtable;

import DummyCore.Utils.MiscUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.entities.EntityAspectOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileNodeManipulator extends TileEntity implements IWandable
{
	public int workTime = 0;
	public Hashtable<String,Integer> nodeAspects = new Hashtable<String,Integer>();
	public Hashtable<String,Integer> newNodeAspects = new Hashtable<String,Integer>();
	public boolean firstTick = true;
	
	public void updateEntity() 
	{
		INode node = getNode();
		
		if(node == null)
			return;
		
		newNodeAspects.clear();
		for(int i = 0; i < node.getAspects().size(); ++i)
		{
			newNodeAspects.put(node.getAspects().getAspects()[i].getTag(), node.getAspects().getAmount(node.getAspects().getAspects()[i]));
		}
		
		if(this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 0 || getNode() == null)
			workTime = 0;
		
		if(this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 0 && getNode() != null)
		{
			int color = 0xffffff;
			int effect = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord)-1;
			
			switch(this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord)-1)
			{
				case 0:
				{
					color = 0xffffff;
					break;
				}
				case 1:
				{
					color = 0x4e4756;
					break;
				}
				case 2:
				{
					color = 0xd2d200;
					break;
				}
				case 3:
				{
					color = 0xaf7c23;
					break;
				}
				case 4:
				{
					color = 0x0b4d42;
					break;
				}
				case 5:
				{
					color = 0xccc8f7;
					break;
				}
				case 6:
				{
					color = 0x643c5b;
					break;
				}
				case 7:
				{
					color = 0xeaeaea;
					break;
				}
				case 8:
				{
					color = 0xd0e0f8;
					break;
				}
				case 9:
				{
					color = 0x713496;
					break;
				}
			}
			if(!firstTick && this.worldObj.isRemote)
				Thaumcraft.proxy.beam(this.worldObj, xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, xCoord+0.5D, yCoord-0.5D, zCoord+0.5D, 2, color, false, 0.5F, 2);
		
			if(!firstTick)
			{
				if(effect == 7)
				{
					if(!this.worldObj.isRemote)
					{
						boolean isNodeChanged = false;
						for(int i = 0; i < node.getAspects().size(); ++i)
						{
							Aspect a = node.getAspects().getAspects()[i];
							int max = node.getNodeVisBase(a);
							int current = node.getAspects().getAmount(a);
							
							if(current < max && this.worldObj.rand.nextFloat() < 0.01F)
							{
								node.getAspects().add(a, 1);
								isNodeChanged = true;
							}
						}
						if(isNodeChanged)
						{
							MiscUtils.sendPacketToAllAround(worldObj, this.worldObj.getTileEntity(xCoord, yCoord-1, zCoord).getDescriptionPacket(), xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId, 6);
						}
					}
				}
				if(effect == 1)
				{
					int maxTimeRequired = node.getNodeModifier() == NodeModifier.BRIGHT ? 1*60*20 : node.getNodeModifier() == NodeModifier.PALE ? 3*60*20 : node.getNodeModifier() == NodeModifier.FADING ? 6*60*20 : 2*60*20;
					if(workTime >= maxTimeRequired)
					{
						workTime = 0;
						if(node.getNodeModifier() == NodeModifier.FADING)
						{
							this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
							return;
						}
						node.setNodeModifier(node.getNodeModifier() == NodeModifier.BRIGHT ? null : node.getNodeModifier() == NodeModifier.PALE ? NodeModifier.FADING : node.getNodeModifier() == NodeModifier.FADING ? NodeModifier.FADING : NodeModifier.PALE);
						
					}else
					{
						++workTime;
						if(this.worldObj.getWorldTime() % 10 == 0)
						{
							Aspect a = node.getAspects().getAspects()[this.worldObj.rand.nextInt(node.getAspects().getAspects().length)];
							EntityAspectOrb aspect = new EntityAspectOrb(worldObj, xCoord+0.5D, yCoord-0.5D, zCoord+0.5D, a, 1);
							if(!this.worldObj.isRemote)
							{
								this.worldObj.spawnEntityInWorld(aspect);
							}
						}
					}
				}
				if(effect == 2)
				{
					for(int i = 0; i < node.getAspects().size(); ++i)
					{
						Aspect a = node.getAspects().getAspects()[i];
						int current = node.getAspects().getAmount(a);
						if(nodeAspects.containsKey(a.getTag()))
						{
							int prev = nodeAspects.get(a.getTag());
								
							if(current < prev && this.worldObj.rand.nextFloat() < 0.5F)
							{
								node.getAspects().add(a, 1);
							}
						}
					}
				}
				if(effect == 3)
				{
					if(node.getNodeType() == NodeType.NORMAL)
					{
						if(workTime > 5*60*20)
						{
							workTime = 0;
							node.setNodeType(NodeType.HUNGRY);
						}else
						{
							++workTime;
						}
					}
				}
				if(effect == 4)
				{
					if(node.getNodeType() == NodeType.NORMAL)
					{
						if(workTime > 7*60*20)
						{
							workTime = 0;
							node.setNodeType(NodeType.UNSTABLE);
						}else
						{
							++workTime;
						}
					}
				}
				if(effect == 5)
				{
					if(node.getNodeType() == NodeType.NORMAL)
					{
						if(workTime > 3*60*20)
						{
							workTime = 0;
							node.setNodeType(NodeType.PURE);
						}else
						{
							++workTime;
						}
					}
				}
				if(effect == 6)
				{
					if(node.getNodeType() == NodeType.NORMAL)
					{
						if(workTime > 6*60*20)
						{
							workTime = 0;
							node.setNodeType(NodeType.DARK);
						}else
						{
							++workTime;
						}
					}
				}
				if(effect == 8)
				{
					int maxTimeRequired = 0;
					
					if(node.getNodeModifier() == NodeModifier.FADING)
					{
						maxTimeRequired = 5*60*20;
					}
					
					if(node.getNodeModifier() == NodeModifier.PALE)
					{
						maxTimeRequired = 10*60*20;
					}
					
					if(node.getNodeType() == NodeType.DARK)
					{
						maxTimeRequired = 2*60*20;
					}
					
					if(node.getNodeType() == NodeType.HUNGRY)
					{
						maxTimeRequired = 30*20;
					}
					
					if(node.getNodeType() == NodeType.UNSTABLE)
					{
						maxTimeRequired = 7*60*20;
					}
					
					if(workTime >= maxTimeRequired)
					{
						workTime = 0;
						if(node.getNodeModifier() == NodeModifier.FADING)
						{
							node.setNodeModifier(NodeModifier.PALE);
							return;
						}
						
						if(node.getNodeModifier() == NodeModifier.PALE)
						{
							node.setNodeModifier(null);
							return;
						}
						
						if(node.getNodeType() == NodeType.DARK)
						{
							node.setNodeType(NodeType.NORMAL);
							return;
						}
						
						if(node.getNodeType() == NodeType.HUNGRY)
						{
							node.setNodeType(NodeType.NORMAL);
							return;
						}
						
						if(node.getNodeType() == NodeType.UNSTABLE)
						{
							node.setNodeType(NodeType.NORMAL);
							return;
						}
						
					}else
					{
						++workTime;
					}
				}
				if(effect == 0)
				{
					if(node.getNodeModifier() == null)
					{
						if(workTime > 20*60*20)
						{
							workTime = 0;
							node.setNodeModifier(NodeModifier.BRIGHT);
						}else
						{
							++workTime;
						}
					}
				}
				if(effect == 9)
				{
					if(node.getNodeType() == NodeType.NORMAL)
					{
						if(workTime > 6*60*20)
						{
							workTime = 0;
							node.setNodeType(NodeType.TAINTED);
						}else
						{
							++workTime;
						}
					}
				}
			}
		}
		
		firstTick = false;
		
		nodeAspects.clear();
		for(int i = 0; i < node.getAspects().size(); ++i)
		{
			nodeAspects.put(node.getAspects().getAspects()[i].getTag(), node.getAspects().getAmount(node.getAspects().getAspects()[i]));
		}
	}
	
	public INode getNode()
	{
		if(this.worldObj.getTileEntity(xCoord, yCoord-1, zCoord) instanceof INode)
			return INode.class.cast(worldObj.getTileEntity(xCoord, yCoord-1, zCoord));
		
		return null;
	}
	
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

	@Override
	public int onWandRightClick(World world, ItemStack wandstack,EntityPlayer player, int x, int y, int z, int side, int md) {
		return 0;
	}

	@Override
	public ItemStack onWandRightClick(World world, ItemStack wandstack,	EntityPlayer player) {
		return wandstack;
	}

	@Override
	public void onUsingWandTick(ItemStack wandstack, EntityPlayer player,int count) {
	}

	@Override
	public void onWandStoppedUsing(ItemStack wandstack, World world,EntityPlayer player, int count) {
	}

}
