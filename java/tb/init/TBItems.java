package tb.init;

import DummyCore.Items.ItemRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import tb.common.item.ItemBloodyArmor;
import tb.common.item.ItemCastingBracelet;
import tb.common.item.ItemHerobrinesScythe;
import tb.common.item.ItemKnoseFragment;
import tb.common.item.ItemMortarAndPestle;
import tb.common.item.ItemNodeFoci;
import tb.common.item.ItemPyrofluidBucket;
import tb.common.item.ItemRevolver;
import tb.common.item.ItemRosehipSyrup;
import tb.common.item.ItemSeeds;
import tb.common.item.ItemShardCluster;
import tb.common.item.ItemSmokingPipe;
import tb.common.item.ItemSpawnerCompass;
import tb.common.item.ItemThauminiteArmor;
import tb.common.item.ItemThauminiteAxe;
import tb.common.item.ItemThauminiteHoe;
import tb.common.item.ItemThauminitePickaxe;
import tb.common.item.ItemThauminiteShears;
import tb.common.item.ItemThauminiteShovel;
import tb.common.item.ItemThauminiteSword;
import tb.common.item.ItemUkulele;
import tb.common.item.ItemVoidFlintAndSteel;
import tb.common.item.ItemVoidShears;
import tb.common.item.TBResource;
import tb.common.item.TBTobacco;
import tb.core.TBCore;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.items.wands.WandRodPrimalOnUpdate;

public class TBItems {
	
	public static void setup()
	{
		ItemRegistry.registerItem(resource, "resource",core);
		ItemRegistry.registerItem(spawnerCompass, "spawnerCompass",core);
		
		ItemRegistry.registerItem(tobacco, "tobaccoPowder",core);
		ItemRegistry.registerItem(greatwoodPipe, "greatwoodPipe",core);
		ItemRegistry.registerItem(silverwoodPipe, "silverwoodPipe",core);
		
		ItemRegistry.registerItem(bloodyChest, "bloodyChest",core);
		ItemRegistry.registerItem(bloodyLeggings, "bloodyLeggings",core);
		ItemRegistry.registerItem(bloodyBoots, "bloodyBoots",core);
		ItemRegistry.registerItem(knoseFragment, "knoseFragment",core);
		
		ItemRegistry.registerItem(castingBracelet, "castingBracelet",core);
		ItemRegistry.registerItem(herobrinesScythe, "herobrinesScythe",core);
		
		ItemRegistry.registerItem(nodeFoci, "nodeFoci",core);
		ItemRegistry.registerItem(voidShears, "voidShears",core);
		ItemRegistry.registerItem(voidFAS, "voidFAS",core);
		
		ItemRegistry.registerItem(thauminiteHelmet, "thauminiteHelmet",core);
		ItemRegistry.registerItem(thauminiteChest, "thauminiteChest",core);
		ItemRegistry.registerItem(thauminiteLeggings, "thauminiteLeggings",core);
		ItemRegistry.registerItem(thauminiteBoots, "thauminiteBoots",core);
		ItemRegistry.registerItem(pyroBucket, "pyroBucket",core);
		ItemRegistry.registerItem(plaxSeed, "plaxSeed",core);
		ItemRegistry.registerItem(metalleatSeeds, "metalleatSeeds",core);
		ItemRegistry.registerItem(lucriteSeeds, "lucriteSeeds",core);
		ItemRegistry.registerItem(knoseSeed, "knoseSeed",core);
		ItemRegistry.registerItem(sweedSeeds, "sweedSeeds",core);
		ItemRegistry.registerItem(lazulliaSeeds, "lazulliaSeeds",core);
		ItemRegistry.registerItem(redlonSeeds, "redlonSeeds",core);
		ItemRegistry.registerItem(glieoniaSeed, "glieoniaSeed",core);
		ItemRegistry.registerItem(rosehipSyrup, "rosehipSyrup",core);
		ItemRegistry.registerItem(tobaccoSeeds, "tobaccoSeeds",core);
		ItemRegistry.registerItem(mortar, "mortar",core);
		ItemRegistry.registerItem(voidSeed, "voidSeed",core);
		
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
		ItemRegistry.registerItem(revolver, "revolver",core);
		ItemRegistry.registerItem(ukulele, "ukulele",core);
		ItemRegistry.registerItem(shardCluster, "shardCluster",core);
		
		WAND_CAP_THAUMINITE = new WandCap("thauminite",0.85F,1,new ItemStack(resource,1,2),6,new ResourceLocation("thaumicbases","items/thauminite/wand_cap_thauminite_uv"));	
		WAND_ROD_THAUMIUM = new WandRod("tbthaumium", 450, new ItemStack(resource,1,3), 6, new WandRodPrimalOnUpdate(), new ResourceLocation("thaumicbases","items/wand_rod_thaumium_uv"));
		WAND_ROD_VOID = new WandRod("tbvoid", 750, new ItemStack(resource,1,4), 16, new WandRodPrimalOnUpdate(), new ResourceLocation("thaumicbases","items/wand_rod_void_uv"));

	}
	
