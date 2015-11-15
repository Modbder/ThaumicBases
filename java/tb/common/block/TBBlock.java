package tb.common.block;

import java.util.Arrays;
import java.util.List;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.IOldCubicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IInfusionStabiliser;

public class TBBlock extends Block implements IInfusionStabiliser,IOldCubicBlock{
	
	boolean isGlass;
	boolean stabilise;
	Icon blockIcon;
	String iconName;
	
	public TBBlock(Material m,boolean b)
	{
		super(m);
		isGlass = b;
	}
	
	public String getTextureName()
	{
		return iconName;
	}
	
	public TBBlock setBlockName(String name)
	{
		this.setUnlocalizedName(name);
		return this;
	}

	public TBBlock stabilise()
	{
		stabilise = true;
		return this;
	}
	
    public boolean isOpaqueCube()
    {
        return !isGlass;
    }
    
    public int getLightOpacity()
    {
        return isGlass ? 7 : 15;
    }
    
    public int getRenderBlockPass()
    {
        return isGlass ? 1 : 0;
    }
    
    public EnumWorldBlockLayer getBlockLayer()
    {
    	return isGlass ? EnumWorldBlockLayer.TRANSLUCENT : EnumWorldBlockLayer.SOLID;
    }

	@Override
	public boolean canStabaliseInfusion(World world, BlockPos paramBlockPos) {
		return stabilise;
	}

	@Override
	public Icon getIcon(int side, int meta) {
		return blockIcon;
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
		blockIcon = ir.registerBlockIcon(iconName);
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CUBE;
	}
	
	public TBBlock setBlockTextureName(String tex)
	{
		iconName = tex;
		return this;
	}
}
