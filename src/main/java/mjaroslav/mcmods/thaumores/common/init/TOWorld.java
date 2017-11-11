package mjaroslav.mcmods.thaumores.common.init;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.mjutils.common.objects.IModModule;
import mjaroslav.mcmods.mjutils.common.objects.ModInitModule;
import mjaroslav.mcmods.mjutils.common.reaction.ReactionUtils;
import mjaroslav.mcmods.thaumores.ThaumOresMod;
import mjaroslav.mcmods.thaumores.common.config.TOConfig;
import mjaroslav.mcmods.thaumores.common.world.InfusedOreGeneration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

/** Register all world generations */
@ModInitModule(modid = ThaumOresMod.MODID)
public class TOWorld implements IModModule {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
	}

	@Override
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(ThaumOresMod.events);
		FMLCommonHandler.instance().bus().register(ThaumOresMod.events);
		if (TOConfig.generalAngryPigs)
			ReactionUtils.addBlockToPigAngryList(TOBlocks.netherrackInfusedOre, OreDictionary.WILDCARD_VALUE);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		if (TOConfig.generationEnable)
			GameRegistry.registerWorldGenerator(new InfusedOreGeneration(), 0);
	}

	@Override
	public String getModuleName() {
		return "World";
	}

	@Override
	public int getPriority() {
		return 2;
	}
}
