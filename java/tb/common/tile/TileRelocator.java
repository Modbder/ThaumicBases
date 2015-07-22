package tb.common.tile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tb.common.block.BlockRelocator;
import thaumcraft.api.wands.IWandable;

public class TileRelocator extends TileEntity implements IWandable{

	public boolean firstTick = true;
	public int checkCount = 0;
	public int power;
	
	@SuppressWarnings("unchecked")
	public void updateEntity()
	{
		ForgeDirection orientation = ForgeDirection.values()[this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord)%6];
		ForgeDirection opposite = orientation.getOpposite();
		
		if(firstTick || checkCount % 100 == 0)
		{
			power = 0;
			firstTick = false;
			int cont = 0;
			while(worldObj.getBlock(xCoord+opposite.offsetX*cont, yCoord+opposite.offsetY*cont, zCoord+opposite.offsetZ*cont) instanceof BlockRelocator)
			{
				++cont;
			}
			
			int cnt = 1;
			
			while(cnt <= cont*10 && (worldObj.isAirBlock(xCoord+orientation.offsetX*cnt, yCoord+orientation.offsetY*cnt, zCoord+orientation.offsetZ*cnt) || !worldObj.getBlock(xCoord+orientation.offsetX*cnt, yCoord+orientation.offsetY*cnt, zCoord+orientation.offsetZ*cnt).isNormalCube(worldObj, xCoord+orientation.offsetX*cnt, yCoord+orientation.offsetY*cnt, zCoord+orientation.offsetZ*cnt)))
			{
				
				++power;
				++cnt;
			}
			
			
		}
		
		if(!isBlockPowered())
		{
			if(!this.worldObj.getBlock(xCoord+orientation.offsetX, yCoord+orientation.offsetY, zCoord+orientation.offsetZ).isSideSolid(worldObj, xCoord, yCoord, zCoord, opposite))
			{
				int meta = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord)%6;
				boolean attract = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord) > 5;
				AxisAlignedBB aabb = null;
				if(meta == 0)
					aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord-power, zCoord, xCoord+1, yCoord+1, zCoord+1);
				if(meta == 1)
					aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1+power, zCoord+1);
				if(meta == 2)
					aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord-power, xCoord+1, yCoord+1, zCoord+1);
				if(meta == 3)
					aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1+power);
				if(meta == 4)
					aabb = AxisAlignedBB.getBoundingBox(xCoord-power, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1);
				if(meta == 5)
					aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1+power, yCoord+1, zCoord+1);
				
				List<Entity> affected = this.worldObj.getEntitiesWithinAABB(Entity.class, aabb);
				
				for(Entity base : affected)
				{
					boolean invert = attract ? !base.isSneaking() : base.isSneaking();
					if(invert)
					{
						if(meta == 0)
						{
							base.motionY += 0.1D;
						}
						if(meta == 1)
						{
							base.motionY -= 0.1D;
							base.fallDistance = 0;
						}
						if(meta == 2)
						{
							base.motionZ += 0.1D;
						}
						if(meta == 3)
						{
							base.motionZ -= 0.1D;
						}
						if(meta == 4)
						{
							base.motionX += 0.1D;
						}
						if(meta == 5)
						{
							base.motionX -= 0.1D;
						}
					}else
					{
						if(meta == 0)
						{
							base.motionY -= 0.1D;
							base.fallDistance = 0;
						}
						if(meta == 1)
						{
							base.motionY += 0.1D;
						}
						if(meta == 2)
						{
							base.motionZ -= 0.1D;
						}
						if(meta == 3)
						{
							base.motionZ += 0.1D;
						}
						if(meta == 4)
						{
							base.motionX -= 0.1D;
						}
						if(meta == 5)
						{
							base.motionX += 0.1D;
						}
					}
				}
			}
		}
		
		++checkCount;
	}
	
	@Override
	public int onWandRightClick(World paramWorld, ItemStack paramItemStack,	EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2,int paramInt3, int paramInt4, int paramInt5)
	{
		paramEntityPlayer.swingItem();
		paramWorld.playSound(xCoord, yCoord, zCoord, "thaumcraft:cameraticks", 1, 1, false);
		if(!paramEntityPlayer.isSneaking())
		{
			int meta = paramWorld.getBlockMetadata(xCoord, yCoord, zCoord) > 5 ? 6 : 0;
			paramWorld.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta+paramInt4, 3);
		}else
		{
			int meta = paramWorld.getBlockMetadata(xCoord, yCoord, zCoord) > 5 ? 6 : 0;
			paramWorld.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta+ForgeDirection.OPPOSITES[paramInt4], 3);
		}
		return 0;
	}

	@Override
	public ItemStack onWandRightClick(World paramWorld,	ItemStack paramItemStack, EntityPlayer paramEntityPlayer) 
	{
		return paramItemStack;
	}

	@Override
	public void onUsingWandTick(ItemStack paramItemStack,EntityPlayer paramEntityPlayer, int paramInt)
	{
		
	}

	@Override
	public void onWandStoppedUsing(ItemStack paramItemStack, World paramWorld,EntityPlayer paramEntityPlayer, int paramInt) 
	{
		
	}
	
	public boolean isBlockPowered()
	{
		return this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) || this.worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) > 0;
	}

}
