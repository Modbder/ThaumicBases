package tb.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.IOldCubicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tb.init.TBBlocks;
import thaumcraft.api.crafting.IInfusionStabiliser;

public class BlockHalfSlab extends BlockTBSlab{
	
	public static Block[] parents = new Block[]{
		TBBlocks.eldritchArk,
		TBBlocks.oldBrick,
		TBBlocks.oldCobble,
		TBBlocks.oldCobbleMossy,
		TBBlocks.oldDiamond,
		TBBlocks.oldGold,
		TBBlocks.oldIron,
		TBBlocks.oldLapis
	};

	public BlockHalfSlab(boolean fullBlock, Material material) 
	{
		super(material, fullBlock);
	}
	
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(TBBlocks.genericSlab);
    }

    protected ItemStack createStackedBlock(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(TBBlocks.genericSlab), 2, BlockStateMetadata.getMetaFromState(state) & 7);
    }
	
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
    	return Item.getItemFromBlock(TBBlocks.genericSlab);
    }

	@Override
	public String getUnlocalizedName(int meta) {
		return super.getUnlocalizedName()+parents[meta].getUnlocalizedName();
	}
	
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
    	return IOldCubicBlock.class.cast(parents[meta & 7]).getIcon(side, 0);
    }
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itm, CreativeTabs tab, List lst)
    {
    	for(int i = 0; i < parents.length; ++i)
    	{
    		lst.add(new ItemStack(itm,1,i));
    	}
    }

	@Override
	public boolean canStabaliseInfusion(World world, BlockPos pos) {
		int meta = BlockStateMetadata.getBlockMetadata(world, pos) & 7;
		return parents[meta] instanceof IInfusionStabiliser ? IInfusionStabiliser.class.cast(parents[meta]).canStabaliseInfusion(world, pos) : false;
	}

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return this.getIcon(side, BlockStateMetadata.getBlockMetadata(world, x,y,z));
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		ArrayList<IBlockState> retLst = new ArrayList<IBlockState>();
		for(int i = 0; i < 16; ++i)
			retLst.add(getStateFromMeta(i));
		return retLst;
	}

	@Override
	public void registerBlockIcons(IconRegister ir) {
		
	}

}
