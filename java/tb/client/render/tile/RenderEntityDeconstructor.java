package tb.client.render.tile;

import org.lwjgl.opengl.GL11;

import tb.common.tile.TileEntityDeconstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderEntityDeconstructor extends TileEntitySpecialRenderer{

	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases","models/entityDeconstructor/deconstructor.obj"));
	public static final ResourceLocation frameIcon = new ResourceLocation("thaumicbases","textures/blocks/entityDeconstructor/goldbase.png");
	public static final ResourceLocation glassIcon = new ResourceLocation("thaumicbases","textures/blocks/entityDeconstructor/glass.png");
	
	public static final ResourceLocation air = new ResourceLocation("thaumicbases","textures/blocks/crystal/air.png");
	public static final ResourceLocation fire = new ResourceLocation("thaumicbases","textures/blocks/crystal/fire.png");
	public static final ResourceLocation water = new ResourceLocation("thaumicbases","textures/blocks/crystal/water.png");
	public static final ResourceLocation earth = new ResourceLocation("thaumicbases","textures/blocks/crystal/earth.png");
	public static final ResourceLocation ordo = new ResourceLocation("thaumicbases","textures/blocks/crystal/order.png");
	public static final ResourceLocation entropy = new ResourceLocation("thaumicbases","textures/blocks/crystal/entropy.png");
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double screenX,	double screenY, double screenZ, float partialTicks)
	{
		GL11.glPushMatrix();
		
		TileEntityDeconstructor d = (TileEntityDeconstructor) tile;
		
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
		
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(true);
        char c0 = 61680;
        int j = c0 % 65536;
        int k = c0 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		if(d.hasAir)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(air);
			model.renderPart("Sphere");
		}
		
		if(d.hasFire)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(fire);
			model.renderPart("Sphere.001");
		}
		
		if(d.hasWater)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(water);
			model.renderPart("Sphere.002");
		}
		
		if(d.hasEarth)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(earth);
			model.renderPart("Sphere.003");
		}
		
		if(d.hasOrdo)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(ordo);
			model.renderPart("Sphere.004");
		}
		
		if(d.hasEntropy)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(entropy);
			model.renderPart("Sphere.005");
		}
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		GL11.glPopMatrix();
	}

}
