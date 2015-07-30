package tb.network.proxy;

import tb.common.inventory.ContainerOverchanter;
import tb.common.inventory.ContainerRevolver;
import tb.common.inventory.ContainerThaumicAnvil;
import tb.common.inventory.ContainerVoidAnvil;
import tb.common.tile.TileOverchanter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class TBServer implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		
		if(ID == 0x421922)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			
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
				return new ContainerThaumicAnvil(player.inventory, world, x, y, z, player);
			
			if(ID == 0x421920)
				return new ContainerVoidAnvil(player.inventory, world, x, y, z, player);
			
			if(ID == 0x421919)
				return new ContainerRevolver(player.inventory, world, x, y, z);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		// TODO Auto-generated method stub
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
