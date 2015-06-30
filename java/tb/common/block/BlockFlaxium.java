package tb.common.block;

import java.util.Random;

import thaumcraft.common.config.ConfigBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockFlaxium extends BlockBush{

	public BlockFlaxium() {
		super(Material.plants);
		this.setTickRandomly(true);
		this.setBlockBounds(0.25F, 0, 0.25F, 0.75F, 0.85F, 0.75F);
	}
	
	public void updateTick(World w, int x, int y, int z, Random rnd) 
	{
		if(w.isAirBlock(x, y+1, z))
			w.setBlock(x, y+1, z, ConfigBlocks.blockFluxGas, 0, 3);
		
		if(!canGrowOn(w,x,y-1,z))
			w.setBlockToAir(x, y, z);
	}
	
	public void onNeighborBlockChange(World w, int x, int y, int z, Block updated) 
	{
		if(!canGrowOn(w,x,y-1,z))
			w.setBlockToAir(x, y, z);
	}
	
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return 1;
    }
    
	public static boolean canGrowOn(World w, int x, int y, int z)
	{
		return !w.isAirBlock(x, y, z) && (w.getBlock(x, y, z).isReplaceableOreGen(w, x, y, z, Blocks.grass) || w.getBlock(x, y, z).isReplaceableOreGen(w, x, y, z, Blocks.dirt));
	}

}
