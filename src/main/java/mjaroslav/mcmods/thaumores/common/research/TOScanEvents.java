package mjaroslav.mcmods.thaumores.common.research;

import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.init.TOBlocks;
import mjaroslav.mcmods.thaumores.common.init.TOThaum;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.research.IScanEventHandler;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ScanResult;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketSyncResearch;
import thaumcraft.common.lib.research.ResearchManager;

public class TOScanEvents implements IScanEventHandler {
	@Override
	public ScanResult scanPhenomena(ItemStack stack, World world, EntityPlayer player) {
		/*if (stack != null && stack.getItem() != null && Block.getBlockFromItem(stack.getItem()) != null
				&& Block.getBlockFromItem(stack.getItem()) instanceof BlockInfusedBlockOre) {
			BlockInfusedBlockOre ore = (BlockInfusedBlockOre) Block.getBlockFromItem(stack.getItem());
			if (ore == TOBlocks.netherrackInfusedOre && !ThaumcraftApiHelper
					.isResearchComplete(player.getCommandSenderName(), TOThaum.riInfusedNetherrack))
				giveResearch(player, TOThaum.riInfusedNetherrack);
			if (ore == TOBlocks.bedrockInfusedOre
					&& !ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), TOThaum.riInfusedBedrock))
				giveResearch(player, TOThaum.riInfusedBedrock);
			if (ore == TOBlocks.endstoneInfusedOre && !ThaumcraftApiHelper
					.isResearchComplete(player.getCommandSenderName(), TOThaum.riInfusedEndstone))
				giveResearch(player, TOThaum.riInfusedEndstone);

		}*/
		return null;
	}

	public static void giveResearch(EntityPlayer player, String research) {
		if (ResearchCategories.getResearch(research) != null) {
			giveRecursiveResearch(player, research);
			PacketHandler.INSTANCE.sendToServer(new PacketSyncResearch(player));
		}
	}

	public static void giveRecursiveResearch(EntityPlayer player, String research) {
		if (ResearchManager.isResearchComplete(player.getCommandSenderName(), research))
			return;
		Thaumcraft.proxy.getResearchManager().completeResearch(player, research);

		if (ResearchCategories.getResearch(research).parents != null) {
			for (String rsi : ResearchCategories.getResearch(research).parents)
				giveRecursiveResearch(player, rsi);
		}
		if (ResearchCategories.getResearch(research).parentsHidden != null) {
			for (String rsi : ResearchCategories.getResearch(research).parentsHidden)
				giveRecursiveResearch(player, rsi);
		}
		if (ResearchCategories.getResearch(research).siblings != null)
			for (String rsi : ResearchCategories.getResearch(research).siblings)
				giveRecursiveResearch(player, rsi);
	}
}
