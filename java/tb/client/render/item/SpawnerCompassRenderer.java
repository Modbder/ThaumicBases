package tb.client.render.item;

import javax.vecmath.Matrix4f;

import org.lwjgl.util.vector.Vector3f;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IItemRenderer;
import DummyCore.Client.IModelCustom;
import DummyCore.Client.RenderAccessLibrary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

@SuppressWarnings("deprecation")
public class SpawnerCompassRenderer implements IItemRenderer{
	public static final IModelCustom compassModel = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases","models/spawnerCompass/compass.obj"));
	public static final ResourceLocation textures = new ResourceLocation("thaumicbases","textures/blocks/spawnerCompassUV.png");
	
	@Override
	public boolean handleRenderType(ItemStack item, TransformType type) {
		return true;
	}
	
	@Override
	public void renderItem(TransformType type, ItemStack item) {
		EntityPlayerSP entityclientplayermp = Minecraft.getMinecraft().thePlayer;
		GlStateManager.pushMatrix();
		if(type == TransformType.FIRST_PERSON)
		{
			GlStateManager.pushMatrix();

            Minecraft.getMinecraft().getTextureManager().bindTexture(entityclientplayermp.getLocationSkin());
            	
            GlStateManager.translate(0.6D, 0.1D, -0.3D);
            GlStateManager.rotate(160, 0, 1, 0);
            GlStateManager.rotate(-20, 1, 0, 0);
            Render render = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(Minecraft.getMinecraft().thePlayer);
            RenderPlayer renderplayer = (RenderPlayer)render;
            float f10 = 2.0F;
            GlStateManager.scale(f10, f10, f10);
            renderplayer.renderRightArm(Minecraft.getMinecraft().thePlayer);
            GlStateManager.popMatrix();
            
            GlStateManager.pushMatrix();
            
            GlStateManager.rotate(90, 1, 0, 0);
            GlStateManager.rotate(45, 0, 0, 1);
            GlStateManager.translate(2.5, 0, -3.4);
            GlStateManager.scale(0.4d, 0.4d, 0.4d);
            GlStateManager.translate(-1d, -1, 4);
            
            Minecraft.getMinecraft().renderEngine.bindTexture(textures);
            compassModel.renderAll();
            
            GlStateManager.popMatrix();
		}else
		{
			GlStateManager.pushMatrix();
			GlStateManager.rotate(90, 1, 0, 0);
			GlStateManager.rotate(-45, 0, 0, 1);
			GlStateManager.scale(0.25d, 0.25d, 0.25d);
			if(type == RenderAccessLibrary.ENTITY)
				GlStateManager.translate(0, 3, 0);
	        Minecraft.getMinecraft().renderEngine.bindTexture(textures);
	        compassModel.renderAll();
	        GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
	}
	
	public static final Matrix4f THIRD_PERSON_FIX = ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(0,1.6F,0),new Vector3f(0F,0.5F,0F),new Vector3f(1,1,1)));
	public static final Matrix4f GENERIC_FIX = ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(-0.3F,2.2F,0),new Vector3f(0F,0.1F,0.11F),new Vector3f(0.5F,0.5F,0.5F)));
	
	@Override
	public Matrix4f handleTransformsFor(ItemStack item, TransformType type) {
		if(type != TransformType.FIRST_PERSON)
		{
			if(type != TransformType.THIRD_PERSON)
				return THIRD_PERSON_FIX;
			
			return GENERIC_FIX;
			
		}
		return null;
	}
	
	
}
