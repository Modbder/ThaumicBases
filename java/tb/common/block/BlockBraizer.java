package tb.common.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.IOldCubicBlock;
import DummyCore.Utils.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tb.common.tile.TileBraizer;
import thaumcraft.api.blocks.BlocksTC;

public class BlockBraizer extends BlockContainer implements IOldCubicBlock
{

	public BlockBraizer()
	{
		super(Material.rock);
		setHardness(1F);
		setResistance(1F);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) 
	{
		return new TileBraizer();
	}
	
    public boolean isOpaqueCube()
    {
        return false;
    }
	
	public int getRenderType()
	{
		return 3;
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
    
    public EnumWorldBlockLayer getBlockLayer()
    {
    	return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }

    public void randomDisplayTick(World w, BlockPos pos, IBlockState state, Random r)
    {
    	if(BlockStateMetadata.getMetaFromState(state) > 0)
    	{
    		int x = pos.getX();
    		int y = pos.getY();
    		int z = pos.getZ();
    		
    		w.spawnParticle(EnumParticleTypes.FLAME, x+0.5D+MathUtils.randomDouble(r)/4, y+0.6D, z+0.5D+MathUtils.randomDouble(r)/4, 0, 0.04D, 0);
    		for(int i = 0; i < 2; ++i)
    			w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x+0.5D+MathUtils.randomDouble(r)/4, y+0.7D, z+0.5D+MathUtils.randomDouble(r)/4, 0, r.nextDouble()/20, 0);
    		
    		w.playSound(x+0.5D, y+0.5D, z+0.5D, "thaumicbases:fire.loop", 0.1F, 0.1F, false);
    	}
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
		return Arrays.asList(this.getStateFromMeta(0),this.getStateFromMeta(1));
	}

	@Override
	public void registerBlockIcons(IconRegister ir) {
	}

	@Override
	public int getDCRenderID() {
		return 0x1242fd;
	}
}
