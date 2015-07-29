package tb.client.render.tile;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderNodeLinker extends TileEntitySpecialRenderer{

	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases","models/nodeLinker/nodeLinker.obj"));
	public static final ResourceLocation genIcon = new ResourceLocation("thaumicbases","textures/blocks/nodeLinker/nodeLinkerUV.png");
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double screenX,	double screenY, double screenZ, float partialTicks)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(screenX+0.5D, screenY, screenZ+0.5D);
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(genIcon);
		model.renderAll();
		
		GL11.glPopMatrix();
	}

}