	public static ToolMaterial thauminite = EnumHelper.addToolMaterial("THAUMINITE", 3, 974, 7F, 2.8F, 15);
	public static ArmorMaterial thauminiteA = EnumHelper.addArmorMaterial("ATHAUMINITE", "thaumicbases:textures/items/armor/thauminite/thauminite", 27, new int[]{3, 8, 6, 3}, 17);
	public static ArmorMaterial bloodyA = EnumHelper.addArmorMaterial("TBBLOODY", "thaumicbases:textures/items/armor/bloody/bloody" ,21, new int[]{2, 6, 5, 2}, 21);
	
	public static Item resource = new TBResource().setUnlocalizedName("resource");
	public static Item spawnerCompass = new ItemSpawnerCompass().setFull3D().setMaxStackSize(1).setMaxDamage(0).setUnlocalizedName("tb.spawnerCompass");
	
	public static Item tobacco = new TBTobacco().setUnlocalizedName("tobacco");
	public static Item greatwoodPipe = new ItemSmokingPipe(false).setTextureName("thaumicbases:smokingPipe").setUnlocalizedName("greatwoodPipe");
	public static Item silverwoodPipe = new ItemSmokingPipe(true).setTextureName("thaumicbases:smokingPipe_silverwood").setUnlocalizedName("silverwoodPipe");
	
	public static Item bloodyChest = new ItemBloodyArmor(bloodyA,1).setTextureName("thaumicbases:bloodyRobes/bloodychest").setUnlocalizedName("bloodyChest");
	public static Item bloodyLeggings = new ItemBloodyArmor(bloodyA,2).setTextureName("thaumicbases:bloodyRobes/bloodylegs").setUnlocalizedName("bloodyLegs");
	public static Item bloodyBoots = new ItemBloodyArmor(bloodyA,3).setTextureName("thaumicbases:bloodyRobes/bloodyboots").setUnlocalizedName("bloodyBoots");
	
	public static Item knoseFragment = new ItemKnoseFragment().setTextureName("thaumicbases:knose/").setUnlocalizedName("knoseFragment.");
	
	public static Item castingBracelet = new ItemCastingBracelet().setUnlocalizedName("tb.bracelet");
	public static Item herobrinesScythe = new ItemHerobrinesScythe().setTextureName("thaumicbases:herobrinesScythe").setUnlocalizedName("herobrinesScythe");
	
	public static Item thauminiteAxe = new ItemThauminiteAxe(thauminite).setTextureName("thaumicbases:thauminite/thauminiteaxe").setUnlocalizedName("thauminiteAxe");
	public static Item thauminiteHoe = new ItemThauminiteHoe(thauminite).setTextureName("thaumicbases:thauminite/thauminitehoe").setUnlocalizedName("thauminiteHoe");
	public static Item thauminitePickaxe = new ItemThauminitePickaxe(thauminite).setTextureName("thaumicbases:thauminite/thauminitepick").setUnlocalizedName("thauminitePickaxe");
	public static Item thauminiteShears = new ItemThauminiteShears().setTextureName("thaumicbases:thauminite/thauminiteshears").setUnlocalizedName("thauminiteShears").setFull3D().setMaxStackSize(1).setMaxDamage(974);
	public static Item thauminiteShovel = new ItemThauminiteShovel(thauminite).setTextureName("thaumicbases:thauminite/thauminiteshovel").setUnlocalizedName("thauminiteShovel");
	public static Item thauminiteSword = new ItemThauminiteSword(thauminite).setTextureName("thaumicbases:thauminite/thauminitesword").setUnlocalizedName("thauminiteSword");

