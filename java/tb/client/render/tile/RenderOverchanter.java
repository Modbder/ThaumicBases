package tb.client.render.tile;

import DummyCore.Utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import tb.common.tile.TileOverchanter;

@SuppressWarnings("rawtypes")
public class RenderOverchanter extends TileEntitySpecialRenderer{

	@Override
	public void renderTileEntityAt(TileEntity tile, double screenX,	double screenY, double screenZ, float partialTicks, int i)
	{
		TileOverchanter overchanter = (TileOverchanter) tile;
		
		if(overchanter.inventory != null)
		{
			GlStateManager.pushMatrix();
			int time = Minecraft.getMinecraft().thePlayer != null ? Minecraft.getMinecraft().thePlayer.ticksExisted : 0;
			DrawUtils.renderItemStack_Full(overchanter.inventory, 0, 0, 0, screenX, screenY, screenZ, time%360+partialTicks, 0, 1, 1, 1, 0.5F, 1.0F, 0.5F,false);
			
			GlStateManager.popMatrix();
		}
		
		if(overchanter.renderedLightning != null)
		{
			GlStateManager.pushMatrix();
			
			overchanter.renderedLightning.render(screenX+0.5D, screenY+1D, screenZ+0.5D, partialTicks);
			
			GlStateManager.popMatrix();
		}
	}
	
}
