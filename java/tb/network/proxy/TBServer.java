package tb.network.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tb.common.inventory.ContainerOverchanter;
import tb.common.inventory.ContainerRevolver;
import tb.common.inventory.ContainerThaumicAnvil;
import tb.common.inventory.ContainerVoidAnvil;
import tb.common.tile.TileOverchanter;

public class TBServer implements IGuiHandler{

	public void playGuitarSound(String sound)
	{
		
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		
		if(ID == 0x421922)
		{
			TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
			
			if(tile != null)
			{
				if(tile instanceof TileOverchanter)
				{
					return new ContainerOverchanter(player.inventory,tile);
				}
			}
		}else
		{
			if(ID == 0x421921)
				return new ContainerThaumicAnvil(player.inventory, world, new BlockPos(x, y, z), player);
			
			
			if(ID == 0x421920)
				return new ContainerVoidAnvil(player.inventory, world, new BlockPos(x, y, z), player);
			
			if(ID == 0x421919)
				return new ContainerRevolver(player.inventory, world, x, y, z);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		return null;
	}
	
	public void registerRenderInformation()
	{
		
	}
	
	public void lightning(World world, double sx, double sy, double sz, double ex, double ey, double ez, int dur, float curve, int speed, int type)
	{
		
	}
	
	public void sparkle(World w, double x, double y, double z, double dx, double dy, double dz, int color, float scale)
	{
		
	}
	
	public World clientWorld()
	{
		return null;
	}

}
