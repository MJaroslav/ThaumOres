package mjaroslav.mcmods.thaumores.common;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mjaroslav.mcmods.mjutils.module.Proxy;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy extends Proxy {
    @Override
    public EntityPlayer getEntityPlayer(MessageContext ctx) {
        return ctx.getServerHandler().playerEntity;
    }
}
