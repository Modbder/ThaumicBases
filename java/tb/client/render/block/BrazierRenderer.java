package tb.client.render.block;

import org.lwjgl.opengl.GL11;

import thaumcraft.common.config.ConfigBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BrazierRenderer implements ISimpleBlockRenderingHandler{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,RenderBlocks renderer) 
	{
		GL11.glTranslated(0, -0.5D, 0);
		GL11.glPushMatrix();
			
			GL11.glScaled(0.95D, 0.05D, 0.95D);
			renderer.setOverrideBlockTexture(ConfigBlocks.blockCosmeticSolid.getIcon(0, 6));
			renderer.renderBlockAsItem(Blocks.stone, 0, 1);
		
		GL11.glPopMatrix();
	
		GL11.glTranslated(0, 0.05D, 0);
		
		GL11.glPushMatrix();
		
			GL11.glScaled(0.9D, 0.05D, 0.9D);
			renderer.setOverrideBlockTexture(ConfigBlocks.blockCosmeticSolid.getIcon(0, 6));
			renderer.renderBlockAsItem(Blocks.stone, 0, 1);
	
		GL11.glPopMatrix();
		
		GL11.glTranslated(0, 0.05D, 0);
		
		GL11.glPushMatrix();
		
			GL11.glScaled(0.85D, 0.05D, 0.85D);
			renderer.setOverrideBlockTexture(ConfigBlocks.blockCosmeticSolid.getIcon(0, 6));
			renderer.renderBlockAsItem(Blocks.stone, 0, 1);
	
		GL11.glPopMatrix();
		
		GL11.glTranslated(0, 0.20D, 0);
		
		GL11.glPushMatrix();
		
			GL11.glScaled(0.4D, 0.4D, 0.4D);
			renderer.setOverrideBlockTexture(ConfigBlocks.blockCosmeticSolid.getIcon(0, 6));
			renderer.renderBlockAsItem(Blocks.stone, 0, 1);
	
		GL11.glPopMatrix();
		
		GL11.glTranslated(0, 0.3D, 0);
		
		GL11.glPushMatrix();
		
		GL11.glScaled(0.6D, 0.3D, 0.6D);
		renderer.setOverrideBlockTexture(ConfigBlocks.blockCosmeticSolid.getIcon(0, 6));
		renderer.renderBlockAsItem(Blocks.stone, 0, 1);

		GL11.glPopMatrix();
		
		renderer.clearOverrideBlockTexture();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,Block block, int modelId, RenderBlocks renderer) 
	{
		renderer.renderAllFaces = true;
		
		renderer.setOverrideBlockTexture(ConfigBlocks.blockCosmeticSolid.getIcon(0, 6));
		renderer.setRenderBounds(0.05D, 0, 0.05D, 0.95D, 0.05D, 0.95D);
		renderer.renderStandardBlock(Blocks.stone, x, y, z);
		renderer.setRenderBounds(0.1D, 0.05D, 0.1D, 0.9D, 0.1D, 0.9D);
		renderer.renderStandardBlock(Blocks.stone, x, y, z);
		renderer.setRenderBounds(0.15D, 0.1D, 0.15D, 0.85D, 0.15D, 0.85D);
		renderer.renderStandardBlock(Blocks.stone, x, y, z);
		renderer.setRenderBounds(0.3D, 0.15D, 0.3D, 0.7D, 0.6D, 0.7D);
		renderer.renderStandardBlock(Blocks.stone, x, y, z);
		renderer.setRenderBounds(0.2D, 0.55D, 0.2D, 0.8D, 0.61D, 0.8D);
		renderer.renderStandardBlock(Blocks.stone, x, y, z);
		renderer.setRenderBounds(0.2D, 0.61D, 0.2D, 0.3D, 0.85D, 0.8D);
		renderer.renderStandardBlock(Blocks.stone, x, y, z);
		renderer.setRenderBounds(0.7D, 0.61D, 0.2D, 0.8D, 0.85D, 0.8D);
		renderer.renderStandardBlock(Blocks.stone, x, y, z);
		renderer.setRenderBounds(0.3D, 0.61D, 0.2D, 0.7D, 0.85D, 0.3D);
		renderer.renderStandardBlock(Blocks.stone, x, y, z);
		renderer.setRenderBounds(0.3D, 0.61D, 0.7D, 0.7D, 0.85D, 0.8D);
		renderer.renderStandardBlock(Blocks.stone, x, y, z);
		
		renderer.setOverrideBlockTexture(Blocks.coal_block.getIcon(0, 0));
		renderer.setRenderBounds(0.3D, 0.61D, 0.3D, 0.7D, 0.75D, 0.7D);
		renderer.renderStandardBlock(Blocks.stone, x, y, z);
		
		if(world.getBlockMetadata(x, y, z) == 1)
		{
			renderer.setOverrideBlockTexture(Blocks.fire.getIcon(2, 0));
			Tessellator.instance.setBrightness(247);
			Tessellator.instance.setColorOpaque_I(0xffffff);
			renderer.drawCrossedSquares(Blocks.fire.getIcon(2, 0), x, y+0.7D, z, 0.5F);
		}
		
		renderer.renderAllFaces = false;
		renderer.clearOverrideBlockTexture();
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public int getRenderId() 
	{
		return 0x1242fd;
	}

}
