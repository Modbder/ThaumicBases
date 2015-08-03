package tb.client.render.item;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import tb.api.RevolverUpgrade;
import tb.common.item.ItemRevolver;
import DummyCore.Utils.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderRevolver implements IItemRenderer
{

	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases","models/revolver/revolver.obj"));
	public static final ResourceLocation handle = new ResourceLocation("thaumicbases","textures/items/revolver/revolverHandleUV.png");
	public static final ResourceLocation barrel = new ResourceLocation("thaumicbases","textures/items/revolver/revolverBarrelUV.png");
	public static final ResourceLocation metal = new ResourceLocation("thaumicbases","textures/items/revolver/revolverDarkMetal.png");
	public static final ResourceLocation gun = new ResourceLocation("thaumicbases","textures/items/revolver/revolverGunUV.png");
	public static final ResourceLocation press = new ResourceLocation("thaumicbases","textures/items/revolver/revolverPressUV.png");
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		GL11.glPushMatrix();
		
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		if(type == ItemRenderType.EQUIPPED_FIRST_PERSON)
		{
			if(Minecraft.getMinecraft().thePlayer.isSneaking())
			{
				GL11.glScaled(0.4D, 0.4D, 0.4D);
				GL11.glRotated(-45, 0, 1, 0);
				GL11.glTranslated(-1.73D, 0D, 4D);
			}else
			{
				GL11.glScaled(0.4D, 0.4D, 0.4D);
				GL11.glRotated(-45, 0, 1, 0);
				GL11.glTranslated(4, -1, 1);
			}
		}
		
		if(type == ItemRenderType.INVENTORY)
		{
			GL11.glScaled(0.2D, 0.2D, 0.2D);
			GL11.glTranslated(-5, -5, 0);
		}
		
		if(type == ItemRenderType.EQUIPPED)
		{
			Entity e = (Entity) data[1];
			if(e.isSneaking())
			{
				GL11.glScaled(0.2D, 0.2D, 0.2D);
				GL11.glRotated(45, 0, 1, 0);
				GL11.glRotated(25, 1, 0, 0);
				GL11.glTranslated(0, 4, 6);
			}else
			{
				GL11.glScaled(0.2D, 0.2D, 0.2D);
				GL11.glRotated(45, 0, 1, 0);
				GL11.glTranslated(0, 2, 6);
			}
		}
		
		if(type == ItemRenderType.ENTITY)
		{
			GL11.glScaled(0.2D, 0.2D, 0.2D);
			GL11.glTranslated(0, 0, 5);
		}
		
		ResourceLocation handle = RenderRevolver.handle;
		ResourceLocation barrel = RenderRevolver.barrel;
		ResourceLocation metal = RenderRevolver.metal;
		ResourceLocation gun = RenderRevolver.gun;
		ResourceLocation press = RenderRevolver.press;
		
		ArrayList<Pair<RevolverUpgrade,Integer>> upgrades = ItemRevolver.getAllUpgradesFor(item);
		for(Pair<RevolverUpgrade,Integer> p : upgrades)
		{
			RevolverUpgrade ru = p.getFirst();
			handle = ru.getOverridePartTexture(item, 0, p.getSecond()) == null ? handle : ru.getOverridePartTexture(item, 0, p.getSecond());
			barrel = ru.getOverridePartTexture(item, 1, p.getSecond()) == null ? barrel : ru.getOverridePartTexture(item, 1, p.getSecond());
			metal = ru.getOverridePartTexture(item, 2, p.getSecond()) == null ? metal : ru.getOverridePartTexture(item, 2, p.getSecond());
			gun = ru.getOverridePartTexture(item, 3, p.getSecond()) == null ? gun : ru.getOverridePartTexture(item, 3, p.getSecond());
			press = ru.getOverridePartTexture(item, 4, p.getSecond()) == null ? press : ru.getOverridePartTexture(item, 4, p.getSecond());
		}
		
		Minecraft.getMinecraft().renderEngine.bindTexture(handle);
		model.renderPart("Cylinder");
		model.renderPart("Plane");
		
		double rotation = item.hasTagCompound() ? item.getTagCompound().getDouble("renderedRotation") : 0;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 2.9D, 0);
		GL11.glTranslated(0, -Math.cos(Math.toRadians(rotation))*2.8D, 0);
		GL11.glTranslated(Math.sin(Math.toRadians(rotation))*2.8D, 0, 0);
		GL11.glRotated(rotation, 0, 0, 1);
		
		
		Minecraft.getMinecraft().renderEngine.bindTexture(barrel);
		model.renderPart("rBarrel_Cube.001");
		GL11.glPopMatrix();
		//GL11.glRotated(-rotation, 0, 0, 1);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(metal);
		model.renderPart("Cube.003_Cube.004");
		model.renderPart("Cube.002_Cube.003");
		model.renderPart("Cube_Cube.001");
		Minecraft.getMinecraft().renderEngine.bindTexture(gun);
		model.renderPart("Cylinder.002");
		model.renderPart("Plane.001");
		Minecraft.getMinecraft().renderEngine.bindTexture(press);
		model.renderPart("Cube.001_Cube.005");
		
		if(type == ItemRenderType.EQUIPPED_FIRST_PERSON && item.getTagCompound() != null && item.getTagCompound().getBoolean("hasJar"))
		{
			GL11.glPushMatrix();
			
				GL11.glScaled(1.1, 1.1, 1.1);
				GL11.glTranslated(-0.11D, -0.25D, 1);
				
				double tickIndex = (double)Minecraft.getMinecraft().thePlayer.ticksExisted % 40 / 20;
				if(tickIndex > 1)
					tickIndex = -tickIndex+2;
				
				GL11.glColor4d(1, 0, 0, tickIndex);
				
				Minecraft.getMinecraft().renderEngine.bindTexture(metal);
				model.renderPart("Cube_Cube.001");
				
			GL11.glPopMatrix();
		}
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		GL11.glPopMatrix();
	}

}
