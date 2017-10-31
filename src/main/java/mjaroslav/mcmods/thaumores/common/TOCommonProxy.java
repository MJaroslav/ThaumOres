package mjaroslav.mcmods.thaumores.common;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class TOCommonProxy {
	public void preInit(FMLPreInitializationEvent event) {

	}

	public void init(FMLInitializationEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {

	}

	public Minecraft getMinecraft() {
		return null;
	}

	public EntityPlayer getEntityPlayer(MessageContext ctx) {
		return null;
	}

	public void spawnParticle(String name, double x, double y, double z, Object... args) {
	}
}
