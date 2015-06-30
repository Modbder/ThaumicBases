package tb.common.block;

import java.util.List;
import java.util.Random;

import tb.common.event.WorldGenOak;
import tb.init.TBBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockGoldenOakSapling extends BlockSapling{

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
    	return this.blockIcon;
    }
    
    //doGrowTree
    public void func_149878_d(World w, int x, int y, int z, Random rnd)
    {
    	
    	 if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(w, rnd, x, y, z))
    		 return;
    	 
    	 w.setBlockToAir(x, y, z);
    	 
    	 new WorldGenOak(true, 5, 0, 0, false, Blocks.log, TBBlocks.genLeaves).generate(w, rnd, x, y, z);
    }
    
    public int damageDropped(int meta)
    {
    	return 0;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
    	blockIcon = reg.registerIcon(getTextureName());
    }
    
    
	
}
