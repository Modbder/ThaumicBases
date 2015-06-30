package tb.common.block;

import java.util.Random;

import tb.init.TBItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockAureliaLeaf extends Block{

	public BlockAureliaLeaf() 
	{
		super(Material.plants);
		this.setBlockBounds(0.25F, 0, 0.25F, 0.75F, 0.25F, 0.75F);
		this.setLightLevel(0.3F);
	}
	
    public int damageDropped(int meta)
    {
    	return 5;
    }
    
    public Item getItemDropped(int meta, Random rnd, int fort)
    {
    	return TBItems.resource;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return 1;
    }

}
