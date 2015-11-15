package tb.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentVaporising extends Enchantment{

	public EnchantmentVaporising(int id, int weight) 
	{
		super(id, new ResourceLocation("tb.elderKnowledge"), weight,EnumEnchantmentType.WEAPON);
	}

    public int getMaxLevel()
    {
        return 3;
    }
    
    public boolean canApplyTogether(Enchantment ench)
    {
    	return !(ench instanceof EnchantmentElderKnowledge) && !(ench instanceof EnchantmentMagicTouch) && !(ench instanceof EnchantmentTainted) && !(ench instanceof EnchantmentVaporising);
    }

}
