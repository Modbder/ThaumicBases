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
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tb.utils.TBConfig;

public class BlockPyrofluid extends Block implements IOldCubicBlock{
	
	public static Icon fluidIcon;
	public static Icon staticIcon;

	public BlockPyrofluid()
	{
		super(Material.lava);
        float f = 0.0F;
        float f1 = 0.0F;
        this.setBlockBounds(0.0F + f1, 0.0F + f, 0.0F + f1, 1.0F + f1, 1.0F + f, 1.0F + f1);
        this.setTickRandomly(true);
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
	{
		int meta = BlockStateMetadata.getBlockMetadata(worldIn, pos);
		double removed = 0;
		if(meta == 15)
			removed = 0.99D;
		else
			removed = 1-((8D - meta)/8D);
		
		if(removed < 0)
			removed = 0.95D;
		
		this.setBlockBounds(0, 0, 0, 1, (float) (1-removed), 1);
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
    public boolean isPassable(IBlockAccess world, BlockPos pos)
    {
        return true;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state)
    {
        return null;
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }
    
    @Override
    public boolean isFullCube()
    {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
    
    public int tickRate(World par1World)
    {
        return 5;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        double d0 = pos.getX();
        double d1 = pos.getY();
        double d2 = pos.getZ();

        if (this.blockMaterial == Material.lava && worldIn.getBlockState(pos.up()).getBlock().getMaterial() == Material.air && !worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube())
        {
            if (rand.nextInt(100) == 0)
            {
                double d8 = d0 + rand.nextFloat();
                double d4 = d1 + this.maxY;
                double d6 = d2 + rand.nextFloat();
                worldIn.spawnParticle(EnumParticleTypes.LAVA, d8, d4, d6, 0.0D, 0.0D, 0.0D, new int[0]);
                worldIn.playSound(d8, d4, d6, "liquid.lavapop", 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
            }

            if (rand.nextInt(200) == 0)
            {
                worldIn.playSound(d0, d1, d2, "liquid.lava", 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
            }
        }

        if (rand.nextInt(10) == 0 && World.doesBlockHaveSolidTopSurface(worldIn, pos.down()))
        {
            Material material = worldIn.getBlockState(pos.down(2)).getBlock().getMaterial();

            if (!material.blocksMovement() && !material.isLiquid())
            {
                double d3 = d0 + rand.nextFloat();
                double d5 = d1 - 1.05D;
                double d7 = d2 + rand.nextFloat();

                if (this.blockMaterial == Material.water)
                {
                    worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d3, d5, d7, 0.0D, 0.0D, 0.0D, new int[0]);
                }
                else
                {
                    worldIn.spawnParticle(EnumParticleTypes.DRIP_LAVA, d3, d5, d7, 0.0D, 0.0D, 0.0D, new int[0]);
                }
            }
        }
    }
    
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    	int meta = BlockStateMetadata.getMetaFromState(state) + 1;
    	if(meta > 15)
    		return;
    	
    	if(meta >= 8)
    		meta = 15;
    	
    	worldIn.setBlockState(pos, this.getStateFromMeta(meta));
    }
    
	public void registerBlockIcons(IconRegister ir)
	{
		fluidIcon = ir.registerBlockIcon("thaumicbases:blazingFluid/block");
		staticIcon = ir.registerBlockIcon("thaumicbases:blazingFluid/leftovers");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < 8; ++i)
			par3List.add(new ItemStack(par1,1,i));
		
		par3List.add(new ItemStack(par1,1,15));
	}
	
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
    	return meta == 15 ? staticIcon : fluidIcon;
    }
    
    @Override
    public float getBlockHardness(World w, BlockPos pos)
    {
    	return BlockStateMetadata.getBlockMetadata(w, pos) == 15 ? 5 : -1;
    }
    
    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
    	ArrayList<ItemStack> retLst = new ArrayList<ItemStack>();
    	
    	retLst.add(new ItemStack(Items.blaze_powder,TBConfig.minBlazePowderFromPyrofluid+RANDOM.nextInt(TBConfig.maxBlazePowderFromPyrofluid-TBConfig.minBlazePowderFromPyrofluid),0));
    	
    	return retLst;
    }

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return this.getIcon(side, BlockStateMetadata.getBlockMetadata(world, x,y,z));
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		ArrayList<IBlockState> retLst = new ArrayList<IBlockState>();
		for(int i = 0; i < 9; ++i)
			retLst.add(getStateFromMeta(i));
		
		retLst.add(getStateFromMeta(15));
		
		return retLst;
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CUBE;
	}
}
