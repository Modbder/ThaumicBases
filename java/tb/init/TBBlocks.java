package tb.init;

import DummyCore.Blocks.BlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tb.common.block.BlockAshroom;
import tb.common.block.BlockAuraLinker;
import tb.common.block.BlockAureliaLeaf;
import tb.common.block.BlockAureliaPlant;
import tb.common.block.BlockBraizer;
import tb.common.block.BlockBriar;
import tb.common.block.BlockCampfire;
import tb.common.block.BlockCryingObelisk;
import tb.common.block.BlockCrystalBlock;
import tb.common.block.BlockCrystalSlab;
import tb.common.block.BlockFlaxium;
import tb.common.block.BlockHalfSlab;
import tb.common.block.BlockKnose;
import tb.common.block.BlockLucritePlant;
import tb.common.block.BlockNodeManipulator;
import tb.common.block.BlockOverchanter;
import tb.common.block.BlockPyrofluid;
import tb.common.block.BlockRainbowCactus;
import tb.common.block.BlockRedlonStem;
import tb.common.block.BlockSpike;
import tb.common.block.BlockSweed;
import tb.common.block.BlockTBLeaves;
import tb.common.block.BlockTBLog;
import tb.common.block.BlockTBPlanks;
import tb.common.block.BlockTBPlant;
import tb.common.block.BlockTBSapling;
import tb.common.block.BlockThaumicAnvil;
import tb.common.block.BlockVoidAnvil;
import tb.common.block.BlockWoodSlab;
import tb.common.block.TBBlock;
import tb.common.block.TBSidedBlock;
import tb.common.itemblock.ItemAnvilBlock;
import tb.common.itemblock.ItemBlockCrystal;
import tb.common.itemblock.ItemBlockCrystalSlab;
import tb.common.itemblock.ItemBlockHalfSlab;
import tb.common.itemblock.ItemBlockPyrofluid;
import tb.common.itemblock.ItemBlockSpike;
import tb.common.itemblock.ItemBlockTBLeaves;
import tb.common.itemblock.ItemBlockTBLogs;
import tb.common.itemblock.ItemBlockTBPlanks;
import tb.common.itemblock.ItemBlockTBSapling;
import tb.common.itemblock.ItemBlockWoodSlab;
import tb.core.TBCore;

public class TBBlocks {
	
