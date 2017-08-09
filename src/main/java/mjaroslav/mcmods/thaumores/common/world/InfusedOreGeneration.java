package mjaroslav.mcmods.thaumores.common.world;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.config.TOConfig;
import mjaroslav.mcmods.thaumores.common.init.TOBlocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.lib.world.biomes.BiomeHandler;

public class InfusedOreGeneration implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		if (TOConfig.generationEnable)
			switch (world.provider.dimensionId) {
			case -1: {
				generateNether(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
				break;
			}
			case 0: {
				generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
				break;
			}
			case 1: {
				generateEnd(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
				break;
			}
			}
	}

	private void generateEnd(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		if (TOConfig.generationOres && TOConfig.generationEndstoneEnable)
			gemerateOre((BlockInfusedBlockOre) TOBlocks.endstoneInfusedOre, world, random, chunkX, chunkZ,
					TOConfig.generationEndstoneVeinSize, TOConfig.generationEndstoneMaxOnChunk,
					TOConfig.generationEndstoneMinY, TOConfig.generationEndstoneMaxY);
	}

	private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		if (TOConfig.generationOres && TOConfig.generationBedrockEnable)
			gemerateOre((BlockInfusedBlockOre) TOBlocks.bedrockInfusedOre, world, random, chunkX, chunkZ,
					TOConfig.generationBedrockVeinSize, TOConfig.generationBedrockMaxOnChunk,
					TOConfig.generationBedrockMinY, TOConfig.generationBedrockMaxY);
	}

	private void generateNether(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		if (TOConfig.generationOres && TOConfig.generationNetherrackEnable)
			gemerateOre((BlockInfusedBlockOre) TOBlocks.netherrackInfusedOre, world, random, chunkX, chunkZ,
					TOConfig.generationNetherrackVeinSize, TOConfig.generationNetherrackMaxOnChunk,
					TOConfig.generationNetherrackMinY, TOConfig.generationNetherrackMaxY);
	}

	private void gemerateOre(BlockInfusedBlockOre block, World world, Random random, int chunkX, int chunkZ, int count,
			int chancesToSpawn, int minHeight, int maxHeight) {
		if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
			throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

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
