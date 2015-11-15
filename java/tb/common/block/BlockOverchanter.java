package tb.common.block;

import java.util.Arrays;
import java.util.List;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.IOldCubicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tb.common.tile.TileOverchanter;
import tb.core.TBCore;

public class BlockOverchanter extends BlockContainer implements IOldCubicBlock{
	
	public Icon topIcon;
	public Icon botIcon;
	public Icon sideIcon;

	public BlockOverchanter() 
	{
		super(Material.rock);
		this.setHarvestLevel("pickaxe", 3);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        this.setLightOpacity(0);
	}
	
    public int getRenderType()
    {
        return 3;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public Icon getIcon(int side, int meta)
    {
    	return side == 0 ? botIcon : side == 1 ? topIcon : sideIcon;
    }
    
    public void registerBlockIcons(IconRegister reg)
    {
    	topIcon = reg.registerBlockIcon("thaumicbases:overchanter/top");
    	botIcon = reg.registerBlockIcon("thaumicbases:overchanter/bottom");
    	sideIcon = reg.registerBlockIcon("thaumicbases:overchanter/side");
    }

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileOverchanter();
	}
	
    public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	if(!p.isSneaking())
    	{
    		if(!w.isRemote)
    		{
    			p.openGui(TBCore.instance, 0x421922, w, pos.getX(),pos.getY(),pos.getZ());
    			return true;
    		}
			return true;
    	}
    	return false;
    }

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return getIcon(side,0);
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		return Arrays.asList(getDefaultState());
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CUBE;
	}
}
