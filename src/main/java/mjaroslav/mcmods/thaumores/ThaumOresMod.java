package mjaroslav.mcmods.thaumores;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import baubles.api.BaublesApi;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mjaroslav.mcmods.thaumores.common.TOCommonProxy;
import mjaroslav.mcmods.thaumores.common.config.TOConfig;
import mjaroslav.mcmods.thaumores.common.creativetab.TOCreativeTab;
import mjaroslav.mcmods.thaumores.common.event.TOEvents;
import mjaroslav.mcmods.thaumores.common.init.TOBlocks;
import mjaroslav.mcmods.thaumores.common.init.TOIntegration;
import mjaroslav.mcmods.thaumores.common.init.TOItems;
import mjaroslav.mcmods.thaumores.common.init.TOThaum;
import mjaroslav.mcmods.thaumores.common.init.TOWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.common.MinecraftForge;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.events.EventHandlerRunic;

@Mod(modid = ThaumOresMod.MODID, name = ThaumOresMod.NAME, version = ThaumOresMod.VERSION, dependencies = ThaumOresMod.DEPENDENCIES, guiFactory = ThaumOresMod.GUIFACTORY)
public class ThaumOresMod {
	/** ThaumOres: id */
	public static final String MODID = "thaumores";
	/** ThaumOres: name */
	public static final String NAME = "ThaumOres";
	/** ThaumOres: version */
	public static final String VERSION = "1.3.2";
	/** ThaumOres: guiFactory */
	public static final String GUIFACTORY = "mjaroslav.mcmods.thaumores.client.gui.TOGuiFactory";
	/** ThaumOres: dependencies */
	public static final String DEPENDENCIES = "required-after:Thaumcraft@[4.2.3.5,);";

	/** ThaumOres: server proxy */
	public static final String SERVERPROXY = "mjaroslav.mcmods.thaumores.common.TOCommonProxy";
	/** ThaumOres: client proxy */
	public static final String CLIENTPROXY = "mjaroslav.mcmods.thaumores.client.TOClientProxy";

	/** ThaumOres: logger */
	public static Logger log = LogManager.getLogger(NAME);

	/** ThaumOres: creative tab */
	public static TOCreativeTab tab = new TOCreativeTab(MODID);

	@SidedProxy(serverSide = SERVERPROXY, clientSide = CLIENTPROXY)
	public static TOCommonProxy proxy;

	/** ThaumOres: instance */
	public static ThaumOresMod instance;

	/** Path to ThaumOres configuration file */
	public static String configFilePath;

	public static TOEvents events = new TOEvents();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configFilePath = event.getModConfigurationDirectory() + "/" + MODID + ".cfg";
		TOConfig.init();
		logInitDebug("Pre init");
		logInitDebug("Config init done");
		instance = this;
		logInitDebug("Instance pre init done");
		TOBlocks.preInit(event);
		logInitDebug("Blocks pre init done");
		TOItems.preInit(event);
		logInitDebug("Items pre init done");
		TOThaum.preInit(event);
		logInitDebug("Thaum pre init done");
		TOWorld.preInit(event);
		logInitDebug("World pre init done");
		TOIntegration.preInit(event);
		logInitDebug("Integration pre init done");
		proxy.preInit(event);
		logInitDebug("Proxy pre init done");
		logInitDebug("Pre init done");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		logInitDebug("Initialization");
		MinecraftForge.EVENT_BUS.register(events);
		FMLCommonHandler.instance().bus().register(events);
		logInitDebug("Events init done");
		TOBlocks.init(event);
		logInitDebug("Blocks init done");
		TOItems.init(event);
		logInitDebug("Items init done");
		TOThaum.init(event);
		logInitDebug("Thaum init done");
		TOWorld.init(event);
		logInitDebug("World init done");
		TOIntegration.init(event);
		logInitDebug("Integration init done");
		proxy.init(event);
		logInitDebug("Proxy init done");
		logInitDebug("Init done");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		logInitDebug("Post init");
		TOBlocks.postInit(event);
		logInitDebug("Blocks post init done");
		TOItems.postInit(event);
		logInitDebug("Items post init done");
		TOThaum.postInit(event);
		logInitDebug("Thaum post init done");
		TOWorld.postInit(event);
		logInitDebug("World post init done");
		TOIntegration.postInit(event);
		logInitDebug("Integration post init done");
		proxy.postInit(event);
		logInitDebug("Proxy post init done");
		logInitDebug("Post init done");
		logInitDebug("ThaumOres ready!");
	}

	public static void logInitDebug(String text) {
		if (TOConfig.debugEnable && TOConfig.debugInit)
			log.info(text);
	}

	public static void logLine() {
		ThaumOresMod.log.info("==================================================================");
	}

	public static int getTotalWarp(EntityPlayer player) {
		int warp = Thaumcraft.proxy.getPlayerKnowledge().getWarpTotal(player.getCommandSenderName());

		warp += EventHandlerRunic.getFinalWarp(player.getCurrentEquippedItem(), player);

		for (int a = 0; a < 4; ++a) {
			warp += EventHandlerRunic.getFinalWarp(player.inventory.armorItemInSlot(a), player);
		}

		IInventory baubles = BaublesApi.getBaubles(player);
		for (int a = 0; a < 4; ++a) {
			warp += EventHandlerRunic.getFinalWarp(baubles.getStackInSlot(a), player);
		}

		return warp;
	}
}