	public static void setup()
	{
		BlocksRegistry.registerBlock(crystalBlock,"crystalBlock",core, ItemBlockCrystal.class);
		OreDictionary.registerOre("blockCrystalCluster", new ItemStack(crystalBlock,1,OreDictionary.WILDCARD_VALUE));
		BlocksRegistry.registerBlock(genLogs, "genLogs",core,ItemBlockTBLogs.class);
		OreDictionary.registerOre("logWood", new ItemStack(genLogs,1,OreDictionary.WILDCARD_VALUE));
		BlocksRegistry.registerBlock(genLeaves, "genLeaves",core,ItemBlockTBLeaves.class);
		BlocksRegistry.registerBlock(sapling, "goldenOakSapling",core,ItemBlockTBSapling.class);
		OreDictionary.registerOre("treeSapling", sapling);
		BlocksRegistry.registerBlock(planks, "planks",core,ItemBlockTBPlanks.class);
		OreDictionary.registerOre("plankWood", new ItemStack(planks,1,OreDictionary.WILDCARD_VALUE));
		
		BlocksRegistry.registerBlock(quicksilverBlock, "quicksilverBlock",core,ItemBlock.class);
		OreDictionary.registerOre("blockQuicksilver", quicksilverBlock);
		BlocksRegistry.registerBlock(quicksilverBrick, "quicksilverBrick",core,ItemBlock.class);
		OreDictionary.registerOre("blockQuicksilver", quicksilverBrick);
		BlocksRegistry.registerBlock(dustBlock, "blockSalisMundus",core,ItemBlock.class);
		OreDictionary.registerOre("blockSalisMundus", dustBlock);
		BlocksRegistry.registerBlock(thauminiteBlock, "thauminiteBlock",core,ItemBlock.class);
		OreDictionary.registerOre("blockThauminite", thauminiteBlock);
		BlocksRegistry.registerBlock(eldritchArk, "eldritchArk",core,ItemBlock.class);
		BlocksRegistry.registerBlock(ironGreatwood, "ironGreatwood",core,ItemBlock.class);
		
		BlocksRegistry.registerBlock(oldCobble, "oldCobble",core,ItemBlock.class);
		oldCobble.setHarvestLevel("pickaxe", 0);
		OreDictionary.registerOre("cobblestone", oldCobble);
		BlocksRegistry.registerBlock(oldCobbleMossy, "oldCobbleMossy",core,ItemBlock.class);
		oldCobbleMossy.setHarvestLevel("pickaxe", 0);
		OreDictionary.registerOre("cobblestone", oldCobbleMossy);
		OreDictionary.registerOre("mossyCobblestone", oldCobbleMossy);
		BlocksRegistry.registerBlock(oldGravel, "oldGravel",core,ItemBlock.class);
		oldGravel.setHarvestLevel("shovel", 0);
		OreDictionary.registerOre("gravel", oldGravel);
		BlocksRegistry.registerBlock(oldBrick, "oldBrick",core,ItemBlock.class);
		oldBrick.setHarvestLevel("pickaxe", 0);
		OreDictionary.registerOre("brick", oldBrick);
		BlocksRegistry.registerBlock(oldLapis, "oldLapis",core,ItemBlock.class);
		oldLapis.setHarvestLevel("pickaxe", 0);
		OreDictionary.registerOre("blockLapis", oldLapis);
		BlocksRegistry.registerBlock(oldIron, "oldIron",core,ItemBlock.class);
		oldIron.setHarvestLevel("pickaxe", 0);
		OreDictionary.registerOre("blockIron", oldIron);
		BlocksRegistry.registerBlock(oldGold, "oldGold",core,ItemBlock.class);
		oldGold.setHarvestLevel("pickaxe", 0);
		OreDictionary.registerOre("blockGold", oldGold);
		BlocksRegistry.registerBlock(oldDiamond, "oldDiamond",core,ItemBlock.class);
		oldDiamond.setHarvestLevel("pickaxe", 0);
		OreDictionary.registerOre("blockDiamond", oldDiamond);
		
		BlocksRegistry.registerBlock(plax, "plax",core,ItemBlock.class);
		BlocksRegistry.registerBlock(aureliaPetal, "aureliaPetal",core,ItemBlock.class);
		BlocksRegistry.registerBlock(aurelia, "aurelia",core,ItemBlock.class);
		BlocksRegistry.registerBlock(metalleat, "metalleat",core,ItemBlock.class);
		BlocksRegistry.registerBlock(lucrite, "lucrite",core,ItemBlock.class);
		BlocksRegistry.registerBlock(knose, "knose",core,ItemBlock.class);
		BlocksRegistry.registerBlock(sweed, "sweed",core,ItemBlock.class);
		BlocksRegistry.registerBlock(lazullia, "lazullia",core,ItemBlock.class);
		BlocksRegistry.registerBlock(ashroom, "ashroom",core,ItemBlock.class);
		BlocksRegistry.registerBlock(flaxium, "flaxium",core,ItemBlock.class);
		BlocksRegistry.registerBlock(glieonia, "glieonia",core,ItemBlock.class);
		BlocksRegistry.registerBlock(briar, "briar",core,ItemBlock.class);
		BlocksRegistry.registerBlock(tobacco, "tobacco",core,ItemBlock.class);
		BlocksRegistry.registerBlock(voidPlant, "voidPlant",core,ItemBlock.class);
		BlocksRegistry.registerBlock(rainbowCactus, "rainbowCactus",core,ItemBlock.class);
		BlocksRegistry.registerBlock(redlonStem, "redlonStem",core,ItemBlock.class);
		
		BlocksRegistry.registerBlock(spike, "spike",core,ItemBlockSpike.class);
		BlocksRegistry.registerBlock(cryingObsidian, "cryingObsidian",core,ItemBlock.class);
		BlocksRegistry.registerBlock(pyrofluid,"pyrofluid",core, ItemBlockPyrofluid.class);
		BlocksRegistry.registerBlock(thaumicAnvil, "thaumicAnvil",core,ItemAnvilBlock.class);
		BlocksRegistry.registerBlock(voidAnvil, "voidAnvil",core,ItemAnvilBlock.class);
		
		BlocksRegistry.registerBlock(crystalSlab, "crystalSlab",core,ItemBlockCrystalSlab.class);
		BlocksRegistry.registerBlock(crystalSlab_full, "crystalSlab_full",core,ItemBlockCrystalSlab.class);
		BlocksRegistry.registerBlock(genericSlab, "genericSlab",core,ItemBlockHalfSlab.class);
		BlocksRegistry.registerBlock(genericSlab_full, "genericSlab_full",core,ItemBlockHalfSlab.class);
		BlocksRegistry.registerBlock(woodSlab, "woodSlab",core,ItemBlockWoodSlab.class);
		BlocksRegistry.registerBlock(woodSlab_full, "woodSlab_full",core,ItemBlockWoodSlab.class);
		
		BlocksRegistry.registerBlock(overchanter, "overchanter",core,ItemBlock.class);
		BlocksRegistry.registerBlock(campfire, "campfire",core,ItemBlock.class);
		BlocksRegistry.registerBlock(braizer, "brazier",core,ItemBlock.class);
		BlocksRegistry.registerBlock(auraLinker, "auraLinker",core,ItemBlock.class);
		BlocksRegistry.registerBlock(nodeManipulator, "nodeManipulator",core,ItemBlock.class);
		
		Blocks.fire.setFireInfo(genLogs, 5, 5);
		Blocks.fire.setFireInfo(genLeaves, 30, 60);
		Blocks.fire.setFireInfo(planks, 5, 20);
	}
	
