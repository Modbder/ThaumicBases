package tb.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentElderKnowledge extends Enchantment{

	public EnchantmentElderKnowledge(int id, int weight) 
	{
		super(id, new ResourceLocation("tb.elderKnowledge"), weight,EnumEnchantmentType.WEAPON);
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
