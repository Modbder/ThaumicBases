package tb.common.itemblock;

import net.minecraft.block.Block;
import tb.common.block.BlockTBSlab;
import tb.init.TBBlocks;

public class ItemBlockCrystalSlab extends ItemTBSlab{

	public ItemBlockCrystalSlab(Block b){
		this(b,(BlockTBSlab)TBBlocks.crystalSlab,(BlockTBSlab)TBBlocks.crystalSlab_full);
	}
	
	public ItemBlockCrystalSlab(Block block, BlockTBSlab singleSlab, BlockTBSlab doubleSlab) {
		super(block, singleSlab, doubleSlab);
	}

}
