package tb.common.itemblock;

import tb.init.TBBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

public class ItemBlockSlab_0 extends ItemSlab{
	
	public ItemBlockSlab_0(Block b)
	{
		this(b,(BlockSlab)TBBlocks.genericSlab,(BlockSlab)TBBlocks.genericSlab_full,b == TBBlocks.genericSlab_full);
	}

	public ItemBlockSlab_0(Block block, BlockSlab slab,BlockSlab doubleSlab, boolean isFullSlab) 
	{
		super(block, slab, doubleSlab, isFullSlab);
	}

}
