package tb.common.itemblock;

import tb.init.TBBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

public class ItemBlockSlab_1 extends ItemSlab{
	
	public ItemBlockSlab_1(Block b)
	{
		this(b,(BlockSlab)TBBlocks.crystalSlab,(BlockSlab)TBBlocks.crystalSlab_full,b == TBBlocks.crystalSlab_full);
	}

	public ItemBlockSlab_1(Block block, BlockSlab slab,BlockSlab doubleSlab, boolean isFullSlab) 
	{
		super(block, slab, doubleSlab, isFullSlab);
	}

}
