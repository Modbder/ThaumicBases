package tb.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMortarAndPestle extends Item{
	
	public ItemMortarAndPestle()
	{
		this.setMaxDamage(256);
		this.setMaxStackSize(1);
	}
	
    public boolean hasContainerItem(ItemStack stack)
    {
    	return !(stack.getItemDamage() >= stack.getMaxDamage());
    }
	
    public ItemStack getContainerItem(ItemStack itemStack)
    {
    	return itemStack.getItemDamage() >= itemStack.getMaxDamage() ? null : new ItemStack(itemStack.getItem(),1,itemStack.getItemDamage()+1);
    }
    
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack)
    {
    	return false;
    }

}
