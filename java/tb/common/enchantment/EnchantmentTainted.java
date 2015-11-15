package tb.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentTainted extends Enchantment{

	public EnchantmentTainted(int id, int weight) 
	{
		super(id, new ResourceLocation("tb.tainted"), weight,EnumEnchantmentType.WEAPON);
	}

    public int getMaxLevel()
    {
        return 3;
    }
    
    public boolean canApplyTogether(Enchantment ench)
    {
    	return !(ench instanceof EnchantmentElderKnowledge) && !(ench instanceof EnchantmentMagicTouch) && !(ench instanceof EnchantmentTainted) && !(ench instanceof EnchantmentVaporising);
    }

    public boolean canApply(ItemStack p_92089_1_)
    {
    	return false;
    }

}
