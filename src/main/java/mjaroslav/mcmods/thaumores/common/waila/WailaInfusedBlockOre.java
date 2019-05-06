package mjaroslav.mcmods.thaumores.common.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class WailaInfusedBlockOre implements IWailaDataProvider {
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        ItemStack stack = accessor.getStack();
        if (accessor.getBlock() instanceof BlockInfusedBlockOre && accessor.getPlayer() != null
                && config.getConfig("hideinfusedore"))
            stack = accessor.getBlock().getPickBlock(accessor.getPosition(), accessor.getWorld(), accessor.getPosition()
                            .blockX, accessor.getPosition().blockY, accessor.getPosition().blockZ,
                    accessor.getPlayer());
        return stack;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
                                     IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
                                     IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
                                     IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
                                     int y, int z) {
        return tag;
    }

}
