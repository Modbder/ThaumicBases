package tb.common.item;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Utils.IOldItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMortarAndPestle extends Item implements IOldItem{
	
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

    Icon icon;
    String textureName;
    
	public ItemMortarAndPestle setTextureName(String s)
	{
		textureName = s;
		return this;
	}

	@Override
	public Icon getIconFromDamage(int meta) {
		return icon;
	}

	@Override
	public Icon getIconFromItemStack(ItemStack stk) {
		return getIconFromDamage(stk.getMetadata());
	}

	@Override
	public void registerIcons(IconRegister reg) {
		icon = reg.registerItemIcon(textureName);
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
