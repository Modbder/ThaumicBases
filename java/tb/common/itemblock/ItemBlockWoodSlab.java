package tb.common.itemblock;

import net.minecraft.block.Block;
import tb.common.block.BlockTBSlab;
import tb.init.TBBlocks;

public class ItemBlockWoodSlab extends ItemTBSlab{

	public ItemBlockWoodSlab(Block b){
		this(b,(BlockTBSlab)TBBlocks.woodSlab,(BlockTBSlab)TBBlocks.woodSlab_full);
	}
	
	public ItemBlockWoodSlab(Block block, BlockTBSlab singleSlab, BlockTBSlab doubleSlab) {
		super(block, singleSlab, doubleSlab);
	}

}
