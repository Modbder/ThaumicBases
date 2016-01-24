package tb.client.render.tile;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IModelCustom;
import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import tb.common.item.ItemNodeFoci;

@SuppressWarnings("rawtypes")
public class RenderNodeManipulator extends TileEntitySpecialRenderer{
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases","models/nodeManipulator/nodeManipulator.obj"));
	public static final ResourceLocation genIcon = new ResourceLocation("thaumicbases","textures/blocks/nodeManipulator/baseUVMap.png");
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double screenX,	double screenY, double screenZ, float partialTicks, int i)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(screenX+0.5D, screenY, screenZ+0.5D);
		GlStateManager.scale(0.5D, 0.5D, 0.5D);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(genIcon);
		model.renderPart("base_Cube.001");
		model.renderPart("handle_0_Cube.002");
		model.renderPart("handle_1_Cube.002");
		model.renderPart("handle_2_Cube.002");
		model.renderPart("handle_3_Cube.002");
		
		int meta = BlockStateMetadata.getBlockMetadata(tile.getWorld(), tile.getPos());
		if(meta != 0)
		{
			DrawUtils.bindTexture("thaumicbases", "textures/blocks/nodeManipulator/foci_"+ItemNodeFoci.names[meta-1]+".png");
			model.renderPart("foci_Cube.003");
		}
		
		GlStateManager.popMatrix();
	}
}
