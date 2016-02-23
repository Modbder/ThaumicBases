package tb.client.render.item;

import javax.vecmath.Matrix4f;

import org.lwjgl.util.vector.Vector3f;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IItemRenderer;
import DummyCore.Client.IModelCustom;
import DummyCore.Utils.DrawUtils;
import DummyCore.Utils.TessellatorWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import tb.api.BraceletState;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWand;

@SuppressWarnings("deprecation")
public class CastingBraceletRenderer implements IItemRenderer{

	public static final IModelCustom bracelet = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases","models/bracelet/bracelet.obj"));
	
	@Override
	public boolean handleRenderType(ItemStack item, TransformType type) {
		return true;
	}

	@Override
	public void renderItem(TransformType type, ItemStack item) {
		
		EntityPlayerSP entityclientplayermp = Minecraft.getMinecraft().thePlayer;
		
		ItemWand wand = (ItemWand)item.getItem();
		ItemStack focusStack = wand.getFocusStack(item);
		
		GlStateManager.pushMatrix();
		RenderHelper.disableStandardItemLighting();
		if(type == TransformType.FIRST_PERSON)
		{
			GlStateManager.pushMatrix();
			
            Minecraft.getMinecraft().getTextureManager().bindTexture(entityclientplayermp.getLocationSkin());
        	
            Render<?> render = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(Minecraft.getMinecraft().thePlayer);
            RenderPlayer renderplayer = (RenderPlayer)render;
            
            float f10 = 4.0F;
            GlStateManager.scale(f10, f10, f10);
            
            GlStateManager.rotate(90, 1, 0, 0);
            GlStateManager.rotate(40, 0, 0, 1);
            GlStateManager.translate(0.2, 0, 0);
            if(entityclientplayermp.isUsingItem())
            {
            	 GlStateManager.rotate(60, 1, 0, 0);
            	 GlStateManager.translate(0, 0, 0);
            }
            renderplayer.renderRightArm(Minecraft.getMinecraft().thePlayer);
			
			GlStateManager.popMatrix();
			
			
		}
		
		double ds = 0.2D;
		GlStateManager.scale(ds, ds, ds);
		Minecraft.getMinecraft().renderEngine.bindTexture(BraceletState.forMetadata(item.getMetadata()).getBraceletTexture(item));
		GlStateManager.pushMatrix();
		if(wand.getRod(item) != null && wand.getRod(item) == ConfigItems.WAND_ROD_BLAZE)
		{
			 int j = (int)(200.0F + MathHelper.sin(entityclientplayermp.ticksExisted) * 5.0F + 5.0F);
			 int k = j % 65536;
			 int l = j / 65536;
			 OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
		}
		
		if(type == TransformType.FIRST_PERSON)
		{
			GlStateManager.translate(-10, 2, 4);
			GlStateManager.rotate(-40, 0, 1, 0);
			GlStateManager.rotate(-15, 1, 0, 0);
			GlStateManager.translate(1.6,2.6,1);
			ds = 0.7;
			GlStateManager.scale(ds, ds, ds);
            if(entityclientplayermp.isUsingItem())
            {
            	 GlStateManager.rotate(-10, 1, 0, 0);
            	 GlStateManager.rotate(20, 0, 1, 0);
            	 GlStateManager.translate(1.7, -1, 0);
            }
		}
		bracelet.renderAll();
		if(focusStack != null)
		{
			GlStateManager.pushMatrix();
			ds = 0.125D;
			GlStateManager.scale(ds, ds, ds);
			GlStateManager.translate(-8, 22, -8);
			DrawUtils.bindTexture("thaumcraft", "textures/models/vcrystal.png");
			TessellatorWrapper tec = TessellatorWrapper.getInstance();
			int color = wand.getFocus(item).getFocusColor(focusStack);
			
			RenderHelper.disableStandardItemLighting();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 0);
			tec.startDrawingQuads();
			
			tec.setColorOpaque_I(color);
			tec.addVertexWithUV(16, 0, 0, 0, 0);
			tec.addVertexWithUV(0, 0, 0, 1, 0);
			tec.addVertexWithUV(0, 16, 0, 1, 1);
			tec.addVertexWithUV(16, 16, 0, 0, 1);
			
			tec.addVertexWithUV(16, 16, 0, 0, 0);
			tec.addVertexWithUV(0, 16, 0, 1, 0);
			tec.addVertexWithUV(0, 16, 16, 1, 1);
			tec.addVertexWithUV(16, 16, 16, 0, 1);
			
			tec.addVertexWithUV(0, 0, 0, 0, 0);
			tec.addVertexWithUV(16, 0, 0, 1, 0);
			tec.addVertexWithUV(16, 0, 16, 1, 1);
			tec.addVertexWithUV(0, 0, 16, 0, 1);
			
			tec.addVertexWithUV(0, 0, 16, 0, 0);
			tec.addVertexWithUV(16, 0, 16, 1, 0);
			tec.addVertexWithUV(16, 16, 16, 1, 1);
			tec.addVertexWithUV(0, 16, 16, 0, 1);
			
			tec.addVertexWithUV(16, 0, 16, 0, 0);
			tec.addVertexWithUV(16, 0, 0, 1, 0);
			tec.addVertexWithUV(16, 16, 0, 1, 1);
			tec.addVertexWithUV(16, 16, 16, 0, 1);
			
			tec.addVertexWithUV(0, 0, 0, 0, 0);
			tec.addVertexWithUV(0, 0, 16, 1, 0);
			tec.addVertexWithUV(0, 16, 16, 1, 1);
			tec.addVertexWithUV(0, 16, 0, 0, 1);
			
			Tessellator.getInstance().draw();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.popMatrix();
	}

	public static final Matrix4f GUI_FIX = ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(0,-0.8F,0),new Vector3f(-0.21F,0,0),new Vector3f(0.55F,0.55F,0.55F)));
	public static final Matrix4f THIRD_PERSON_FIX = ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(-1.4F,0,0),new Vector3f(-0.06F,0,-0.14F),new Vector3f(0.15F,0.15F,0.15F)));
	
	@Override
	public Matrix4f handleTransformsFor(ItemStack item, TransformType type) {
		if(type == TransformType.GUI)
			return GUI_FIX;
		
		if(type == TransformType.THIRD_PERSON)
			return THIRD_PERSON_FIX;
		
		return null;
	}

}
