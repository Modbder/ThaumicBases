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

public class BlockWoodSlab extends BlockTBSlab{

	public BlockWoodSlab(boolean fullBlock, Material material) 
	{
		super(material, fullBlock);
		setHardness(2.0F);
		setResistance(1.0F);
		setStepSound(Block.soundTypeWood);
	}
	
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(TBBlocks.woodSlab);
    }

    protected ItemStack createStackedBlock(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(TBBlocks.woodSlab), 2, BlockStateMetadata.getMetaFromState(state) % 7);
    }
	
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
    	return Item.getItemFromBlock(TBBlocks.woodSlab);
    }

	@Override
	public String getUnlocalizedName(int meta) {
		return super.getUnlocalizedName()+BlockTBPlanks.names[meta & 7];
	}
	
    public Icon getIcon(int side, int meta)
    {
    	return IOldCubicBlock.class.cast(TBBlocks.planks).getIcon(side, meta & 7);
    }
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itm, CreativeTabs tab, List lst)
    {
    	for(int i = 0; i < 4; ++i)
    	{
    		lst.add(new ItemStack(itm,1,i));
    	}
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
