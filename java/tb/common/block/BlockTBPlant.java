package tb.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.IOldCubicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTBPlant extends BlockBush implements IGrowable, IOldCubicBlock
{
	
	public int growthStages;
	public int growthDelay;
	public boolean requiresFarmland;
	public Icon[] growthIcons;
	public ItemStack dropItem;
	public ItemStack dropSeed;
	String textureName;

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
	
	public String getTextureName()
	{
		return textureName;
	}
	
	public BlockTBPlant setBlockName(String name)
	{
		this.setUnlocalizedName(name);
		return this;
	}
	
	public BlockTBPlant setBlockTextureName(String tex)
	{
		textureName = tex;
		return this;
	}
	
	@Override
    public Item getItem(World w, BlockPos pos)
    {
        return dropSeed != null ? dropSeed.getItem() : Item.getItemFromBlock(this);
    }
	
    protected boolean canPlaceBlockOn(Block b)
    {
        return requiresFarmland ? b == Blocks.farmland : true;
    }
    
    public void updateTick(World w, BlockPos pos, IBlockState state, Random rnd)
    {
        super.updateTick(w, pos,state, rnd);

        if (w.getLight(pos.up()) >= 9)
        {
            int l = BlockStateMetadata.getMetaFromState(state);

            if (l < growthStages)
            {
                float f = this.calculateGrowth(w, pos);

                if (rnd.nextInt((int)(growthDelay / f) + 1) == 0)
                {
                    ++l;
                    if(l >= growthStages)
                    	l = growthStages-1;
                    w.setBlockState(pos, this.getStateFromMeta(l));
                }
            }
        }
    }
    
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
    	return this.requiresFarmland ? EnumPlantType.Crop : EnumPlantType.Plains;
    }
    
    private float calculateGrowth(World w, BlockPos pos)
    {
        float f = 1.0F;
        Block block = w.getBlockState(pos.north()).getBlock();
        Block block1 = w.getBlockState(pos.south()).getBlock();
        Block block2 = w.getBlockState(pos.west()).getBlock();
        Block block3 = w.getBlockState(pos.east()).getBlock();
        Block block4 = w.getBlockState(pos.east().north()).getBlock();
        Block block5 = w.getBlockState(pos.east().south()).getBlock();
        Block block6 = w.getBlockState(pos.west().north()).getBlock();
        Block block7 = w.getBlockState(pos.west().south()).getBlock();
        boolean flag = block2 == this || block3 == this;
        boolean flag1 = block == this || block1 == this;
        boolean flag2 = block4 == this || block5 == this || block6 == this || block7 == this;

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        
        for (int l = x - 1; l <= x + 1; ++l)
        {
            for (int i1 = z - 1; i1 <= z + 1; ++i1)
            {
                float f1 = 0.0F;

                if (w.getBlockState(new BlockPos(l, y - 1, i1)).getBlock().canSustainPlant(w, new BlockPos(l, y - 1, i1), EnumFacing.UP, this))
                {
                    f1 = 1.0F;

                    if (w.getBlockState(new BlockPos(l, y - 1, i1)).getBlock().isFertile(w, new BlockPos(l, y - 1, i1)))
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
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return BlockStateMetadata.getBlockMetadata(worldIn, pos) < growthStages-1;
	}

	//canGrowPlant
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return worldIn.getLight(pos.up()) >= 9;
	}

	//growPlant
	@Override
	public void grow(World worldIn, Random r, BlockPos pos, IBlockState state)
	{
		worldIn.setBlockState(pos, this.getStateFromMeta(Math.min(growthStages-1, BlockStateMetadata.getBlockMetadata(worldIn, pos)+r.nextInt(3)+1)));
	}
	
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IconRegister reg)
    {
    	growthIcons = new Icon[growthStages];
    	for(int i = 0; i < growthStages; ++i)
    		growthIcons[i] = reg.registerBlockIcon(textureName+"stage_"+i);
    }
    
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
    	return growthIcons[meta >= growthIcons.length ? growthIcons.length-1 : meta];
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
    	Random rand = new Random(System.currentTimeMillis());
    	int metadata = BlockStateMetadata.getMetaFromState(state);
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (metadata >= growthStages-1)
        {
            for (int i = 0; i < (rand.nextDouble()*(fortune) > 0.75D ? 2 : 1); ++i) //Fix for the seed duplication
            {
                if (rand.nextInt(growthStages) <= metadata)
                {
                	if(dropSeed != null)
                	{
                		ret.add(dropSeed.copy());
                		if(dropSeed.getItem() instanceof ItemBlock) //Fix for the primal shroom duplication
                			break;
                	}
                }
            }
            
            for (int i = 0; i < 1 + rand.nextInt(fortune+1); ++i) //Change for the resource drop
            {
                if (rand.nextInt(growthStages) <= metadata)
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
    
    public IBlockState getStateFromMeta(int meta)
    {
    	return this.getDefaultState().withProperty(BlockStateMetadata.METADATA, BlockStateMetadata.MetadataValues.values()[meta]);
    }
    
    public int getMetaFromState(IBlockState state)
    {
    	return BlockStateMetadata.getMetaFromState(state);
    }

    protected BlockState createBlockState()
    {
    	return new BlockState(this,BlockStateMetadata.METADATA);
    }

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return this.getIcon(side, BlockStateMetadata.getBlockMetadata(world, x,y,z));
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		ArrayList<IBlockState> retLst = new ArrayList<IBlockState>();
		for(int i = 0; i < growthStages; ++i)
			retLst.add(getStateFromMeta(i));
		return retLst;
	}

	@Override
	public int getDCRenderID() {
		return requiresFarmland ? RenderAccessLibrary.RENDER_ID_CROPS : RenderAccessLibrary.RENDER_ID_CROSS;
	}
    
    public EnumWorldBlockLayer getBlockLayer()
    {
    	return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
}
