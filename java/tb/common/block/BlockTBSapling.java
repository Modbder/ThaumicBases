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
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tb.common.event.WorldGenBigOak;
import tb.common.event.WorldGenOak;
import tb.init.TBBlocks;

public class BlockTBSapling extends BlockBush implements IGrowable,IOldCubicBlock{

	public Icon[] icons = new Icon[8];
	public static final String[] names = new String[]{
		"goldenOakSapling",
		"peacefullTreeSapling",
		"netherTreeSapling",
		"enderTreeSapling"
	};
	
	public static final String[] textures = new String[]{
		"thaumicbases:goldenOak/sapling",
		"thaumicbases:peacefullTree/sapling",
		"thaumicbases:netherTree/sapling",
		"thaumicbases:enderTree/sapling"
	};
	
	public BlockTBSapling()
	{
		super();
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
	}
	
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            super.updateTick(worldIn, pos, state, rand);
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
            {
                this.grow(worldIn, pos, state, rand);
            }
        }
    }
    
    public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (BlockStateMetadata.getBlockMetadata(worldIn, pos) < 8)
            worldIn.setBlockState(pos, this.getStateFromMeta(BlockStateMetadata.getBlockMetadata(worldIn, pos)+8), 4);
        else
            this.generateTree(worldIn, pos, state, rand);
    }
    
    public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    	int meta = BlockStateMetadata.getBlockMetadata(worldIn, pos)%8;
    	if(BlockStateMetadata.getBlockMetadata(worldIn, pos)%8 == 0)
    	{
	    	if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos))
	    		return;
	    	 
	    	worldIn.setBlockToAir(pos);
	    	 
	    	new WorldGenOak(true, 5, 3, 0, false, TBBlocks.genLogs, TBBlocks.genLeaves).generate(worldIn, rand, pos);
	    	return;
    	}
    	if(BlockStateMetadata.getBlockMetadata(worldIn, pos)%8 == 1)
    	{
	    	if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos))
	    		return;
	    	 
	    	worldIn.setBlockToAir(pos);
	    	 
	    	new WorldGenOak(true, 6, 0, 1, false, TBBlocks.genLogs, TBBlocks.genLeaves).generate(worldIn, rand, pos);
	    	
	    	return;
    	}
    	if(BlockStateMetadata.getBlockMetadata(worldIn, pos)%8 == 2)
    	{
    		WorldGenBigOak tree = new WorldGenBigOak(true,6,1,2,1,TBBlocks.genLogs,TBBlocks.genLeaves);
    		
    		worldIn.setBlockToAir(pos);
    		
    		if(!tree.generate(worldIn, rand, pos))
    			worldIn.setBlockState(pos, this.getStateFromMeta(meta), 4);
    		
    		return;
    	}
    	if(BlockStateMetadata.getBlockMetadata(worldIn, pos)%8 == 3)
    	{
    		WorldGenBigOak tree = new WorldGenBigOak(true,4,2,3,3,TBBlocks.genLogs,TBBlocks.genLeaves);
    		
    		worldIn.setBlockToAir(pos);
    		
    		if(!tree.generate(worldIn, rand, pos))
    			worldIn.setBlockState(pos, this.getStateFromMeta(meta), 4);
    		
    		return;
    	}
    }
    
    public int damageDropped(IBlockState state)
    {
        return BlockStateMetadata.getMetaFromState(state) % 8;
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
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.grow(worldIn, pos, state, rand);
	}

	@Override
	public Icon getIcon(int side, int meta) {
		return icons[meta%8];
	}

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return getIcon(side,BlockStateMetadata.getBlockMetadata(world, x, y, z));
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		ArrayList<IBlockState> retLst = new ArrayList<IBlockState>();
		for(int i = 0; i < names.length; ++i)
		{
			retLst.add(getStateFromMeta(i));
			retLst.add(getStateFromMeta(i+8));
		}
		return retLst;
	}

	@Override
	public void registerBlockIcons(IconRegister ir) {
    	for(int i = 0; i < names.length; ++i)
    		icons[i] = ir.registerBlockIcon(textures[i]);
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
    	for(int i = 0; i < names.length; ++i)
    		p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
    }

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CROSS;
	}

}
