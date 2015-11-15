package tb.common.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.IOldCubicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tb.init.TBBlocks;

public class BlockAureliaPlant extends Block implements IOldCubicBlock{

	String textureName;
	
	public BlockAureliaPlant() 
	{
		super(Material.plants);
		this.setTickRandomly(true);
		this.setBlockBounds(0.25F, 0, 0.25F, 0.75F, 0.85F, 0.75F);
		this.setLightLevel(0.5F);
	}
	
	public BlockAureliaPlant setBlockName(String name)
	{
		this.setUnlocalizedName(name);
		return this;
	}

    public EnumWorldBlockLayer getBlockLayer()
    {
    	return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
	public BlockAureliaPlant setBlockTextureName(String tex)
	{
		textureName = tex;
		return this;
	}
	
	public static Icon[] icons = new Icon[2];
	
    public void registerBlockIcons(IconRegister reg)
    {
    	icons[0] = reg.registerBlockIcon(textureName+"closed");
    	icons[1] = reg.registerBlockIcon(textureName+"open");
    }
    
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
    	return icons[meta];
    }
	
    public void updateTick(World w, BlockPos pos, IBlockState state, Random rnd)
	{
		checkForMoonlight(w,pos);
		if(BlockStateMetadata.getMetaFromState(state) == 1)
		{
			int rndX = pos.getX() + rnd.nextInt(8) - rnd.nextInt(8);
			int rndZ = pos.getZ() + rnd.nextInt(8) - rnd.nextInt(8);
			int rndY = findSutableGround(w,rndX,pos.getY()+2,rndZ)+1;
			if(rndY > 2)
				w.setBlockState(new BlockPos(rndX, rndY, rndZ), TBBlocks.aureliaPetal.getDefaultState());
		}
	}
	
	public int findSutableGround(World w, int x, int y, int z)
	{
		while(w.isAirBlock(new BlockPos(x,y,z)) && y > 0 && !w.isSideSolid(new BlockPos(x, y, z), EnumFacing.UP))
			--y;
		
		return y;
	}
	
    public int tickRate(World w)
    {
    	return 10;
    }
	
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		if(!canGrowOn(worldIn,pos.down()))
			worldIn.setBlockToAir(pos);
		else
			checkForMoonlight(worldIn,pos);
		
	}
	
    public int damageDropped(int meta)
    {
    	return 0;
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

    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
	public void checkForMoonlight(World w, BlockPos pos)
	{
		boolean underSky = w.canBlockSeeSky(pos.up());
		boolean night = !w.isDaytime();
		boolean isFullMoon = w.provider.getMoonPhase(w.getWorldTime()) == 0;
		boolean isOpen = BlockStateMetadata.getBlockMetadata(w, pos) == 1;
		
		if(underSky && night && isFullMoon)
		{
			if(!isOpen)
			{
				w.setBlockState(pos, getStateFromMeta(1));
				w.markBlockRangeForRenderUpdate(pos.down().west().north(), pos.up().east().south());
			}
		}else
		{
			if(isOpen)
			{
				w.setBlockState(pos, getStateFromMeta(0));
				w.markBlockRangeForRenderUpdate(pos.down().west().north(), pos.up().east().south());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static boolean canGrowOn(World w, BlockPos pos)
	{
		return !w.isAirBlock(pos) && (w.getBlockState(pos).getBlock().isReplaceableOreGen(w, pos,BlockHelper.forBlock(Blocks.grass)) || w.getBlockState(pos).getBlock().isReplaceableOreGen(w, pos, BlockHelper.forBlock(Blocks.dirt)));
	}

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return this.getIcon(side, BlockStateMetadata.getBlockMetadata(world, x, y, z));
	}
	
    public IBlockState getStateFromMeta(int meta)
    {
    	return this.getDefaultState().withProperty(BlockStateMetadata.METADATA, BlockStateMetadata.MetadataValues.values()[meta]);
    }
    
    public int getMetaFromState(IBlockState state)
    {
    	return BlockStateMetadata.getMetaFromState(state);
    }

    protected BlockState createBlockState()
    {
    	return new BlockState(this,BlockStateMetadata.METADATA);
    }

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		return Arrays.asList(getStateFromMeta(0),getStateFromMeta(1));
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CROSSES;
	}
}
