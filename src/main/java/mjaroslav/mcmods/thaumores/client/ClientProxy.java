package mjaroslav.mcmods.thaumores.client;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import mjaroslav.mcmods.mjutils.util.UtilsRender;
import mjaroslav.mcmods.thaumores.client.render.block.BlockInfusedBlockOreRenderer;
import mjaroslav.mcmods.thaumores.client.render.tile.TileInfusedBlockOreRenderer;
import mjaroslav.mcmods.thaumores.common.CommonProxy;
import mjaroslav.mcmods.thaumores.common.tile.TileInfusedOre;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {
    @Override
    public void init(FMLInitializationEvent event) {
        UtilsRender.rendererBlock(new BlockInfusedBlockOreRenderer());
        UtilsRender.rendererTileEntity(TileInfusedOre.class, new TileInfusedBlockOreRenderer());
    }

    @Override
    public EntityPlayer getEntityPlayer(MessageContext ctx) {
        return ctx.side == Side.CLIENT ? Minecraft.getMinecraft().thePlayer : super.getEntityPlayer(ctx);
    }
}
