package tb.client.render.item;

import javax.vecmath.Matrix4f;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IItemRenderer;
import DummyCore.Client.IModelCustom;
import DummyCore.Client.RPAwareModel;
import DummyCore.Utils.DrawUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import tb.common.item.ItemNodeFoci;

@SuppressWarnings("deprecation")
public class NodeFociRenderer implements IItemRenderer {
	
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases","models/nodeManipulator/foci.obj"));
	
	@Override
	public boolean handleRenderType(ItemStack item, TransformType type) {
		return true;
	}

	@Override
	public void renderItem(TransformType type, ItemStack item) {
		
		GlStateManager.pushMatrix();
		RenderHelper.disableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();
		
		GlStateManager.scale(0.25, 0.25, 0.25);
		GlStateManager.translate(2, 1, 2);
		DrawUtils.bindTexture("thaumicbases", "textures/blocks/nodeManipulator/foci_"+ItemNodeFoci.names[item.getItemDamage()]+".png");
		model.renderAll();
		
		GlStateManager.popMatrix();
	}

	@Override
	public Matrix4f handleTransformsFor(ItemStack item, TransformType type) {
		if(type == TransformType.THIRD_PERSON)
			return RPAwareModel.THIRD_PERSON_2D;
		return null;
	}

}
