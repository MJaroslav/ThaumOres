package mjaroslav.mcmods.thaumores.common.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mjaroslav.mcmods.mjutils.common.objects.IModModule;
import mjaroslav.mcmods.mjutils.common.objects.ModInitModule;
import mjaroslav.mcmods.thaumores.ThaumOresMod;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.config.TOConfig;
import mjaroslav.mcmods.thaumores.common.research.ResearchItemCopy;
import mjaroslav.mcmods.thaumores.common.wands.TOInfusedOreWandTrigger;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.WandTriggerRegistry;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

/** Register all thaumcraft things */
@ModInitModule(modid = ThaumOresMod.MODID)
public class TOThaum implements IModModule {
	/** Primal aspect list */
	private static final Aspect[] primals = new Aspect[] { Aspect.AIR, Aspect.FIRE, Aspect.WATER, Aspect.EARTH,
			Aspect.ORDER, Aspect.ENTROPY };

	/** Special page list */
	public static HashMap<String, Object> recipes = new HashMap();

	@Override
	public void preInit(FMLPreInitializationEvent event) {
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		initRecipes();
		for (int meta = 0; meta < 6; meta++) {
			addClusterAspect(primals[meta], meta);
			addInfusedOreAspect((BlockInfusedBlockOre) TOBlocks.netherrackInfusedOre, primals[meta], meta);
			addInfusedOreAspect((BlockInfusedBlockOre) TOBlocks.bedrockInfusedOre, primals[meta], meta);
			addInfusedOreAspect((BlockInfusedBlockOre) TOBlocks.endstoneInfusedOre, primals[meta], meta);
		}
		addInsusedOreWandEvent((BlockInfusedBlockOre) TOBlocks.netherrackInfusedOre);
		addInsusedOreWandEvent((BlockInfusedBlockOre) TOBlocks.bedrockInfusedOre);
		addInsusedOreWandEvent((BlockInfusedBlockOre) TOBlocks.endstoneInfusedOre);
		registerResearches();
	}

	/** Add aspects to raw cluster */
	private static void addClusterAspect(Aspect aspect, int... metas) {
		ThaumcraftApi.registerObjectTag(new ItemStack(TOItems.shardCluster), metas,
				(new AspectList()).add(Aspect.CRYSTAL, 6).add(Aspect.MAGIC, 6).add(Aspect.TRAP, 4).add(aspect,
						TOConfig.generalAspectCount));
	}

	/** Add aspects to infused ore */
	private static void addInfusedOreAspect(BlockInfusedBlockOre ore, Aspect aspect, int... metas) {
		Block baseBlock = ore.getBaseBlock();
		int baseMeta = ore.getBaseMeta();
		AspectList baseAspects = ThaumcraftApiHelper.getObjectAspects(new ItemStack(baseBlock, 1, baseMeta));
		AspectList oreAspects = new AspectList().add(aspect, TOConfig.generalAspectCount);
		oreAspects.add(baseAspects);
		ThaumcraftApi.registerObjectTag(new ItemStack(ore), metas, oreAspects);
	}

	/** Wand trigger event id counter */
	private static int eventId = 0;

	/** Add wand trigger to infused ore */
	private static void addInsusedOreWandEvent(BlockInfusedBlockOre ore) {
		WandTriggerRegistry.registerWandBlockTrigger(new TOInfusedOreWandTrigger(eventId), eventId, ore, -1,
				ThaumOresMod.MODID);
		eventId++;
	}

	/** ThaumOres research item lang key prefix */
	public static final String pageLangKey = "thaumores.research_page.";

	/** ThaumOres research category */
	public static final String category = "THAUMORES";

