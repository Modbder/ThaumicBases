package tb.client.render.block;

import DummyCore.Client.DynamicModelBakery;
import DummyCore.Client.ISimpleBlockRenderingHandler;
import DummyCore.Client.Icon;
import DummyCore.Client.SBRHAwareModel;
import DummyCore.Utils.BlockStateMetadata;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BraizerRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderWorldBlock(IBlockAccess world, Block b, BlockPos pos, DynamicModelBakery bakery,	SBRHAwareModel model) {
		bakery.forceRenderAllFaces();
		
		bakery.setRenderBounds(0.05D, 0, 0.05D, 0.95D, 0.05D, 0.95D);
		bakery.addCube();
		bakery.setRenderBounds(0.1D, 0.05D, 0.1D, 0.9D, 0.1D, 0.9D);
		bakery.addCube();
		bakery.setRenderBounds(0.15D, 0.1D, 0.15D, 0.85D, 0.15D, 0.85D);
		bakery.addCube();
		bakery.setRenderBounds(0.3D, 0.15D, 0.3D, 0.7D, 0.6D, 0.7D);
		bakery.addCube();
		bakery.setRenderBounds(0.2D, 0.55D, 0.2D, 0.8D, 0.61D, 0.8D);
		bakery.addCube();
		bakery.setRenderBounds(0.2D, 0.61D, 0.2D, 0.3D, 0.85D, 0.8D);
		bakery.addCube();
		bakery.setRenderBounds(0.7D, 0.61D, 0.2D, 0.8D, 0.85D, 0.8D);
		bakery.addCube();
		bakery.setRenderBounds(0.3D, 0.61D, 0.2D, 0.7D, 0.85D, 0.3D);
		bakery.addCube();
		bakery.setRenderBounds(0.3D, 0.61D, 0.7D, 0.7D, 0.85D, 0.8D);
		bakery.addCube();
		
		bakery.setOverrideBlockTexture(Icon.fromBlock(Blocks.coal_block));
		bakery.setRenderBounds(0.3D, 0.61D, 0.3D, 0.7D, 0.75D, 0.7D);
		bakery.addCube();
		
		if(BlockStateMetadata.getBlockMetadata(world, pos) > 0)
		{
			bakery.setRenderBounds(0.3D, 0.8, 0.3D, 0.7D, 1.1D, 0.7D);
			bakery.setOverrideBlockTexture(Icon.fromBlock(Blocks.fire));
			bakery.addCrossedSquares();
		}
		
		
		bakery.clearOverrideBlockTexture();
		bakery.disableRenderAllFaces();
	}

	@Override
	public void renderInventoryBlock(ItemStack stack, DynamicModelBakery bakery, SBRHAwareModel model) {
		
		bakery.forceRenderAllFaces();
		
		bakery.setRenderBounds(0.05D, 0, 0.05D, 0.95D, 0.05D, 0.95D);
		bakery.addCube();
		bakery.setRenderBounds(0.1D, 0.05D, 0.1D, 0.9D, 0.1D, 0.9D);
		bakery.addCube();
		bakery.setRenderBounds(0.15D, 0.1D, 0.15D, 0.85D, 0.15D, 0.85D);
		bakery.addCube();
		bakery.setRenderBounds(0.3D, 0.15D, 0.3D, 0.7D, 0.6D, 0.7D);
		bakery.addCube();
		bakery.setRenderBounds(0.2D, 0.55D, 0.2D, 0.8D, 0.61D, 0.8D);
		bakery.addCube();
		bakery.setRenderBounds(0.2D, 0.61D, 0.2D, 0.3D, 0.85D, 0.8D);
		bakery.addCube();
		bakery.setRenderBounds(0.7D, 0.61D, 0.2D, 0.8D, 0.85D, 0.8D);
		bakery.addCube();
		bakery.setRenderBounds(0.3D, 0.61D, 0.2D, 0.7D, 0.85D, 0.3D);
		bakery.addCube();
		bakery.setRenderBounds(0.3D, 0.61D, 0.7D, 0.7D, 0.85D, 0.8D);
		bakery.addCube();
		
		bakery.setOverrideBlockTexture(Icon.fromBlock(Blocks.coal_block));
		bakery.setRenderBounds(0.3D, 0.61D, 0.3D, 0.7D, 0.75D, 0.7D);
		bakery.addCube();
		
		bakery.clearOverrideBlockTexture();
		bakery.disableRenderAllFaces();
	}

	@Override
	public int getRenderID() {
		return 0x1242fd;
	}

	@Override
	public boolean render3DInInventory() {
		return true;
	}

}
