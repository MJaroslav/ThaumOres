package mjaroslav.mcmods.thaumores.common.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mjaroslav.mcmods.thaumores.ThaumOresMod;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.config.TOConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;

public class TOEvents {
	@SubscribeEvent
	public void eventBreakEvent(BlockEvent.BreakEvent event) {
		World world = event.world;
		int x = event.x;
		int y = event.y;
		int z = event.z;
		EntityPlayer player = event.getPlayer();

		if (player != null && player.getHeldItem() != null && player.getHeldItem().getItem() == Items.stick) {
			if (TOConfig.debugEnable && TOConfig.debugRemover && !world.isRemote
					&& player.capabilities.isCreativeMode) {
				int radius = TOConfig.debugRemoverScannerRadius;
				for (int xx = x - radius; xx < x + radius; xx++)
					for (int zz = z - radius; zz < z + radius; zz++)
						for (int yy = 0; yy < 257; yy++)
							if (!(world.getBlock(xx, yy, zz) instanceof BlockInfusedBlockOre))
								world.setBlock(xx, yy, zz, Blocks.air, 0, 2);
				String text = "[DEBUG " + ThaumOresMod.NAME + "] Removed blocks at " + x + ";" + z + " with radius "
						+ radius;
				player.addChatMessage(new ChatComponentText(text));
				ThaumOresMod.log.info(text);
			}
		}
	}

	@SubscribeEvent
	public void eventPlayerInteractEvent(PlayerInteractEvent event) {
		World world = event.world;
		int x = event.x;
		int y = event.y;
		int z = event.z;
		EntityPlayer player = event.entityPlayer;
		if (player != null && player.getHeldItem() != null && player.getHeldItem().getItem() == Items.stick) {
			if (TOConfig.debugEnable && TOConfig.debugScanner && !world.isRemote
					&& event.action.equals(Action.RIGHT_CLICK_BLOCK) && player.capabilities.isCreativeMode) {
				int radius = TOConfig.debugRemoverScannerRadius;
				int[] counter = new int[] { 0, 0, 0, 0, 0, 0 };
				for (int xx = x - radius; xx < x + radius; xx++)
					for (int zz = z - radius; zz < z + radius; zz++)
						for (int yy = 0; yy < 257; yy++)
							if ((world.getBlock(xx, yy, zz) instanceof BlockInfusedBlockOre)
									&& world.getBlockMetadata(xx, yy, zz) < 6)
								counter[world.getBlockMetadata(xx, yy, zz)]++;
				String text = "[DEBUG " + ThaumOresMod.NAME + "] Scanned blocks at " + x + ";" + z + " with radius "
						+ radius;
				for (int meta = 0; meta < 6; meta++)
					text += "\n Count ores with meta " + meta + " = " + counter[meta];
				for (String string : text.split("\n")) {
					ThaumOresMod.log.info(string);
					player.addChatMessage(new ChatComponentText(string));
				}
			}
		}
	}
}
