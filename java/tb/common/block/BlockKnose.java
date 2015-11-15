package tb.common.block;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Utils.BlockStateMetadata;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tb.init.TBBlocks;
import tb.init.TBItems;

public class BlockKnose extends BlockTBPlant {

	public BlockKnose(int stages, int delay, boolean isCrop) {
		super(stages, delay, isCrop);
	}
	
    public boolean canBlockStay(World w, BlockPos pos, IBlockState state)
    {
    	return !w.isAirBlock(pos) && canPlaceBlockOn(w.getBlockState(pos.down()).getBlock());
    }

    protected boolean canPlaceBlockOn(Block b)
    {
    	return b == TBBlocks.crystalBlock;
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess w, BlockPos pos, IBlockState state, int fortune)
    {
    	 ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
    	 int metadata = BlockStateMetadata.getMetaFromState(state);
    	 
         if (metadata >= growthStages-1)
         {
        	 if(dropSeed != null)
        		 ret.add(dropSeed.copy());
        	 Block b = w.getBlockState(pos.down()).getBlock();
        	 if(b == TBBlocks.crystalBlock)
        	 {
        		 int md = BlockStateMetadata.getBlockMetadata(w, pos.down());
        		 ret.add(new ItemStack(TBItems.knoseFragment,1,md));
        	 }
         }else
         {
        	 if(dropSeed != null)
        		 ret.add(dropSeed.copy());
         }
    	 
    	 return ret;
    }
}
