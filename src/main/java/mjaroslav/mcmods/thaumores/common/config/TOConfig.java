package mjaroslav.mcmods.thaumores.common.config;

import java.lang.reflect.Field;

import org.apache.logging.log4j.Logger;

import mjaroslav.mcmods.mjutils.common.objects.ConfigurationBase;
import mjaroslav.mcmods.thaumores.ThaumOresMod;
import net.minecraftforge.common.config.Configuration;

public class TOConfig extends ConfigurationBase {
	/** ThaumOres: configuration */
	private static Configuration instance;

	/** Debug options */
	public static final String categoryDebug = "debug";
	/** Enable debug */
	public static boolean debugEnable;
	/** Log ThaumOres researches */
	public static boolean debugResearches;
	/** Log configuration fields */
	public static boolean debugConfig;
	/**
	 * Enable block remover (break any block with stick) (don't remove infused
	 * ore)
	 */
	public static boolean debugRemover;
	/**
	 * Enable ore scanner (right click on any block with stick) (Only scans
	 * infused ore)
	 */
	public static boolean debugScanner;
	/** Block remover and scanner radius */
	public static int debugRemoverScannerRadius;

	/** Main options */
	public static final String categoryGeneral = "general";
	/** Pig Zombies will be angry if you mine infused ore in Nether */
	public static boolean generalAngryPigs;
	/** Raw clusters and infused ore primal aspect count */
	public static int generalAspectCount;
	/** Count of perditio for wand click on infused ore */
	public static int generalWandVisCount;
	/** Count of ordo and ore aspect for wand click on infused ore */
	public static int generalWandVisCountPrimal;
	/** Multiplier for warp visual acuity (Distance = warpCount * this value) */
	public static float generalWarpVisualAcuityModifier;

	/** Graphics options */
	public static final String categoryGeneralGraphics = categoryGeneral + ".graphics";
	/** Use old icons (Heavy shards) for raw clusters */
	public static boolean generalGraphicsClustersOldIcons;
	/** Render particles on infused ore without Glasses/Thaumometer */
	public static boolean generalGraphicsRenderInfusedOreParticles;
	/** Render particles on infused ore with Glasses/Thaumometer */
	public static boolean generalGraphicsRenderInfusedOreParticlesGlasses;
	/**
	 * Chance spawn particle on infused ore without Glasses/Thaumometer (1 in
	 * this value)
	 */
	public static int generalGraphicsRenderInfusedOreParticlesChance;
	/**
	 * Chance spawn particle on infused ore with Glasses/Thaumometer (1 in this
	 * value)
	 */
	public static int generalGraphicsRenderInfusedOreParticlesGlassesChance;

	/** WorldGen options */
	public static final String categoryGeneration = "generation";
	/** Enable all generation */
	public static boolean generationEnable;
	/** Enable ore generation */
	public static boolean generationOres;
	/** Infused bedrock generation options */
	public static final String categoryGenerationBedrock = categoryGeneration + ".bedrock";
	/** Enable infused bedrock generation */
	public static boolean generationBedrockEnable;
	/** Infused bedrock generation min height */
	public static int generationBedrockMinY;
	/** Infused bedrock generation max height */
	public static int generationBedrockMaxY;
	/** Infused bedrock generation max veins on chunk */
	public static int generationBedrockMaxOnChunk;
	/** Infused bedrock generation max vein size */
	public static int generationBedrockVeinSize;
	/** Infused netherrack generation options */
	public static final String categoryGenerationNetherrack = categoryGeneration + ".netherrack";
	/** Enable infused netherrack generation */
	public static boolean generationNetherrackEnable;
	/** Infused netherrack generation min height */
	public static int generationNetherrackMinY;
	/** Infused netherrack generation max height */
	public static int generationNetherrackMaxY;
	/** Infused netherrack generation max veins on chunk */
	public static int generationNetherrackMaxOnChunk;
	/** Infused netherrack generation max vein size */
	public static int generationNetherrackVeinSize;
	/** Infused endstone generation options */
	public static final String categoryGenerationEndstone = categoryGeneration + ".endstone";
	/** Enable infused endstone generation */
	public static boolean generationEndstoneEnable;
	/** Infused endstone generation min height */
	public static int generationEndstoneMinY;
	/** Infused endstone generation max height */
	public static int generationEndstoneMaxY;
	/** Infused endstone generation max veins on chunk */
	public static int generationEndstoneMaxOnChunk;
	/** Infused endstone generation max vein size */
	public static int generationEndstoneVeinSize;

