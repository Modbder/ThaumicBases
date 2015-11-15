package tb.common.block;

import java.util.Arrays;
import java.util.List;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.IOldCubicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tb.common.tile.TileAuraLinker;
import thaumcraft.api.blocks.BlocksTC;

public class BlockAuraLinker extends BlockContainer implements IOldCubicBlock{
	
	public BlockAuraLinker()
	{
		super(Material.rock);
	}
	
    public boolean isOpaqueCube()
    {
        return false;
    }
    
	public int getRenderType()
	{
		return 3;
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileAuraLinker();
	}

	@Override
	public Icon getIcon(int side, int meta) {
		return Icon.fromBlock(BlocksTC.stone);
	}

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return Icon.fromBlock(BlocksTC.stone);
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		return Arrays.asList(getDefaultState());
	}

	@Override
	public void registerBlockIcons(IconRegister ir) {
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_NONE;
	}
}
