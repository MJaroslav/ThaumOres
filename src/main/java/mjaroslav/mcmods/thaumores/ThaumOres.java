package mjaroslav.mcmods.thaumores;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mjaroslav.mcmods.mjutils.module.AnnotationBasedConfiguration;
import mjaroslav.mcmods.mjutils.module.ModuleSystem;
import mjaroslav.mcmods.thaumores.common.CommonProxy;
import mjaroslav.mcmods.thaumores.common.creativetab.ThaumOresTab;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral.CategoryDebug;

import static mjaroslav.mcmods.thaumores.lib.ModInfo.*;

@Mod(modid = MOD_ID, name = NAME, version = VERSION, dependencies = DEPENDENCIES, guiFactory = GUI_FACTORY)
public class ThaumOres {
    public static ThaumOresTab tab = new ThaumOresTab(MOD_ID);

    @SidedProxy(serverSide = SERVER_PROXY, clientSide = CLIENT_PROXY)
    public static CommonProxy proxy;

    public static AnnotationBasedConfiguration config;
    private static ModuleSystem moduleSystem;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        moduleSystem.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        moduleSystem.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        moduleSystem.postInit(event);
    }

    @EventHandler
    public void constr(FMLConstructionEvent event) {
        config = new AnnotationBasedConfiguration(MOD_ID, LOG);
        moduleSystem = new ModuleSystem(MOD_ID, config, proxy);
        moduleSystem.initSystem(event);
    }

    public static void debug(String text) {
        if (CategoryDebug.enable)
            LOG.info(String.format("[DEBUG] %s", text));
    }

    private static final String LINE = "==================================================================";

    public static void logLine(boolean debug) {
        if (debug)
            debug(LINE);
        else
            LOG.info(LINE);
    }
}
