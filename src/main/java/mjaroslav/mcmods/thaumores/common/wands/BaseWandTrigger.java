package mjaroslav.mcmods.thaumores.common.wands;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.IWandTriggerManager;

public class BaseWandTrigger implements IWandTriggerManager {
    private AspectList consumeAspects;

    static final Aspect[] primals = new Aspect[]{Aspect.AIR, Aspect.FIRE, Aspect.WATER, Aspect.EARTH,
            Aspect.ORDER, Aspect.ENTROPY};

    private int id;

    BaseWandTrigger(AspectList reqAspects, int id) {
        this.consumeAspects = reqAspects;
        this.id = id;
    }

    @Override
    public boolean performTrigger(World world, ItemStack wand, EntityPlayer player, int x, int y, int z, int side,
                                  int event) {
        if (event == id)
            if (executionCondition(world, wand, player, x, y, z, side, event))
                if (consumeAspects.size() == 0
                        || ThaumcraftApiHelper.consumeVisFromWand(wand, player, consumeAspects, true, false))
                    return executeAction(world, wand, player, x, y, z, side, event);
        return true;
    }

    public boolean executionCondition(World world, ItemStack wand, EntityPlayer player, int x, int y, int z, int side,
                                      int event) {
        return false;
    }

    public boolean executeAction(World world, ItemStack wand, EntityPlayer player, int x, int y, int z, int side,
                                 int event) {
        return false;
    }
}
