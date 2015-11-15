package tb.common.itemblock;

import net.minecraft.block.Block;
import tb.common.block.BlockTBSlab;
import tb.init.TBBlocks;

public class ItemBlockHalfSlab extends ItemTBSlab{

	public ItemBlockHalfSlab(Block b){
		this(b,(BlockTBSlab)TBBlocks.genericSlab,(BlockTBSlab)TBBlocks.genericSlab_full);
	}
	
	public ItemBlockHalfSlab(Block block, BlockTBSlab singleSlab, BlockTBSlab doubleSlab) {
		super(block, singleSlab, doubleSlab);
	}

}
