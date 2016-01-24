package tb.common.item;

import tb.api.ITobacco;
import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Utils.IOldItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemSmokingPipe extends Item implements IOldItem
{
	public boolean isSilverwood;
	public Icon texture;
	public String textureName;
	
	public ItemSmokingPipe(boolean silverwood)
	{
		super();
		isSilverwood = silverwood;
		this.setFull3D();
		this.setMaxStackSize(1);
	}
	
	public ItemSmokingPipe setTextureName(String s)
	{
		textureName = s;
		return this;
	}
	
	public ItemStack getTobacco(EntityPlayer p)
	{
		for(int i = 0; i < p.inventory.getSizeInventory(); ++i)
		{
			ItemStack stk = p.inventory.getStackInSlot(i);
			if(stk != null && stk.getItem() != null && stk.getItem() instanceof ITobacco)
				return stk;
		}
		return null;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer player)
	{
		if(this.getTobacco(player) != null)
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
	    return stack;
	}
	    
	public EnumAction getItemUseAction(ItemStack stack)
	{
	    return EnumAction.BOW;
	}

	public int getMaxItemUseDuration(ItemStack stack)
	{
	    return 64;
	}
	
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
    	Vec3 look = player.getLookVec();
    	double x = player.posX+look.xCoord/5;
    	double y = player.posY+player.getEyeHeight()+look.yCoord/5;
    	double z = player.posZ+look.zCoord/5;
    	if(count < 32)
    		player.worldObj.spawnParticle(isSilverwood ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, x, y, z, look.xCoord/10, look.yCoord/10, look.zCoord/10);
    }
	 
    @Override
	public ItemStack onItemUseFinish(ItemStack stack, World w, EntityPlayer player)
	{
		ItemStack tobacco = getTobacco(player);
		if(tobacco == null)
			return stack;
		ITobacco t = ITobacco.class.cast(tobacco.getItem());
		t.performTobaccoEffect(player, tobacco.getItemDamage(), isSilverwood);
		for(int i = 0; i < player.inventory.getSizeInventory(); ++i)
		{
			ItemStack stk = player.inventory.getStackInSlot(i);
			if(stk != null && stk.getItem() != null && stk.getItem() instanceof ITobacco)
			{
				player.inventory.decrStackSize(i, 1);
				break;
			}
		}
		Vec3 look = player.getLookVec();
		for(int i = 0; i < 100; ++i)
		{
	    	double x = player.posX+look.xCoord/5;
	    	double y = player.posY+player.getEyeHeight()+look.yCoord/5;
	    	double z = player.posZ+look.zCoord/5;
	    	
	    	player.worldObj.spawnParticle(isSilverwood ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, x, y, z, look.xCoord/10, look.yCoord/10, look.zCoord/10);
		}
		
		return stack;
	}

	@Override
	public Icon getIconFromDamage(int meta) {
		return texture;
	}

	@Override
	public Icon getIconFromItemStack(ItemStack stk) {
		return getIconFromDamage(stk.getMetadata());
	}

	@Override
	public void registerIcons(IconRegister reg) {
		texture = reg.registerItemIcon(textureName);
	}

	@Override
	public int getRenderPasses(ItemStack stk) {
		return 0;
	}

	@Override
	public Icon getIconFromItemStackAndRenderPass(ItemStack stk, int pass) {
		return getIconFromItemStack(stk);
	}

	@Override
	public boolean recreateIcon(ItemStack stk) {
		return false;
	}

	@Override
	public boolean render3D(ItemStack stk) {
		return false;
	}
	

}
