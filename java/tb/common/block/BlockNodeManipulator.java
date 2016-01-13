package tb.common.block;

import java.util.Arrays;
import java.util.List;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.IOldCubicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tb.common.item.ItemNodeFoci;
import tb.common.tile.TileNodeManipulator;
import tb.init.TBItems;
import thaumcraft.api.blocks.BlocksTC;

public class BlockNodeManipulator extends BlockContainer implements IOldCubicBlock{
	
	public BlockNodeManipulator()
	{
		super(Material.rock);
	}
	
    public boolean isOpaqueCube()
    {
        return false;
    }
    
	public int getRenderType()
	{
		return 3;
	}
	
    public IBlockState getStateFromMeta(int meta)
    {
    	return this.getDefaultState().withProperty(BlockStateMetadata.METADATA, meta);
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
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileNodeManipulator();
	}

	@Override
	public Icon getIcon(int side, int meta) {
		return Icon.fromBlock(BlocksTC.stone);
	}

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return Icon.fromBlock(BlocksTC.stone);
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		return Arrays.asList(getStateFromMeta(0),getStateFromMeta(1),getStateFromMeta(2),getStateFromMeta(3),getStateFromMeta(4),getStateFromMeta(5),getStateFromMeta(6),getStateFromMeta(7),getStateFromMeta(8),getStateFromMeta(9),getStateFromMeta(10));
	}

	@Override
	public void registerBlockIcons(IconRegister ir) {
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_NONE;
	}
	
    public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	if(p.getCurrentEquippedItem() != null)
    	{
    		ItemStack current = p.getCurrentEquippedItem();
    		if(current.getItem() instanceof ItemNodeFoci)
    		{
    			if(BlockStateMetadata.getMetaFromState(state) != 0)
    			{
    				int meta = BlockStateMetadata.getMetaFromState(state);
    				ItemStack stk = new ItemStack(TBItems.nodeFoci,1,meta-1);
    				EntityItem itm = new EntityItem(w,pos.getX()+0.5D,pos.getY(),pos.getZ()+0.5D,stk);
    				if(!w.isRemote)
    					w.spawnEntityInWorld(itm);
    			}
    			w.setBlockState(pos, getStateFromMeta(current.getMetadata()+1));
    			p.destroyCurrentEquippedItem();
    			
    			return true;
    		}
    	}else
    	{
			if(BlockStateMetadata.getMetaFromState(state) != 0)
			{
				int meta = BlockStateMetadata.getMetaFromState(state);
				ItemStack stk = new ItemStack(TBItems.nodeFoci,1,meta-1);
				EntityItem itm = new EntityItem(w,pos.getX()+0.5D,pos.getY(),pos.getZ()+0.5D,stk);
				if(!w.isRemote)
					w.spawnEntityInWorld(itm);
			}
			w.setBlockState(pos, getStateFromMeta(0));
    	}
    	return true;
    }
    
    @Override
    public void breakBlock(World w, BlockPos pos, IBlockState state)
    {
    	int meta = BlockStateMetadata.getMetaFromState(state);
    	if(meta > 0) //Fix for the manipulator not dropping the foci.
    	{
    		ItemStack foci = new ItemStack(TBItems.nodeFoci,1,meta-1);
    		EntityItem itm = new EntityItem(w,pos.getX()+0.5D,pos.getY()+0.5D,pos.getZ()+0.5D,foci);
    		if(!w.isRemote)
    			w.spawnEntityInWorld(itm);
    	}
    	super.breakBlock(w, pos, state);
    }
}
