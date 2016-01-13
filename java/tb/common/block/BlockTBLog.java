package tb.common.block;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.IOldCubicBlock;
import DummyCore.Utils.MetadataBasedMethodsHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTBLog extends Block implements IOldCubicBlock{

	public static final String[] names = new String[]{
		"peacefullTreeLog",
		"netherTreeLog",
		"enderTreeLog",
		"goldenTreeLog"
	};
		
	public static final String[] textures = new String[]{
		"peacefullTree/log",
		"netherTree/log",
		"enderTree/log",
		"goldenOak/log"
	};
	
	@Override
    public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity)
    {
	    if(BlockStateMetadata.getBlockMetadata(world, pos)%4==2)
	    	if(entity instanceof EntityDragon)
	    		return false;
    	
    	return super.canEntityDestroy(world, pos, entity);
    }
	
	@Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
    	if(BlockStateMetadata.getBlockMetadata(world, pos)%4==1)
    		return true;
    	
    	return super.isFlammable(world, pos, face);
    }
	
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
    	if(BlockStateMetadata.getBlockMetadata(world, pos)%4==1)
    		return 0;
    	
    	return super.getFlammability(world, pos, face);
    }
    
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
    	if(BlockStateMetadata.getBlockMetadata(world, pos)%4==1)
    		return 0;
    	
    	return super.getFlammability(world, pos, face);
    }
    
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
    	if(BlockStateMetadata.getBlockMetadata(world, pos)%4==1)
    		return true;
    	
    	return super.isFireSource(world, pos, side);
    }
    
    protected ItemStack createStackedBlock(IBlockState state)
    {
    	return new ItemStack(state.getBlock(),1,BlockStateMetadata.getMetaFromState(state) % 4);
    }
    
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
		MetadataBasedMethodsHelper.breakLog(worldIn, pos, state);
    }
	
    @Override 
    public boolean canSustainLeaves(IBlockAccess world, BlockPos pos)
    { 
    	return true; 
    }
    
    @Override 
    public boolean isWood(IBlockAccess world, BlockPos pos)
    {
    	return true; 
    }
    
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis)
    {
        IBlockState state = world.getBlockState(pos);
        int meta = BlockStateMetadata.getMetaFromState(state);
        int mv = getMetaFromAxis(axis.getAxis(),meta % 4);
        world.setBlockState(pos, getStateFromMeta(mv));
        return false;
    }
    
    public int damageDropped(IBlockState state)
    {
    	return BlockStateMetadata.getMetaFromState(state) % 4;
    }
    
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(BlockStateMetadata.METADATA, getMetaFromAxis(facing.getAxis(),meta));
    }
    
    public int getMetaFromAxis(EnumFacing.Axis axis, int originalMeta)
    {
    	int addedMeta = 0;
    	switch(axis)
    	{
	    	case Y:
	    	{
	    		addedMeta = 0;
	    		break;
	    	}
	    	case X:
	    	{
	    		addedMeta = 1;
	    		break;
	    	}
	    	case Z:
	    	{
	    		addedMeta = 2;
	    		break;
	    	}
    	}
    	
    	return originalMeta+addedMeta*4;
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
	
	public Icon[][] bIcons = new Icon[names.length][2];
	
	public BlockTBLog() {
		super(Material.wood);
		this.setHarvestLevel("axe", 0);
        this.setHardness(2.0F);
        this.setStepSound(soundTypeWood);
	}

	@Override
	public Icon getIcon(int side, int meta) {
		int subMeta = meta / 4;
		int aIndex = 0;
		if(subMeta == 0 && (side == 1 || side == 0))
			aIndex = 1;
		if(subMeta == 1 && (side == 4 || side == 5))
			aIndex = 1;
		if(subMeta == 2 && (side == 2 || side == 3))
			aIndex = 1;
		return bIcons[meta % 4][aIndex];
	}

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return getIcon(side,BlockStateMetadata.getBlockMetadata(world, x, y, z));
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		ArrayList<IBlockState> retLst = new ArrayList<IBlockState>();
		for(int i = 0; i < names.length*3; ++i)
			retLst.add(getStateFromMeta(i));
		return retLst;
	}

	@Override
	public void registerBlockIcons(IconRegister ir) {
		for(int i = 0; i < names.length; ++i)
			for(int j = 0; j < 2; ++j)
				bIcons[i][j] = ir.registerBlockIcon("thaumicbases",textures[i]+(j == 0 ? "" : "_top"));
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(Item itm, CreativeTabs tab, List lst)
    {
    	for(int i = 0; i < names.length; ++i)
    		lst.add(new ItemStack(itm,1,i));
    }

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CUBE;
	}

}
