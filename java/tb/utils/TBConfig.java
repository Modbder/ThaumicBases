package tb.utils;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.config.Configuration;
import DummyCore.Utils.IDummyConfig;

public class TBConfig implements IDummyConfig{

	@Override
	public void load(Configuration config) {
		cfg = config;
		elderKnowledgeID = cfg.getInt("elderKnowledgeEnchantmentID", "Enchantments", 98, 0, Enchantment.enchantmentsList.length, "");
		eldritchBaneID = cfg.getInt("eldritchBaneEnchantmentID", "Enchantments", 99, 0, Enchantment.enchantmentsList.length, "");
		magicTouchID = cfg.getInt("magicTouchEnchantmentID", "Enchantments", 100, 0, Enchantment.enchantmentsList.length, "");
		taintedID = cfg.getInt("taintedEnchantmentID", "Enchantments", 101, 0, Enchantment.enchantmentsList.length, "");
		vaporisingID = cfg.getInt("vaporisingEnchantmentID", "Enchantments", 102, 0, Enchantment.enchantmentsList.length, "");
	
		aquaticFociUID = cfg.getInt("aquaticFociUID", "Foci", 42, 0, Integer.MAX_VALUE, "");
		nethericFociUID = cfg.getInt("nethericFociUID", "Foci", 43, 0, Integer.MAX_VALUE, "");
		decomposingFociUID = cfg.getInt("decomposingFociUID", "Foci", 44, 0, Integer.MAX_VALUE, "");
		vaporisingFociUID = cfg.getInt("vaporisingFociUID", "Foci", 45, 0, Integer.MAX_VALUE, "");
		calmingFociUID = cfg.getInt("calmingFociUID", "Foci", 46, 0, Integer.MAX_VALUE, "");
		crystalizationFociUID = cfg.getInt("crystalizationFociUID", "Foci", 47, 0, Integer.MAX_VALUE, "");
		warpingFociUID = cfg.getInt("warpingFociUID", "Foci", 48, 0, Integer.MAX_VALUE, "");
	}
	
	static Configuration cfg;
	
	public static int elderKnowledgeID;
	public static int eldritchBaneID;
	public static int magicTouchID;
	public static int taintedID;
	public static int vaporisingID;
	
	public static int aquaticFociUID;
	public static int nethericFociUID;
	public static int decomposingFociUID;
	public static int vaporisingFociUID;
	public static int calmingFociUID;
	public static int crystalizationFociUID;
	public static int warpingFociUID;

}
