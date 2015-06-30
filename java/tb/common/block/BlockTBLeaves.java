package tb.common.block;

import java.util.List;
import java.util.Random;

import tb.core.TBCore;
import tb.init.TBBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTBLeaves extends BlockOldLeaf{
	
	public static final String[] names = new String[]{
		"goldenOakLeaves",
		"richBirchLeaves"
	};
	
	public static final String[] textures = new String[]{
		"goldenOak/leaves",
		"richBirch/leaves"
		
	};
	
	public static IIcon[] icons = new IIcon[names.length];

    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
    	return 0xffffff;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int meta)
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World w, int x, int y, int z, Random rnd)
    {
    	super.randomDisplayTick(w, x, y, z, rnd);
    	
    	if(w.getBlockMetadata(x, y, z) == 0)
    		w.spawnParticle("reddust", x+rnd.nextDouble(), y+rnd.nextDouble(), z+rnd.nextDouble(), 1, 1, 0);
    }
    
    public Item getItemDropped(int meta, Random rnd, int fortune)
    {
        return Item.getItemFromBlock(TBBlocks.goldenOakSapling);
    }
	
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int p_149741_1_)
    {
    	return 0xffffff;
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
    {
    	return 0xffffff;
    }
    
    //getSaplingDropRate
    protected int func_150123_b(int meta)
    {
        return meta == 0 ? 50 : 30;
    }
    
    //dropRareItem
    protected void func_150124_c(World w, int x, int y, int z, int meta, int chance)
    {
        if (meta == 0 && w.rand.nextInt(chance) == 0)
        {
            this.dropBlockAsItem(w, x, y, z, new ItemStack(Items.golden_apple, 1, 0));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	return icons[meta % 8];
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item i, CreativeTabs p_149666_2_, List p_149666_3_)
    {
    	for(int f = 0; f < names.length; ++f)
    		p_149666_3_.add(new ItemStack(i,1,f));
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
    	for(int i = 0; i < icons.length; ++i)
    		icons[i] = reg.registerIcon(TBCore.modid+":"+textures[i]);
    	
    	blockIcon = reg.registerIcon(getTextureName());
    }
    
    
}
