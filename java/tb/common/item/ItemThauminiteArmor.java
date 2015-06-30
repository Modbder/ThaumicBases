package tb.common.item;

import java.util.List;

import thaumcraft.api.IRepairable;
import thaumcraft.api.IRunicArmor;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemThauminiteArmor extends ItemArmor implements IRepairable, IVisDiscountGear, IRunicArmor{

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + ": " + getVisDiscount(stack, player, null) + "%");
		super.addInformation(stack, player, list, par4);
	}
}
