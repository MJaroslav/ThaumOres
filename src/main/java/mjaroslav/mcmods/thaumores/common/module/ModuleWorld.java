package mjaroslav.mcmods.thaumores.common.module;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.mjutils.module.Modular;
import mjaroslav.mcmods.mjutils.module.Module;
import mjaroslav.mcmods.mjutils.util.UtilsFishing;
import mjaroslav.mcmods.mjutils.util.UtilsInteractions;
import mjaroslav.mcmods.thaumores.common.event.Events;
import mjaroslav.mcmods.thaumores.common.world.InfusedOreGeneration;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral.CategoryGeneration;
import mjaroslav.mcmods.thaumores.lib.ModInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.FishingHooks.FishableCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.common.config.ConfigItems;

@Module(ModInfo.MOD_ID)
public class ModuleWorld implements Modular {
    @Override
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(Events.instance);
        FMLCommonHandler.instance().bus().register(Events.instance);
        if (CategoryGeneral.angryPigs)
            UtilsInteractions.setPigmanTriggerBlock(ModuleBlocks.netherrackInfusedOre, OreDictionary.WILDCARD_VALUE,
                    true);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        if (CategoryGeneration.enable)
            GameRegistry.registerWorldGenerator(new InfusedOreGeneration(), 0);
        if (CategoryGeneral.fishingLoot) {
            UtilsFishing.add(new ItemStack(ConfigItems.itemWandCap, 1, 0), FishableCategory.JUNK, 5, false, 0);
            UtilsFishing.add(new ItemStack(ConfigItems.itemBaubleBlanks, 1, 0), FishableCategory.TREASURE, 1, false, 0);
            UtilsFishing.add(new ItemStack(ConfigItems.itemBaubleBlanks, 1, 2), FishableCategory.JUNK, 20, false, 0);
            UtilsFishing.add(new ItemStack(ConfigItems.itemBaubleBlanks, 1, 1), FishableCategory.TREASURE, 1, false, 0);
        }
    }

    @Override
    public int priority() {
        return 2;
    }
}