	public static Block quicksilverBlock = new TBBlock(Material.rock,false).setBlockTextureName("thaumicbases:quicksilverBlock").stabilise().setUnlocalizedName("quicksilverBlock").setHardness(0.5F);
	public static Block quicksilverBrick = new TBBlock(Material.rock,true).setBlockTextureName("thaumicbases:quicksilverBrick").stabilise().setUnlocalizedName("quicksilverBrick").setHardness(0.5F);
	public static Block dustBlock = new TBBlock(Material.sand,false).setBlockTextureName("thaumicbases:dust_block").stabilise().setStepSound(Block.soundTypeSand).setUnlocalizedName("salisMundusBlock").setHardness(1);
	public static Block thauminiteBlock = new TBBlock(Material.iron,false).setBlockTextureName("thaumicbases:thauminiteblock").stabilise().setUnlocalizedName("thauminiteBlock").setHardness(2F);
	public static Block eldritchArk = new TBBlock(Material.rock,false).setBlockTextureName("thaumicbases:eldritchArk").setUnlocalizedName("eldritchArk").setHardness(3F);
	public static Block ironGreatwood = new TBBlock(Material.wood,false).setBlockTextureName("thaumicbases:ironGreatwood").setUnlocalizedName("ironGreatwood").setHardness(1F).setStepSound(Block.soundTypeWood);
	
	public static Block oldCobble = new TBBlock(Material.rock,false).setBlockName("TBoldCobblestone").setBlockTextureName("thaumicbases:cobblestone").setHardness(1).setResistance(1);
	public static Block oldCobbleMossy = new TBBlock(Material.rock,false).setBlockName("TBoldCobblestoneMossy").setBlockTextureName("thaumicbases:cobblestone_mossy").setHardness(1).setResistance(1);
	public static Block oldGravel = new TBBlock(Material.sand, false).setBlockName("TBoldGravel").setBlockTextureName("thaumicbases:gravel").setHardness(0.6F).setResistance(0).setStepSound(Block.soundTypeGravel);
	public static Block oldBrick = new TBBlock(Material.rock,false).setBlockName("TBoldBricks").setBlockTextureName("thaumicbases:brick").setHardness(1).setResistance(1);
	public static Block oldLapis = new TBBlock(Material.rock,false).setBlockName("TBoldLapis").setBlockTextureName("thaumicbases:lapis_block").setHardness(1).setResistance(1);
	public static Block oldIron = new TBSidedBlock(Material.rock,false).setBlockName("TBoldIron").setBlockTextureName("thaumicbases:iron_block").setHardness(1).setResistance(1);
	public static Block oldGold = new TBSidedBlock(Material.rock,false).setBlockName("TBoldGold").setBlockTextureName("thaumicbases:gold_block").setHardness(1).setResistance(1);
	public static Block oldDiamond = new TBSidedBlock(Material.rock,false).setBlockName("TBoldDiamond").setBlockTextureName("thaumicbases:diamond_block").setHardness(1).setResistance(1);
	
	
	public static Block crystalBlock = new BlockCrystalBlock().setUnlocalizedName("crystalBlock");
	public static Block genLogs = new BlockTBLog();
	public static Block genLeaves = new BlockTBLeaves();
	public static Block sapling = new BlockTBSapling();
	public static Block planks = new BlockTBPlanks().setUnlocalizedName("tb.planks");
	
