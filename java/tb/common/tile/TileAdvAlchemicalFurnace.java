package tb.common.tile;

import java.lang.reflect.Field;

import thaumcraft.common.tiles.TileAlchemyFurnace;

public class TileAdvAlchemicalFurnace extends TileAlchemyFurnace{

	public void updateEntity()
	{
		try
		{
			Class<TileAlchemyFurnace> furnace = TileAlchemyFurnace.class;
			Field count = furnace.getDeclaredField("count");
			count.setAccessible(true);
			
			count.setInt(this, count.getInt(this)+3);
			
			count.setAccessible(false);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		super.updateEntity();
	}
	
}
