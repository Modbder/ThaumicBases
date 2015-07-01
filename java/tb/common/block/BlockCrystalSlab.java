package tb.common.block;

import java.util.List;
import java.util.Random;

import tb.init.TBBlocks;
import thaumcraft.api.crafting.IInfusionStabiliser;
import thaumcraft.common.blocks.CustomStepSound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCrystalSlab extends BlockSlab implements IInfusionStabiliser{

	public BlockCrystalSlab(boolean fullBlock, Material material) 
	{
		super(fullBlock, material);
		setHardness(0.7F);
		setResistance(1.0F);
		setLightLevel(0.5F);
		setStepSound(new CustomStepSound("crystal", 1.0F, 1.0F));
	}
	
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(TBBlocks.crystalSlab);
    }

    protected ItemStack createStackedBlock(int p_149644_1_)
    {
        return new ItemStack(Item.getItemFromBlock(TBBlocks.crystalSlab), 2, p_149644_1_ & 7);
    }
	
    @SideOnly(Side.CLIENT)
    public Item getItem(World w, int x, int y, int z)
    {
    	return Item.getItemFromBlock(TBBlocks.crystalSlab);
    }

	@Override
	public String func_150002_b(int meta) {
		return super.getUnlocalizedName()+BlockCrystalBlock.names[meta & 7];
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	return TBBlocks.crystalBlock.getIcon(side, meta & 7);
    }
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itm, CreativeTabs tab, List lst)
    {
    	for(int i = 0; i < 8; ++i)
    	{
    		lst.add(new ItemStack(itm,1,i));
    	}
    }

	@Override
	public boolean canStabaliseInfusion(World world, int x, int y, int z) {
		return true;
	}

}
