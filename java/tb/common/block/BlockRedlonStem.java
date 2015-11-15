package tb.common.block;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import DummyCore.Client.IBlockConnector;
import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRedlonStem extends BlockBush implements IGrowable,IBlockConnector{

	public Icon connected;
	public Icon disconnected;
	String textureName;
	public Block crop;
	
    public BlockRedlonStem(Block crop)
    {
    	super();
    	this.crop = crop;
        this.setTickRandomly(true);
        float f = 0.125F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
    }
    
    protected boolean canPlaceBlockOn(Block ground)
    {
        return ground == Blocks.farmland;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    	worldIn.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX()+rand.nextDouble(), pos.getY()+rand.nextDouble(), pos.getZ()+rand.nextDouble(), 0, 0, 0);
    }
    
    @SuppressWarnings("rawtypes")
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);
        if (worldIn.getLight(pos.up()) >= 9)
        {
            float f = MiscUtils.getGrowthChance(this, worldIn, pos);
            if (rand.nextInt((int)(25F / f) + 1) == 0)
            {
                int i = BlockStateMetadata.getMetaFromState(state);

                if (i < 7)
                {
                    state = this.getStateFromMeta(i+1);
                    worldIn.setBlockState(pos, state, 2);
                }
                else
                {
                    Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

                    while (iterator.hasNext())
                    {
                        EnumFacing enumfacing = (EnumFacing)iterator.next();

                        if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this.crop)
                            return;
                    }

                    pos = pos.offset(EnumFacing.Plane.HORIZONTAL.random(rand));

                    if (worldIn.isAirBlock(pos))
                        worldIn.setBlockState(pos, this.crop.getDefaultState());
                }
            }
        }
    }
    
    public void growStem(World worldIn, BlockPos pos, IBlockState state)
    {
        int i = BlockStateMetadata.getMetaFromState(state) + MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
        worldIn.setBlockState(pos, this.getStateFromMeta(Integer.valueOf(Math.min(7, i))), 2);
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        this.maxY = (BlockStateMetadata.getBlockMetadata(worldIn, pos) * 2 + 2) / 16.0F;
        float f = 0.125F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, (float)this.maxY, 0.5F + f);
    }
    
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    }
    
    protected Item getSeedItem()
    {
    	return Items.wheat_seeds;
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
        {
            Item item = this.getSeedItem();

            if (item != null)
            {
                int j = BlockStateMetadata.getMetaFromState(state);

                for (int k = 0; k < 3; ++k)
                    if (RANDOM.nextInt(15) <= j)
                        ret.add(new ItemStack(item));
            }
        }
        return ret;
    }
    
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }
    
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return BlockStateMetadata.getMetaFromState(state) < 7;
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        Item item = this.getSeedItem();
        return item != null ? item : null;
    }
    
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        this.growStem(worldIn, pos, state);
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return true;
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

	
	public String getTextureName()
	{
		return textureName;
	}
	
	public BlockRedlonStem setBlockName(String name)
	{
		this.setUnlocalizedName(name);
		return this;
	}
	
	public BlockRedlonStem setBlockTextureName(String tex)
	{
		textureName = tex;
		return this;
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return disconnected;
	}

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return disconnected;
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		ArrayList<IBlockState> lst = new ArrayList<IBlockState>();
		for(int i = 0; i < 8; ++i)
			lst.add(this.getStateFromMeta(i));
		return lst;
	}

	@Override
	public void registerBlockIcons(IconRegister ir) {
		disconnected = ir.registerBlockIcon(this.getTextureName()+"_disconnected");
		connected = ir.registerBlockIcon(this.getTextureName()+"_connected");
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CONNECTED_TO_BLOCK;
	}

	@Override
	public boolean connectsTo(IBlockAccess world, BlockPos pos, BlockPos originalPos, EnumFacing face,IBlockState state) {
		return world.getBlockState(pos).getBlock()==this.crop;
	}

	@Override
	public Icon getConnectionIcon(IBlockAccess world, int x, int y, int z) {
		return connected;
	}
	
}
