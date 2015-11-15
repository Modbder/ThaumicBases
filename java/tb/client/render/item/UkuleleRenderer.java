package tb.client.render.item;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IItemRenderer;
import DummyCore.Client.IModelCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

@SuppressWarnings("deprecation")
public class UkuleleRenderer implements IItemRenderer{

	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases","models/ukulele/ukulele.obj"));
	public static final ResourceLocation base = new ResourceLocation("thaumicbases","textures/items/ukulele/ukulelebase.png");
	public static final ResourceLocation strings = new ResourceLocation("thaumicbases","textures/items/ukulele/ukulelestrings.png");
	public static final ResourceLocation handle = new ResourceLocation("thaumicbases","textures/items/ukulele/ukulelehandle.png");
	
	
	@Override
	public boolean handleRenderType(ItemStack item, TransformType type) {
		return true;
	}

	@Override
	public void renderItem(TransformType type, ItemStack item) {
		GlStateManager.pushMatrix();
		
		GlStateManager.scale(0.4, 0.4, 0.4);
		GlStateManager.translate(1, 0, 1);
		
		if(type == TransformType.GUI)
		{
			GlStateManager.rotate(90, 1, 0, 0);
			GlStateManager.rotate(45, 0, 0, 1);
			GlStateManager.translate(0.5, -2.6, 0);
			GlStateManager.scale(1, 1, 0.7);
		}
		
		if(type == TransformType.FIRST_PERSON)
		{
			GlStateManager.scale(1.8, 1.8, 1.8);	
			GlStateManager.translate(2, 3, 2);
			GlStateManager.rotate(210, 0, 1, 0);
			GlStateManager.rotate(10, 0, 0, 1);
			
			GlStateManager.pushMatrix();
			
            Minecraft.getMinecraft().getTextureManager().bindTexture(Minecraft.getMinecraft().thePlayer.getLocationSkin());
        	
            Render render = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(Minecraft.getMinecraft().thePlayer);
            RenderPlayer renderplayer = (RenderPlayer)render;
            
            GlStateManager.pushMatrix();
            
            GlStateManager.translate(0, 1, 2);
            GlStateManager.rotate(90, 1, 0, 0);
            GlStateManager.rotate(-90, 0, 0, 1);
            GlStateManager.translate(0, -1.3, 0);
            GlStateManager.rotate(20, 1, 0, 0);
            GlStateManager.rotate(-20, 0, 0, 1);
            GlStateManager.scale(1.5, 1.5, 1.5);
            
            if(Minecraft.getMinecraft().thePlayer.isUsingItem())
            {
            	float mOffset = Minecraft.getMinecraft().thePlayer.ticksExisted % 30 * 12;
            	float nOffset = Minecraft.getMinecraft().thePlayer.ticksExisted % 20 * 18;
            	float oOffset = Minecraft.getMinecraft().thePlayer.ticksExisted % 10 * 36;
            	GlStateManager.translate(0.3+Math.sin(Math.toRadians(mOffset))/6, 0, 0);
            	GlStateManager.translate(0, Math.sin(Math.toRadians(nOffset))/10, 0);
            	GlStateManager.translate(0, 0, Math.sin(Math.toRadians(oOffset))/20);
            }
            
            renderplayer.func_177138_b(Minecraft.getMinecraft().thePlayer);
			
            GlStateManager.popMatrix();
            
            GlStateManager.pushMatrix();
            
            GlStateManager.translate(-0.5, 0.8, -1);
            GlStateManager.rotate(90, 1, 0, 0);
            GlStateManager.rotate(-90, 0, 0, 1);
            GlStateManager.translate(0, -1.3, 0);
            GlStateManager.rotate(20, 1, 0, 0);
            GlStateManager.rotate(-50, 0, 0, 1);
            GlStateManager.scale(1.5, 3, 1.5);
            
            if(Minecraft.getMinecraft().thePlayer.isUsingItem())
            {
            	float mOffset = System.currentTimeMillis()/40 % 20 * 18;
            	float oOffset = Minecraft.getMinecraft().thePlayer.ticksExisted % 10 * 36;
            	GlStateManager.translate(Math.sin(Math.toRadians(-mOffset))/6, 0, 0);
            	GlStateManager.translate(0, 0, -0.03+Math.sin(Math.toRadians(-oOffset))/100);
            }
            
            renderplayer.func_177138_b(Minecraft.getMinecraft().thePlayer);
			
            GlStateManager.popMatrix();
            
			GlStateManager.popMatrix();
		}
		
		Minecraft.getMinecraft().renderEngine.bindTexture(base);
		model.renderPart("base_Cube.001");
		Minecraft.getMinecraft().renderEngine.bindTexture(strings);
		model.renderPart("strings_Cube.003");
		Minecraft.getMinecraft().renderEngine.bindTexture(handle);
		model.renderPart("hand_Cube.002");
		
		GlStateManager.popMatrix();
	}

	@Override
	public Matrix4f handleTransformsFor(ItemStack item, TransformType type) {
		if(type == TransformType.THIRD_PERSON)
			return ForgeHooksClient.getMatrix(new ItemTransformVec3f(
				new Vector3f(3.2F,1.2F,-2),
				new Vector3f(0,0,-0.3F),
				new Vector3f(0.7F,0.7F,0.7F)
			));
		return null;
	}

}
