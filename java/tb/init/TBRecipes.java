package tb.init;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tb.api.BraceletState;
import tb.common.block.BlockHalfSlab;
import tb.common.block.BlockTBPlant;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.lib.utils.CropUtils;

public class TBRecipes {
	
	public static void setup()
	{
		new BraceletState.BraceletStateGeneric("iron", loc("thaumcraft","textures/blocks/base_metal.png"), ConfigItems.WAND_CAP_IRON, ConfigItems.WAND_ROD_WOOD, 50);
		new BraceletState.BraceletStateGeneric("gold", loc("thaumcraft","textures/blocks/base_gold.png"), ConfigItems.WAND_CAP_GOLD, ConfigItems.WAND_ROD_WOOD, 75);
		new BraceletState.BraceletStateGeneric("greatwood", loc("thaumcraft","textures/blocks/greatwood_plank.png"), ConfigItems.WAND_CAP_GOLD, ConfigItems.WAND_ROD_GREATWOOD, 80);
		new BraceletState.BraceletStateGeneric("brass", loc("thaumcraft","textures/blocks/base_brass.png"), ConfigItems.WAND_CAP_BRASS, ConfigItems.WAND_ROD_GREATWOOD, 90);
		new BraceletState.BraceletStateGeneric("thaumium", loc("thaumcraft","textures/blocks/thaumium_metal.png"), TBItems.WAND_CAP_THAUMINITE, TBItems.WAND_ROD_THAUMIUM, 100);
		new BraceletState.BraceletStateGeneric("silverwood", loc("thaumcraft","textures/blocks/silverwood_plank.png"), ConfigItems.WAND_CAP_THAUMIUM, ConfigItems.WAND_ROD_SILVERWOOD, 110);
		new BraceletState.BraceletStateGeneric("reed", loc("thaumicbases","textures/blocks/sugarcaneblock.png"), ConfigItems.WAND_CAP_THAUMIUM, ConfigItems.WAND_ROD_REED, 110);
		new BraceletState.BraceletStateGeneric("bone", loc("thaumicbases","textures/blocks/boneblock.png"), ConfigItems.WAND_CAP_THAUMIUM, ConfigItems.WAND_ROD_BONE, 110);
		new BraceletState.BraceletStateGeneric("obsidian", loc("minecraft","textures/blocks/obsidian.png"), ConfigItems.WAND_CAP_THAUMIUM, ConfigItems.WAND_ROD_OBSIDIAN, 110);
		new BraceletState.BraceletStateGeneric("blaze", loc("thaumicbases","textures/blocks/blazeblock.png"), ConfigItems.WAND_CAP_THAUMIUM, ConfigItems.WAND_ROD_BLAZE, 110);
		new BraceletState.BraceletStateGeneric("ice", loc("minecraft","textures/blocks/ice_packed.png"), ConfigItems.WAND_CAP_THAUMIUM, ConfigItems.WAND_ROD_ICE, 110);
		new BraceletState.BraceletStateGeneric("quartz", loc("minecraft","textures/blocks/quartz_block_bottom.png"), ConfigItems.WAND_CAP_THAUMIUM, ConfigItems.WAND_ROD_QUARTZ, 110);
		new BraceletState.BraceletStateGeneric("void", loc("thaumicbases","textures/blocks/voidblock.png"), ConfigItems.WAND_CAP_THAUMIUM, TBItems.WAND_ROD_VOID, 150);
		new BraceletState.BraceletStateGeneric("primal", loc("thaumcraft","textures/blocks/eldritch_door.png"), ConfigItems.WAND_CAP_VOID, ConfigItems.STAFF_ROD_PRIMAL, 225);
	
		OreDictionary.registerOre("obsidian", Blocks.obsidian);
		
		ShapelessOreRecipe qBl = new ShapelessOreRecipe(new ItemStack(TBBlocks.quicksilverBlock), new Object[]{"quicksilver","quicksilver","quicksilver","quicksilver","quicksilver","quicksilver","quicksilver","quicksilver","quicksilver"});
		ShapelessOreRecipe qSi = new ShapelessOreRecipe(new ItemStack(ItemsTC.quicksilver, 9, 0),new Object[]{"blockQuicksilver"});
		ShapelessOreRecipe qBr = new ShapelessOreRecipe(new ItemStack(TBBlocks.quicksilverBrick, 4, 0),new Object[]{"blockQuicksilver","blockQuicksilver","blockQuicksilver","blockQuicksilver"});
		ShapelessOreRecipe smB = new ShapelessOreRecipe(new ItemStack(ItemsTC.salisMundus,9,0),new Object[]{"blockSalisMundus"});
		ShapelessOreRecipe tnU = new ShapelessOreRecipe(new ItemStack(TBItems.resource,9,0),new Object[]{new ItemStack(TBItems.resource,1,1)});
		ShapelessOreRecipe tnI = new ShapelessOreRecipe(new ItemStack(TBItems.resource,1,1),new Object[]{new ItemStack(TBItems.resource,1,0),new ItemStack(TBItems.resource,1,0),new ItemStack(TBItems.resource,1,0),new ItemStack(TBItems.resource,1,0),new ItemStack(TBItems.resource,1,0),new ItemStack(TBItems.resource,1,0),new ItemStack(TBItems.resource,1,0),new ItemStack(TBItems.resource,1,0),new ItemStack(TBItems.resource,1,0)});
		ShapelessOreRecipe tnB = new ShapelessOreRecipe(new ItemStack(TBBlocks.thauminiteBlock, 1, 0),new Object[]{"ingotThauminite","ingotThauminite","ingotThauminite","ingotThauminite","ingotThauminite","ingotThauminite","ingotThauminite","ingotThauminite","ingotThauminite"});
		ShapelessOreRecipe tiB = new ShapelessOreRecipe(new ItemStack(TBItems.resource,9,1),new Object[]{TBBlocks.thauminiteBlock});
		ShapelessOreRecipe enP = new ShapelessOreRecipe(new ItemStack(TBBlocks.planks,4,0),new Object[]{new ItemStack(TBBlocks.genLogs,1,2)});
		ShapelessOreRecipe goP = new ShapelessOreRecipe(new ItemStack(TBBlocks.planks,4,1),new Object[]{new ItemStack(TBBlocks.genLogs,1,3)});
		ShapelessOreRecipe peP = new ShapelessOreRecipe(new ItemStack(TBBlocks.planks,4,2),new Object[]{new ItemStack(TBBlocks.genLogs,1,0)});
		ShapelessOreRecipe neP = new ShapelessOreRecipe(new ItemStack(TBBlocks.planks,4,3),new Object[]{new ItemStack(TBBlocks.genLogs,1,1)});
		
		ShapedOreRecipe eAr = new ShapedOreRecipe(new ItemStack(TBBlocks.eldritchArk,5,0),new Object[]{
			"@#@",
			"###",
			"@#@",
			'@',"nuggetGold",
			'#',"obsidian"
		});
		
		ShapedOreRecipe iGw = new ShapedOreRecipe(new ItemStack(TBBlocks.ironGreatwood,5,0),new Object[]{
			"@#@",
			"###",
			"@#@",
			'@',"nuggetIron",
			'#',new ItemStack(BlocksTC.plank,1,0)
		});
		
		GameRegistry.addSmelting(new ItemStack(TBBlocks.genLogs,1,0), new ItemStack(Items.coal,1,1), 0.15F);
		GameRegistry.addSmelting(new ItemStack(TBBlocks.genLogs,1,1), new ItemStack(Items.coal,1,1), 0.15F);
		GameRegistry.addSmelting(new ItemStack(TBBlocks.genLogs,1,2), new ItemStack(Items.coal,1,1), 0.15F);
		
		recipes.put("quicksilverBlock", qBl);
		recipes.put("quicksilver", qSi);
		recipes.put("quicksilverBrick", qBr);
		recipes.put("salisMundusFromBlock", smB);
		recipes.put("thauminiteNugget", tnU);
		recipes.put("thauminiteIngot", tnI);
		recipes.put("thauminiteIngotFromBlock", tiB);
		recipes.put("thauminiteBlock", tnB);
		recipes.put("eldritchArk", eAr);
		recipes.put("ironGreatwood", iGw);
		recipes.put("enderPlanks", enP);
		recipes.put("goldenOakPlanks", goP);
		recipes.put("peacefullTreePlanks", peP);
		recipes.put("netherTreePlanks", neP);
		GameRegistry.addRecipe(qBl);
		GameRegistry.addRecipe(qSi);
		GameRegistry.addRecipe(qBr);
		GameRegistry.addRecipe(smB);
		GameRegistry.addRecipe(tnU);
		GameRegistry.addRecipe(tnI);
		GameRegistry.addRecipe(tiB);
		GameRegistry.addRecipe(tnB);
		GameRegistry.addRecipe(eAr);
		GameRegistry.addRecipe(iGw);
		GameRegistry.addRecipe(enP);
		GameRegistry.addRecipe(goP);
		GameRegistry.addRecipe(peP);
		GameRegistry.addRecipe(neP);
		
		for(int i = 0; i < BlockHalfSlab.parents.length; ++i)
		{
			ShapedOreRecipe slabRec = new ShapedOreRecipe(new ItemStack(TBBlocks.genericSlab,6,i),new Object[]{
				"###",
				'#',BlockHalfSlab.parents[i]
			});
			slabs.add(slabRec);
			GameRegistry.addRecipe(slabRec);
		}
		
		for(int i = 0; i < 8; ++i)
		{
			ShapedOreRecipe slabRec = new ShapedOreRecipe(new ItemStack(TBBlocks.crystalSlab,6,i),new Object[]{
				"###",
				'#',new ItemStack(TBBlocks.crystalBlock,1,i)
			});
			slabs.add(slabRec);
			GameRegistry.addRecipe(slabRec);
		}
		
		for(int i = 0; i < 4; ++i)
		{
			ShapedOreRecipe slabRec = new ShapedOreRecipe(new ItemStack(TBBlocks.woodSlab,6,i),new Object[]{
				"###",
				'#',new ItemStack(TBBlocks.planks,1,i)
			});
			slabs.add(slabRec);
			GameRegistry.addRecipe(slabRec);
		}
		
		BlockTBPlant.class.cast(TBBlocks.plax).dropItem = new ItemStack(Items.string,3,0);
		BlockTBPlant.class.cast(TBBlocks.plax).dropSeed = new ItemStack(TBItems.plaxSeed,1,0);
		
		BlockTBPlant.class.cast(TBBlocks.metalleat).dropItem = new ItemStack(ItemsTC.nuggets,2,0);
		BlockTBPlant.class.cast(TBBlocks.metalleat).dropSeed = new ItemStack(TBItems.metalleatSeeds,1,0);
		
		BlockTBPlant.class.cast(TBBlocks.lucrite).dropItem = new ItemStack(Items.gold_nugget,3,0);
		BlockTBPlant.class.cast(TBBlocks.lucrite).dropSeed = new ItemStack(TBItems.lucriteSeeds,1,0);
		
		BlockTBPlant.class.cast(TBBlocks.knose).dropSeed = new ItemStack(TBItems.knoseSeed,1,0);
		
		BlockTBPlant.class.cast(TBBlocks.sweed).dropSeed = new ItemStack(TBItems.sweedSeeds,1,0);
		
		BlockTBPlant.class.cast(TBBlocks.lazullia).dropItem = new ItemStack(Items.dye,1,4);
		BlockTBPlant.class.cast(TBBlocks.lazullia).dropSeed = new ItemStack(TBItems.lazulliaSeeds,1,0);
		
		BlockTBPlant.class.cast(TBBlocks.ashroom).dropSeed = new ItemStack(TBBlocks.ashroom,1,0);
		
		BlockTBPlant.class.cast(TBBlocks.glieonia).dropItem = new ItemStack(Items.glowstone_dust,2,0);
		BlockTBPlant.class.cast(TBBlocks.glieonia).dropSeed = new ItemStack(TBItems.glieoniaSeed,1,0);
		
		BlockTBPlant.class.cast(TBBlocks.tobacco).dropItem = new ItemStack(TBItems.resource,1,7);
		BlockTBPlant.class.cast(TBBlocks.tobacco).dropSeed = new ItemStack(TBItems.tobaccoSeeds,1,0);
		
		BlockTBPlant.class.cast(TBBlocks.voidPlant).dropItem = new ItemStack(ItemsTC.voidSeed,1,0);
		BlockTBPlant.class.cast(TBBlocks.voidPlant).dropSeed = new ItemStack(TBItems.voidSeed,1,0);
		
		EntityEnderman.setCarriable(TBBlocks.genLogs, true);
		
		CropUtils.addStandardCrop(new ItemStack(TBBlocks.aureliaPetal), 0);
		CropUtils.addStandardCrop(new ItemStack(TBBlocks.briar), 7);
		CropUtils.addStandardCrop(new ItemStack(TBBlocks.glieonia), 3);
		CropUtils.addStandardCrop(new ItemStack(TBBlocks.knose), 3);
		CropUtils.addStandardCrop(new ItemStack(TBBlocks.lazullia), 7);
		CropUtils.addStandardCrop(new ItemStack(TBBlocks.lucrite), 7);
		CropUtils.addStandardCrop(new ItemStack(TBBlocks.metalleat), 7);
		CropUtils.addStandardCrop(new ItemStack(TBBlocks.plax), 7);
		CropUtils.addStandardCrop(new ItemStack(TBBlocks.sweed), 3);
		CropUtils.addStandardCrop(new ItemStack(TBBlocks.tobacco), 7);
		CropUtils.addStandardCrop(new ItemStack(TBBlocks.voidPlant), 7);
	}	

	public static final ResourceLocation loc(String domain, String path)
	{
		return new ResourceLocation(domain,path);
	}
	
	public static final Hashtable<String, IRecipe> recipes = new Hashtable<String, IRecipe>();
	
	public static final List<ShapedOreRecipe> slabs = new ArrayList<ShapedOreRecipe>();
}
