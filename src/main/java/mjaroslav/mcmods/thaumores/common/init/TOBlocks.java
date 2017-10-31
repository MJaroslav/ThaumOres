package mjaroslav.mcmods.thaumores.common.init;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.mjutils.common.objects.IModModule;
import mjaroslav.mcmods.mjutils.common.objects.ModInitModule;
import mjaroslav.mcmods.thaumores.ThaumOresMod;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.block.ItemInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.tile.TileInfusedOre;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/** Register all Blocks, ItemBlocks and TileEntities */
@ModInitModule(modid = ThaumOresMod.MODID)
public class TOBlocks implements IModModule {
	/* Render IDs */
	public static int renderInfusedBlockOreID = RenderingRegistry.getNextAvailableRenderId();

	/* Block list */
	/** Nether Infused Ore */
	public static Block netherrackInfusedOre = new BlockInfusedBlockOre(Blocks.netherrack, 0);
	/** End Stone Infused Ore */
	public static Block endstoneInfusedOre = new BlockInfusedBlockOre(Blocks.end_stone, 0);
	/** Bedrock Infused Ore */
	public static Block bedrockInfusedOre = new BlockInfusedBlockOre(Blocks.bedrock, 0);

	/** Names of primal aspects for oreDict */
	private static String[] primalNames = new String[] { "Air", "Fire", "Water", "Earth", "Order", "Entropy" };

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		GameRegistry.registerBlock(TOBlocks.netherrackInfusedOre, ItemInfusedBlockOre.class, "netherrackInfusedOre");
		GameRegistry.registerBlock(TOBlocks.endstoneInfusedOre, ItemInfusedBlockOre.class, "endstoneInfusedOre");
		GameRegistry.registerBlock(TOBlocks.bedrockInfusedOre, ItemInfusedBlockOre.class, "bedrockInfusedOre");
		GameRegistry.registerTileEntity(TileInfusedOre.class, "infusedBlockOre");
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		for (int meta = 0; meta < 6; meta++) {
			OreDictionary.registerOre("oreInfused" + TOBlocks.primalNames[meta],
					new ItemStack(TOBlocks.netherrackInfusedOre, 1, meta));
			OreDictionary.registerOre("oreInfused" + TOBlocks.primalNames[meta],
					new ItemStack(TOBlocks.bedrockInfusedOre, 1, meta));
			OreDictionary.registerOre("oreInfused" + TOBlocks.primalNames[meta],
					new ItemStack(TOBlocks.endstoneInfusedOre, 1, meta));
		}
	}

	@Override
	public String getModuleName() {
		return "Blocks";
	}

	@Override
	public int getPriority() {
		return 0;
	}
}
