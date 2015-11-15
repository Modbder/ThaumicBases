package tb.common.block;

import java.util.List;
import java.util.Random;

import DummyCore.Utils.BlockStateMetadata;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tb.init.TBBlocks;

public class BlockLucritePlant extends BlockTBPlant {

	public BlockLucritePlant(int stages, int delay, boolean isCrop) {
		super(stages, delay, isCrop);
	}
	
	@Override
	public void grow(World worldIn, Random r, BlockPos pos, IBlockState state)
	{
		int meta = BlockStateMetadata.getMetaFromState(state);
		worldIn.setBlockState(pos, this.getStateFromMeta(Math.min(growthStages,meta+1)));
	}

	@Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        return super.canBlockStay(worldIn,pos,state) && worldIn.getBlockState(pos.down(2)).getBlock() == TBBlocks.dustBlock;
    }
	
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> ret = super.getDrops(world, pos, state, fortune);

        if(world instanceof World)
        {
        	World w = World.class.cast(world);
        	int metadata = BlockStateMetadata.getMetaFromState(state);
	        if (metadata >= growthStages-1)
	            if (w.rand.nextInt(16) == 0)
	            	ret.add(new ItemStack(Items.golden_carrot,1,0));
        }
        
        return ret;
    }
}
