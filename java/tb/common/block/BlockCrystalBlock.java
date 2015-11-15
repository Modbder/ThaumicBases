package tb.common.block;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.CustomStepSound;
import DummyCore.Utils.IOldCubicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.crafting.IInfusionStabiliser;

public class BlockCrystalBlock extends Block implements IInfusionStabiliser, IOldCubicBlock{
	
	public static Icon[] icons = new Icon[8];
	public static final String[] names = new String[]{"air","fire","water","earth","order","entropy","mixed","tainted"};
	
	public BlockCrystalBlock()
	{
		super(Material.glass);
		setHardness(0.7F);
		setResistance(1.0F);
		setLightLevel(0.5F);
		setStepSound(new CustomStepSound("crystal", 1.0F, 1.0F));
		this.setDefaultState(BlockStateMetadata.createDefaultBlockState(this));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < 8; ++i)
			par3List.add(new ItemStack(par1,1,i));
	}
	
	public void registerBlockIcons(IconRegister ir)
	{
		for(int i = 0; i < 8; ++i)
			icons[i] = ir.registerBlockIcon("thaumicbases:crystal/"+names[i]);
	}
	
    public int damageDropped(IBlockState state)
    {
    	return BlockStateMetadata.getMetaFromState(state);
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
	public boolean canStabaliseInfusion(World paramWorld, BlockPos paramBlockPos) {
		return true;
	}
	
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
    	if(meta >= 8)
    		meta = 7;
    	return icons[meta];
    }

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return getIcon(side,BlockStateMetadata.getBlockMetadata(world, x, y, z));
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		ArrayList<IBlockState> retLst = new ArrayList<IBlockState>();
		for(int i = 0; i < 8; ++i)
			retLst.add(getStateFromMeta(i));
		return retLst;
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CUBE;
	}
	
}

