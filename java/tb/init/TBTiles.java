package tb.init;

import tb.common.tile.TileAdvAlchemicalFurnace;
import tb.common.tile.TileEntityDeconstructor;
import tb.common.tile.TileNodeManipulator;
import tb.common.tile.TileOverchanter;
import cpw.mods.fml.common.registry.GameRegistry;

public class TBTiles {
	
	public static void setup()
	{
		GameRegistry.registerTileEntity(TileAdvAlchemicalFurnace.class, "tb.advancedAlchemicalFurnace");
		GameRegistry.registerTileEntity(TileEntityDeconstructor.class, "tb.entityDeconstructor");
		GameRegistry.registerTileEntity(TileOverchanter.class, "tb.overchanter");
		GameRegistry.registerTileEntity(TileNodeManipulator.class, "tb.manipulator");
		
	}

}
