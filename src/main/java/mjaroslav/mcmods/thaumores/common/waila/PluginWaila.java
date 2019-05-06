package mjaroslav.mcmods.thaumores.common.waila;

import mcp.mobius.waila.api.IWailaRegistrar;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.lib.ModInfo;

public class PluginWaila {
    public static void wailaInit(IWailaRegistrar registrar) {
        registrar.addConfigRemote(ModInfo.NAME, "hideinfusedore", true);
        registrar.registerStackProvider(new WailaInfusedBlockOre(), BlockInfusedBlockOre.class);
    }
}
