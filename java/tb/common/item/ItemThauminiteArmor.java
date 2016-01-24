package tb.common.item;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Utils.IOldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.items.IRepairable;
import thaumcraft.api.items.IRunicArmor;
import thaumcraft.api.items.IVisDiscountGear;

public class ItemThauminiteArmor extends ItemArmor implements IRepairable, IVisDiscountGear, IRunicArmor, IOldItem{

	int aType;
	
	public ItemThauminiteArmor(ArmorMaterial mat,int aType) {
		super(mat, 0, aType);
		this.aType = aType;
	}
	
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
    	return slot == 2 ? "thaumicbases:textures/items/armor/thauminite/thauminite_2.png" : "thaumicbases:textures/items/armor/thauminite/thauminite_1.png";
    }

	@Override
	public int getVisDiscount(ItemStack stack, EntityPlayer player,
			Aspect aspect) {
		return discount[aType];
	}
	
	static final int[] discount = new int[]{5,2,3,1};

	@Override
	public int getRunicCharge(ItemStack itemstack) {
		return 0;
	}

    Icon icon;
    String textureName;
    
	public Item setTextureName(String s)
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
