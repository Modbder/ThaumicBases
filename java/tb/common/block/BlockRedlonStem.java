package tb.common.block;

import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.util.ArrayList;
import java.util.Random;

import tb.init.TBItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedlonStem extends BlockStem{

	private Block field_149877_a;
	
	public BlockRedlonStem(Block created)
	{
		super(created);
		field_149877_a = created;
	}
	
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
    {
    	this.checkAndDropBlock(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);

        if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9)
        {
            float f = this.func_149875_n(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);

            if (p_149674_5_.nextInt((int)(25.0F / f) + 1) == 0)
            {
                int l = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);

                if (l < 7)
                {
                    ++l;
                    p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, l, 2);
                }
                else
                {
                    if (p_149674_1_.getBlock(p_149674_2_ - 1, p_149674_3_, p_149674_4_) == this.field_149877_a)
                    {
                        return;
                    }

                    if (p_149674_1_.getBlock(p_149674_2_ + 1, p_149674_3_, p_149674_4_) == this.field_149877_a)
                    {
                        return;
                    }

                    if (p_149674_1_.getBlock(p_149674_2_, p_149674_3_, p_149674_4_ - 1) == this.field_149877_a)
                    {
                        return;
                    }

                    if (p_149674_1_.getBlock(p_149674_2_, p_149674_3_, p_149674_4_ + 1) == this.field_149877_a)
                    {
                        return;
                    }

                    int i1 = p_149674_5_.nextInt(4);
                    int j1 = p_149674_2_;
                    int k1 = p_149674_4_;
                    
                    if(p_149674_5_.nextInt(4) != 0)
                    	return;

                    if (i1 == 0)
                    {
                        j1 = p_149674_2_ - 1;
                    }

                    if (i1 == 1)
                    {
                        ++j1;
                    }

                    if (i1 == 2)
                    {
                        k1 = p_149674_4_ - 1;
                    }

                    if (i1 == 3)
                    {
                        ++k1;
                    }

                    if (p_149674_1_.isAirBlock(j1, p_149674_3_, k1))
                    {
                        p_149674_1_.setBlock(j1, p_149674_3_, k1, this.field_149877_a);
                    }
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
    {
        this.func_150186_m(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_);
    }

    private void func_150186_m(World p_150186_1_, int p_150186_2_, int p_150186_3_, int p_150186_4_)
    {
        Random random = p_150186_1_.rand;
        double d0 = 0.0625D;

        for (int l = 0; l < 6; ++l)
        {
            double d1 = (double)((float)p_150186_2_ + random.nextFloat());
            double d2 = (double)((float)p_150186_3_ + random.nextFloat());
            double d3 = (double)((float)p_150186_4_ + random.nextFloat());

            if (l == 0 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_ + 1, p_150186_4_).isOpaqueCube())
            {
                d2 = (double)(p_150186_3_ + 1) + d0;
            }

            if (l == 1 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_ - 1, p_150186_4_).isOpaqueCube())
            {
                d2 = (double)(p_150186_3_ + 0) - d0;
            }

            if (l == 2 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ + 1).isOpaqueCube())
            {
                d3 = (double)(p_150186_4_ + 1) + d0;
            }

            if (l == 3 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ - 1).isOpaqueCube())
            {
                d3 = (double)(p_150186_4_ + 0) - d0;
            }

            if (l == 4 && !p_150186_1_.getBlock(p_150186_2_ + 1, p_150186_3_, p_150186_4_).isOpaqueCube())
            {
                d1 = (double)(p_150186_2_ + 1) + d0;
            }

            if (l == 5 && !p_150186_1_.getBlock(p_150186_2_ - 1, p_150186_3_, p_150186_4_).isOpaqueCube())
            {
                d1 = (double)(p_150186_2_ + 0) - d0;
            }

            if (d1 < (double)p_150186_2_ || d1 > (double)(p_150186_2_ + 1) || d2 < 0.0D || d2 > (double)(p_150186_3_ + 1) || d3 < (double)p_150186_4_ || d3 > (double)(p_150186_4_ + 1))
            {
                p_150186_1_.spawnParticle("reddust", d1, d2, d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    private float func_149875_n(World p_149875_1_, int p_149875_2_, int p_149875_3_, int p_149875_4_)
    {
        float f = 1.0F;
        Block block = p_149875_1_.getBlock(p_149875_2_, p_149875_3_, p_149875_4_ - 1);
        Block block1 = p_149875_1_.getBlock(p_149875_2_, p_149875_3_, p_149875_4_ + 1);
        Block block2 = p_149875_1_.getBlock(p_149875_2_ - 1, p_149875_3_, p_149875_4_);
        Block block3 = p_149875_1_.getBlock(p_149875_2_ + 1, p_149875_3_, p_149875_4_);
        Block block4 = p_149875_1_.getBlock(p_149875_2_ - 1, p_149875_3_, p_149875_4_ - 1);
        Block block5 = p_149875_1_.getBlock(p_149875_2_ + 1, p_149875_3_, p_149875_4_ - 1);
        Block block6 = p_149875_1_.getBlock(p_149875_2_ + 1, p_149875_3_, p_149875_4_ + 1);
        Block block7 = p_149875_1_.getBlock(p_149875_2_ - 1, p_149875_3_, p_149875_4_ + 1);
        boolean flag = block2 == this || block3 == this;
        boolean flag1 = block == this || block1 == this;
        boolean flag2 = block4 == this || block5 == this || block6 == this || block7 == this;

        for (int l = p_149875_2_ - 1; l <= p_149875_2_ + 1; ++l)
        {
            for (int i1 = p_149875_4_ - 1; i1 <= p_149875_4_ + 1; ++i1)
            {
                Block block8 = p_149875_1_.getBlock(l, p_149875_3_ - 1, i1);
                float f1 = 0.0F;

                if (block8.canSustainPlant(p_149875_1_, l, p_149875_3_ - 1, i1, UP, this))
                {
                    f1 = 1.0F;

                    if (block8.isFertile(p_149875_1_, l, p_149875_3_ - 1, i1))
                    {
                        f1 = 3.0F;
                    }
                }

                if (l != p_149875_2_ || i1 != p_149875_4_)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        if (flag2 || flag && flag1)
        {
            f /= 2.0F;
        }

        return f;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        Item item = TBItems.redlonSeeds;
        for (int i = 0; item != null && i < 3; i++)
        {
            if (world.rand.nextInt(15) <= meta)
                ret.add(new ItemStack(item));
        }

        return ret;
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
    	return TBItems.redlonSeeds;
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
    {
    	return 0xffffff;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int p_149741_1_)
    {
    	return 0xffffff;
    }
    
    
}
