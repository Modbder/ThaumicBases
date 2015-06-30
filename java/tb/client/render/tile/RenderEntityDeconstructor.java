package tb.client.render.tile;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderEntityDeconstructor extends TileEntitySpecialRenderer{

	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases","models/entityDeconstructor/deconstructor.obj"));
	public static final ResourceLocation frameIcon = new ResourceLocation("thaumicbases","textures/blocks/entityDeconstructor/goldbase.png");
	public static final ResourceLocation glassIcon = new ResourceLocation("thaumicbases","textures/blocks/entityDeconstructor/glass.png");
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double screenX,	double screenY, double screenZ, float partialTicks)
	{
		GL11.glPushMatrix();
		
		GL11.glTranslated(screenX+0.5D, screenY, screenZ+0.5D);
		GL11.glScalef(0.4F, 0.4F, 0.4F);
		Minecraft.getMinecraft().renderEngine.bindTexture(frameIcon);
		model.renderPart("Cube_Cube.001");
		
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(glassIcon);
		model.renderPart("Plane");
		
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		GL11.glPopMatrix();
	}

}
