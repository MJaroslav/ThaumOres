package mjaroslav.mcmods.thaumores.common.event;

import baubles.api.BaublesApi;
import baubles.common.Config;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mjaroslav.mcmods.mjutils.object.event.FishingSuccessEvent;
import mjaroslav.mcmods.mjutils.util.UtilsInventory;
import mjaroslav.mcmods.thaumores.ThaumOres;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral.CategoryDebug;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.FishingHooks.FishableCategory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.monster.boss.EntityTaintacleGiant;

public class Events {
    public static final Events instance = new Events();

    private Events() {
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        World world = event.world;
        int x = event.x;
        int z = event.z;
        EntityPlayer player = event.getPlayer();
        if (player != null && player.getHeldItem() != null && player.getHeldItem().getItem() == Items.stick) {
            if (CategoryDebug.enable && CategoryDebug.remover && !world.isRemote
                    && player.capabilities.isCreativeMode) {
                int radius = CategoryDebug.radius;
                for (int xx = x - radius; xx < x + radius; xx++)
                    for (int zz = z - radius; zz < z + radius; zz++)
                        for (int yy = 0; yy < 256; yy++)
                            if (!(world.getBlock(xx, yy, zz) instanceof BlockInfusedBlockOre))
                                world.setBlock(xx, yy, zz, Blocks.air, 0, 2);
                String text = String.format("Blocks removed at [%s;%s] with radius [%s]", x, z, radius);
                player.addChatMessage(new ChatComponentText(text));
                ThaumOres.debug(text);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        World world = event.world;
        int x = event.x;
        int z = event.z;
        EntityPlayer player = event.entityPlayer;
        if (player != null && player.getHeldItem() != null && player.getHeldItem().getItem() == Items.stick) {
            if (CategoryDebug.enable && CategoryDebug.scanner && !world.isRemote
                    && event.action.equals(Action.RIGHT_CLICK_BLOCK) && player.capabilities.isCreativeMode) {
                int radius = CategoryDebug.radius;
                int[] counter = new int[]{0, 0, 0, 0, 0, 0};
                for (int xx = x - radius; xx < x + radius; xx++)
                    for (int zz = z - radius; zz < z + radius; zz++)
                        for (int yy = 0; yy < 256; yy++)
                            if ((world.getBlock(xx, yy, zz) instanceof BlockInfusedBlockOre)
                                    && world.getBlockMetadata(xx, yy, zz) < 6)
                                counter[world.getBlockMetadata(xx, yy, zz)]++;
                StringBuilder text = new StringBuilder();
                text.append(String.format("Blocks scanned at [%s;%s] with radius [%s]:", x, z, radius));
                for (int meta = 0; meta < 6; meta++)
                    text.append(String.format("\nMeta %s count: %s", meta, counter[meta]));
                for (String string : text.toString().split("\n")) {
                    ThaumOres.debug(string);
                    player.addChatMessage(new ChatComponentText(string));
                }
            }
        }
    }

    private ItemStack lazyRing;

    private ItemStack lazyRing() {
        if (lazyRing == null)
            lazyRing = new ItemStack(ConfigItems.itemBaubleBlanks, 1, 1);
        return lazyRing;
    }

    @SubscribeEvent
    public void onFishingSuccess(FishingSuccessEvent event) {
        if (event.world.isRemote)
            return;
        if (CategoryGeneral.minersRing && event.y < 11 && event.world.rand.nextInt(10) == 0) {
            IInventory baubles = BaublesApi.getBaubles(event.fisher);
            int ringSlot = -1;
            for (int id = 0; id < baubles.getSizeInventory(); id++)
                if (UtilsInventory.itemStackTypeEquals(baubles.getStackInSlot(id), lazyRing(), false)) {
                    ringSlot = id;
                    break;
                }
            if (ringSlot > -1) {
                baubles.setInventorySlotContents(ringSlot, null);
                IChatComponent component = new ChatComponentTranslation("chat.thaumores.ring");
                component.getChatStyle().setItalic(true).setColor(EnumChatFormatting.GRAY);
                event.fisher.addChatMessage(component);
                event.catchStack = new ItemStack(Config.itemRing, 1);
                return;
            }
        }
        if (event.world.getBiomeGenForCoords((int) event.x, (int) event.z).biomeID ==
                thaumcraft.common.config.Config.biomeTaintID) {
            if (CategoryGeneral.warpByFishingInTaint && event.world.rand.nextInt(2) == 0) {
                int chance = event.world.rand.nextInt(10);
                if (chance == 0)
                    ThaumcraftApiHelper.addWarpToPlayer(event.fisher, 1, false);
                else if (chance < 4)
                    ThaumcraftApiHelper.addStickyWarpToPlayer(event.fisher, 1);
                else ThaumcraftApiHelper.addWarpToPlayer(event.fisher, 1, true);
            }
            if (CategoryGeneral.fishingLoot) {
                if (event.world.rand.nextInt(500 - (event.luck < 4 ? event.luck * 50
                        : 150)) == 0) {
                    int x = (int) event.x;
                    int z = (int) event.z;
                    int height = event.world.getHeightValue(x, z);
                    int y = height;
                    while (y > 0 && event.world.getBlock(x, y, z).getMaterial().equals(Material.water))
                        y--;
                    if (height - y < 10) {
                        IChatComponent component = new ChatComponentTranslation("chat.thaumores.taintacle");
                        component.getChatStyle().setItalic(true).setColor(EnumChatFormatting.DARK_PURPLE);
                        event.fisher.addChatMessage(component);
                        EntityTaintacleGiant taintacle = new EntityTaintacleGiant(event.world);
                        taintacle.setLocationAndAngles(event.x, y, event.z, 0.0f, 0.0f);
                        event.world.spawnEntityInWorld(taintacle);
                        event.category = null;
                    }
                    event.catchStack = new ItemStack(ConfigItems.itemResource, 1, 12);
                    return;
                } else if (event.world.rand.nextInt(20) == 0) {
                    event.category = FishableCategory.JUNK;
                    event.catchStack = new ItemStack(ConfigItems.itemResource, 1, 12);
                    return;
                }
            }
        }
        if (CategoryGeneral.lootBags) {
            int fishCount = 0;
            for (FishableCategory category : FishableCategory.values())
                fishCount += ((EntityPlayerMP) event.fisher).func_147099_x().writeStat(category.stat);
            if (fishCount > 0 && (fishCount % 25 == 0 || fishCount % 10 == 0)) {
                event.catchStack = new ItemStack(ConfigItems.itemLootbag, 1, fishCount % 50 == 0 ? 2 : fishCount % 25 == 0 ? 1 : 0);
                event.category = FishableCategory.TREASURE;
            }
        }
    }
}
