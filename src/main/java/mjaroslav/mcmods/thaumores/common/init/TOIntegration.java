package mjaroslav.mcmods.thaumores.common.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mjaroslav.mcmods.mjutils.common.objects.IModModule;
import mjaroslav.mcmods.mjutils.common.objects.ModInitModule;
import mjaroslav.mcmods.thaumores.ThaumOresMod;

/** Register all integrations */
@ModInitModule(modid = ThaumOresMod.MODID)
public class TOIntegration implements IModModule {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
	}

	@Override
	public void init(FMLInitializationEvent event) {
		FMLInterModComms.sendMessage("Waila", "register", "mjaroslav.mcmods.thaumores.common.init.TOWaila.wailaInit");
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}

	@Override
	public String getModuleName() {
		return "Integration";
	}

	@Override
	public int getPriority() {
		return 3;
	}
}
