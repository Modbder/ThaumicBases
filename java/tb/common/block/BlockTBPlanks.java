package tb.common.block;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.IOldCubicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTBPlanks extends Block implements IOldCubicBlock
{

	public static final String[] names = new String[]{
		"enderTreePlanks",
		"goldenOakPlanks",
		"peacefullTreePlanks",
		"netherTreePlanks"
	};
	
	public static final String[] textures = new String[]{
		"enderTree/planks",
		"goldenOak/planks",
		"peacefullTree/planks",
		"netherTree/planks"
	};
	
	public Icon[] icons = new Icon[textures.length];
	
	public BlockTBPlanks() 
	{
		super(Material.wood);
		this.setHardness(2);
		this.setResistance(45);
		this.setStepSound(Block.soundTypeWood);
	}
	
    protected ItemStack createStackedBlock(IBlockState state)
    {
    	return new ItemStack(state.getBlock(),1,BlockStateMetadata.getMetaFromState(state));
    }
	
    public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity)
    {
	    if(BlockStateMetadata.getBlockMetadata(world, pos)==0)
	    	if(entity instanceof EntityDragon)
	    		return false;
    	
    	return super.canEntityDestroy(world, pos, entity);
    }
    
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
    	if(BlockStateMetadata.getBlockMetadata(world, pos)==3)
    		return true;
    	
    	return super.isFlammable(world, pos, face);
    }
    
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
    	if(BlockStateMetadata.getBlockMetadata(world, pos)==0)
    		return 0;
    	
    	return super.getFlammability(world, pos, face);
    }
    
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
    	if(BlockStateMetadata.getBlockMetadata(world, pos)==0)
    		return 0;
    	
    	return super.getFlammability(world, pos, face);
    }
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
    	if(BlockStateMetadata.getBlockMetadata(world, pos)==0)
    		return true;
    	
    	return super.isFireSource(world, pos, side);
    }
    
    public Icon getIcon(int side, int meta)
    {
    	return icons[Math.min(meta,icons.length-1)];
    }
    
    public int damageDropped(int meta)
    {
    	return meta;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IconRegister reg)
    {
    	for(int i = 0; i < names.length; ++i)
    		icons[i] = reg.registerBlockIcon("thaumicbases:"+textures[i]);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
    	for(int i = 0; i < names.length; ++i)
    		p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
    }
    
    public IBlockState getStateFromMeta(int meta)
    {
    	return this.getDefaultState().withProperty(BlockStateMetadata.METADATA, BlockStateMetadata.MetadataValues.values()[meta]);
    }
    
    public int getMetaFromState(IBlockState state)
    {
    	return BlockStateMetadata.getMetaFromState(state);
    }

    
    public int damageDropped(IBlockState state)
    {
        return this.damageDropped(BlockStateMetadata.getMetaFromState(state));
    }
    
    protected BlockState createBlockState()
    {
    	return new BlockState(this,BlockStateMetadata.METADATA);
    }

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return getIcon(side,BlockStateMetadata.getBlockMetadata(world, x, y, z));
	}
	
	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		ArrayList<IBlockState> retLst = new ArrayList<IBlockState>();
		for(int i = 0; i < names.length; ++i)
			retLst.add(getStateFromMeta(i));
		
		return retLst;
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CUBE;
	}

}
