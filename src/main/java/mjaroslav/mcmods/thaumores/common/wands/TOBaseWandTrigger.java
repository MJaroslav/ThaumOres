package mjaroslav.mcmods.thaumores.common.wands;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.IWandTriggerManager;
import thaumcraft.common.items.wands.ItemWandCasting;

public class TOBaseWandTrigger implements IWandTriggerManager {
	/** This aspect list will consume from wand in process */
	private AspectList consumeAspects;

	/** Primal aspect list */
	protected static final Aspect[] primals = new Aspect[] { Aspect.AIR, Aspect.FIRE, Aspect.WATER, Aspect.EARTH,
			Aspect.ORDER, Aspect.ENTROPY };
	
	/** Event id */
	private int id;

	public TOBaseWandTrigger(AspectList reqAspects, int id) {
		this.consumeAspects = reqAspects;
		this.id = id;
	}

	@Override
	public boolean performTrigger(World world, ItemStack wand, EntityPlayer player, int x, int y, int z, int side,
			int event) {
		ItemWandCasting casting = (ItemWandCasting) wand.getItem();
		if (event == id)
			if (executionCondition(world, wand, player, x, y, z, side, event))
				if (consumeAspects.size() == 0
						|| ThaumcraftApiHelper.consumeVisFromWand(wand, player, consumeAspects, true, false))
					return executeAction(world, wand, player, x, y, z, side, event);
		return true;
	}

	/** The condition, before the action is performed */
	public boolean executionCondition(World world, ItemStack wand, EntityPlayer player, int x, int y, int z, int side,
			int event) {
		return false;
	}

	/** The action to be taken */
	public boolean executeAction(World world, ItemStack wand, EntityPlayer player, int x, int y, int z, int side,
			int event) {
		return false;
	}
}
