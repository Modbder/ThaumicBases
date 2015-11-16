package tb.core;

import static tb.core.TBCore.dependencies;
import static tb.core.TBCore.modid;
import static tb.core.TBCore.name;
import static tb.core.TBCore.version;

import java.util.ArrayList;

import DummyCore.Core.Core;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import tb.api.RevolverUpgrade;
import tb.common.enchantment.EnchantmentHandler;
import tb.common.entity.EntityAspectOrb;
import tb.common.entity.EntityRevolverBullet;
import tb.common.event.TBEventHandler;
import tb.init.TBBlocks;
import tb.init.TBEnchant;
import tb.init.TBItems;
import tb.init.TBRecipes;
import tb.init.TBThaumonomicon;
import tb.init.TBTiles;
import tb.network.proxy.PacketTB;
import tb.network.proxy.TBNetworkManager;
import tb.network.proxy.TBServer;
import tb.utils.RecipesFragmentAdditions;
import tb.utils.TBConfig;

@Mod(modid = modid, version = version, name = name,dependencies=dependencies)
public class TBCore {
	
	public static final String modid = "thaumicbases";
	public static final String version = "1.4.18.7";
	public static final String name = "Thaumic Bases";
	public static final String serverProxy = "tb.network.proxy.TBServer";
	public static final String clientProxy = "tb.network.proxy.TBClient";
	public static final String dependencies = "required-after:Thaumcraft@[5.0.1,);required-after:Baubles@[1.1.1.0,);required-after:DummyCore@[2.1.18.0,);";

	@SidedProxy(serverSide = serverProxy,clientSide = clientProxy)
	public static TBServer proxy;
	public static SimpleNetworkWrapper network;
	
	public static TBCore instance;
	public static final TBConfig cfg = new TBConfig();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;
		Core.registerModAbsolute(getClass(), name, event.getModConfigurationDirectory().getAbsolutePath(), cfg);
		setupModInfo(event.getModMetadata());
		TBBlocks.setup();
		TBItems.setup();
		TBEnchant.setup();
		TBTiles.setup();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new EnchantmentHandler());
		MinecraftForge.EVENT_BUS.register(new TBEventHandler());
		FMLCommonHandler.instance().bus().register(new TBEventHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		
		EntityRegistry.registerModEntity(EntityAspectOrb.class, "tb.old.tc.aspectOrb", 0, this, 32, 1, true);
		EntityRegistry.registerModEntity(EntityRevolverBullet.class, "tb.bullet", 1, this, 32, 1, true);
		
		//TBFociUpgrades.init();
		proxy.registerRenderInformation();
		RevolverUpgrade.initConflictingMappings();
		
		network = NetworkRegistry.INSTANCE.newSimpleChannel("thaumbases");
		network.registerMessage(TBNetworkManager.class, PacketTB.class, 0, Side.SERVER);
		network.registerMessage(TBNetworkManager.class, PacketTB.class, 0, Side.CLIENT);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		TBRecipes.setup();
		TBThaumonomicon.setup();
		CraftingManager.getInstance().addRecipe(new RecipesFragmentAdditions());
	}
	
	public void setupModInfo(ModMetadata meta)
	{
		meta.autogenerated = false;
		meta.modId = modid;
		meta.name = name;
		meta.version = version;
		meta.description = "A Thaumcraft addon, that adds more earlygame and midgame content";
		ArrayList<String> authors = new ArrayList<String>();
		authors.add("Modbder");
		meta.authorList = authors;
	}
}
