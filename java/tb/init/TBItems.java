package tb.init;

import DummyCore.Items.ItemRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import tb.common.item.ItemBloodyArmor;
import tb.common.item.ItemConcentratedTaint;
import tb.common.item.ItemHerobrinesScythe;
import tb.common.item.ItemKnoseFragment;
import tb.common.item.ItemKnoseSeeds;
import tb.common.item.ItemMortarAndPestle;
import tb.common.item.ItemNodeFoci;
import tb.common.item.ItemPyrofluidBucket;
import tb.common.item.ItemRosehipSyrup;
import tb.common.item.ItemSmokingPipe;
import tb.common.item.ItemThauminiteArmor;
import tb.common.item.ItemThauminiteAxe;
import tb.common.item.ItemThauminiteHoe;
import tb.common.item.ItemThauminitePickaxe;
import tb.common.item.ItemThauminiteShears;
import tb.common.item.ItemThauminiteShovel;
import tb.common.item.ItemThauminiteSword;
import tb.common.item.TBResource;
import tb.common.item.TBTobacco;
import tb.common.item.foci.FociActivation;
import tb.common.item.foci.FociDrain;
import tb.common.item.foci.FociExperience;
import tb.common.item.foci.FociFlux;
import tb.core.TBCore;
import thaumcraft.api.wands.StaffRod;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.items.wands.WandRodPrimalOnUpdate;

public class TBItems {
	
	public static void setup()
	{
		ItemRegistry.registerItem(resource, "resource",core);
		
		ItemRegistry.registerItem(thauminiteAxe, "thauminiteAxe",core);
		thauminiteAxe.setHarvestLevel("axe", 3);
		ItemRegistry.registerItem(thauminiteHoe, "thauminiteHoe",core);
		thauminiteHoe.setHarvestLevel("hoe", 3);
		ItemRegistry.registerItem(thauminitePickaxe, "thauminitePickaxe",core);
		thauminitePickaxe.setHarvestLevel("pickaxe", 3);
		ItemRegistry.registerItem(thauminiteShears, "thauminiteShears",core);
		thauminiteShears.setHarvestLevel("shears", 3);
		ItemRegistry.registerItem(thauminiteShovel, "thauminiteShovel",core);
		thauminiteShovel.setHarvestLevel("shovel", 3);
		ItemRegistry.registerItem(thauminiteSword, "thauminiteSword",core);
		thauminiteSword.setHarvestLevel("sword", 3);
		
		ItemRegistry.registerItem(thauminiteHelmet, "thauminiteHelmet",core);
		ItemRegistry.registerItem(thauminiteChest, "thauminiteChest",core);
		ItemRegistry.registerItem(thauminiteLeggings, "thauminiteLeggings",core);
		ItemRegistry.registerItem(thauminiteBoots, "thauminiteBoots",core);
		ItemRegistry.registerItem(pyroBucket, "pyroBucket",core);
		ItemRegistry.registerItem(plaxSeed, "plaxSeed",core);
		ItemRegistry.registerItem(metalleatSeeds, "metalleatSeeds",core);
		ItemRegistry.registerItem(lucriteSeeds, "lucriteSeeds",core);
		ItemRegistry.registerItem(knoseFragment, "knoseFragment",core);
		ItemRegistry.registerItem(knoseSeed, "knoseSeed",core);
		ItemRegistry.registerItem(sweedSeeds, "sweedSeeds",core);
		ItemRegistry.registerItem(lazulliaSeeds, "lazulliaSeeds",core);
		ItemRegistry.registerItem(redlonSeeds, "redlonSeeds",core);
		ItemRegistry.registerItem(glieoniaSeed, "glieoniaSeed",core);
		ItemRegistry.registerItem(rosehipSyrup, "rosehipSyrup",core);
		ItemRegistry.registerItem(tobaccoSeeds, "tobaccoSeeds",core);
		ItemRegistry.registerItem(mortar, "mortar",core);
		ItemRegistry.registerItem(tobacco, "tobaccoPowder",core);
		ItemRegistry.registerItem(greatwoodPipe, "greatwoodPipe",core);
		ItemRegistry.registerItem(silverwoodPipe, "silverwoodPipe",core);
		ItemRegistry.registerItem(voidSeed, "voidSeed",core);
		
		ItemRegistry.registerItem(bloodyChest, "bloodyChest",core);
		ItemRegistry.registerItem(bloodyLeggings, "bloodyLeggings",core);
		ItemRegistry.registerItem(bloodyBoots, "bloodyBoots",core);
		
		ItemRegistry.registerItem(concentratedTaint, "concentratedTaint",core);
		
		ItemRegistry.registerItem(fociActivation, "fociActivation",core);
		ItemRegistry.registerItem(fociDrain, "fociDrain",core);
		ItemRegistry.registerItem(fociExperience, "fociExperience",core);
		ItemRegistry.registerItem(fociFlux, "fociFlux",core);
		
		ItemRegistry.registerItem(nodeFoci, "nodeFoci",core);
		ItemRegistry.registerItem(herobrinesScythe, "herobrinesScythe",core);
		
		OreDictionary.registerOre("ingotThauminite", new ItemStack(resource,1,1));
		
		WAND_CAP_THAUMINITE = new WandCap("thauminite",0.85F,new ItemStack(resource,1,2),6);
		WAND_CAP_THAUMINITE.setTexture(new ResourceLocation("thaumicbases","textures/items/thauminite/wand_cap_thauminite_uv.png"));
		
		WAND_ROD_THAUMIUM = new WandRod("tbthaumium", 80, new ItemStack(resource,1,3), 6, new WandRodPrimalOnUpdate(), new ResourceLocation("thaumicbases","textures/items/wand_rod_thaumium_uv.png"));
		WAND_ROD_VOID = new WandRod("tbvoid", 160, new ItemStack(resource,1,4), 16, new WandRodPrimalOnUpdate(), new ResourceLocation("thaumicbases","textures/items/wand_rod_void_uv.png"));
	}

