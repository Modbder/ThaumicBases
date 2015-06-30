package tb.client.render.item;

import org.lwjgl.opengl.GL11;

import tb.common.item.ItemNodeFoci;
import DummyCore.Utils.MiscUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class NodeFociRenderer implements IItemRenderer{

	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases","models/nodeManipulator/foci.obj"));
	
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
		
		GL11.glScalef(0.25F, 0.25F, 0.25F);
		
		if(type == ItemRenderType.INVENTORY)
			GL11.glTranslated(0, -1, 0);
		
		MiscUtils.bindTexture("thaumicbases", "textures/blocks/nodeManipulator/foci_"+ItemNodeFoci.names[item.getItemDamage()]+".png");
		model.renderAll();
		
		GL11.glPopMatrix();
		
	}

}
