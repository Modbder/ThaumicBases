package tb.common.block;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.IOldCubicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class BlockRainbowCactus extends BlockCactus implements IOldCubicBlock{

    public Icon top;
    public Icon bot;
    public Icon side;
    String texture;
	
	public BlockRainbowCactus()
	{
		super();
	}
	
	public BlockRainbowCactus setBlockName(String name)
	{
		this.setUnlocalizedName(name);
		return this;
	}
    
	public BlockRainbowCactus setBlockTextureName(String tex)
	{
		texture = tex;
		return this;
	}
	
	public String getTextureName()
	{
		return texture;
	}
	
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess w, BlockPos pos, IBlockState state, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        if(w instanceof World)
        {
        	World world = World.class.cast(w);
	        if(world.getBlockState(pos.down()).getBlock() != this)
	        {
	        	ret.add(new ItemStack(this,1,0));
	        	return ret;
	        }
	        	
	        for(int i = 0; i < 3+world.rand.nextInt(8); ++i)
	        	ret.add(new ItemStack(Items.dye,1,allowedDyes[world.rand.nextInt(allowedDyes.length)]));
        }
        return ret;
    }
    
    public static final int[] allowedDyes = new int[]{1,2,5,2,6,7,2,8,9,10,2,11,12,13,14,2};
    
    public void registerBlockIcons(IconRegister ir)
    {
        side = ir.registerBlockIcon(this.getTextureName() + "side");
        top = ir.registerBlockIcon(this.getTextureName() + "top");
        bot = ir.registerBlockIcon(this.getTextureName() + "bottom");
    }

    public Icon getIcon(int side, int meta)
    {
        return side == 1 ? top : side == 0 ? bot : this.side;
    }
	
    @Override
    public boolean canSustainPlant(IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
    {
        Block plant = plantable.getPlant(world, pos.up()).getBlock();
        
        if (plant == this)
            return true;
        
        return super.canSustainPlant(world, pos, direction, plantable);
    }

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return getIcon(side,this.getMetaFromState(world.getBlockState(new BlockPos(x,y,z))));
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		ArrayList<IBlockState> ret = new ArrayList<IBlockState>();
		for(int i = 0; i < 15; ++i)
			ret.add(this.getStateFromMeta(i));
		return ret;
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_FACES_WITH_HORIZONTAL_OFFSET;
	}
}
