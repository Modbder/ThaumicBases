package tb.common.block;

import java.util.List;
import java.util.Random;

import DummyCore.Utils.BlockStateMetadata;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tb.common.entity.EntityAspectOrb;
import thaumcraft.api.aspects.Aspect;

public class BlockAshroom extends BlockTBPlant {

	public BlockAshroom(int stages, int delay, boolean isCrop) {
		super(stages, delay, isCrop);
	}
	
	@Override
	public void grow(World worldIn, Random r, BlockPos pos, IBlockState state)
	{
		worldIn.setBlockState(pos, this.getStateFromMeta(Math.min(growthStages-1, BlockStateMetadata.getBlockMetadata(worldIn, pos)+1)));
	}
	
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.canPlaceBlockAt(worldIn, pos.up()))
        {
        	this.dropBlockAsItem(worldIn, pos, state, 0);
        	worldIn.setBlockToAir(pos);
            return false;
        }
        
		return true;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
    	if(world instanceof World)
    	{
	    	World w = World.class.cast(world);
	    	int metadata = BlockStateMetadata.getMetaFromState(state);
	    	if(metadata >= this.growthStages - 1)
	    	{
	    		for(int i = 0; i < 8 + w.rand.nextInt(32); ++i) //Nerf for the shrooms
	    		{
	    			Aspect primal = Aspect.getPrimalAspects().get(w.rand.nextInt(Aspect.getPrimalAspects().size()));
					EntityAspectOrb orb = new EntityAspectOrb(w, pos.getX(),pos.getY(),pos.getZ(), primal, 1);
					if(!w.isRemote)
						w.spawnEntityInWorld(orb);
	    		}
	    	}
    	}
        return super.getDrops(world, pos, state, fortune);
    }
}
