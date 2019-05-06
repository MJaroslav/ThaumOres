package mjaroslav.mcmods.thaumores.common.world;

import cpw.mods.fml.common.IWorldGenerator;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.module.ModuleBlocks;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral.CategoryGeneration.CategoryBedrock;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral.CategoryGeneration.CategoryEndstone;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral.CategoryGeneration.CategoryNetherrack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.lib.world.biomes.BiomeHandler;

import java.util.Random;

public class InfusedOreGeneration implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
                         IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
            case -1: {
                generateNether(random, chunkX, chunkZ, world);
                break;
            }
            case 0: {
                generateOverworld(random, chunkX, chunkZ, world);
                break;
            }
            case 1: {
                generateEnd(random, chunkX, chunkZ, world);
                break;
            }
        }
    }

    private void generateEnd(Random random, int chunkX, int chunkZ, World world) {
        if (CategoryEndstone.enable)
            generateOre((BlockInfusedBlockOre) ModuleBlocks.endstoneInfusedOre, world, random, chunkX, chunkZ,
                    CategoryEndstone.veinSize, CategoryEndstone.maxOnChunk, CategoryEndstone.minY,
                    CategoryEndstone.maxY);
    }

    private void generateOverworld(Random random, int chunkX, int chunkZ, World world) {
        if (CategoryBedrock.enable)
            generateOre((BlockInfusedBlockOre) ModuleBlocks.bedrockInfusedOre, world, random, chunkX, chunkZ,
                    CategoryBedrock.veinSize, CategoryBedrock.maxOnChunk, CategoryBedrock.minY, CategoryBedrock.maxY);
    }

    private void generateNether(Random random, int chunkX, int chunkZ, World world) {
        if (CategoryNetherrack.enable)
            generateOre((BlockInfusedBlockOre) ModuleBlocks.netherrackInfusedOre, world, random, chunkX, chunkZ,
                    CategoryNetherrack.veinSize, CategoryNetherrack.maxOnChunk, CategoryNetherrack.minY,
                    CategoryNetherrack.maxY);
    }

    private void generateOre(BlockInfusedBlockOre block, World world, Random random, int chunkX, int chunkZ, int count,
                             int chancesToSpawn, int minHeight, int maxHeight) {
        if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
            throw new IllegalArgumentException(String.format(
                    "Illegal Height Arguments for WorldGenerator: min=%s, max=%s", minHeight, maxHeight));

        int heightDiff = maxHeight - minHeight + 1;
        for (int i = 0; i < chancesToSpawn; i++) {
            int x = chunkX * 16 + random.nextInt(16);
            int y = minHeight + random.nextInt(heightDiff);
            int z = chunkZ * 16 + random.nextInt(16);

            int md = random.nextInt(6);
            if (random.nextInt(3) == 0) {
                Aspect tag = BiomeHandler.getRandomBiomeTag(world.getBiomeGenForCoords(x, z).biomeID, random);
                if (tag == null) {
                    md = random.nextInt(6);
                } else if (tag == Aspect.AIR)
                    md = 0;
                else if (tag == Aspect.FIRE)
                    md = 1;
                else if (tag == Aspect.WATER)
                    md = 2;
                else if (tag == Aspect.EARTH)
                    md = 3;
                else if (tag == Aspect.ORDER)
                    md = 4;
                else if (tag == Aspect.ENTROPY)
                    md = 5;
            }
            new WorldGenMinable(block, md, count, block.getBaseBlock()).generate(world, random, x, y, z);
        }
    }
}
