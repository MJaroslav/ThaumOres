package mjaroslav.mcmods.thaumores.common.module;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.mjutils.module.Modular;
import mjaroslav.mcmods.mjutils.module.Module;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.block.ItemInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.tile.TileInfusedOre;
import mjaroslav.mcmods.thaumores.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@Module(ModInfo.MOD_ID)
public class ModuleBlocks implements Modular {
    private static final String[] primalNames = new String[]{"Air", "Fire", "Water", "Earth", "Order", "Entropy"};

    public static int renderInfusedBlockOreID = RenderingRegistry.getNextAvailableRenderId();

    public static Block netherrackInfusedOre = new BlockInfusedBlockOre(Blocks.netherrack, 0);
    public static Block endstoneInfusedOre = new BlockInfusedBlockOre(Blocks.end_stone, 0);
    public static Block bedrockInfusedOre = new BlockInfusedBlockOre(Blocks.bedrock, 0);


    @Override
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerBlock(ModuleBlocks.netherrackInfusedOre, ItemInfusedBlockOre.class, "netherrackInfusedOre");
        GameRegistry.registerBlock(ModuleBlocks.endstoneInfusedOre, ItemInfusedBlockOre.class, "endstoneInfusedOre");
        GameRegistry.registerBlock(ModuleBlocks.bedrockInfusedOre, ItemInfusedBlockOre.class, "bedrockInfusedOre");
        GameRegistry.registerTileEntity(TileInfusedOre.class, "infusedBlockOre");
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        for (int meta = 0; meta < 6; meta++) {
            OreDictionary.registerOre("oreInfused" + ModuleBlocks.primalNames[meta],
                    new ItemStack(ModuleBlocks.netherrackInfusedOre, 1, meta));
            OreDictionary.registerOre("oreInfused" + ModuleBlocks.primalNames[meta],
                    new ItemStack(ModuleBlocks.bedrockInfusedOre, 1, meta));
            OreDictionary.registerOre("oreInfused" + ModuleBlocks.primalNames[meta],
                    new ItemStack(ModuleBlocks.endstoneInfusedOre, 1, meta));
        }
    }
}