	@Override
	public void readFields() {
		syncDebug();
		syncGeneral();
		syncGeneration();
		if (debugEnable && debugConfig)
			logFields();
	}

	private static void syncDebug() {
		instance.addCustomCategoryComment(categoryDebug, "Debug options");
		debugEnable = instance.getBoolean("enable", categoryDebug, false, "Enable debug");
		debugConfig = instance.getBoolean("config", categoryDebug, true, "Log configuration fields ");
		debugResearches = instance.getBoolean("researches", categoryDebug, true, "Log ThaumOres researches");
		debugRemover = instance.getBoolean("remover", categoryDebug, true,
				"Enable block remover (break any block with stick) (don't remove infused ore)");
		debugScanner = instance.getBoolean("scanner", categoryDebug, true,
				"Enable ore scanner (right click on any block with stick) (Only scans infused ore)");
		debugRemoverScannerRadius = instance.getInt("remover_scanner_radius", categoryDebug, 8, 1, 30,
				"Block remover and scanner radius");
	}

	private static void syncGeneral() {
		instance.addCustomCategoryComment(categoryGeneral, "Main options");
		generalAngryPigs = instance.getBoolean("angry_pigs", categoryGeneral, true,
				"Pig Zombies will be angry if you mine infused ore in Nether");
		generalAspectCount = instance.getInt("aspect_count", categoryGeneral, 8, 1, 16,
				"Raw clusters and infused ore primal aspect count ");
		generalWandVisCount = instance.getInt("wand_vis_count", categoryGeneral, 1, 0, 100,
				"Count of perditio for wand click on infused ore");
		generalWandVisCountPrimal = instance.getInt("wand_vis_count_primal", categoryGeneral, 2, 1, 200,
				"Count of ordo and ore aspect for wand click on infused ore");
		generalWarpVisualAcuityModifier = instance.getFloat("warp_visual_acuity_modifier", categoryGeneral, 1.5F, 0.01F,
				10F, "Multiplier for warp visual acuity (Distance = warpCount * this value)");

		instance.addCustomCategoryComment(categoryGeneralGraphics, "Graphics options");
		generalGraphicsClustersOldIcons = instance.getBoolean("clusters_olds_icons", categoryGeneralGraphics, false,
				"Use old icons (Heavy shards) for raw clusters");
		generalGraphicsRenderInfusedOreParticles = instance.getBoolean("infused_ore_particles", categoryGeneralGraphics,
				true, "Render particles on infused ore without Glasses/Thaumometer");
		generalGraphicsRenderInfusedOreParticlesGlasses = instance.getBoolean("infused_ore_particles_glasses",
				categoryGeneralGraphics, true, "Render particles on infused ore with Glasses/Thaumometer");
		generalGraphicsRenderInfusedOreParticlesChance = instance.getInt("infused_ore_particles_chance",
				categoryGeneralGraphics, 10000, 4000, 10000,
				"Chance spawn particle on infused ore without Glasses/Thaumometer (1 in this value)");
		generalGraphicsRenderInfusedOreParticlesGlassesChance = instance.getInt("infused_ore_particles_glasses_chance",
				categoryGeneralGraphics, 100, 20, 10000,
				"Chance spawn particle on infused ore with Glasses/Thaumometer (1 in this value)");
	}

