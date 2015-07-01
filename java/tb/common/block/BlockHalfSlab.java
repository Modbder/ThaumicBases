package tb.common.block;

import java.util.List;
import java.util.Random;

import tb.init.TBBlocks;
import thaumcraft.api.crafting.IInfusionStabiliser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockHalfSlab extends BlockSlab implements IInfusionStabiliser{
	
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
		super(fullBlock, material);
	}
	
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(TBBlocks.genericSlab);
    }

    protected ItemStack createStackedBlock(int p_149644_1_)
    {
        return new ItemStack(Item.getItemFromBlock(TBBlocks.genericSlab), 2, p_149644_1_ & 7);
    }
	
    @SideOnly(Side.CLIENT)
    public Item getItem(World w, int x, int y, int z)
    {
    	return Item.getItemFromBlock(TBBlocks.genericSlab);
    }

	@Override
	public String func_150002_b(int meta) {
		return super.getUnlocalizedName()+parents[meta].getUnlocalizedName();
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	return parents[meta & 7].getIcon(side, 0);
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
	public boolean canStabaliseInfusion(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z) & 7;
		return parents[meta] instanceof IInfusionStabiliser ? IInfusionStabiliser.class.cast(parents[meta]).canStabaliseInfusion(world, x, y, z) : false;
	}

}
