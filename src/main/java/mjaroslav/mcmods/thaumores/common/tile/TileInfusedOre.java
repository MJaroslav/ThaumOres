package mjaroslav.mcmods.thaumores.common.tile;

import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class TileInfusedOre extends TileEntity {
    @Override
    public boolean canUpdate() {
        return false;
    }

    public Block getBaseBlock() {
        if (worldObj.getBlock(xCoord, yCoord, zCoord) instanceof BlockInfusedBlockOre)
            return ((BlockInfusedBlockOre) worldObj.getBlock(xCoord, yCoord, zCoord)).getBaseBlock();
        return worldObj.getBlock(xCoord, yCoord, zCoord);
    }

    public int getBaseMeta() {
        if (worldObj.getBlock(xCoord, yCoord, zCoord) instanceof BlockInfusedBlockOre)
            return ((BlockInfusedBlockOre) worldObj.getBlock(xCoord, yCoord, zCoord)).getBaseMeta();
        return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }
}
