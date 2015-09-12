package tb.client.render.item;

import java.awt.Color;
import java.lang.reflect.Field;

import org.lwjgl.opengl.GL11;

import tb.common.item.ItemCastingBracelet;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.block.BlockRenderer;
import thaumcraft.client.renderers.models.gear.ModelWand;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.ItemWandCasting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class CastingBraceletRenderer implements IItemRenderer{

	public static final IModelCustom bracelet = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases","models/bracelet/bracelet.obj"));
	public static final RenderBlocks renderBlocks = new RenderBlocks();
	public static final ModelWand wand = new ModelWand();
	
	public static ModelRenderer getWandFociModel() 
	{
		try
		{
			Class<ModelWand> wandModelClass = ModelWand.class;
			Field foci = wandModelClass.getDeclaredField("Focus");//<- If only that field had been declared as public... Oh well.
			boolean accessible = foci.isAccessible();
			if(!accessible)
				foci.setAccessible(true);
			
			ModelRenderer model = ModelRenderer.class.cast(foci.get(wand));
			
			if(!accessible)
				foci.setAccessible(false);
			
			return model;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) 
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,ItemRendererHelper helper)
	{
		if (helper == IItemRenderer.ItemRendererHelper.BLOCK_3D) 
			return false;
		return true;
	}
	
	public static Timer getMCTimer()
	{
		try
		{
			Class<Minecraft> mcClass = Minecraft.class;
			Field timerField = mcClass.getDeclaredFields()[16];
			if(!timerField.isAccessible())
				timerField.setAccessible(true);
			
			return Timer.class.cast(timerField.get(Minecraft.getMinecraft()));
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) 
	{
		EntityClientPlayerMP entityclientplayermp = Minecraft.getMinecraft().thePlayer;
		
		ItemWandCasting wand = (ItemWandCasting)item.getItem();
		ItemStack focusStack = wand.getFocusItem(item);
		
		//float renderPartialTicks = getMCTimer().renderPartialTicks;
			
		if(type == ItemRenderType.EQUIPPED_FIRST_PERSON)
		{
            GL11.glPushMatrix();
            Minecraft.getMinecraft().getTextureManager().bindTexture(entityclientplayermp.getLocationSkin());
            
            GL11.glTranslated(0.5D, 1, 1);

            GL11.glRotated(90, 0, 0, 1);
            GL11.glRotated(220, 1, 0, 0);
            
            if(entityclientplayermp.isUsingItem())
            {
            	GL11.glTranslated(-0.5D, 0D, 0);
            	GL11.glRotated(-45, 0, 0, 1);
            	
            }
            
            Render render = RenderManager.instance.getEntityRenderObject(Minecraft.getMinecraft().thePlayer);
            RenderPlayer renderplayer = (RenderPlayer)render;
            float f10 = 3.0F;
            GL11.glScalef(f10, f10, f10);
            renderplayer.renderFirstPersonArm(Minecraft.getMinecraft().thePlayer);
            GL11.glPopMatrix();
            
            GL11.glRotated(-45, 0, 1, 0);
            GL11.glTranslated(1.2D, 0.1D, -1);
            GL11.glScaled(0.6D, 0.6D, 0.6D);
            if(entityclientplayermp.isUsingItem())
            {
            	GL11.glTranslated(0D, 1D, 0);
            	GL11.glRotated(35, 1, 0, 0);
            	GL11.glTranslated(0D, 0D, -0.7D);
            	GL11.glScaled(0.8D, 0.8D, 0.8D);
            }
		}
		
		if(type == ItemRenderType.INVENTORY)
		{
			GL11.glScaled(0.5D, 0.5D, 0.5D);
		}
		
		if(type == ItemRenderType.EQUIPPED)
		{
			GL11.glScaled(0.25D, 0.25D, 0.25D);
			GL11.glRotated(-45, 0, 1, 0);
			GL11.glTranslated(2.85D, 2.9D, 1.3D);
			EntityLivingBase wielder = (EntityLivingBase) data[1];
			if(wielder.isInvisible())
				return;
		}
		
		if(type == ItemRenderType.ENTITY)
			GL11.glScaled(0.5D, 0.5D, 0.5D);
		
		double ds = 0.2D;
		GL11.glScaled(ds, ds, ds);
		Minecraft.getMinecraft().renderEngine.bindTexture(ItemCastingBracelet.braceletTextures[Math.min(ItemCastingBracelet.braceletTextures.length-1, item.getItemDamage())]);
		GL11.glPushMatrix();
		if(wand.getRod(item) != null && wand.getRod(item).isGlowing())
		{
			 int j = (int)(200.0F + MathHelper.sin(entityclientplayermp.ticksExisted) * 5.0F + 5.0F);
			 int k = j % 65536;
			 int l = j / 65536;
			 OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
		}
		bracelet.renderAll();
		GL11.glPopMatrix();
		
		if(focusStack != null)
		{
			if (wand.getFocus(item).getOrnament(focusStack) != null) 
			{
				GL11.glPushMatrix();
				GL11.glScaled(5, 5, 5);
				GL11.glTranslated(0, 0.7D, 0);
				Tessellator tessellator = Tessellator.instance;
				IIcon icon = wand.getFocus(item).getOrnament(focusStack);
				float minU = icon.getMinU();
				float minV = icon.getMinV();
				float maxU = icon.getMaxU();
				float maxV = icon.getMaxV();
				Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
				GL11.glPushMatrix();
				GL11.glTranslatef(-0.25F, -0.1F, 0.0275F);
				GL11.glScaled(0.5D, 0.5D, 0.5D);
				ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, icon.getIconWidth(), icon.getIconHeight(), 0.1F);
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-0.25F, -0.1F, 0.0275F);
				GL11.glScaled(0.5D, 0.5D, 0.5D);
				ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, icon.getIconWidth(), icon.getIconHeight(), 0.1F);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
			
			float alpha = 0.95F;
			if (wand.getFocus(item).getFocusDepthLayerIcon(focusStack) != null) 
			{
				GL11.glPushMatrix();
				GL11.glScaled(5, 5, 5);
				GL11.glTranslated(0, 0.87D, 0);
				GL11.glTranslatef(0.0F, -0.15F, 0.0F);
				GL11.glScaled(0.165D, 0.1765D, 0.165D); //<- using the same numbers as Azanor, since the rendering should be identical to his.
				Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
				renderBlocks.setRenderBoundsFromBlock(Blocks.glass);
				BlockRenderer.drawFaces(renderBlocks, null, wand.getFocus(item).getFocusDepthLayerIcon(focusStack), false);
				alpha = 0.6F;
				GL11.glPopMatrix();
			}
			
			if (Thaumcraft.isHalloween)
				UtilsFX.bindTexture("textures/models/spec_h.png");
			else
				UtilsFX.bindTexture("textures/models/wand.png");
			
			GL11.glPushMatrix();
			
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			GL11.glScaled(5, 5, 5);
			GL11.glTranslated(0, 0.87D, 0);
			GL11.glTranslatef(0.0F, -0.0475F, 0.0F);
			GL11.glScaled(0.525D, 0.5525D, 0.525D);
			Color c = new Color(wand.getFocus(item).getFocusColor(focusStack));
			GL11.glColor4f(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, alpha);
			int j = (int)(195.0F + MathHelper.sin(entityclientplayermp.ticksExisted / 3.0F) * 10.0F + 10.0F);
			int k = j % 65536;
			int l = j / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
			getWandFociModel().render(0.0625F);
			
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			
			GL11.glPopMatrix();
		}
		

	}

}
