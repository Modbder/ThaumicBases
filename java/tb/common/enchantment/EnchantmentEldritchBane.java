package tb.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentEldritchBane extends Enchantment{

	public EnchantmentEldritchBane(int id, int weight,	EnumEnchantmentType type) 
	{
		super(id, weight, type);
	}
    
    public int getMaxLevel()
    {
        return 5;
    }
    
    public int getMinEnchantability(int lvl)
    {
        return 1 + lvl * 5;
    }
    
    public boolean canApplyTogether(Enchantment ench)
    {
    	return this != ench && ench != Enchantment.smite && ench != Enchantment.baneOfArthropods && ench != Enchantment.sharpness && !(ench instanceof EnchantmentDamage);
    }

}