	/** Copy of ore research */
	public static final String riCopyOre = "COPYORE";
	/** Copy of goggles research */
	public static final String riCopyGoggles = "COPYGOGGLES";
	/** Increased visibility range */
	public static final String riVisualAcuity = "VISUALACUITY";
	/** Copy of eldritch major */
	public static final String riCopyEldritchMajor = "COPYELDRITCHMAJOR";
	/** Warped Increased visibility range */
	public static final String riWarpVisualAcuity = "WARPVISUALACUITY";
	/** Infused bedrock info */
	public static final String riInfusedBedrock = "INFUSEDBEDROCK";
	/** Infused netherrack info */
	public static final String riInfusedNetherrack = "INFUSEDNETHERRACK";
	/** Infused end stone info */
	public static final String riInfusedEndstone = "INFUSEDENDSTONE";
	/** Copy of pure iron research */
	public static final String riCopyPureIron = "COPYPUREIRON";
	/** Raw clusters */
	public static final String riRawCluster = "RAWCLUSTERS";
	/** Copy of entropy processing research */
	public static final String riCopyEntropicProcessing = "COPYENTROPICPROCESSING";
	/** Raw clusters to shards */
	public static final String riEntropicClusters = "ENTROPICCLUSTERS";
	/** Copy of alchemical manufacture research */
	public static final String riCopyAlchemicalManufacture = "COPYALCHEMICALMANUFACTURE";
	/** Crystallize raw clusters */
	public static final String riCrystallizeRawClusters = "CRYSTALLIZERAWCLUSTERS";

	/** Used for logs */
	private static ArrayList<ResearchItem> researchItems = new ArrayList<ResearchItem>();

