package tb.init;

import net.minecraft.util.ResourceLocation;
import tb.utils.TBConfig;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;

public class TBFociUpgrades {
	
	public static void init()
	{
		
	}
	
	public static FocusUpgradeType aquatic = new FocusUpgradeType(TBConfig.aquaticFociUID, new ResourceLocation("thaumicbases","textures/items/foci/drain/aquatic.png"), "tb.foci.aquatic.name", "tb.foci.aquatic.desc", new AspectList().add(Aspect.WATER, 16).add(Aspect.ORDER, 16));
	public static FocusUpgradeType netheric = new FocusUpgradeType(TBConfig.nethericFociUID, new ResourceLocation("thaumicbases","textures/items/foci/drain/netheric.png"), "tb.foci.netheric.name", "tb.foci.netheric.desc", new AspectList().add(Aspect.FIRE, 16).add(Aspect.ENTROPY, 16));
	public static FocusUpgradeType decomposing = new FocusUpgradeType(TBConfig.decomposingFociUID, new ResourceLocation("thaumicbases","textures/items/foci/experience/decomposing.png"), "tb.foci.decomposing.name", "tb.foci.decomposing.desc", new AspectList().add(Aspect.ENTROPY, 16).add(Aspect.EARTH, 16));
	public static FocusUpgradeType vaporizing = new FocusUpgradeType(TBConfig.vaporisingFociUID, new ResourceLocation("thaumicbases","textures/items/foci/experience/vaporizing.png"), "tb.foci.vaporizing.name", "tb.foci.vaporizing.desc", new AspectList().add(Aspect.ENTROPY, 16).add(Aspect.ORDER, 16).add(Aspect.AIR, 16));
	public static FocusUpgradeType warping = new FocusUpgradeType(TBConfig.warpingFociUID, new ResourceLocation("thaumicbases","textures/items/foci/flux/warping.png"), "tb.foci.warping.name", "tb.foci.warping.desc", new AspectList().add(Aspect.ENTROPY, 16).add(Aspect.FIRE, 16).add(Aspect.AIR, 16));
	public static FocusUpgradeType crystalization = new FocusUpgradeType(TBConfig.crystalizationFociUID, new ResourceLocation("thaumicbases","textures/items/foci/flux/crystalization.png"), "tb.foci.crystalization.name", "tb.foci.crystalization.desc", new AspectList().add(Aspect.ORDER, 16).add(Aspect.EARTH, 16).add(Aspect.WATER, 16));
	public static FocusUpgradeType calming = new FocusUpgradeType(TBConfig.calmingFociUID, new ResourceLocation("thaumicbases","textures/items/foci/flux/calming.png"), "tb.foci.calming.name", "tb.foci.calming.desc", new AspectList().add(Aspect.WATER, 16).add(Aspect.ORDER, 16).add(Aspect.EARTH, 16));
	
}
