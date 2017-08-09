package mjaroslav.mcmods.thaumores.common.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.thaumores.common.config.TOConfig;
import mjaroslav.mcmods.thaumores.common.world.InfusedOreGeneration;

/** Register all world generations */
public class TOWorld {
	public static void preInit(FMLPreInitializationEvent event) {
	}

	public static void init(FMLInitializationEvent event) {
	}

	public static void postInit(FMLPostInitializationEvent event) {
		if (TOConfig.generationEnable)
			GameRegistry.registerWorldGenerator(new InfusedOreGeneration(), 0);
	}
}
