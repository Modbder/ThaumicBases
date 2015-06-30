package tb.client.render.item;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class NodeManipulatorItemRenderer implements IItemRenderer{
	
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases","models/nodeManipulator/nodeManipulator.obj"));
	public static final ResourceLocation genIcon = new ResourceLocation("thaumicbases","textures/blocks/nodeManipulator/baseUVMap.png");

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		
		GL11.glRotated(180, 1, 0, 0);
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		GL11.glTranslated(0, -0.5D, 0);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(genIcon);
		model.renderPart("base_Cube.001");
		model.renderPart("handle_0_Cube.002");
		model.renderPart("handle_1_Cube.002");
		model.renderPart("handle_2_Cube.002");
		model.renderPart("handle_3_Cube.002");
		
		GL11.glPopMatrix();
	}

}
