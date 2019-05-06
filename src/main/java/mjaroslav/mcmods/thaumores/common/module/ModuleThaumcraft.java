package mjaroslav.mcmods.thaumores.common.module;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import mjaroslav.mcmods.mjutils.module.Modular;
import mjaroslav.mcmods.mjutils.module.Module;
import mjaroslav.mcmods.mjutils.object.other.ResearchItemCopy;
import mjaroslav.mcmods.thaumores.ThaumOres;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.wands.InfusedOreWandTrigger;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral.CategoryDebug;
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

import java.util.*;

import static mjaroslav.mcmods.thaumores.lib.ModInfo.MOD_ID;

@Module(MOD_ID)
public class ModuleThaumcraft implements Modular {
    private static final Aspect[] PRIMALS = new Aspect[]{Aspect.AIR, Aspect.FIRE, Aspect.WATER, Aspect.EARTH,
            Aspect.ORDER, Aspect.ENTROPY};

    private static Map<String, Object> recipes = new HashMap<>();

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        initRecipes();
        for (int meta = 0; meta < 6; meta++) {
            addClusterAspect(PRIMALS[meta], meta);
            addInfusedOreAspect((BlockInfusedBlockOre) ModuleBlocks.netherrackInfusedOre, PRIMALS[meta], meta);
            addInfusedOreAspect((BlockInfusedBlockOre) ModuleBlocks.bedrockInfusedOre, PRIMALS[meta], meta);
            addInfusedOreAspect((BlockInfusedBlockOre) ModuleBlocks.endstoneInfusedOre, PRIMALS[meta], meta);
        }
        addInfusedOreWandEvent((BlockInfusedBlockOre) ModuleBlocks.netherrackInfusedOre);
        addInfusedOreWandEvent((BlockInfusedBlockOre) ModuleBlocks.bedrockInfusedOre);
        addInfusedOreWandEvent((BlockInfusedBlockOre) ModuleBlocks.endstoneInfusedOre);
        registerResearches();
    }

    private static void addClusterAspect(Aspect aspect, int... metas) {
        ThaumcraftApi.registerObjectTag(new ItemStack(ModuleItems.shardCluster), metas,
                (new AspectList()).add(Aspect.CRYSTAL, 6).add(Aspect.MAGIC, 6).add(Aspect.TRAP, 4).add(aspect,
                        CategoryGeneral.aspectAmount));
    }

    private static void addInfusedOreAspect(BlockInfusedBlockOre ore, Aspect aspect, int... metas) {
        Block baseBlock = ore.getBaseBlock();
        int baseMeta = ore.getBaseMeta();
        AspectList baseAspects = ThaumcraftApiHelper.getObjectAspects(new ItemStack(baseBlock, 1, baseMeta));
        AspectList oreAspects = new AspectList().add(aspect, CategoryGeneral.aspectAmount);
        oreAspects.add(baseAspects);
        ThaumcraftApi.registerObjectTag(new ItemStack(ore), metas, oreAspects);
    }

    private static int eventId = 0;

    private static void addInfusedOreWandEvent(BlockInfusedBlockOre ore) {
        WandTriggerRegistry.registerWandBlockTrigger(new InfusedOreWandTrigger(eventId), eventId, ore, -1, MOD_ID);
        eventId++;
    }

    private static final String pageLangKey = "thaumores.research_page.";
    private static final String category = "THAUMORES";

    private static final String riCopyEntropicProcessing = "COPYENTROPICPROCESSING";
    private static final String riCopyOre = "COPYORE";
    private static final String riCopyPureIron = "COPYPUREIRON";
    private static final String riCopyGoggles = "COPYGOGGLES";
    private static final String riCopyAlchemicalManufacture = "COPYALCHEMICALMANUFACTURE";

    public static final String riVisualAcuity = "VISUALACUITY";
    public static final String riWarpVisualAcuity = "WARPVISUALACUITY";
    private static final String riCopyEldritchMajor = "COPYELDRITCHMAJOR";
    private static final String riInfusedBedrock = "INFUSEDBEDROCK";
    private static final String riInfusedNetherrack = "INFUSEDNETHERRACK";
    private static final String riInfusedEndstone = "INFUSEDENDSTONE";
    public static final String riRawCluster = "RAWCLUSTERS";
    private static final String riEntropicClusters = "ENTROPICCLUSTERS";
    private static final String riCrystallizeRawClusters = "CRYSTALLIZERAWCLUSTERS";

    private static ArrayList<ResearchItem> researchItems = new ArrayList<>();

    private static void registerResearches() {
        ResearchCategories.registerCategory("THAUMORES",
                new ResourceLocation(MOD_ID, "textures/items/clustershardmixed.png"), new ResourceLocation("thaumcraft",
                        "textures/gui/gui_researchback.png"));

        researchItems.add(new ResearchItemCopy("ORE", riCopyOre, category, 0, 4,
                new ItemStack(ConfigBlocks.blockCustomOre, 1, 32767)).registerResearchItem());

        researchItems.add(
                new ResearchItemCopy("GOGGLES", riCopyGoggles, category, 2, 4, new ItemStack(ConfigItems.itemGoggles))
                        .registerResearchItem());

        researchItems.add(new ResearchItem(riVisualAcuity, category,
                new AspectList().add(Aspect.SENSES, 3).add(Aspect.AURA, 2).add(Aspect.CRYSTAL, 2).add(Aspect.EARTH, 2),
                4, 4, 2, new ResourceLocation(MOD_ID, "textures/misc/r_visualacuity.png")).setParents(riCopyGoggles)
                .setSecondary().setConcealed().setPages(new ResearchPage(pageLangKey + riVisualAcuity))
                .registerResearchItem());

        researchItems.add(new ResearchItemCopy("ELDRITCHMAJOR", riCopyEldritchMajor, category, 6, 0,
                new ResourceLocation("thaumcraft", "textures/misc/r_eldritchmajor.png")).registerResearchItem());

        researchItems.add(new ResearchItem(riWarpVisualAcuity, category,
                new AspectList().add(Aspect.SENSES, 6).add(Aspect.AURA, 4).add(Aspect.CRYSTAL, 4).add(Aspect.EARTH, 4)
                        .add(Aspect.ELDRITCH, 6), 5, 2, 3, new ResourceLocation(MOD_ID,
                "textures/misc/r_warpvisualacuity.png")).setParents(riCopyEldritchMajor, riVisualAcuity).setConcealed()
                .setPages(new ResearchPage(pageLangKey + riWarpVisualAcuity))
                .registerResearchItem());

        ThaumcraftApi.addWarpToResearch(riWarpVisualAcuity, 3);

        researchItems.add(new ResearchItem(riInfusedBedrock, category, new AspectList(), -2, 2, 0,
                new ItemStack(ModuleBlocks.bedrockInfusedOre, 1, OreDictionary.WILDCARD_VALUE))
                .setParents(riCopyOre).setPages(new ResearchPage(pageLangKey + riInfusedBedrock),
                        new ResearchPage((List) recipes.get("InfusedBedrock"))).setAutoUnlock().setRound()
                .registerResearchItem());

        researchItems.add(new ResearchItem(riInfusedNetherrack, category, new AspectList(), 2, 2, 0,
                new ItemStack(ModuleBlocks.netherrackInfusedOre, 1, OreDictionary.WILDCARD_VALUE)).setParents(riCopyOre).setPages(new ResearchPage(pageLangKey + riInfusedNetherrack),
                new ResearchPage((List) recipes.get("InfusedNetherrack")))
                .setAutoUnlock().setRound().registerResearchItem());

        researchItems.add(new ResearchItem(riInfusedEndstone, category, new AspectList(), 0, 2, 0,
                new ItemStack(ModuleBlocks.endstoneInfusedOre, 1, OreDictionary.WILDCARD_VALUE)).setParents(riCopyOre)
                .setPages(new ResearchPage(pageLangKey + riInfusedEndstone), new ResearchPage((List)
                        recipes.get("InfusedEndstone"))).setAutoUnlock().setRound().registerResearchItem());

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
                0, 0, 3, new ItemStack(ModuleItems.shardCluster, 1, OreDictionary.WILDCARD_VALUE)).setConcealed()
                .setParents(riInfusedBedrock, riInfusedEndstone, riInfusedNetherrack, riCopyPureIron)
                .setPages(pages).registerResearchItem());

        researchItems.add(new ResearchItemCopy("ENTROPICPROCESSING", riCopyEntropicProcessing, category, 4, 0,
                new ResourceLocation("thaumcraft", "textures/misc/r_alchent.png")).registerResearchItem());

        ArrayList<IArcaneRecipe> arc = new ArrayList<>();
        for (int meta = 0; meta < 6; ++meta)
            arc.add((IArcaneRecipe) recipes.get("EntropicCluster_" + meta));
        researchItems.add(new ResearchItem(riEntropicClusters, category,
                new AspectList().add(Aspect.ENTROPY, 3).add(Aspect.CRYSTAL, 3), 2, -1, 1,
                new ResourceLocation(MOD_ID, "textures/misc/r_entropicclusters.png")).setConcealed().setPages(
                new ResearchPage(pageLangKey + riEntropicClusters), new ResearchPage(arc.toArray(
                        new IArcaneRecipe[]{}))).setParents(riCopyEntropicProcessing, riRawCluster)
                .setSecondary().registerResearchItem());

        researchItems.add(new ResearchItemCopy("ALCHEMICALMANUFACTURE", riCopyAlchemicalManufacture, category, 2, -3,
                new ResourceLocation("thaumcraft", "textures/misc/r_alchman.png")).registerResearchItem());

        ArrayList<CrucibleRecipe> cruc = new ArrayList<>();
        for (int meta = 0; meta < 6; ++meta)
            cruc.add((CrucibleRecipe) recipes.get("CrystalizeCluster_" + meta));
        researchItems.add(new ResearchItem(riCrystallizeRawClusters, category, new AspectList().add(Aspect.EXCHANGE, 4)
                .add(Aspect.CRYSTAL, 8), 0, -2, 1, new ResourceLocation(MOD_ID,
                "textures/misc/r_crystallizerawclusters.png")).setConcealed().setPages(new ResearchPage(pageLangKey +
                riCrystallizeRawClusters), new ResearchPage(cruc.toArray(new CrucibleRecipe[]{})))
                .setParents(riCopyAlchemicalManufacture, riRawCluster).registerResearchItem());

        if (CategoryDebug.enable && CategoryDebug.researches)
            logResearchItems();
    }

    private static void initRecipes() {
        ItemStack basicWand = new ItemStack(ConfigItems.itemWandCasting);
        recipes.put("InfusedBedrock", Arrays.asList(new AspectList().add(Aspect.ENTROPY, CategoryGeneral.visCost), 1, 2,
                1, Arrays.asList(basicWand, new ItemStack(ModuleBlocks.bedrockInfusedOre, 1,
                        OreDictionary.WILDCARD_VALUE))));
        recipes.put("InfusedNetherrack", Arrays.asList(new AspectList().add(Aspect.ENTROPY, CategoryGeneral.visCost), 1,
                2, 1, Arrays.asList(basicWand, new ItemStack(ModuleBlocks.netherrackInfusedOre, 1,
                        OreDictionary.WILDCARD_VALUE))));
        recipes.put("InfusedEndstone", Arrays.asList(new AspectList().add(Aspect.ENTROPY, CategoryGeneral.visCost), 1,
                2, 1, Arrays.asList(basicWand, new ItemStack(ModuleBlocks.endstoneInfusedOre, 1,
                        OreDictionary.WILDCARD_VALUE))));

        for (int meta = 0; meta < 6; meta++) {
            recipes.put("RawClusterBedrock_" + meta, Arrays.asList(new AspectList().add(Aspect.ORDER,
                    CategoryGeneral.visCostPrimal).add(PRIMALS[meta], CategoryGeneral.visCostPrimal), 1, 3, 1,
                    Arrays.asList(new ItemStack(ModuleItems.shardCluster, 1, meta), basicWand,
                            new ItemStack(ModuleBlocks.bedrockInfusedOre, 1, meta))));
            recipes.put("RawClusterNetherrack_" + meta, Arrays.asList(new AspectList().add(Aspect.ORDER,
                    CategoryGeneral.visCostPrimal).add(PRIMALS[meta], CategoryGeneral.visCostPrimal), 1, 3, 1,
                    Arrays.asList(new ItemStack(ModuleItems.shardCluster, 1, meta), basicWand,
                            new ItemStack(ModuleBlocks.netherrackInfusedOre, 1, meta))));
            recipes.put("RawClusterEndstone_" + meta, Arrays.asList(new AspectList().add(Aspect.ORDER,
                    CategoryGeneral.visCostPrimal).add(PRIMALS[meta], CategoryGeneral.visCostPrimal), 1, 3, 1,
                    Arrays.asList(new ItemStack(ModuleItems.shardCluster, 1, meta), basicWand,
                            new ItemStack(ModuleBlocks.endstoneInfusedOre, 1, meta))));

            recipes.put("EntropicCluster_" + meta, ThaumcraftApi.addShapelessArcaneCraftingRecipe(riEntropicClusters,
                    new ItemStack(ConfigItems.itemShard, 4, meta), new AspectList().add(Aspect.ENTROPY, 1),
                    new ItemStack(ModuleItems.shardCluster, 1, meta)));

            recipes.put("CrystalizeCluster_" + meta, ThaumcraftApi.addCrucibleRecipe(riEntropicClusters,
                    new ItemStack(ConfigBlocks.blockCrystal, 1, meta), new ItemStack(ModuleItems.shardCluster, 1, meta),
                    new AspectList().add(Aspect.CRYSTAL, 12).add(PRIMALS[meta], 4)));
        }
    }

    private static void logResearchItems() {
        ThaumOres.logLine(true);
        ThaumOres.debug("ThaumOres research items: ");
        ThaumOres.debug(String.format("Categories \"%s\":", category));
        for (ResearchItem item : researchItems)
            ThaumOres.debug(String.format("ResearchItem \"%s\" (%s) [%s;%s]", item.key, item.getName(),
                    item.displayColumn, item.displayRow));
        ThaumOres.logLine(true);
    }

    @Override
    public int priority() {
        return 3;
    }
}
