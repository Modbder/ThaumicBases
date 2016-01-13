package tb.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.IOldCubicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tb.init.TBItems;

public class BlockBriar extends BlockBush implements IGrowable,IOldCubicBlock{
    private Icon[] doublePlantBottomIcons;
    private Icon[] doublePlantTopIcons;
    public int growthStages;
    public int growthDelay;
    
    String textureName;
    
    public BlockBriar(int stages, int g)
    {
        super(Material.plants);
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        growthStages = stages;
        growthDelay = g;
    }
    
	public BlockBriar setBlockName(String name)
	{
		this.setUnlocalizedName(name);
		return this;
	}

    public EnumWorldBlockLayer getBlockLayer()
    {
    	return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
	public BlockBriar setBlockTextureName(String tex)
	{
		textureName = tex;
		return this;
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

    
    public void updateTick(World w, BlockPos pos, IBlockState state, Random rnd)
    {
    	super.updateTick(w, pos, state, rnd);
    	
    	if(w.getBlockState(pos).getBlock()==this)
    	{
    		int meta = BlockStateMetadata.getMetaFromState(state);
    		if(isTopBlock(meta))
    		{
    			if(meta-8 < growthStages-1 && w.rand.nextInt(growthDelay) == 0)
    			{
    				w.setBlockState(pos, this.getStateFromMeta(meta+1));
    				w.setBlockState(pos.down(), this.getStateFromMeta(meta-7));
    			}
    		}else
    		{
    			if(meta < growthStages-1 && w.rand.nextInt(growthDelay) == 0)
    			{
    				w.setBlockState(pos, this.getStateFromMeta(meta+1));
    				w.setBlockState(pos.up(), this.getStateFromMeta(meta+9));
    			}
    		}
    	}
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess w, int x, int y, int z)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public boolean canPlaceBlockAt(World w, BlockPos pos)
    {
        return super.canPlaceBlockAt(w, pos) && w.isAirBlock(pos.up());
    }
    
    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {
    	int meta = BlockStateMetadata.getMetaFromState(state);
    	if(isTopBlock(meta))
    	{
    		if(worldIn.getBlockState(pos.down()).getBlock() != this)
    		{
    			dropBlockAsItem(worldIn, pos, state, 0);
    			worldIn.setBlockToAir(pos);
    		}
    	}else
    	{
    		if(worldIn.getBlockState(pos.up()).getBlock() != this)
    		{
    			dropBlockAsItem(worldIn, pos, state, 0);
    			worldIn.setBlockToAir(pos);
    			return;
    		}
    		if(!canBlockStay(worldIn, pos, state))
    		{
    			dropBlockAsItem(worldIn, pos.up(), state, 0);
    			worldIn.setBlockToAir(pos.up());
    			dropBlockAsItem(worldIn, pos, state, 0);
    			worldIn.setBlockToAir(pos);
    		}
    	}
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess w, BlockPos pos, IBlockState state, int fortune)
    {
    	if(w instanceof World)
    	{
	    	World world = World.class.cast(w);
	    	int metadata = BlockStateMetadata.getMetaFromState(state);
	    	ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
	    	
	    	if(!isTopBlock(metadata))
	    	{
	    		ret.add(new ItemStack(this,1,0));
	    		return ret;
	    	}
	    	
			metadata -= 8;
			if(metadata >= this.growthStages - 1)
			{
				ret.add(new ItemStack(TBItems.resource,1+world.rand.nextInt(4),6));
				return ret;
			}
			return ret;
    	}
    	return super.getDrops(w, pos, state, fortune);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public boolean canBlockStay(World w, BlockPos pos, IBlockState state)
    {
    	return !w.isAirBlock(pos) && (w.getBlockState(pos.down()).getBlock().isReplaceableOreGen(w, pos.down(), BlockHelper.forBlock(Blocks.grass)) || w.getBlockState(pos.down()).getBlock().isReplaceableOreGen(w, pos.down(), BlockHelper.forBlock(Blocks.dirt)) || w.getBlockState(pos.down()).getBlock().canSustainPlant(w, pos.down(), EnumFacing.UP, this));
    }
    
    public boolean isTopBlock(int meta)
    {
    	return meta > 7;
    }
    
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
    	worldIn.setBlockState(pos.up(), this.getStateFromMeta(8));
    }
    
    public void registerBlockIcons(IconRegister reg)
    {
    	doublePlantBottomIcons = new Icon[growthStages];
    	doublePlantTopIcons = new Icon[growthStages];
    	
    	for(int i = 0; i < growthStages; ++i)
    	{
    		doublePlantBottomIcons[i] = reg.registerBlockIcon(textureName+"stage_"+i+"_bot");
    		doublePlantTopIcons[i] = reg.registerBlockIcon(textureName+"stage_"+i+"_top");
    	}
    }
    
    @Override
    public Icon getIcon(int side, int meta)
    {
    	return isTopBlock(meta) ? doublePlantTopIcons[meta-8] : doublePlantBottomIcons[meta];
    }

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return getIcon(side,BlockStateMetadata.getBlockMetadata(world, x, y, z));
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		ArrayList<IBlockState> retLst = new ArrayList<IBlockState>();
		for(int i = 0; i < growthStages; ++i)
		{
			retLst.add(getStateFromMeta(i));
			retLst.add(getStateFromMeta(i+8));
		}
		return retLst;
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CROPS;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return worldIn.getLight(pos.up()) >= 9;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return BlockStateMetadata.getMetaFromState(state) % 8 < growthStages-1;
	}

	@Override
	public void grow(World w, Random rand, BlockPos pos, IBlockState state) {
    	if(w.getBlockState(pos).getBlock()==this)
    	{
    		int meta = BlockStateMetadata.getMetaFromState(state);
    		if(isTopBlock(meta))
    		{
    			if(meta-8 < growthStages-1 && w.rand.nextInt(growthDelay) == 0)
    			{
    				w.setBlockState(pos, this.getStateFromMeta(meta+1));
    				w.setBlockState(pos.down(), this.getStateFromMeta(meta-7));
    			}
    		}else
    		{
    			if(meta < growthStages-1 && w.rand.nextInt(growthDelay) == 0)
    			{
    				w.setBlockState(pos, this.getStateFromMeta(meta+1));
    				w.setBlockState(pos.up(), this.getStateFromMeta(meta+9));
    			}
    		}
    	}
	}
}
