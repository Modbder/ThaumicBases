package tb.common.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.IOldCubicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.aura.AuraHelper;

public class BlockFlaxium extends BlockBush implements IOldCubicBlock{

	public BlockFlaxium() {
		super(Material.plants);
		this.setTickRandomly(true);
		this.setBlockBounds(0.25F, 0, 0.25F, 0.75F, 0.85F, 0.75F);
	}
	
	Icon icon;
	String texture;
	
	public BlockFlaxium setBlockName(String name)
	{
		this.setUnlocalizedName(name);
		return this;
	}

    public EnumWorldBlockLayer getBlockLayer()
    {
    	return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
	public BlockFlaxium setBlockTextureName(String tex)
	{
		texture = tex;
		return this;
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return icon;
	}

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return getIcon(side,this.getMetaFromState(world.getBlockState(new BlockPos(x,y,z))));
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		return Arrays.asList(this.getDefaultState());
	}
	
	@Override
	public void registerBlockIcons(IconRegister ir) {
		icon = ir.registerBlockIcon(texture);
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CROSSES;
	}
	
	public void updateTick(World w, BlockPos pos, IBlockState state, Random rnd)
	{
		super.updateTick(w, pos, state, rnd);
		if(w.isAirBlock(pos.up()))
			AuraHelper.pollute(w, pos, 1, true);
		
		if(!canGrowOn(w,pos.down()))
			w.setBlockToAir(pos);
	}
	
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		if(!canGrowOn(worldIn,pos.down()))
			worldIn.setBlockToAir(pos);
	}
	
    @Override
    public AxisAlignedBB getCollisionBoundingBox(World w, BlockPos pos,IBlockState state)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }
    
	@SuppressWarnings("unchecked")
	public static boolean canGrowOn(World w, BlockPos pos)
	{
		return !w.isAirBlock(pos) && (w.getBlockState(pos).getBlock().isReplaceableOreGen(w, pos,BlockHelper.forBlock(Blocks.grass)) || w.getBlockState(pos).getBlock().isReplaceableOreGen(w, pos, BlockHelper.forBlock(Blocks.dirt)));
	}

}
