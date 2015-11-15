package tb.common.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.IOldCubicBlock;
import DummyCore.Utils.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tb.common.tile.TileCampfire;
import thaumcraft.common.Thaumcraft;

public class BlockCampfire extends BlockContainer implements IOldCubicBlock
{

	public BlockCampfire()
	{
		super(Material.wood);
		setHardness(1F);
		setResistance(1F);
		setStepSound(soundTypeWood);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) 
	{
		return new TileCampfire();
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
    
    public EnumWorldBlockLayer getBlockLayer()
    {
    	return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }

    public void randomDisplayTick(World w, BlockPos pos, IBlockState state, Random r)
    {
    	if(BlockStateMetadata.getMetaFromState(state) > 1)
    	{
    		int x = pos.getX();
    		int y = pos.getY();
    		int z = pos.getZ();
    		TileCampfire tc = (TileCampfire) w.getTileEntity(pos);
    		if(tc.tainted)
    			Thaumcraft.proxy.getFX().drawPollutionParticles(pos);
    		
    		w.spawnParticle(EnumParticleTypes.FLAME, x+0.5D+MathUtils.randomDouble(r)/4, y, z+0.5D+MathUtils.randomDouble(r)/4, 0, 0.04D, 0);
    		for(int i = 0; i < 10; ++i)
    			w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x+0.5D+MathUtils.randomDouble(r)/4, y+0.1D, z+0.5D+MathUtils.randomDouble(r)/4, 0, r.nextDouble()/20, 0);
    		
    		w.playSound(x+0.5D, y+0.5D, z+0.5D, "thaumicbases:fire.loop", 0.1F, 0.1F, false);
    	}
    }
    
    public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	if(w.isRemote)
    		return true;
    	
    	TileCampfire fire = (TileCampfire) w.getTileEntity(pos);
    	ItemStack stk = p.getCurrentEquippedItem();
    	boolean added = fire.addLog(stk);
    	if(!added)
    		added = fire.addFuel(stk);
    	
    	if(added)
    	{
    		p.inventory.decrStackSize(p.inventory.currentItem, 1);
    		if(p.openContainer != null)
    			p.openContainer.detectAndSendChanges();
    		p.inventory.markDirty();
    		return true;
    	}
    	
    	return false;
    }
    
    Icon icon;
    String textureName;

	public BlockCampfire setBlockTextureName(String tex)
	{
		textureName = tex;
		return this;
	}
    
	@Override
	public Icon getIcon(int side, int meta) {
		return icon;
	}

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return icon;
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		return Arrays.asList(this.getStateFromMeta(0),this.getStateFromMeta(1),this.getStateFromMeta(2));
	}

	@Override
	public void registerBlockIcons(IconRegister ir) {
		icon = ir.registerBlockIcon(textureName);
	}

	@Override
	public int getDCRenderID() {
		return 0x1242fe;
	}
}
