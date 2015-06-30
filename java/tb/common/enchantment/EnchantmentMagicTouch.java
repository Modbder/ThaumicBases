package tb.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentMagicTouch extends Enchantment{

	public EnchantmentMagicTouch(int id, int weight,	EnumEnchantmentType type) 
	{
		super(id, weight, type);
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
