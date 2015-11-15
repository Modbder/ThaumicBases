package tb.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import tb.common.tile.TileAuraLinker;
import tb.common.tile.TileBraizer;
import tb.common.tile.TileCampfire;
import tb.common.tile.TileNodeManipulator;
import tb.common.tile.TileOverchanter;

public class TBTiles {

	public static void setup()
	{
		GameRegistry.registerTileEntity(TileOverchanter.class, "tb.overchanter");
		GameRegistry.registerTileEntity(TileCampfire.class, "tb.campfire");
		GameRegistry.registerTileEntity(TileBraizer.class, "tb.brazier");
		GameRegistry.registerTileEntity(TileAuraLinker.class, "tb.auraLinker");
		GameRegistry.registerTileEntity(TileNodeManipulator.class, "tb.nodeManipulator");
	}
}
