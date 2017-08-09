package mjaroslav.mcmods.thaumores.common.init;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.thaumores.ThaumOresMod;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.block.ItemInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.tile.TileInfusedOre;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/** Register all Blocks, ItemBlocks and TileEntities */
public class TOBlocks {
	/* Render IDs */
	public static int renderInfusedBlockOreID = RenderingRegistry.getNextAvailableRenderId();

	/* Block list */
	/** Nether Infused Ore */
	public static Block netherrackInfusedOre = new BlockInfusedBlockOre(Blocks.netherrack, 0)
			.setCreativeTab(ThaumOresMod.tab);
	/** End Stone Infused Ore */
	public static Block endstoneInfusedOre = new BlockInfusedBlockOre(Blocks.end_stone, 0)
			.setCreativeTab(ThaumOresMod.tab);
	/** Bedrock Infused Ore */
	public static Block bedrockInfusedOre = new BlockInfusedBlockOre(Blocks.bedrock, 0)
			.setCreativeTab(ThaumOresMod.tab);

	/** Names of primal aspects for oreDict */
	private static String[] primalNames = new String[] { "Air", "Fire", "Water", "Earth", "Order", "Entropy" };

	public static void preInit(FMLPreInitializationEvent event) {
		GameRegistry.registerBlock(netherrackInfusedOre, ItemInfusedBlockOre.class, "netherrackInfusedOre");
		GameRegistry.registerBlock(endstoneInfusedOre, ItemInfusedBlockOre.class, "endstoneInfusedOre");
		GameRegistry.registerBlock(bedrockInfusedOre, ItemInfusedBlockOre.class, "bedrockInfusedOre");
		GameRegistry.registerTileEntity(TileInfusedOre.class, "infusedBlockOre");
	}

	public static void init(FMLInitializationEvent event) {
	}

	public static void postInit(FMLPostInitializationEvent event) {
		for (int meta = 0; meta < 6; meta++) {
			OreDictionary.registerOre("oreInfused" + primalNames[meta], new ItemStack(netherrackInfusedOre, 1, meta));
			OreDictionary.registerOre("oreInfused" + primalNames[meta], new ItemStack(bedrockInfusedOre, 1, meta));
			OreDictionary.registerOre("oreInfused" + primalNames[meta], new ItemStack(endstoneInfusedOre, 1, meta));
		}
	}
}
