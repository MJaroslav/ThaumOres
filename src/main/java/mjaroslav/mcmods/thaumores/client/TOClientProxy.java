package mjaroslav.mcmods.thaumores.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import mjaroslav.mcmods.thaumores.client.render.block.BlockInfusedBlockOreRenderer;
import mjaroslav.mcmods.thaumores.client.render.tile.TileInfusedBlockOreRenderer;
import mjaroslav.mcmods.thaumores.common.TOCommonProxy;
import mjaroslav.mcmods.thaumores.common.tile.TileInfusedOre;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

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

	@Override
	public EntityPlayer getEntityPlayer(MessageContext ctx) {
		return ctx.side == Side.CLIENT ? Minecraft.getMinecraft().thePlayer : super.getEntityPlayer(ctx);
	}

	@Override
	public Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}
}
