package tb.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DummyCore.Utils.BlockStateMetadata;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSweed extends BlockTBPlant {

	public BlockSweed(int stages, int delay, boolean isCrop) {
		super(stages, delay, isCrop);
	}

    protected boolean canPlaceBlockOn(Block b)
    {
    	return b != null && (b == Blocks.grass || b == Blocks.dirt || b instanceof BlockGrass || b instanceof BlockDirt);
    }
    
    public void updateTick(World w, BlockPos pos, IBlockState state, Random rnd)
    {
        super.updateTick(w,pos, state, rnd);
        if(BlockStateMetadata.getMetaFromState(state) == growthStages-1 && !w.isRemote)
        {
        	EnumFacing dir = EnumFacing.getFront(2+w.rand.nextInt(4));
        	
        	int newX = pos.getX()+dir.getFrontOffsetX();
        	int newZ = pos.getZ()+dir.getFrontOffsetY();
        	int newY = findSutableY(w,newX,pos.getY(),newZ);
        	if(canPlaceBlockOn(w.getBlockState(new BlockPos(newX, newY-1, newZ)).getBlock()) && w.isAirBlock(new BlockPos(newX, newY, newZ))) //fix for the Sweeds destroying blocks
        		w.setBlockState(new BlockPos(newX, newY, newZ), this.getDefaultState());
        }
    }
    
    public int findSutableY(World w, int x, int y, int z)
    {
    	int bY = y;
    	y += 1;
    	while(!canPlaceBlockOn(w.getBlockState(new BlockPos(x, y, z)).getBlock()) && y > bY - 2)
    		--y;
    	
    	return y+1;
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess w, BlockPos pos, IBlockState state, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if(w instanceof World)
        {
        	World world = World.class.cast(w);
        	int metadata = BlockStateMetadata.getMetaFromState(state);
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
        }
        return ret;
    }
}
