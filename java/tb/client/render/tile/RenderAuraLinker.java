package tb.client.render.tile;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IModelCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("rawtypes")
public class RenderAuraLinker extends TileEntitySpecialRenderer{

	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases","models/nodeLinker/nodeLinker.obj"));
	public static final ResourceLocation genIcon = new ResourceLocation("thaumicbases","textures/blocks/nodeLinker/nodeLinkerUV.png");
	
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double screenX,	double screenY, double screenZ, float partialTicks, int i)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(screenX+0.5, screenY+0.9, screenZ+0.5);
		GlStateManager.scale(0.5, 0.5, 0.5);
		GlStateManager.rotate(180, 1, 0, 0);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(genIcon);
		model.renderAll();
		
		GlStateManager.popMatrix();
	}
	
}
