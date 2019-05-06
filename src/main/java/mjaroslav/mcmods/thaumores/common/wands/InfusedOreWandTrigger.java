package mjaroslav.mcmods.thaumores.common.wands;

import mjaroslav.mcmods.mjutils.util.UtilsThaumcraft;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.module.ModuleThaumcraft;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.lib.UtilsFX;

public class InfusedOreWandTrigger extends BaseWandTrigger {
    public InfusedOreWandTrigger(int id) {
        super(new AspectList(), id);
    }

    @Override
    public boolean executionCondition(World world, ItemStack wand, EntityPlayer player, int x, int y, int z, int side,
                                      int event) {
        int meta = world.getBlockMetadata(x, y, z);
        return player.isSneaking() ? UtilsThaumcraft.isComplete(player, ModuleThaumcraft.riRawCluster) &&
                ThaumcraftApiHelper.consumeVisFromWand(wand, player, new AspectList().add(primals[meta], CategoryGeneral
                                .visCostPrimal * 100).add(Aspect.ORDER, CategoryGeneral.visCostPrimal * 100), true,
                        false) : ThaumcraftApiHelper.consumeVisFromWand(wand, player, new AspectList().add(
                Aspect.ENTROPY, CategoryGeneral.visCost * 100), true, false);
    }

    @Override
    public boolean executeAction(World world, ItemStack wand, EntityPlayer player, int x, int y, int z, int side,
                                 int event) {
        int md = world.getBlockMetadata(x, y, z);
        BlockInfusedBlockOre block = (BlockInfusedBlockOre) world.getBlock(x, y, z);
        if (world.isRemote) {
            for (int i = 0; i < 10; i++)
                if (md < 6)
                    UtilsFX.infusedStoneSparkle(world, x, y, z, md + 1);
            return false;
        } else {
            ForgeDirection dir = ForgeDirection.getOrientation(side);
            int iX = dir.equals(ForgeDirection.EAST) ? x + 1 : dir.equals(ForgeDirection.WEST) ? x - 1 : x;
            int iY = dir.equals(ForgeDirection.UP) ? y + 1 : dir.equals(ForgeDirection.DOWN) ? y - 1 : y;
            int iZ = dir.equals(ForgeDirection.SOUTH) ? z + 1 : dir.equals(ForgeDirection.NORTH) ? z - 1 : z;
            for (ItemStack item : block.getDropsWand(world, md, player.isSneaking())) {
                float f = 0.7F;
                double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                EntityItem entityitem = new EntityItem(world, (double) iX + d0, (double) iY + d1, (double) iZ + d2,
                        item);
                entityitem.delayBeforeCanPickup = 10;
                world.spawnEntityInWorld(entityitem);
            }
            world.setBlock(x, y, z, block.getBaseBlock(), block.getBaseMeta(), 2);
            return true;
        }
    }
}
