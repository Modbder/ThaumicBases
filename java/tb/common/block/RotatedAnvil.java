package tb.common.block;

import java.util.Arrays;
import java.util.List;

import DummyCore.Client.IRotationProvider;
import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.BlockStateMetadata;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RotatedAnvil extends Block implements IRotationProvider {

	public RotatedAnvil() {
		super(Material.iron);
	}

    public IBlockState getStateFromMeta(int meta)
    {
    	return this.getDefaultState().withProperty(BlockStateMetadata.METADATA, meta);
    }
    
    public int getMetaFromState(IBlockState state)
    {
    	return BlockStateMetadata.getMetaFromState(state);
    }
    
    protected BlockState createBlockState()
    {
    	return new BlockState(this,BlockStateMetadata.METADATA);
    }
	
    public boolean isOpaqueCube()
    {
        return false;
    }
    
	@Override
	public Icon getIcon(int side, int meta) {
		return Icon.fromBlock(Blocks.iron_block);
	}

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return this.getIcon(side, 0);
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		return Arrays.asList(this.getStateFromMeta(0),this.getStateFromMeta(1));
	}

	@Override
	public void registerBlockIcons(IconRegister ir) {

	}

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	int meta = BlockStateMetadata.getMetaFromState(state);
    	worldIn.setBlockState(pos, this.getStateFromMeta(meta >= 1 ? 0 : 1));
        return true;
    }
	
	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_ANVIL;
	}

	@Override
	public EnumFacing getRotation(IBlockAccess world, int x, int y, int z, IBlockState state) {
		return BlockStateMetadata.getMetaFromState(state) == 0 ? EnumFacing.NORTH : EnumFacing.WEST;
	}

}
