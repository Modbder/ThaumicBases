package tb.common.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSweed extends BlockTBPlant {

	public BlockSweed(int stages, int delay, boolean isCrop) {
		super(stages, delay, isCrop);
	}

    protected boolean canPlaceBlockOn(Block b)
    {
    	return b != null && (b == Blocks.grass || b == Blocks.dirt || b instanceof BlockGrass || b instanceof BlockDirt);
    }
    
    public void updateTick(World w, int x, int y, int z, Random rnd)
    {
        super.updateTick(w, x, y, z, rnd);
        if(w.getBlockMetadata(x, y, z) == growthStages-1 && !w.isRemote)
        {
        	ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[2+w.rand.nextInt(4)];
        	int newX = x+dir.offsetX;
        	int newZ = z+dir.offsetZ;
        	int newY = findSutableY(w,newX,y,newZ);
        	if(canPlaceBlockOn(w.getBlock(newX, newY-1, newZ)) && w.isAirBlock(newX, newY, newZ)) //fix for the Sweeds destroying blocks
        		w.setBlock(newX, newY, newZ, this, 0, 3);
        }
    }
    
    public int findSutableY(World w, int x, int y, int z)
    {
    	int bY = y;
    	y += 1;
    	while(!canPlaceBlockOn(w.getBlock(x, y, z)) && y > bY - 2)
    		--y;
    	
    	return y+1;
    }
    
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (metadata >= growthStages-1)
        {
            for (int i = 0; i < 1 + fortune; ++i)
                if (world.rand.nextInt(growthStages) <= metadata)
                	if(dropSeed != null)
                		ret.add(dropSeed.copy());
            
            for (int i = 0; i < 3 + fortune; ++i)
                if (world.rand.nextBoolean())
                	ret.add(new ItemStack(Items.sugar));

            if (world.rand.nextBoolean())
                ret.add(new ItemStack(Items.reeds));
            
        }else
        	if(dropSeed != null)
        		ret.add(dropSeed.copy());

        return ret;
    }
}