	private static void syncGeneration() {
		instance.addCustomCategoryComment(categoryGeneration, "WorldGen options");
		generationEnable = instance.getBoolean("enable", categoryGeneration, true, "Enable generation");
		generationOres = instance.getBoolean("ores", categoryGeneration, true, "Enable ore generation");

		instance.addCustomCategoryComment(categoryGenerationBedrock, "Infused bedrock generation options ");
		generationBedrockEnable = instance.getBoolean("enable", categoryGenerationBedrock, true, "Enable generation");
		generationBedrockMaxOnChunk = instance.getInt("max_on_chunk", categoryGenerationBedrock, 8, 1, 30,
				"Max veins on chunk");
		generationBedrockVeinSize = instance.getInt("vein_size", categoryGenerationBedrock, 4, 1, 10,
				"Infused bedrock generation max vein size");
		generationBedrockMinY = instance.getInt("min_y", categoryGenerationBedrock, 3, 0, 256, "Min generation height");
		generationBedrockMaxY = instance.getInt("max_y", categoryGenerationBedrock, 5, 0, 256, "Max generation height");

		if (generationBedrockMaxY < generationBedrockMinY)
			generationBedrockMaxY = generationBedrockMinY;

		instance.addCustomCategoryComment(categoryGenerationNetherrack, "Infused netherrack generation options ");
		generationNetherrackEnable = instance.getBoolean("enable", categoryGenerationNetherrack, true,
				"Enable generation");
		generationNetherrackMaxOnChunk = instance.getInt("max_on_chunk", categoryGenerationNetherrack, 15, 1, 30,
				"Max veins on chunk");
		generationNetherrackVeinSize = instance.getInt("vein_size", categoryGenerationNetherrack, 4, 1, 10,
				"Infused bedrock generation max vein size");
		generationNetherrackMinY = instance.getInt("min_y", categoryGenerationNetherrack, 5, 0, 256,
				"Min generation height");
		generationNetherrackMaxY = instance.getInt("max_y", categoryGenerationNetherrack, 123, 0, 256,
				"Max generation height");

		if (generationNetherrackMaxY < generationNetherrackMinY)
			generationNetherrackMaxY = generationNetherrackMinY;

		instance.addCustomCategoryComment(categoryGenerationEndstone, "Infused endstone generation options ");
		generationEndstoneEnable = instance.getBoolean("enable", categoryGenerationEndstone, true, "Enable generation");
		generationEndstoneMaxOnChunk = instance.getInt("max_on_chunk", categoryGenerationEndstone, 15, 1, 30,
				"Max veins on chunk");
		generationEndstoneVeinSize = instance.getInt("vein_size", categoryGenerationEndstone, 4, 1, 10,
				"Infused bedrock generation max vein size");
		generationEndstoneMinY = instance.getInt("min_y", categoryGenerationEndstone, 0, 0, 256,
				"Min generation height");
		generationEndstoneMaxY = instance.getInt("max_y", categoryGenerationEndstone, 128, 0, 256,
				"Max generation height");

		if (generationEndstoneMaxY < generationEndstoneMinY)
			generationEndstoneMaxY = generationEndstoneMinY;

	}

	/** Send configuration fields names and values */
	public static void logFields() {
		ThaumOresMod.logLine();
		ThaumOresMod.log.info("ThaumOres configuration:");
		for (Field field : TOConfig.class.getFields()) {
			String name = field.getName();
			if (!name.equals("config") && !name.equals("langField")) {
				try {
					Object value = field.get(null);
					if (name.startsWith("category"))
						ThaumOresMod.log.info("Configuration category: '" + name + "' = '" + value + "'");
					else
						ThaumOresMod.log.info("Configuration field: '" + name + "' = '" + value + "'");
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		ThaumOresMod.logLine();
	}

	@Override
	public Configuration getInstance() {
		return this.instance;
	}

	@Override
	public Logger getLogger() {
		return ThaumOresMod.log;
	}

	@Override
	public String getModId() {
		return ThaumOresMod.MODID;
	}

	@Override
	public void setInstance(Configuration newConfig) {
		this.instance = newConfig;
	}
}