	public static Item resource = new TBResource().setUnlocalizedName("resource");
	
	public static ToolMaterial thauminite = EnumHelper.addToolMaterial("THAUMINITE", 3, 974, 7F, 2.8F, 15);
	public static ArmorMaterial thauminiteA = EnumHelper.addArmorMaterial("ATHAUMINITE", 27, new int[]{3, 8, 6, 3}, 17);
	public static ArmorMaterial bloodyA = EnumHelper.addArmorMaterial("TBBLOODY", 21, new int[]{2, 6, 5, 2}, 21);
	
	public static Item thauminiteAxe = new ItemThauminiteAxe(thauminite).setUnlocalizedName("thauminiteAxe").setTextureName("thaumicbases:thauminite/thauminiteaxe");
	public static Item thauminiteHoe = new ItemThauminiteHoe(thauminite).setUnlocalizedName("thauminiteHoe").setTextureName("thaumicbases:thauminite/thauminitehoe");
	public static Item thauminitePickaxe = new ItemThauminitePickaxe(thauminite).setUnlocalizedName("thauminitePickaxe").setTextureName("thaumicbases:thauminite/thauminitepick");
	public static Item thauminiteShears = new ItemThauminiteShears().setUnlocalizedName("thauminiteShears").setTextureName("thaumicbases:thauminite/thauminiteshears").setFull3D().setMaxStackSize(1).setMaxDamage(974);
	public static Item thauminiteShovel = new ItemThauminiteShovel(thauminite).setUnlocalizedName("thauminiteShovel").setTextureName("thaumicbases:thauminite/thauminiteshovel");
	public static Item thauminiteSword = new ItemThauminiteSword(thauminite).setUnlocalizedName("thauminiteSword").setTextureName("thaumicbases:thauminite/thauminitesword");

	public static Item thauminiteHelmet = new ItemThauminiteArmor(thauminiteA,0).setUnlocalizedName("thauminiteHelmet").setTextureName("thaumicbases:thauminite/thauminitehelm");
	public static Item thauminiteChest = new ItemThauminiteArmor(thauminiteA,1).setUnlocalizedName("thauminiteChest").setTextureName("thaumicbases:thauminite/thauminitechest");
	public static Item thauminiteLeggings = new ItemThauminiteArmor(thauminiteA,2).setUnlocalizedName("thauminiteLegs").setTextureName("thaumicbases:thauminite/thauminitelegs");
	public static Item thauminiteBoots = new ItemThauminiteArmor(thauminiteA,3).setUnlocalizedName("thauminiteBoots").setTextureName("thaumicbases:thauminite/thauminiteboots");

	public static Item pyroBucket = new ItemPyrofluidBucket().setUnlocalizedName("pyrofluidBucket").setTextureName("thaumicbases:bucket_blazing_fluid");
	
