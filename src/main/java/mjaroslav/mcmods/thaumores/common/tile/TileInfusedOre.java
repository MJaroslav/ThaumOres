package mjaroslav.mcmods.thaumores.common.tile;

import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class TileInfusedOre extends TileEntity {
	public TileInfusedOre() {
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	/** Get base block */
	public Block getBaseBlock() {
		if (worldObj.getBlock(xCoord, yCoord, zCoord) instanceof BlockInfusedBlockOre)
			return ((BlockInfusedBlockOre) worldObj.getBlock(xCoord, yCoord, zCoord)).getBaseBlock();
		return worldObj.getBlock(xCoord, yCoord, zCoord);
	}

	/** Get base block meta data */
	public int getBaseMeta() {
		if (worldObj.getBlock(xCoord, yCoord, zCoord) instanceof BlockInfusedBlockOre)
			return ((BlockInfusedBlockOre) worldObj.getBlock(xCoord, yCoord, zCoord)).getBaseMeta();
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}
}
