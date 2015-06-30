package tb.common.block;

import java.util.List;
import java.util.UUID;

import tb.init.TBItems;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.armor.ItemVoidArmor;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

public class BlockSpike extends Block{

	public static final String[] spikeNames = new String[]{
		"iron",
		"iron_bloody",
		"thaumic",
		"thaumic_bloody",
		"void",
		"void_bloody"
	};
	
	public static IIcon[] icons = new IIcon[spikeNames.length];
	
	public BlockSpike() 
	{
		super(Material.iron);
		float f = 0.0625F;
		this.setBlockBounds(0, 0, 0, 1, 1-f, 1);
	}

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return 1;
    }
    
    public void onNeighborBlockChange(World w, int x, int y, int z, Block changed)
    {
    	if(w.isAirBlock(x, y-1, z))
    	{
    		this.dropBlockAsItem(w, x, y, z, w.getBlockMetadata(x, y, z), 0);
    		w.setBlockToAir(x, y, z);
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	return icons[meta];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
    	for(int i = 0; i < icons.length; ++i)
    		icons[i] = reg.registerIcon("thaumicbases:spike/"+spikeNames[i]);
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1,1,0));
		par3List.add(new ItemStack(par1,1,2));
		par3List.add(new ItemStack(par1,1,4));
	}
	
	public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity collider) 
	{
		int meta = w.getBlockMetadata(x, y, z);
		if(meta == 0 || meta == 1)
		{
			collider.attackEntityFrom(DamageSource.cactus, 8);
			
			if(meta == 0 && collider instanceof EntityLivingBase && ((EntityLivingBase) collider).getHealth() <= 0)
			{
				w.setBlockMetadataWithNotify(x, y, z, 1, 3);
			}
		}
		if(meta == 2 || meta == 3)
		{
			if(w.isBlockIndirectlyGettingPowered(x, y, z))
				return;
			
			if(!(collider instanceof EntityItem))
				collider.attackEntityFrom(DamageSource.cactus, 14);
			
			if(meta == 2 && collider instanceof EntityLivingBase && ((EntityLivingBase) collider).getHealth() <= 0)
			{
				w.setBlockMetadataWithNotify(x, y, z, 3, 3);
			}
		}
		if(meta == 4 || meta == 5)
		{
			if(w.isBlockIndirectlyGettingPowered(x, y, z))
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
					FakePlayer fake = new FakePlayer((WorldServer) w, fakeSpikeProfile);
					collider.attackEntityFrom(DamageSource.causePlayerDamage(fake), 20);
					fake.setDead();
					fake = null;
				}
			}
			
			if(meta == 4 && collider instanceof EntityLivingBase && ((EntityLivingBase) collider).getHealth() <= 0)
			{
				w.setBlockMetadataWithNotify(x, y, z, 5, 3);
			}
		}
	}
	
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ)
    {
    	int meta = w.getBlockMetadata(x, y, z);
    	if(meta != 1 && meta != 3 && meta != 5)
    		return false;
    	
    	ItemStack cI = player.getCurrentEquippedItem();
    	if(cI == null)
    		return false;
    	
    	if(cI.getItem() != ConfigItems.itemResource)
    		return false;
    	
    	if(cI.getItemDamage() != 7)
    		return false;
    	
    	player.inventory.decrStackSize(player.inventory.currentItem, 1);
    	if(!player.inventory.addItemStackToInventory(new ItemStack(TBItems.resource,1,8)))
    		player.dropPlayerItemWithRandomChoice(new ItemStack(TBItems.resource,1,8), false);
    	
    	w.setBlockMetadataWithNotify(x, y, z, meta-1, 3);
    	
        return true;
    }
	
	public int damageDropped(int par1)
	{
		return par1 == 1 ? 0 : par1 == 3 ? 2 : par1 == 5 ? 4 : par1;
	}
	
	public static final GameProfile fakeSpikeProfile = new GameProfile(UUID.randomUUID(),"[TB]Spikes");
}
