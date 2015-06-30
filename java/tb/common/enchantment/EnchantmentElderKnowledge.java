package tb.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentElderKnowledge extends Enchantment{

	public EnchantmentElderKnowledge(int id, int weight,	EnumEnchantmentType type) 
	{
		super(id, weight, type);
	}

    public int getMinEnchantability(int lvl)
    {
        return 1 + lvl * 5;
    }
	
    public int getMaxLevel()
    {
        return 5;
    }
    
    public boolean canApplyTogether(Enchantment ench)
    {
    	return !(ench instanceof EnchantmentElderKnowledge) && !(ench instanceof EnchantmentMagicTouch) && !(ench instanceof EnchantmentTainted) && !(ench instanceof EnchantmentVaporising);
    }

}
