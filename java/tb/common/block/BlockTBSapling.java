package tb.common.block;

import java.util.List;
import java.util.Random;

import tb.common.event.WorldGenBigOak;
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

public class BlockTBSapling extends BlockSapling{

	public IIcon[] icons = new IIcon[8];
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
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	return icons[meta%8];
    }
    
    //doGrowTree
    public void func_149878_d(World w, int x, int y, int z, Random rnd)
    {
    	int meta = w.getBlockMetadata(x, y, z)%8;
    	if(w.getBlockMetadata(x, y, z)%8 == 0)
    	{
	    	if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(w, rnd, x, y, z))
	    		return;
	    	 
	    	w.setBlockToAir(x, y, z);
	    	 
	    	new WorldGenOak(true, 5, 0, 0, false, Blocks.log, TBBlocks.genLeaves).generate(w, rnd, x, y, z);
    	}
    	if(w.getBlockMetadata(x, y, z)%8 == 1)
    	{
	    	if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(w, rnd, x, y, z))
	    		return;
	    	 
	    	w.setBlockToAir(x, y, z);
	    	 
	    	new WorldGenOak(true, 6, 0, 1, false, TBBlocks.genLogs, TBBlocks.genLeaves).generate(w, rnd, x, y, z);
    	}
    	if(w.getBlockMetadata(x, y, z)%8 == 2)
    	{
    		WorldGenBigOak tree = new WorldGenBigOak(true,6,1,2,1,TBBlocks.genLogs,TBBlocks.genLeaves);
    		
    		w.setBlockToAir(x, y, z);
    		
    		if(!tree.generate(w, rnd, x, y, z))
    			w.setBlock(x, y, z, this, meta, 4);
    	}
    	if(w.getBlockMetadata(x, y, z)%8 == 3)
    	{
    		WorldGenBigOak tree = new WorldGenBigOak(true,4,2,3,3,TBBlocks.genLogs,TBBlocks.genLeaves);
    		
    		w.setBlockToAir(x, y, z);
    		
    		if(!tree.generate(w, rnd, x, y, z))
    			w.setBlock(x, y, z, this, meta, 4);
    	}
    }
    
    public int damageDropped(int meta)
    {
    	return meta%8;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
    	for(int i = 0; i < names.length; ++i)
    		p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
    	for(int i = 0; i < names.length; ++i)
    		icons[i] = reg.registerIcon(textures[i]);
    }
    
    
	
}
