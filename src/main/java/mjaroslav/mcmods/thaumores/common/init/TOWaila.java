package mjaroslav.mcmods.thaumores.common.init;

import mcp.mobius.waila.api.IWailaRegistrar;
import mjaroslav.mcmods.thaumores.ThaumOresMod;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.waila.WailaInfusedBlockOre;

public class TOWaila {
	public static void wailaInit(IWailaRegistrar registrar) {
		registrar.addConfigRemote(ThaumOresMod.NAME, "hideinfusedore", true);
		registrar.registerStackProvider(new WailaInfusedBlockOre(), BlockInfusedBlockOre.class);
	}
}
