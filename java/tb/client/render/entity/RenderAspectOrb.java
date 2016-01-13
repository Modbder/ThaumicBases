package tb.client.render.entity;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.TessellatorWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import tb.common.entity.EntityAspectOrb;
import thaumcraft.client.fx.ParticleEngine;

public class RenderAspectOrb extends Render{

	public RenderAspectOrb() {
		super(Minecraft.getMinecraft().getRenderManager());
	    this.shadowSize = 0.1F;
	    this.shadowOpaque = 0.5F;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ParticleEngine.particleTexture;
	}
	
	public void renderAO(EntityAspectOrb orb, double x, double y, double z,float partialTicks)
	{
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x, y, z);
	    
	    GlStateManager.enableBlend();
	    if (orb.getAspect() != null)
	    	GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, orb.getAspect().getBlend());
	    else
	    	GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	    
	    Minecraft.getMinecraft().renderEngine.bindTexture(ParticleEngine.particleTexture);
	    int i = (int)(System.nanoTime() / 25000000L % 16L);
	    
	    float f2 = i / 16.0F;
	    float f3 = (i + 1) / 16.0F;
	    float f4 = 0.5F;
	    float f5 = 0.5625F;
	    
        float f6 = 1.0F;
        float f7 = 0.5F;
        float f8 = 0.25F;
        int j = orb.getBrightnessForRender(partialTicks);
        int k = j % 65536;
        int l = j / 65536;
        
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        float f9 = 0.1F + 0.3F * (((float)orb.orbMaxAge - (float)orb.orbAge) / orb.orbMaxAge);
        GlStateManager.scale(f9, f9, f9);
        
	    TessellatorWrapper worldrenderer = TessellatorWrapper.getInstance();
	    worldrenderer.startDrawingQuads();
	    if (orb.getAspect() != null)
	    	worldrenderer.setColorRGBA_I(orb.getAspect().getColor(), 128);
	    
	    worldrenderer.addVertexWithUV(0.0F - f7, 0.0F - f8, 0.0D, f2, f5);
	    worldrenderer.addVertexWithUV(f6 - f7, 0.0F - f8, 0.0D, f3, f5);
	    worldrenderer.addVertexWithUV(f6 - f7, 1.0F - f8, 0.0D, f3, f4);
	    worldrenderer.addVertexWithUV(0.0F - f7, 1.0F - f8, 0.0D, f2, f4);
	    worldrenderer.draw();
	    
        GlStateManager.disableBlend();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
	}
	
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
    	this.renderAO(EntityAspectOrb.class.cast(entity), x, y, z, partialTicks);
    	super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
    }
}
