package tb.common.block;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Client.IRotationProvider;
import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.BlockStateMetadata;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tb.core.TBCore;
import thaumcraft.api.crafting.IInfusionStabiliser;

public class BlockThaumicAnvil extends Block implements IInfusionStabiliser, IRotationProvider
{
    public BlockThaumicAnvil() {
		super(Material.iron);
		this.setLightOpacity(0);
	}

	public static final String[] anvilDamageNames = new String[] {"intact", "slightlyDamaged", "veryDamaged"};
    private static final String[] anvilIconNames = new String[] {"top_damaged_0", "top_damaged_1", "top_damaged_2"};
    public Icon[] anvilIcons;
    public Icon blockIcon;
    public String texture;
    
	public BlockThaumicAnvil setBlockName(String name)
	{
		this.setUnlocalizedName(name);
		return this;
	}
	
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        EnumFacing enumfacing1 = placer.getHorizontalFacing().rotateY();
        if(enumfacing1 == EnumFacing.NORTH || enumfacing1 == EnumFacing.SOUTH)
        	return getStateFromMeta(meta);
        
        return getStateFromMeta(3+meta);
    }
    
	public BlockThaumicAnvil setBlockTextureName(String tex)
	{
		texture = tex;
		return this;
	}
	
	public String getTextureName()
	{
		return texture;
	}
    
    public Icon getIcon(int side, int meta)
    {
    	if(side == 1)
    		return anvilIcons[meta % 3];
    	
    	return blockIcon;
    }
    
    public void registerBlockIcons(IconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerBlockIcon(this.getTextureName()+"base");
        this.anvilIcons = new Icon[anvilIconNames.length];

        for (int i = 0; i < this.anvilIcons.length; ++i)
        {
            this.anvilIcons[i] = p_149651_1_.registerBlockIcon(this.getTextureName()+anvilIconNames[i]);
        }
    }
    
    public boolean isFullCube()
    {
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public int damageDropped(IBlockState state)
    {
    	int meta = BlockStateMetadata.getMetaFromState(state);
    	return meta >= 3 ? meta - 3 : meta;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(itemIn, 1, 0));
        list.add(new ItemStack(itemIn, 1, 1));
        list.add(new ItemStack(itemIn, 1, 2));
    }
    
	@Override
	public boolean canStabaliseInfusion(World w, BlockPos pos) 
	{
		return true;
	}

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return this.getIcon(side, BlockStateMetadata.getBlockMetadata(world, x, y, z));
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		ArrayList<IBlockState> retLst = new ArrayList<IBlockState>();
		for(int i = 0; i < 6; ++i)
			retLst.add(getStateFromMeta(i));
		
		return retLst;
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_ANVIL;
	}

	@Override
	public EnumFacing getRotation(IBlockAccess world, int x, int y, int z, IBlockState state) {
		return BlockStateMetadata.getBlockMetadata(world, x, y, z) < 3 ? EnumFacing.NORTH : EnumFacing.WEST;
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
    
    public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	if(w.isRemote)
    		return true;
    	
    	p.openGui(TBCore.instance, 0x421921, w, pos.getX(),pos.getY(),pos.getZ());
    	
    	return true;
    }
	
}