	/** Register all thaumcraft researches */
	private static void registerResearches() {
		ResearchCategories.registerCategory("THAUMORES",
				new ResourceLocation(ThaumOresMod.MODID, "textures/items/clustershardmixed.png"),
				new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png"));

		researchItems.add(new ResearchItemCopy("ORE", riCopyOre, category, 0, 4,
				new ItemStack(ConfigBlocks.blockCustomOre, 1, 32767)).registerResearchItem());

		researchItems.add(
				new ResearchItemCopy("GOGGLES", riCopyGoggles, category, 2, 4, new ItemStack(ConfigItems.itemGoggles))
						.registerResearchItem());

		researchItems.add(new ResearchItem(riVisualAcuity, category,
				new AspectList().add(Aspect.SENSES, 3).add(Aspect.AURA, 2).add(Aspect.CRYSTAL, 2).add(Aspect.EARTH, 2),
				4, 4, 2, new ResourceLocation(ThaumOresMod.MODID, "textures/misc/r_visualacuity.png"))
						.setParents(riCopyGoggles).setSecondary().setConcealed()
						.setPages(new ResearchPage[] { new ResearchPage(pageLangKey + riVisualAcuity) })
						.registerResearchItem());

		researchItems.add(new ResearchItemCopy("ELDRITCHMAJOR", riCopyEldritchMajor, category, 6, 0,
				new ResourceLocation("thaumcraft", "textures/misc/r_eldritchmajor.png")).registerResearchItem());

		researchItems.add(new ResearchItem(riWarpVisualAcuity, category,
				new AspectList().add(Aspect.SENSES, 6).add(Aspect.AURA, 4).add(Aspect.CRYSTAL, 4).add(Aspect.EARTH, 4)
						.add(Aspect.ELDRITCH, 6),
				5, 2, 3, new ResourceLocation(ThaumOresMod.MODID, "textures/misc/r_warpvisualacuity.png"))
						.setParents(riCopyEldritchMajor, riVisualAcuity).setConcealed()
						.setPages(new ResearchPage[] { new ResearchPage(pageLangKey + riWarpVisualAcuity) })
						.registerResearchItem());

		ThaumcraftApi.addWarpToResearch(riWarpVisualAcuity, 3);

		researchItems.add(new ResearchItem(riInfusedBedrock, category, new AspectList(), -2, 2, 0,
				new ItemStack(TOBlocks.bedrockInfusedOre, 1, OreDictionary.WILDCARD_VALUE))
						.setParents(riCopyOre)
						.setPages(new ResearchPage[] { new ResearchPage(pageLangKey + riInfusedBedrock),
								new ResearchPage((List) recipes.get("InfusedBedrock")) })
						.setAutoUnlock().setRound().registerResearchItem());

		researchItems.add(new ResearchItem(riInfusedNetherrack, category, new AspectList(), 2, 2, 0,
				new ItemStack(TOBlocks.netherrackInfusedOre, 1, OreDictionary.WILDCARD_VALUE))
						.setParents(riCopyOre)
						.setPages(new ResearchPage[] { new ResearchPage(pageLangKey + riInfusedNetherrack),
								new ResearchPage((List) recipes.get("InfusedNetherrack")) })
						.setAutoUnlock().setRound().registerResearchItem());

		researchItems.add(new ResearchItem(riInfusedEndstone, category, new AspectList(), 0, 2, 0,
				new ItemStack(TOBlocks.endstoneInfusedOre, 1, OreDictionary.WILDCARD_VALUE))
						.setParents(riCopyOre)
						.setPages(new ResearchPage[] { new ResearchPage(pageLangKey + riInfusedEndstone),
								new ResearchPage((List) recipes.get("InfusedEndstone")) })
						.setAutoUnlock().setRound().registerResearchItem());

		researchItems.add(new ResearchItemCopy("PUREIRON", riCopyPureIron, category, -2, -1,
				new ItemStack(ConfigItems.itemNugget, 1, 16)).registerResearchItem());

		ResearchPage[] pages = new ResearchPage[19];
		pages[0] = new ResearchPage(pageLangKey + riRawCluster);
		for (int id = 0; id < 6; id++) {
			pages[id + 1] = new ResearchPage((List) recipes.get("RawClusterBedrock_" + id));
			pages[id + 7] = new ResearchPage((List) recipes.get("RawClusterNetherrack_" + id));
			pages[id + 13] = new ResearchPage((List) recipes.get("RawClusterEndstone_" + id));
		}

		researchItems.add(new ResearchItem(riRawCluster, category,
				new AspectList().add(Aspect.ORDER, 4).add(Aspect.FIRE, 2).add(Aspect.AIR, 2).add(Aspect.WATER, 2)
						.add(Aspect.EARTH, 2).add(Aspect.ENTROPY, 2),
				0, 0, 3, new ItemStack(TOItems.shardCluster, 1, OreDictionary.WILDCARD_VALUE)).setConcealed()
						.setParents(riInfusedBedrock, riInfusedEndstone, riInfusedNetherrack, riCopyPureIron)
						.setPages(pages).registerResearchItem());

		researchItems.add(new ResearchItemCopy("ENTROPICPROCESSING", riCopyEntropicProcessing, category, 4, 0,
				new ResourceLocation("thaumcraft", "textures/misc/r_alchent.png")).registerResearchItem());

		ArrayList arc = new ArrayList();
		for (int meta = 0; meta < 6; ++meta) {
			arc.add((IArcaneRecipe) recipes.get("EntropicCluster_" + meta));
		}
		researchItems.add(new ResearchItem(riEntropicClusters, category,
				new AspectList().add(Aspect.ENTROPY, 3).add(Aspect.CRYSTAL, 3), 2, -1, 1,
				new ResourceLocation(ThaumOresMod.MODID, "textures/misc/r_entropicclusters.png"))
						.setConcealed()
						.setPages(new ResearchPage[] { new ResearchPage(pageLangKey + riEntropicClusters),
								new ResearchPage((IArcaneRecipe[]) arc.toArray(new IArcaneRecipe[0])) })
						.setParents(riCopyEntropicProcessing, riRawCluster).setSecondary().registerResearchItem());

		researchItems.add(new ResearchItemCopy("ALCHEMICALMANUFACTURE", riCopyAlchemicalManufacture, category, 2, -3,
				new ResourceLocation("thaumcraft", "textures/misc/r_alchman.png")).registerResearchItem());

		ArrayList cruc = new ArrayList();
		for (int meta = 0; meta < 6; ++meta) {
			cruc.add((CrucibleRecipe) recipes.get("CrystalizeCluster_" + meta));
		}
		researchItems.add(new ResearchItem(riCrystallizeRawClusters, category,
				new AspectList().add(Aspect.EXCHANGE, 4).add(Aspect.CRYSTAL, 8), 0, -2, 1,
				new ResourceLocation(ThaumOresMod.MODID, "textures/misc/r_crystallizerawclusters.png"))
						.setConcealed()
						.setPages(new ResearchPage[] { new ResearchPage(pageLangKey + riCrystallizeRawClusters),
								new ResearchPage((CrucibleRecipe[]) cruc.toArray(new CrucibleRecipe[0])) })
						.setParents(riCopyAlchemicalManufacture, riRawCluster).registerResearchItem());

		if (TOConfig.debugEnable && TOConfig.debugResearches)
			logResearchItems();
	}

	/** Register recipes pages */
	private static void initRecipes() {
		ItemStack basicWand = new ItemStack(ConfigItems.itemWandCasting);
		ItemStack empty = new ItemStack(ConfigBlocks.blockHole, 1, 15);
		recipes.put("InfusedBedrock",
				Arrays.asList(new Object[] { new AspectList().add(Aspect.ENTROPY, TOConfig.generalWandVisCount),
						Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(1), Arrays.asList(new ItemStack[] {
								basicWand, new ItemStack(TOBlocks.bedrockInfusedOre, 1,
										OreDictionary.WILDCARD_VALUE) }) }));
		recipes.put("InfusedNetherrack",
				Arrays.asList(new Object[] { new AspectList().add(Aspect.ENTROPY, TOConfig.generalWandVisCount),
						Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(1), Arrays.asList(new ItemStack[] {
								basicWand, new ItemStack(TOBlocks.netherrackInfusedOre, 1,
										OreDictionary.WILDCARD_VALUE) }) }));
		recipes.put("InfusedEndstone",
				Arrays.asList(new Object[] { new AspectList().add(Aspect.ENTROPY, TOConfig.generalWandVisCount),
						Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(1), Arrays.asList(new ItemStack[] {
								basicWand, new ItemStack(TOBlocks.endstoneInfusedOre, 1,
										OreDictionary.WILDCARD_VALUE) }) }));

		for (int meta = 0; meta < 6; meta++) {
			recipes.put("RawClusterBedrock_" + meta, Arrays.asList(new Object[] {
					new AspectList().add(Aspect.ORDER, TOConfig.generalWandVisCountPrimal).add(primals[meta],
							TOConfig.generalWandVisCountPrimal),
					Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(1),
					Arrays.asList(new ItemStack[] { new ItemStack(TOItems.shardCluster, 1, meta), basicWand,
							new ItemStack(TOBlocks.bedrockInfusedOre, 1, meta) }) }));
			recipes.put("RawClusterNetherrack_" + meta, Arrays.asList(new Object[] {
					new AspectList().add(Aspect.ORDER, TOConfig.generalWandVisCountPrimal).add(primals[meta],
							TOConfig.generalWandVisCountPrimal),
					Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(1),
					Arrays.asList(new ItemStack[] { new ItemStack(TOItems.shardCluster, 1, meta), basicWand,
							new ItemStack(TOBlocks.netherrackInfusedOre, 1, meta) }) }));
			recipes.put("RawClusterEndstone_" + meta, Arrays.asList(new Object[] {
					new AspectList().add(Aspect.ORDER, TOConfig.generalWandVisCountPrimal).add(primals[meta],
							TOConfig.generalWandVisCountPrimal),
					Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(1),
					Arrays.asList(new ItemStack[] { new ItemStack(TOItems.shardCluster, 1, meta), basicWand,
							new ItemStack(TOBlocks.endstoneInfusedOre, 1, meta) }) }));

			recipes.put("EntropicCluster_" + meta,
					ThaumcraftApi.addShapelessArcaneCraftingRecipe(riEntropicClusters,
							new ItemStack(ConfigItems.itemShard, 4, meta), new AspectList().add(Aspect.ENTROPY, 1),
							new ItemStack(TOItems.shardCluster, 1, meta)));

			recipes.put("CrystalizeCluster_" + meta, ThaumcraftApi.addCrucibleRecipe(riEntropicClusters,
					new ItemStack(ConfigBlocks.blockCrystal, 1, meta), new ItemStack(TOItems.shardCluster, 1, meta),
					new AspectList().add(Aspect.CRYSTAL, 12).add(primals[meta], 4)));
		}
	}

	public static void logResearchItems() {
		ThaumOresMod.logLine();
		ThaumOresMod.log.info("ThaumOres research items: ");
		ThaumOresMod.log.info("Category: '" + category + "'");
		for (ResearchItem item : researchItems) {
			ThaumOresMod.log.info("Item '" + item.key + "' (" + item.getName() + "), pos [" + item.displayColumn + ";"
					+ item.displayRow + "]");
		}
		ThaumOresMod.logLine();
	}

	@Override
	public String getModuleName() {
		return "Thaum";
	}

	@Override
	public int getPriority() {
		return 4;
	}
}