	public static Item thauminiteHelmet = new ItemThauminiteArmor(thauminiteA,0).setTextureName("thaumicbases:thauminite/thauminitehelm").setUnlocalizedName("thauminiteHelmet");
	public static Item thauminiteChest = new ItemThauminiteArmor(thauminiteA,1).setTextureName("thaumicbases:thauminite/thauminitechest").setUnlocalizedName("thauminiteChest");
	public static Item thauminiteLeggings = new ItemThauminiteArmor(thauminiteA,2).setTextureName("thaumicbases:thauminite/thauminitelegs").setUnlocalizedName("thauminiteLegs");
	public static Item thauminiteBoots = new ItemThauminiteArmor(thauminiteA,3).setTextureName("thaumicbases:thauminite/thauminiteboots").setUnlocalizedName("thauminiteBoots");

	public static Item pyroBucket = new ItemPyrofluidBucket().setTextureName("thaumicbases:bucket_blazing_fluid").setUnlocalizedName("pyrofluidBucket");
	
	public static Item plaxSeed = new ItemSeeds(TBBlocks.plax, Blocks.farmland).setTextureName("thaumicbases:plax_seeds").setUnlocalizedName("plaxSeeds");
	public static Item metalleatSeeds = new ItemSeeds(TBBlocks.metalleat, Blocks.farmland).setTextureName("thaumicbases:metalleat_seeds").setUnlocalizedName("metalleatSeeds");
	public static Item lucriteSeeds = new ItemSeeds(TBBlocks.lucrite, Blocks.farmland).setTextureName("thaumicbases:lucrite_seeds").setUnlocalizedName("lucriteSeeds");
	public static Item knoseSeed = new ItemSeeds(TBBlocks.knose, TBBlocks.crystalBlock).setTextureName("thaumicbases:knose_seed").setUnlocalizedName("knoseSeed");
	public static Item sweedSeeds = new ItemSeeds(TBBlocks.sweed,Blocks.grass).setTextureName("thaumicbases:seeds_sweed").setUnlocalizedName("sweedSeeds");
	public static Item lazulliaSeeds = new ItemSeeds(TBBlocks.lazullia,Blocks.farmland).setTextureName("thaumicbases:lazullia_seeds").setUnlocalizedName("lazulliaSeeds");
	public static Item redlonSeeds = new ItemSeeds(TBBlocks.redlonStem,Blocks.farmland).setTextureName("thaumicbases:redlon_seed").setUnlocalizedName("redlonSeeds");
	public static Item glieoniaSeed = new ItemSeeds(TBBlocks.glieonia,Blocks.grass).setTextureName("thaumicbases:glieonia_seed").setUnlocalizedName("glieoniaSeed");
	public static Item rosehipSyrup = new ItemRosehipSyrup().setTextureName("thaumicbases:rosehip_syrup").setUnlocalizedName("rosehipSyrup").setMaxStackSize(16);
	public static Item tobaccoSeeds = new ItemSeeds(TBBlocks.tobacco,Blocks.farmland).setTextureName("thaumicbases:tobacco_seeds").setUnlocalizedName("tobaccoSeeds");
	public static Item mortar = new ItemMortarAndPestle().setTextureName("thaumicbases:mortar").setUnlocalizedName("mortar");
	public static Item voidSeed = new ItemSeeds(TBBlocks.voidPlant,Blocks.farmland).setTextureName("thaumicbases:void_seed").setUnlocalizedName("voidSeed");
	
	public static Item nodeFoci = new ItemNodeFoci().setUnlocalizedName("nodeFoci.");
	public static Item voidShears = new ItemVoidShears().setTextureName("thaumicbases:shears").setUnlocalizedName("tb.voidShears").setFull3D().setMaxDamage(184).setFull3D().setMaxStackSize(1);
	public static Item voidFAS = new ItemVoidFlintAndSteel().setTextureName("thaumicbases:flint_and_steel").setUnlocalizedName("tb.voidFAS").setFull3D().setMaxDamage(184).setFull3D().setMaxStackSize(1);
	public static Item revolver = new ItemRevolver().setMaxDamage(1561).setMaxStackSize(1).setFull3D().setUnlocalizedName("tb.revolver");
	public static Item ukulele = new ItemUkulele().setFull3D().setMaxStackSize(1).setMaxDamage(0).setUnlocalizedName("tb.ukulele");
	public static Item shardCluster = new ItemShardCluster();
	
	public static WandCap WAND_CAP_THAUMINITE;
	
	public static WandRod WAND_ROD_THAUMIUM;
	public static WandRod WAND_ROD_VOID;
	
	
	public static final Class<TBCore> core = TBCore.class;
}
