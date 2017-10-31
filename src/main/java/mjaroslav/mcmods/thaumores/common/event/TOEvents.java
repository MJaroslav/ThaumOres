package mjaroslav.mcmods.thaumores.common.event;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import mjaroslav.mcmods.thaumores.ThaumOresMod;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.config.TOConfig;
import mjaroslav.mcmods.thaumores.common.init.TOBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketSyncResearch;

public class TOEvents {
	@SubscribeEvent
	public void eventHarvestDrops(BlockEvent.HarvestDropsEvent event) {
		World world = event.world;
		int x = event.x;
		int y = event.y;
		int z = event.z;
		EntityPlayer player = event.harvester;
		Block block = event.block;
		if (block == TOBlocks.netherrackInfusedOre && TOConfig.generalAngryPigs)
			if (player != null && !player.capabilities.isCreativeMode)
				if (!event.isSilkTouching) {
					if (!world.isRemote) {
						List<EntityPigZombie> list = world.getEntitiesWithinAABB(EntityPigZombie.class,
								block.getCollisionBoundingBoxFromPool(world, x, y, z).expand(32.0D, 32.0D, 32.0D));

						for (EntityPigZombie entity : list)
							entity.becomeAngryAt(player);
					}
				}
	}

	public static ArrayList<String> researchCopyList = new ArrayList<String>();

	@SubscribeEvent
	public void eventPlayerTickEvent(TickEvent.PlayerTickEvent event) {
		if (!event.player.worldObj.isRemote) {
			for (String researchCopy : researchCopyList) {
				String[] researchInfo = researchCopy.split(":");
				if (researchInfo != null && researchInfo.length == 2) {
					if (ThaumcraftApiHelper.isResearchComplete(event.player.getCommandSenderName(), researchInfo[0]))
						if (!ThaumcraftApiHelper.isResearchComplete(event.player.getCommandSenderName(),
								researchInfo[1])) {
							Thaumcraft.proxy.getResearchManager().completeResearch(event.player, researchInfo[1]);
							PacketHandler.INSTANCE.sendTo(new PacketSyncResearch(event.player),
									(EntityPlayerMP) event.player);
						}
				}
			}
		}
	}

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
