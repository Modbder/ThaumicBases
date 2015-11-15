package tb.common.event;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class WorldGenOak extends WorldGenTrees{
	
	public Block leavesBlock;
	public Block trunkBlock;
	public int leavesMeta;
	public int trunkMeta;
	public int minTreeHeight;

	public WorldGenOak(boolean doBlockNotify, int minHeight,int metaWood, int metaLeaves, boolean doVines, Block tree, Block leaves) {
		super(doBlockNotify, minHeight, metaWood, metaLeaves, doVines);
		trunkMeta = metaWood;
		leavesMeta = metaLeaves;
		leavesBlock = leaves;
		trunkBlock = tree;
		minTreeHeight = minHeight;
	}
	
	@Override
    public boolean generate(World w, Random rnd, BlockPos pos)
    {
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
    	int height = rnd.nextInt(3) + this.minTreeHeight;
    	boolean flag = true;
    	
        if (y >= 1 && y + height + 1 <= 256)
        {
            byte b0;
            int k1;
            Block block;
            
            for (int i1 = y; i1 <= y + 1 + height; ++i1)
            {
                b0 = 1;

                if (i1 == y)
                {
                    b0 = 0;
                }

                if (i1 >= y + 1 + height - 2)
                {
                    b0 = 2;
                }

                for (int j1 = x - b0; j1 <= x + b0 && flag; ++j1)
                {
                    for (k1 = z - b0; k1 <= z + b0 && flag; ++k1)
                    {
                        if (i1 >= 0 && i1 < 256)
                        {
                            block = w.getBlockState(new BlockPos(j1, i1, k1)).getBlock();
                            
                            if (!this.isReplaceable(w, new BlockPos(j1, i1, k1)))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
                
               
                
                if (!flag)
                {
                    return false;
                }
				Block block2 = w.getBlockState(new BlockPos(x, y - 1, z)).getBlock();

				boolean isSoil = block2.canSustainPlant(w, new BlockPos(x, y - 1, z), EnumFacing.UP, (BlockSapling)Blocks.sapling);
				if (isSoil && y < 256 - height - 1)
				{
				    block2.onPlantGrow(w, new BlockPos(x, y - 1, z), new BlockPos(x, y, z));
				    b0 = 3;
				    byte b1 = 0;
				    int l1;
				    int i2;
				    int j2;
				    int i3;

				    for (k1 = y - b0 + height; k1 <= y + height; ++k1)
				    {
				        i3 = k1 - (y + height);
				        l1 = b1 + 1 - i3 / 2;

				        for (i2 = x - l1; i2 <= x + l1; ++i2)
				        {
				            j2 = i2 - x;

				            for (int k2 = z - l1; k2 <= z + l1; ++k2)
				            {
				                int l2 = k2 - z;

				                if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || rnd.nextInt(2) != 0 && i3 != 0)
				                {
				                    Block block1 = w.getBlockState(new BlockPos(i2, k1, k2)).getBlock();

				                    if (block1.isAir(w, new BlockPos(i2, k1, k2)) || block1.isLeaves(w, new BlockPos(i2, k1, k2)))
				                    {
				                        this.func_175905_a(w, new BlockPos(i2, k1, k2), this.leavesBlock, this.leavesMeta);
				                    }
				                }
				            }
				        }
				    }

				    for (k1 = 0; k1 < height; ++k1)
				    {
				        block = w.getBlockState(new BlockPos(x, y + k1, z)).getBlock();

				        if (block.isAir(w, new BlockPos(x, y + k1, z)) || block.isLeaves(w, new BlockPos(x, y + k1, z)))
				        {
				            this.func_175905_a(w, new BlockPos(x, y + k1, z), this.trunkBlock, this.trunkMeta);
				        }
				    }
				}
				else
				{
				    return false;
				}
            }
        }
    	
    	return false;
    }

}
