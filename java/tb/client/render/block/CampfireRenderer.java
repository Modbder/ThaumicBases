package tb.client.render.block;

import java.util.Random;

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
import tb.common.tile.TileCampfire;
import thaumcraft.api.blocks.BlocksTC;

public class CampfireRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderWorldBlock(IBlockAccess world, Block b, BlockPos pos, DynamicModelBakery bakery,
			SBRHAwareModel model) {

		bakery.forceRenderAllFaces();
		
		bakery.setOverrideBlockTexture(Icon.fromBlock(Blocks.cobblestone));
		
		Random rand = new Random(pos.getX()+pos.getY()+pos.getZ());
		
		for(int i = 0; i < 6+rand.nextInt(5); ++i)
		{
			double dx = rand.nextDouble();
			double dy = 0;
			double dz = rand.nextDouble();
			
			bakery.setRenderBounds(dx, dy, dz, dx+0.1D, dy+0.1D, dz+0.1D);
			bakery.addCube();
		}
		
		bakery.setOverrideBlockTexture(Icon.fromBlock(Blocks.coal_block));
		
		bakery.setRenderBounds(0.25D, 0, 0.25D, 0.75D, 0.06D, 0.75D);
		bakery.addCube();
		
		TileCampfire tc = (TileCampfire) world.getTileEntity(pos);
		if(tc.tainted)
			bakery.setOverrideBlockTexture(Icon.fromBlock(BlocksTC.taintLog, 0));
		else
			bakery.setOverrideBlockTexture(Icon.fromBlock(BlocksTC.log, 0));
		double yIndex = 0;
		if(BlockStateMetadata.getBlockMetadata(world, pos) > 0)
			for(int i = 0; i < 2; ++i)
			{
				bakery.setRenderBounds(0.2D, 0+yIndex+(0.1D*i), 0.1D, 0.3D, 0.11D+yIndex+(0.1D*i), 0.9D);
				bakery.addCube();
				bakery.setRenderBounds(0.7D, 0+yIndex+(0.1D*i), 0.1D, 0.8D, 0.11D+yIndex+(0.1D*i), 0.9D);
				bakery.addCube();
				
				bakery.setRenderBounds(0.1D, 0.1D+yIndex+(0.1D*i), 0.2D, 0.9D, 0.21D+yIndex+(0.1D*i), 0.3D);
				bakery.addCube();
				bakery.setRenderBounds(0.1D, 0.1D+yIndex+(0.1D*i), 0.7D, 0.9D, 0.21D+yIndex+(0.1D*i), 0.8D);
				bakery.addCube();
				yIndex += 0.1D;
			}
		
		bakery.setOverrideBlockTexture(Icon.fromBlock(Blocks.gravel));
		bakery.setRenderBounds(0.05D, 0, 0.05D, 0.95D, 0.03D, 0.95D);
		bakery.addCube();
		
		if(BlockStateMetadata.getBlockMetadata(world, pos) > 1)
		{
			bakery.setRenderBounds(0.2D, 0, 0.2D, 0.8D, 0.7D, 0.8D);
			if(tc.tainted)
				bakery.setOverrideBlockColor(0xffff0044);
			bakery.setOverrideBlockTexture(Icon.fromBlock(Blocks.fire));
			bakery.addCrossedSquares();
		}
		
		bakery.clearOverrideBlockColor();
		bakery.disableRenderAllFaces();
		bakery.clearOverrideBlockTexture();
		bakery.clearRenderBounds();
	}

	@Override
	public void renderInventoryBlock(ItemStack stack, DynamicModelBakery bakery, SBRHAwareModel model) {
		bakery.addCrossedSquares();
	}

	@Override
	public int getRenderID() {
		return 0x1242fe;
	}

	@Override
	public boolean render3DInInventory() {
		return false;
	}

}
