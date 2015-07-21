package tb.common.block;

import java.util.ArrayList;
import java.util.Random;

import tb.init.TBBlocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockLucritePlant extends BlockTBPlant {

	public BlockLucritePlant(int stages, int delay, boolean isCrop) {
		super(stages, delay, isCrop);
	}
	
	//growPlant
	@Override
	public void func_149853_b(World w, Random r,int x, int y, int z)
	{
		int meta = w.getBlockMetadata(x, y, z);
		w.setBlockMetadataWithNotify(x, y, z, Math.min(growthStages,meta+1), 3);
	}

	@Override
    public boolean canBlockStay(World w, int x, int y, int z)
    {
        return super.canBlockStay(w, x, y, z) && w.getBlock(x, y-2, z) == TBBlocks.dustBlock;
    }
	
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);

        if (metadata >= growthStages-1)
        {
            if (world.rand.nextInt(16) == 0)
            {
            	ret.add(new ItemStack(Items.golden_carrot,1,0));
            }
        }
        
        return ret;
    }
}
