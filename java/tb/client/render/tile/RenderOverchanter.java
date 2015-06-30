package tb.client.render.tile;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MiscUtils;
import tb.common.tile.TileOverchanter;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderOverchanter extends TileEntitySpecialRenderer{

	@Override
	public void renderTileEntityAt(TileEntity tile, double screenX,	double screenY, double screenZ, float partialTicks)
	{
		TileOverchanter overchanter = (TileOverchanter) tile;
		
		if(overchanter.inventory != null)
		{
			GL11.glPushMatrix();
			
			MiscUtils.renderItemStack_Full(overchanter.inventory, 0, 0, 0, screenX, screenY, screenZ, tile.getWorldObj().getWorldTime()%360, 0, 1, 1, 1, 0.5F, 0.9F, 0.5F);
			
			GL11.glPopMatrix();
		}
		
		if(overchanter.renderedLightning != null)
		{
			GL11.glPushMatrix();
			
			overchanter.renderedLightning.render(screenX+0.5D, screenY+1D, screenZ+0.5D, partialTicks);
			
			GL11.glPopMatrix();
		}
	}
	
}