	public static Block ashroom = new BlockAshroom(4,8,false).setBlockName("ashroom").setBlockTextureName("thaumicbases:aspectshroom/");
	public static Block plax = new BlockTBPlant(8,4,true).setBlockName("plax").setBlockTextureName("thaumicbases:plax/");
	public static Block aureliaPetal = new BlockAureliaLeaf().setBlockName("aureliaPetal").setBlockTextureName("thaumicbases:aurelia/petal").setHardness(0).setStepSound(Block.soundTypeGrass);
	public static Block aurelia = new BlockAureliaPlant().setBlockName("aurelia").setBlockTextureName("thaumicbases:aurelia/").setHardness(0).setStepSound(Block.soundTypeGrass);
	public static Block metalleat = new BlockTBPlant(8,8,true).setBlockName("metalleat").setBlockTextureName("thaumicbases:metalleat/");
	public static Block lucrite = new BlockLucritePlant(8,16,true).setBlockName("lucrite").setBlockTextureName("thaumicbases:lucrite/");
	public static Block knose = new BlockKnose(4,16,false).setBlockName("knose").setBlockTextureName("thaumicbases:knoze/");
	public static Block sweed = new BlockSweed(4,4,false).setBlockName("sweed").setBlockTextureName("thaumicbases:sweed/");
	public static Block lazullia = new BlockTBPlant(8,16,true).setBlockName("lazullia").setBlockTextureName("thaumicbases:lazullia/");
	public static Block flaxium = new BlockFlaxium().setBlockName("flaxium").setBlockTextureName("thaumicbases:flaxium").setStepSound(Block.soundTypeGrass);
	public static Block glieonia = new BlockTBPlant(4,12,false).setBlockName("glieonia").setBlockTextureName("thaumicbases:glieonia/");
	public static Block briar = new BlockBriar(8,4).setBlockName("briar").setBlockTextureName("thaumicbases:briar/").setStepSound(Block.soundTypeGrass);
	public static Block tobacco = new BlockTBPlant(8,16,true).setBlockName("tobacco").setBlockTextureName("thaumicbases:tobacco/");
	public static Block voidPlant = new BlockTBPlant(4,32,true).setBlockName("voidPlant").setBlockTextureName("thaumicbases:voidPlant/");
	public static Block rainbowCactus = new BlockRainbowCactus().setBlockName("rainbowCactus").setBlockTextureName("thaumicbases:rainbowCacti/").setStepSound(Block.soundTypeCloth).setHardness(0.5F);
	public static Block redlonStem = new BlockRedlonStem(Blocks.redstone_block).setBlockName("redlonStem").setBlockTextureName("thaumicbases:redlon/redlon").setStepSound(Block.soundTypeGrass);
	
	public static Block spike = new BlockSpike().setUnlocalizedName("spike").setHardness(3).setResistance(3).setStepSound(Block.soundTypeMetal);
	public static Block cryingObsidian = new BlockCryingObelisk().setBlockName("cryingObsidian").setBlockTextureName("thaumicbases:cryingObelisk/").setHardness(10).setResistance(10);
	public static Block pyrofluid = new BlockPyrofluid().setUnlocalizedName("pyrofluid").setLightLevel(1);
	public static Block thaumicAnvil = new BlockThaumicAnvil().setBlockName("thaumicAnvil").setBlockTextureName("thaumicbases:thaumiumAnvil/").setHardness(6).setResistance(16).setStepSound(Block.soundTypeAnvil);
	public static Block voidAnvil = new BlockVoidAnvil().setBlockName("voidAnvil").setBlockTextureName("thaumicbases:voidAnvil/");
	
	public static Block crystalSlab = new BlockCrystalSlab(false,Material.glass).setUnlocalizedName("tb.crystalslab.");
	public static Block crystalSlab_full = new BlockCrystalSlab(true,Material.glass).setUnlocalizedName("tb.crystalslab.");
	public static Block genericSlab = new BlockHalfSlab(false,Material.rock).setHardness(1).setResistance(10).setUnlocalizedName("tb.slab.");
	public static Block genericSlab_full = new BlockHalfSlab(true,Material.rock).setHardness(1).setResistance(10).setUnlocalizedName("tb.slab.");
	public static Block woodSlab = new BlockWoodSlab(false,Material.wood).setUnlocalizedName("tb.woodSlab.");
	public static Block woodSlab_full = new BlockWoodSlab(true,Material.wood).setUnlocalizedName("tb.woodSlab.");
	
	public static Block overchanter = new BlockOverchanter().setUnlocalizedName("overchanter").setHardness(5).setResistance(5);
	public static Block campfire = new BlockCampfire().setBlockTextureName("thaumicbases:campfire").setUnlocalizedName("tb.campfire").setLightLevel(1);
	public static Block braizer = new BlockBraizer().setUnlocalizedName("tb.brazier").setLightLevel(1);
	public static Block auraLinker = new BlockAuraLinker().setUnlocalizedName("nodeLinker").setHardness(1);
	public static Block nodeManipulator = new BlockNodeManipulator().setUnlocalizedName("nodeManipulator").setHardness(1);
	
	public static final Class<TBCore> core = TBCore.class;
}
