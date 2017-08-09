package mjaroslav.mcmods.thaumores.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mjaroslav.mcmods.thaumores.client.render.block.BlockInfusedBlockOreRenderer;
import mjaroslav.mcmods.thaumores.client.render.tile.TileInfusedBlockOreRenderer;
import mjaroslav.mcmods.thaumores.common.TOCommonProxy;
import mjaroslav.mcmods.thaumores.common.tile.TileInfusedOre;

public class TOClientProxy extends TOCommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
	}

	@Override
	public void init(FMLInitializationEvent event) {
		RenderingRegistry.registerBlockHandler(new BlockInfusedBlockOreRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileInfusedOre.class, new TileInfusedBlockOreRenderer());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}
}