	public static Item plaxSeed = new ItemSeeds(TBBlocks.plax, Blocks.farmland).setUnlocalizedName("plaxSeeds").setTextureName("thaumicbases:plax_seeds");
	public static Item metalleatSeeds = new ItemSeeds(TBBlocks.metalleat, Blocks.farmland).setUnlocalizedName("metalleatSeeds").setTextureName("thaumicbases:metalleat_seeds");
	public static Item lucriteSeeds = new ItemSeeds(TBBlocks.lucrite, Blocks.farmland).setUnlocalizedName("lucriteSeeds").setTextureName("thaumicbases:lucrite_seeds");
	public static Item knoseFragment = new ItemKnoseFragment().setUnlocalizedName("knoseFragment.").setTextureName("thaumicbases:knose/");
	public static Item knoseSeed = new ItemKnoseSeeds(TBBlocks.knose, TBBlocks.crystalBlock).setUnlocalizedName("knoseSeed").setTextureName("thaumicbases:knose_seed");
	public static Item sweedSeeds = new ItemKnoseSeeds(TBBlocks.sweed,Blocks.grass).setUnlocalizedName("sweedSeeds").setTextureName("thaumicbases:seeds_sweed");
	public static Item lazulliaSeeds = new ItemSeeds(TBBlocks.lazullia,Blocks.farmland).setUnlocalizedName("lazulliaSeeds").setTextureName("thaumicbases:lazullia_seeds");
	public static Item redlonSeeds = new ItemSeeds(TBBlocks.redlonStem,Blocks.farmland).setUnlocalizedName("redlonSeeds").setTextureName("thaumicbases:redlon_seed");
	public static Item glieoniaSeed = new ItemKnoseSeeds(TBBlocks.glieonia,Blocks.grass).setUnlocalizedName("glieoniaSeed").setTextureName("thaumicbases:glieonia_seed");
	public static Item rosehipSyrup = new ItemRosehipSyrup().setUnlocalizedName("rosehipSyrup").setTextureName("thaumicbases:rosehip_syrup").setMaxStackSize(16);
	public static Item tobaccoSeeds = new ItemSeeds(TBBlocks.tobacco,Blocks.farmland).setUnlocalizedName("tobaccoSeeds").setTextureName("thaumicbases:tobacco_seeds");
	public static Item mortar = new ItemMortarAndPestle().setUnlocalizedName("mortar").setTextureName("thaumicbases:mortar");
	public static Item tobacco = new TBTobacco().setUnlocalizedName("tobacco");
	public static Item greatwoodPipe = new ItemSmokingPipe(false).setUnlocalizedName("greatwoodPipe").setTextureName("thaumicbases:smokingPipe");
	public static Item silverwoodPipe = new ItemSmokingPipe(true).setUnlocalizedName("silverwoodPipe").setTextureName("thaumicbases:smokingPipe_silverwood");
	public static Item voidSeed = new ItemSeeds(TBBlocks.voidPlant,Blocks.farmland).setUnlocalizedName("voidSeed").setTextureName("thaumicbases:void_seed");
	
	public static Item bloodyChest = new ItemBloodyArmor(bloodyA,1).setUnlocalizedName("bloodyChest").setTextureName("thaumicbases:bloodyRobes/bloodychest");
	public static Item bloodyLeggings = new ItemBloodyArmor(bloodyA,2).setUnlocalizedName("bloodyLegs").setTextureName("thaumicbases:bloodyRobes/bloodylegs");
	public static Item bloodyBoots = new ItemBloodyArmor(bloodyA,3).setUnlocalizedName("bloodyBoots").setTextureName("thaumicbases:bloodyRobes/bloodyboots");
	
	public static Item concentratedTaint = new ItemConcentratedTaint().setUnlocalizedName("concentratedTaint").setTextureName("thaumicbases:concentratedTaint").setMaxStackSize(1);
	
	public static Item fociActivation = new FociActivation().setUnlocalizedName("activationFoci").setTextureName("thaumicbases:foci/activation/foci");
	public static Item fociDrain = new FociDrain().setUnlocalizedName("drainFoci").setTextureName("thaumicbases:foci/drain/foci");
	public static Item fociExperience = new FociExperience().setUnlocalizedName("experienceFoci").setTextureName("thaumicbases:foci/experience/foci");
	public static Item fociFlux = new FociFlux().setUnlocalizedName("fluxFoci").setTextureName("thaumicbases:foci/flux/foci");
	
	public static Item nodeFoci = new ItemNodeFoci().setUnlocalizedName("nodeFoci.");
	
	public static Item herobrinesScythe = new ItemHerobrinesScythe().setUnlocalizedName("herobrinesScythe").setTextureName("thaumicbases:herobrinesScythe");
	
	public static WandCap WAND_CAP_THAUMINITE;
	
	public static WandRod WAND_ROD_THAUMIUM;
	public static WandRod WAND_ROD_VOID;
	public static StaffRod STAFF_ROD_THAUMIUM;
	
	public static final Class<TBCore> core = TBCore.class;
}
