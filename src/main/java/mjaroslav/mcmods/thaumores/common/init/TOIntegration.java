package mjaroslav.mcmods.thaumores.common.init;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/** Register all integrations */
public class TOIntegration {
	public static void preInit(FMLPreInitializationEvent event) {
	}

	public static void init(FMLInitializationEvent event) {
		FMLInterModComms.sendMessage("Waila", "register",
				"mjaroslav.mcmods.thaumores.common.init.TOWaila.wailaInit");
	}

	public static void postInit(FMLPostInitializationEvent event) {
	}

}
