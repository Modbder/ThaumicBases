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
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (metadata >= growthStages-1)
        {
            for (int i = 0; i < 1 + fortune; ++i)
            {
                if (world.rand.nextInt(growthStages) <= metadata)
                {
                	if(dropSeed != null)
                		ret.add(dropSeed.copy());
                }
            }
            
            for (int i = 0; i < 1 + fortune; ++i)
            {
                if (world.rand.nextInt(growthStages) <= metadata)
                {
                	if(dropItem != null)
                		ret.add(dropItem.copy());
                }
            }
            if (world.rand.nextInt(16) == 0)
            {
            	ret.add(new ItemStack(Items.golden_carrot,1,0));
            }
        }else
        	if(dropSeed != null)
        		ret.add(dropSeed.copy());

        return ret;
    }
}
