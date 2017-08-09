package mjaroslav.mcmods.thaumores.common.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.thaumores.ThaumOresMod;
import mjaroslav.mcmods.thaumores.common.item.ItemShardCluster;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/** Register all Items */
public class TOItems {
	public static Item shardCluster = new ItemShardCluster().setCreativeTab(ThaumOresMod.tab)
			.setUnlocalizedName("shardCluster");
	
	/** Names of primal aspects for oreDict */
	private static String[] primalNames = new String[] { "Air", "Fire", "Water", "Earth", "Order", "Entropy" };

	public static void preInit(FMLPreInitializationEvent event) {
		GameRegistry.registerItem(shardCluster, "shardCluster");
	}

	public static void init(FMLInitializationEvent event) {
	}

	public static void postInit(FMLPostInitializationEvent event) {
		for (int meta = 0; meta < 6; meta++)
			OreDictionary.registerOre("heavyShard" + primalNames[meta], new ItemStack(shardCluster, 1, meta));
	}
}
