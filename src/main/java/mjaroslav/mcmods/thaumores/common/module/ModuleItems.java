package mjaroslav.mcmods.thaumores.common.module;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.mjutils.module.Modular;
import mjaroslav.mcmods.mjutils.module.Module;
import mjaroslav.mcmods.thaumores.ThaumOres;
import mjaroslav.mcmods.thaumores.common.item.ItemShardCluster;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral;
import mjaroslav.mcmods.thaumores.lib.ModInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.common.config.ConfigItems;

@Module(ModInfo.MOD_ID)
public class ModuleItems implements Modular {
    public static Item shardCluster;

    private static final String[] primalNames = new String[]{"Air", "Fire", "Water", "Earth", "Order", "Entropy"};

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ModuleItems.shardCluster = new ItemShardCluster().setCreativeTab(ThaumOres.tab)
                .setUnlocalizedName("shardCluster");
        GameRegistry.registerItem(ModuleItems.shardCluster, "shardCluster");
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        for (int meta = 0; meta < 6; meta++)
            OreDictionary.registerOre("cluster" + ModuleItems.primalNames[meta],
                    new ItemStack(ModuleItems.shardCluster, 1, meta));
        if(CategoryGeneral.ironCapUncraft)
            GameRegistry.addShapelessRecipe(new ItemStack(ConfigItems.itemNugget, 1, 0),
                new ItemStack(ConfigItems.itemWandCap, 1, 0));
    }

    @Override
    public int priority() {
        return 1;
    }
}
