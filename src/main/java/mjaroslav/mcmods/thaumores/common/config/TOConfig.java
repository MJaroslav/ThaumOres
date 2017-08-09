package mjaroslav.mcmods.thaumores.common.config;

import java.io.File;
import java.lang.reflect.Field;

import mjaroslav.mcmods.thaumores.ThaumOresMod;
import net.minecraftforge.common.config.Configuration;

public class TOConfig {
	/** ThaumOres: configuration */
	public static Configuration config;

	/** Debug options */
	public static final String categoryDebug = "debug";
	/** Enable debug */
	public static boolean debugEnable;
	/** Log mod initialization (pre and post too) */
	public static boolean debugInit;
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

	/** Create or/and read ThaumOres configuration file */
	public static void init() {
		if (config == null) {
			config = new Configuration(new File(ThaumOresMod.configFilePath));
		}
		try {
			config.load();
		} catch (Exception e) {
			ThaumOresMod.log.error("Unable to load configuration!");
		} finally {
			if (config.hasChanged()) {
				config.save();
			}
		}
		sync();
	}

	/** Synchronize configuration fields with file and save changes */
	public static void sync() {
		syncDebug();
		syncGeneral();
		syncGeneration();

		if (debugEnable && debugConfig)
			logFields();

		if (config.hasChanged()) {
			config.save();
			ThaumOresMod.log.info("Configuration saved");
		}
	}

	private static void syncDebug() {
		config.addCustomCategoryComment(categoryDebug, "Debug options");
		debugEnable = config.getBoolean("enable", categoryDebug, false, "Enable debug");
		debugInit = config.getBoolean("init", categoryDebug, true, "Log mod initialization (pre and post too)");
		debugConfig = config.getBoolean("config", categoryDebug, true, "Log configuration fields ");
		debugResearches = config.getBoolean("researches", categoryDebug, true, "Log ThaumOres researches");
		debugRemover = config.getBoolean("remover", categoryDebug, true,
				"Enable block remover (break any block with stick) (don't remove infused ore)");
		debugScanner = config.getBoolean("scanner", categoryDebug, true,
				"Enable ore scanner (right click on any block with stick) (Only scans infused ore)");
		debugRemoverScannerRadius = config.getInt("remover_scanner_radius", categoryDebug, 8, 1, 30,
				"Block remover and scanner radius");
	}

	private static void syncGeneral() {
		config.addCustomCategoryComment(categoryGeneral, "Main options");
		generalAngryPigs = config.getBoolean("angry_pigs", categoryGeneral, true,
				"Pig Zombies will be angry if you mine infused ore in Nether");
		generalAspectCount = config.getInt("aspect_count", categoryGeneral, 8, 1, 16,
				"Raw clusters and infused ore primal aspect count ");
		generalWandVisCount = config.getInt("wand_vis_count", categoryGeneral, 1, 0, 100,
				"Count of perditio for wand click on infused ore");
		generalWandVisCountPrimal = config.getInt("wand_vis_count_primal", categoryGeneral, 2, 1, 200,
				"Count of ordo and ore aspect for wand click on infused ore");
		generalWarpVisualAcuityModifier = config.getFloat("warp_visual_acuity_modifier", categoryGeneral, 1.5F, 0.01F,
				10F, "Multiplier for warp visual acuity (Distance = warpCount * this value)");

		config.addCustomCategoryComment(categoryGeneralGraphics, "Graphics options");
		generalGraphicsClustersOldIcons = config.getBoolean("clusters_olds_icons", categoryGeneralGraphics, false,
				"Use old icons (Heavy shards) for raw clusters");
		generalGraphicsRenderInfusedOreParticles = config.getBoolean("infused_ore_particles", categoryGeneralGraphics,
				true, "Render particles on infused ore without Glasses/Thaumometer");
		generalGraphicsRenderInfusedOreParticlesGlasses = config.getBoolean("infused_ore_particles_glasses",
				categoryGeneralGraphics, true, "Render particles on infused ore with Glasses/Thaumometer");
		generalGraphicsRenderInfusedOreParticlesChance = config.getInt("infused_ore_particles_chance",
				categoryGeneralGraphics, 10000, 4000, 10000,
				"Chance spawn particle on infused ore without Glasses/Thaumometer (1 in this value)");
		generalGraphicsRenderInfusedOreParticlesGlassesChance = config.getInt("infused_ore_particles_glasses_chance",
				categoryGeneralGraphics, 100, 20, 10000,
				"Chance spawn particle on infused ore with Glasses/Thaumometer (1 in this value)");
	}

	private static void syncGeneration() {
		config.addCustomCategoryComment(categoryGeneration, "WorldGen options");
		generationEnable = config.getBoolean("enable", categoryGeneration, true, "Enable generation");
		generationOres = config.getBoolean("ores", categoryGeneration, true, "Enable ore generation");

		config.addCustomCategoryComment(categoryGenerationBedrock, "Infused bedrock generation options ");
		generationBedrockEnable = config.getBoolean("enable", categoryGenerationBedrock, true, "Enable generation");
		generationBedrockMaxOnChunk = config.getInt("max_on_chunk", categoryGenerationBedrock, 8, 1, 30,
				"Max veins on chunk");
		generationBedrockVeinSize = config.getInt("vein_size", categoryGenerationBedrock, 4, 1, 10,
				"Infused bedrock generation max vein size");
		generationBedrockMinY = config.getInt("min_y", categoryGenerationBedrock, 3, 0, 256, "Min generation height");
		generationBedrockMaxY = config.getInt("max_y", categoryGenerationBedrock, 5, 0, 256, "Max generation height");

		if (generationBedrockMaxY < generationBedrockMinY)
			generationBedrockMaxY = generationBedrockMinY;

		config.addCustomCategoryComment(categoryGenerationNetherrack, "Infused netherrack generation options ");
		generationNetherrackEnable = config.getBoolean("enable", categoryGenerationNetherrack, true,
				"Enable generation");
		generationNetherrackMaxOnChunk = config.getInt("max_on_chunk", categoryGenerationNetherrack, 15, 1, 30,
				"Max veins on chunk");
		generationNetherrackVeinSize = config.getInt("vein_size", categoryGenerationNetherrack, 4, 1, 10,
				"Infused bedrock generation max vein size");
		generationNetherrackMinY = config.getInt("min_y", categoryGenerationNetherrack, 5, 0, 256,
				"Min generation height");
		generationNetherrackMaxY = config.getInt("max_y", categoryGenerationNetherrack, 123, 0, 256,
				"Max generation height");

		if (generationNetherrackMaxY < generationNetherrackMinY)
			generationNetherrackMaxY = generationNetherrackMinY;

		config.addCustomCategoryComment(categoryGenerationEndstone, "Infused endstone generation options ");
		generationEndstoneEnable = config.getBoolean("enable", categoryGenerationEndstone, true, "Enable generation");
		generationEndstoneMaxOnChunk = config.getInt("max_on_chunk", categoryGenerationEndstone, 15, 1, 30,
				"Max veins on chunk");
		generationEndstoneVeinSize = config.getInt("vein_size", categoryGenerationEndstone, 4, 1, 10,
				"Infused bedrock generation max vein size");
		generationEndstoneMinY = config.getInt("min_y", categoryGenerationEndstone, 0, 0, 256, "Min generation height");
		generationEndstoneMaxY = config.getInt("max_y", categoryGenerationEndstone, 128, 0, 256,
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
}
