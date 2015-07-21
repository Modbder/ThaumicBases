package tb.common.block;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockTBPlant extends BlockBush implements IGrowable
{
	
	public int growthStages;
	public int growthDelay;
	public boolean requiresFarmland;
	public IIcon[] growthIcons;
	public ItemStack dropItem;
	public ItemStack dropSeed;

	public BlockTBPlant(int stages, int delay, boolean isCrop)
	{
		super();
		growthStages = stages;
		growthDelay = delay;
		requiresFarmland = isCrop;
        this.setTickRandomly(true);
        float f = 0.5F;
        if(isCrop)
        	this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        else
        	this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.75F, 0.75F);
        
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        this.disableStats();
	}
	
    @SideOnly(Side.CLIENT)
    public Item getItem(World w, int x, int y, int z)
    {
        return dropSeed != null ? dropSeed.getItem() : Item.getItemFromBlock(this);
    }
	
    protected boolean canPlaceBlockOn(Block b)
    {
        return requiresFarmland ? b == Blocks.farmland : true;
    }
    
    public void updateTick(World w, int x, int y, int z, Random rnd)
    {
        super.updateTick(w, x, y, z, rnd);

        if (w.getBlockLightValue(x, y + 1, z) >= 9)
        {
            int l = w.getBlockMetadata(x, y, z);

            if (l < growthStages)
            {
                float f = this.calculateGrowth(w, x, y, z);

                if (rnd.nextInt((int)((float)growthDelay / f) + 1) == 0)
                {
                    ++l;
                    w.setBlockMetadataWithNotify(x, y, z, l, 2);
                }
            }
        }
    }
    
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
    {
    	return this.requiresFarmland ? EnumPlantType.Crop : EnumPlantType.Plains;
    }
    
    private float calculateGrowth(World w, int x, int y, int z)
    {
        float f = 1.0F;
        Block block = w.getBlock(x, y, z - 1);
        Block block1 = w.getBlock(x, y, z + 1);
        Block block2 = w.getBlock(x - 1, y, z);
        Block block3 = w.getBlock(x + 1, y, z);
        Block block4 = w.getBlock(x - 1, y, z - 1);
        Block block5 = w.getBlock(x + 1, y, z - 1);
        Block block6 = w.getBlock(x + 1, y, z + 1);
        Block block7 = w.getBlock(x - 1, y, z + 1);
        boolean flag = block2 == this || block3 == this;
        boolean flag1 = block == this || block1 == this;
        boolean flag2 = block4 == this || block5 == this || block6 == this || block7 == this;

        for (int l = x - 1; l <= x + 1; ++l)
        {
            for (int i1 = z - 1; i1 <= z + 1; ++i1)
            {
                float f1 = 0.0F;

                if (w.getBlock(l, y - 1, i1).canSustainPlant(w, l, y - 1, i1, ForgeDirection.UP, this))
                {
                    f1 = 1.0F;

                    if (w.getBlock(l, y - 1, i1).isFertile(w, l, y - 1, i1))
                    {
                        f1 = 3.0F;
                    }
                }

                if (l != x || i1 != z)
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
	
	//canApplyBonemeal
	@Override
	public boolean func_149851_a(World w, int x,int y, int z, boolean remote) {
		return w.getBlockMetadata(x, y, z) < growthStages-1;
	}

	//canGrowPlant
	@Override
	public boolean func_149852_a(World w, Random r,	int x, int y, int z) {
		return w.getBlockLightValue(x, y + 1, z) >= 9;
	}

	//growPlant
	@Override
	public void func_149853_b(World w, Random r,int x, int y, int z) 
	{
		w.setBlockMetadataWithNotify(x, y, z, Math.min(growthStages-1, w.getBlockMetadata(x, y, z)+r.nextInt(3)+1), 3);
	}
	
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
    	growthIcons = new IIcon[growthStages];
    	for(int i = 0; i < growthStages; ++i)
    		growthIcons[i] = reg.registerIcon(textureName+"stage_"+i);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	return growthIcons[meta >= growthIcons.length ? growthIcons.length-1 : meta];
    }
    
    public int getRenderType()
    {
        return requiresFarmland ? 6 : 1;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (metadata >= growthStages-1)
        {
            for (int i = 0; i < (world.rand.nextDouble()*(fortune) > 0.75D ? 2 : 1); ++i) //Fix for the seed duplication
            {
                if (world.rand.nextInt(growthStages) <= metadata)
                {
                	if(dropSeed != null)
                	{
                		ret.add(dropSeed.copy());
                		if(dropSeed.getItem() instanceof ItemBlock) //Fix for the primal shroom duplication
                			break;
                	}
                }
            }
            
            for (int i = 0; i < 1 + world.rand.nextInt(fortune+1); ++i) //Change for the resource drop
            {
                if (world.rand.nextInt(growthStages) <= metadata)
                {
                	if(dropItem != null)
                		ret.add(dropItem.copy());
                }
            }
        }else
        	if(dropSeed != null)
        		ret.add(dropSeed.copy());

        return ret;
    }
    
    
}
