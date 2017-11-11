package mjaroslav.mcmods.thaumores;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import baubles.api.BaublesApi;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.mjutils.common.objects.ModInitHandler;
import mjaroslav.mcmods.thaumores.common.TOCommonProxy;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.block.ItemInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.config.TOConfig;
import mjaroslav.mcmods.thaumores.common.creativetab.TOCreativeTab;
import mjaroslav.mcmods.thaumores.common.event.TOEvents;
import mjaroslav.mcmods.thaumores.common.init.TOBlocks;
import mjaroslav.mcmods.thaumores.common.tile.TileInfusedOre;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.events.EventHandlerRunic;

@Mod(modid = ThaumOresMod.MODID, name = ThaumOresMod.NAME, version = ThaumOresMod.VERSION, dependencies = ThaumOresMod.DEPENDENCIES, guiFactory = ThaumOresMod.GUIFACTORY)
public class ThaumOresMod {
	/** ThaumOres: id */
	public static final String MODID = "thaumores";
	/** ThaumOres: name */
	public static final String NAME = "ThaumOres";
	/** ThaumOres: version */
	public static final String VERSION = "1.5.0";
	/** ThaumOres: guiFactory */
	public static final String GUIFACTORY = "mjaroslav.mcmods.thaumores.client.gui.TOGuiFactory";
	/** ThaumOres: dependencies */
	public static final String DEPENDENCIES = "required-after:Thaumcraft@[4.2.3.5,);required-after:mjutils@[1.4.0,);";

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

	public static TOEvents events = new TOEvents();

	/** ThaumOres init handler */
	private static ModInitHandler initHandler = new ModInitHandler(MODID);

	/** ThaumOres config */
	public static TOConfig config = new TOConfig();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		this.config.preInit(event);
		this.initHandler.preInit(event);
		this.proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		this.config.init(event);
		this.initHandler.init(event);
		this.proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		this.config.postInit(event);
		this.initHandler.postInit(event);
		this.proxy.postInit(event);
	}

	@EventHandler
	public void constr(FMLConstructionEvent event) {
		this.initHandler.findModules(event);
	}

	public static void logDebug(String text) {
		if (TOConfig.debugEnable)
			log.info(text);
	}

	public static void logLine() {
		ThaumOresMod.log.info("==================================================================");
	}
}
