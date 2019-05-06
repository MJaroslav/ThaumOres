package mjaroslav.mcmods.thaumores.common.module;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import mjaroslav.mcmods.mjutils.module.Modular;
import mjaroslav.mcmods.mjutils.module.Module;
import mjaroslav.mcmods.thaumores.common.waila.PluginWaila;
import mjaroslav.mcmods.thaumores.lib.ModInfo;

@Module(ModInfo.MOD_ID)
public class ModuleIntegrations implements Modular {
    @Override
    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendMessage("Waila", "register", String.format("%s.wailaInit", PluginWaila.class.getName()));
    }
}
