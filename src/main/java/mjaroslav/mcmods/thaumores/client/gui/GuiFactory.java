package mjaroslav.mcmods.thaumores.client.gui;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import mjaroslav.mcmods.thaumores.ThaumOres;
import mjaroslav.mcmods.thaumores.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.Set;

public class GuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ModGuiConfig.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    public static class ModGuiConfig extends GuiConfig {
        public ModGuiConfig(GuiScreen parentScreen) {
            super(parentScreen, ThaumOres.config.generalToElementList(), ModInfo.MOD_ID, false, false, ModInfo.NAME);
        }
    }
}
