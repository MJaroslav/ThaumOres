package mjaroslav.mcmods.thaumores.client.gui;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import mjaroslav.mcmods.thaumores.ThaumOresMod;
import mjaroslav.mcmods.thaumores.common.config.TOConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.ConfigElement;

public class TOGuiConfig extends GuiConfig {
	public TOGuiConfig(GuiScreen parentScreen) {
		super(parentScreen, getElements(), ThaumOresMod.MODID, false, false, ThaumOresMod.NAME);
	}

	/** Create configuration element list */
	private static List<IConfigElement> getElements() {
		ArrayList<IConfigElement> list = new ArrayList<IConfigElement>();

		List<IConfigElement> listGeneral = new ConfigElement(TOConfig.config.getCategory(TOConfig.categoryGeneral))
				.getChildElements();
		List<IConfigElement> listDebug = new ConfigElement(TOConfig.config.getCategory(TOConfig.categoryDebug))
				.getChildElements();
		List<IConfigElement> listGeneration = new ConfigElement(
				TOConfig.config.getCategory(TOConfig.categoryGeneration)).getChildElements();

		list.add(new DummyCategoryElement(TOConfig.categoryGeneral,
				TOConfig.categoryGeneral, listGeneral));
		list.add(new DummyCategoryElement(TOConfig.categoryDebug,
				TOConfig.categoryDebug, listDebug));
		list.add(new DummyCategoryElement(TOConfig.categoryGeneration,
				TOConfig.categoryGeneration, listGeneration));
		return list;
	}
}
