package tb.common.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.IOldCubicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tb.init.TBItems;

public class BlockAureliaLeaf extends Block implements IOldCubicBlock{

	Icon icon;
	String texture;
	
	public BlockAureliaLeaf() 
	{
		super(Material.plants);
		this.setBlockBounds(0.25F, 0, 0.25F, 0.75F, 0.25F, 0.75F);
		this.setLightLevel(0.3F);
	}
	
	public BlockAureliaLeaf setBlockName(String name)
	{
		this.setUnlocalizedName(name);
		return this;
	}

    public EnumWorldBlockLayer getBlockLayer()
    {
    	return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
	public BlockAureliaLeaf setBlockTextureName(String tex)
	{
		texture = tex;
		return this;
	}
	
    public int damageDropped(IBlockState state)
    {
    	return 5;
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rnd, int fort)
    {
    	return TBItems.resource;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(World w, BlockPos pos,IBlockState state)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
	public Icon getIcon(int side, int meta) {
		return icon;
	}

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return getIcon(side,this.getMetaFromState(world.getBlockState(new BlockPos(x,y,z))));
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		return Arrays.asList(this.getDefaultState());
	}

	@Override
	public void registerBlockIcons(IconRegister ir) {
		icon = ir.registerBlockIcon(texture);
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CROSSES;
	}

}
