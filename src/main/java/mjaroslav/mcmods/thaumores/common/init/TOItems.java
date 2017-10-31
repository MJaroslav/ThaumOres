package mjaroslav.mcmods.thaumores.common.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.mjutils.common.objects.IModModule;
import mjaroslav.mcmods.mjutils.common.objects.ModInitModule;
import mjaroslav.mcmods.thaumores.ThaumOresMod;
import mjaroslav.mcmods.thaumores.common.item.ItemShardCluster;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/** Register all Items */
@ModInitModule(modid = ThaumOresMod.MODID)
public class TOItems implements IModModule {
	public static Item shardCluster;

	/** Names of primal aspects for oreDict */
	private static String[] primalNames = new String[] { "Air", "Fire", "Water", "Earth", "Order", "Entropy" };

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		TOItems.shardCluster = new ItemShardCluster().setCreativeTab(ThaumOresMod.tab)
				.setUnlocalizedName("shardCluster");
		GameRegistry.registerItem(TOItems.shardCluster, "shardCluster");
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		for (int meta = 0; meta < 6; meta++)
			OreDictionary.registerOre("heavyShard" + TOItems.primalNames[meta],
					new ItemStack(TOItems.shardCluster, 1, meta));
	}

	@Override
	public String getModuleName() {
		return "Items";
	}

	@Override
	public int getPriority() {
		return 1;
	}
}
