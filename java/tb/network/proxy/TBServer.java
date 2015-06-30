package tb.network.proxy;

import tb.common.inventory.ContainerOverchanter;
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

}
