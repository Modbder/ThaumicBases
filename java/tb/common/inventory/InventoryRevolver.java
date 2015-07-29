package tb.common.inventory;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.blocks.ItemJarFilled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryRevolver implements IInventory
{
	public ItemStack[] inventory;
	public Container revolverContainer;
	
	public InventoryRevolver(Container revolver)
	{
		this.inventory = new ItemStack[1];
		this.revolverContainer = revolver;
	}
	
	public int getSizeInventory()
	{
		return 1;
	}
	
	public ItemStack getStackInSlot(int slot)
	{
		return inventory[0];
	}
	
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if (this.inventory[0] != null)
		{
			ItemStack stk = this.inventory[0];
			this.inventory[0] = null;
			return stk;
		}
		return null;
	}
	
	public ItemStack decrStackSize(int slot, int amount)
	{
		if (inventory[0] != null)
		{
			if (inventory[0].stackSize <= amount)
			{
				ItemStack stk = inventory[0];
				inventory[0] = null;
				revolverContainer.onCraftMatrixChanged(this);
				return stk;
			}
			
			ItemStack var3 = this.inventory[0].splitStack(amount);
			
			if (inventory[0].stackSize == 0)
			{
				inventory[0] = null;
			}
			
			revolverContainer.onCraftMatrixChanged(this);
			return var3;
		}
		return null;
	}
	
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inventory[0] = stack;
		revolverContainer.onCraftMatrixChanged(this);
	}
	
	public int getInventoryStackLimit()
	{
		return 1;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}
	
	public boolean isItemValidForSlot(int slot, ItemStack jar)
	{
		if (jar != null && jar.getItem() instanceof ItemJarFilled && jar.hasTagCompound()) 
		{
			AspectList aspects = ((ItemJarFilled)jar.getItem()).getAspects(jar);
			if (aspects != null && aspects.size() > 0 && aspects.getAmount(Aspect.WEAPON) > 0) 
				return true;
		}
		return false;
	}
	
	public String getInventoryName()
	{
		return "container.revolver";
	}
	
	public boolean hasCustomInventoryName()
	{
		return false;
	}
	
	public void openInventory(){}
	
	public void closeInventory(){}

	@Override
	public void markDirty(){}
	
}
