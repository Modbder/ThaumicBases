package tb.common.item;

import tb.api.ITobacco;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemSmokingPipe extends Item
{
	public boolean isSilverwood;
	
	public ItemSmokingPipe(boolean silverwood)
	{
		super();
		isSilverwood = silverwood;
		this.setFull3D();
		this.setMaxStackSize(1);
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
	    return EnumAction.bow;
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
    		player.worldObj.spawnParticle(isSilverwood ? "explode" : "smoke", x, y, z, look.xCoord/10, look.yCoord/10, look.zCoord/10);
    }
	    
	public ItemStack onEaten(ItemStack stack, World w, EntityPlayer player)
	{
		ItemStack tobacco = getTobacco(player);
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
	    	
	    	player.worldObj.spawnParticle(isSilverwood ? "explode" : "smoke", x, y, z, look.xCoord/10, look.yCoord/10, look.zCoord/10);
		}
		
		return stack;
	}
	

}
