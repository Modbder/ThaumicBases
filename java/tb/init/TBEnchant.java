package tb.init;

import java.lang.reflect.Method;

import cpw.mods.fml.common.Loader;
import tb.common.enchantment.EnchantmentElderKnowledge;
import tb.common.enchantment.EnchantmentEldritchBane;
import tb.common.enchantment.EnchantmentMagicTouch;
import tb.common.enchantment.EnchantmentTainted;
import tb.common.enchantment.EnchantmentVaporising;
import tb.utils.TBConfig;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class TBEnchant {
	
	public static void setup()
	{
		elderKnowledge = new EnchantmentElderKnowledge(TBConfig.elderKnowledgeID,12,EnumEnchantmentType.weapon).setName("elderKnowledge");
		eldritchBane = new EnchantmentEldritchBane(TBConfig.eldritchBaneID,7,EnumEnchantmentType.weapon).setName("eldritchBane");
		magicTouch = new EnchantmentMagicTouch(TBConfig.magicTouchID,11,EnumEnchantmentType.weapon).setName("magicTouch");
		tainted = new EnchantmentTainted(TBConfig.taintedID,9,EnumEnchantmentType.weapon).setName("tainted");
		vaporising = new EnchantmentVaporising(TBConfig.vaporisingID,6,EnumEnchantmentType.weapon).setName("vaporising");

		if(Loader.isModLoaded("ThaumicTinkerer") && TBConfig.enableTTCompathability)
		{
			try
			{
				Class<?> c = Class.forName("thaumic.tinkerer.common.enchantment.core.EnchantmentManager");
				Method reg = c.getMethod("registerExponentialCostData", Enchantment.class,String.class,boolean.class,AspectList.class);
				reg.invoke(null, elderKnowledge,"thaumicbases:textures/enchantments/elder_knowledge.png",false,new AspectList().add(Aspect.AIR, 8).add(Aspect.ORDER, 12).add(Aspect.EARTH, 16));
				reg.invoke(null, eldritchBane,"thaumicbases:textures/enchantments/eldritch_bane.png",false,new AspectList().add(Aspect.FIRE, 12).add(Aspect.ENTROPY, 12).add(Aspect.ORDER, 8));
				reg.invoke(null, magicTouch,"thaumicbases:textures/enchantments/magic_touched.png",false,new AspectList().add(Aspect.FIRE, 16).add(Aspect.ORDER, 16).add(Aspect.WATER, 8).add(Aspect.ENTROPY, 12));
				reg.invoke(null, tainted,"thaumicbases:textures/enchantments/tainted.png",false,new AspectList().add(Aspect.FIRE, 12).add(Aspect.ENTROPY, 16));
				reg.invoke(null, vaporising,"thaumicbases:textures/enchantments/vaporising.png",false,new AspectList().add(Aspect.ENTROPY, 8).add(Aspect.ORDER, 12).add(Aspect.FIRE, 16));
			}
			catch(Exception e)
			{
				System.out.println("[ThaumicBases]Unable to add ThaumicTinkerer integration - mod not found");
				return;
			}
		}
	}
	
	public static Enchantment 
	elderKnowledge,
	eldritchBane,
	magicTouch,
	tainted,
	vaporising;
}
