package tb.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tb.init.TBItems;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.armor.ItemVoidArmor;

public class BlockSpike extends Block implements IOldCubicBlock{

	public static final String[] spikeNames = new String[]{
		"iron",
		"iron_bloody",
		"thaumic",
		"thaumic_bloody",
		"void",
		"void_bloody"
	};
	
	public static Icon[] icons = new Icon[spikeNames.length];
	
	public BlockSpike() 
	{
		super(Material.iron);
		float f = 0.0625F;
		this.setBlockBounds(0, 0, 0, 1, 1-f, 1);
		this.setLightOpacity(0);
	}
	
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
    	return true;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public int damageDropped(IBlockState state)
    {
        return this.damageDropped(BlockStateMetadata.getMetaFromState(state));
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
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CROSS;
	}
	
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
    	if(worldIn.isAirBlock(pos.down()))
    	{
    		this.dropBlockAsItem(worldIn, pos, state, 0);
    		worldIn.setBlockToAir(pos);
    	}
    }
    
    public Icon getIcon(int side, int meta)
    {
    	return icons[meta];
    }
    
    public void registerBlockIcons(IconRegister reg)
    {
    	for(int i = 0; i < icons.length; ++i)
    		icons[i] = reg.registerBlockIcon("thaumicbases:spike/"+spikeNames[i]);
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1,1,0));
		par3List.add(new ItemStack(par1,1,2));
		par3List.add(new ItemStack(par1,1,4));
	}
	
	public void onEntityCollidedWithBlock(World w, BlockPos pos, Entity collider)
	{
		int meta = BlockStateMetadata.getBlockMetadata(w, pos);
		if(meta == 0 || meta == 1)
		{
			collider.attackEntityFrom(DamageSource.cactus, 8);
			
			if(meta == 0 && collider instanceof EntityLivingBase && ((EntityLivingBase) collider).getHealth() <= 0)
				w.setBlockState(pos, this.getStateFromMeta(1));
		}
		if(meta == 2 || meta == 3)
		{
			if(w.isBlockIndirectlyGettingPowered(pos) > 0 || w.isBlockIndirectlyGettingPowered(pos.down()) > 0 || w.isBlockPowered(pos))
				return;
			
			if(!(collider instanceof EntityItem))
				collider.attackEntityFrom(DamageSource.cactus, 14);
			
			if(meta == 2 && collider instanceof EntityLivingBase && ((EntityLivingBase) collider).getHealth() <= 0)
				w.setBlockState(pos, this.getStateFromMeta(3));
		}
		if(meta == 4 || meta == 5)
		{
			if(w.isBlockIndirectlyGettingPowered(pos) > 0 || w.isBlockIndirectlyGettingPowered(pos.down()) > 0 || w.isBlockPowered(pos))
				return;
			
			if(collider instanceof EntityPlayer)
			{
				ItemStack boots = ((EntityPlayer) collider).inventory.armorInventory[0];
				if(boots != null && boots.getItem() instanceof ItemVoidArmor)
					return;
			}
			
			if(!(collider instanceof EntityItem) && !(collider instanceof EntityXPOrb))
			{
				if(!w.isRemote)
				{
					FakePlayer fake = FakePlayerFactory.get((WorldServer) w, fakeSpikeProfile);
					collider.attackEntityFrom(DamageSource.causePlayerDamage(fake), 20);
					fake.setDead();
					fake = null;
				}
			}
			
			if(meta == 4 && collider instanceof EntityLivingBase && ((EntityLivingBase) collider).getHealth() <= 0)
				w.setBlockState(pos, this.getStateFromMeta(5));
		}
	}
	
    public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	int meta = BlockStateMetadata.getMetaFromState(state);
    	if(meta != 1 && meta != 3 && meta != 5)
    		return false;
    	
    	ItemStack cI = player.getCurrentEquippedItem();
    	if(cI == null)
    		return false;
    	
    	if(cI.getItem() != ItemsTC.fabric)
    		return false;
    	
    	player.inventory.decrStackSize(player.inventory.currentItem, 1);
    	if(!player.inventory.addItemStackToInventory(new ItemStack(TBItems.resource,1,8)))
    		player.dropPlayerItemWithRandomChoice(new ItemStack(TBItems.resource,1,8), false);
    	
    	w.setBlockState(pos, this.getStateFromMeta(meta-1));
    	
        return true;
    }
	
	public int damageDropped(int par1)
	{
		return par1 == 1 ? 0 : par1 == 3 ? 2 : par1 == 5 ? 4 : par1;
	}
	
	public static final GameProfile fakeSpikeProfile = new GameProfile(UUID.randomUUID(),"[TB]Spikes");

	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return this.getIcon(side, BlockStateMetadata.getBlockMetadata(world, x,y,z));
	}

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		ArrayList<IBlockState> lst = new ArrayList<IBlockState>();
		for(int i = 0; i < 6; ++i)
			lst.add(this.getStateFromMeta(i));
		return lst;
	}
}
