package mjaroslav.mcmods.thaumores.lib;

import mjaroslav.mcmods.mjutils.module.ConfigurationCategory;
import mjaroslav.mcmods.mjutils.module.ConfigurationProperty;

@ConfigurationCategory(modID = ModInfo.MOD_ID, name = ConfigurationCategory.GENERAL_NAME, comment =
        ConfigurationCategory.GENERAL_COMMENT)
public class CategoryGeneral {
    @ConfigurationProperty(defaultBoolean = true, requiresMcRestart = true, comment =
            "Add infused netherrack to pigman trigger blocks.")
    public static boolean angryPigs;

    @ConfigurationProperty(defaultInt = 8, minInt = 1, maxInt = 32, requiresMcRestart = true, comment =
            "The amount of primary aspect in raw clusters.")
    public static int aspectAmount;

    @ConfigurationProperty(defaultInt = 1, minInt = 0, maxInt = 100, requiresMcRestart = true, comment =
            "Cost (perditio vis) for simply crystal extraction from infused ore.")
    public static int visCost;

    @ConfigurationProperty(defaultInt = 3, minInt = 0, maxInt = 100, requiresMcRestart = true, comment =
            "Cost (ordo vis) fro advanced crystal extraction from infused ore.")
    public static int visCostPrimal;

    @ConfigurationProperty(defaultDouble = 1.5, minDouble = 0.01, maxDouble = 10, comment =
            "Multiplier for warp visual acuity (maximum visible distance = warp count * this value).")
    public static double warpVisualAcuityModifier;

    @ConfigurationProperty(defaultBoolean = true, requiresMcRestart = true, comment =
            "Add some thaumcraft items to fishing loot.")
    public static boolean fishingLoot;

    @ConfigurationProperty(defaultBoolean = true, comment =
            "Add the ability to get a miner's ring.")
    public static boolean minersRing;

    @ConfigurationProperty(defaultBoolean = true, comment =
            "Add warp to player if he catches fish in taint biome.")
    public static boolean warpByFishingInTaint;

    @ConfigurationProperty(defaultBoolean = true, comment =
            "Reward the fisherman with a bag of loot for a certain number of successful catches.")
    public static boolean lootBags;

    @ConfigurationProperty(defaultBoolean = true, requiresMcRestart = true, comment =
            "Enable craft: iron wand cap to 5 iron nuggets.")
    public static boolean ironCapUncraft;

    @ConfigurationCategory(name = "client", comment =
            "Cosmetic settings. Not require game or world restart.")
    public static class CategoryClient {
        @ConfigurationProperty(comment =
                "Use icons (heavy shards) for raw clusters.")
        public static boolean clustersOldIcons;

        @ConfigurationProperty(defaultBoolean = true, comment =
                "Render particles on infused ore without glasses or thaumometer.")
        public static boolean enableInfusedOreParticles;

        @ConfigurationProperty(defaultBoolean = true, comment =
                "Render particles on infused ore with glasses or thaumometer.")
        public static boolean enableInfusedOreParticlesInGlasses;

        @ConfigurationProperty(defaultInt = 10000, minInt = 20, maxInt = 10000, comment =
                "Particle spawn chance (1 in n) without glasses or thaumometer.")
        public static int infusedOreParticlesChance;

        @ConfigurationProperty(defaultInt = 100, minInt = 20, maxInt = 10000, comment =
                "Particle spawn chance (1 in n) with glasses or thaumometer.")
        public static int infusedOreParticlesChanceInGlasses;
    }

    @ConfigurationCategory(name = "generation", requiresMcRestart = true, comment =
            "World generation settings.")
    public static class CategoryGeneration {
        @ConfigurationProperty(defaultBoolean = true, comment =
                "Enable all world generators.")
        public static boolean enable;

        @ConfigurationCategory(name = "endstone", comment =
                "Infused endstone generation settings.")
        public static class CategoryEndstone {
            @ConfigurationProperty(defaultBoolean = true, comment =
                    "Enable infused endstone generation.")
            public static boolean enable;

            @ConfigurationProperty(minInt = 0, maxInt = 255, comment =
                    "Infused endstone minimum generation height.")
            public static int minY;

            @ConfigurationProperty(defaultInt = 128, minInt = 0, maxInt = 255, comment =
                    "Infused endstone maximum generation height.")
            public static int maxY;

            @ConfigurationProperty(defaultInt = 15, minInt = 1, maxInt = 30, comment =
                    "Maximum amount of infused endstone veins on chunk.")
            public static int maxOnChunk;

            @ConfigurationProperty(defaultInt = 4, minInt = 1, maxInt = 10, comment =
                    "Maximum vein size of infused endstone.")
            public static int veinSize;
        }

        @ConfigurationCategory(name = "netherrack", comment =
                "Infused netherrack generation settings.")
        public static class CategoryNetherrack {
            @ConfigurationProperty(defaultBoolean = true, comment =
                    "Enable infused netherrack generation.")
            public static boolean enable;

            @ConfigurationProperty(minInt = 0, maxInt = 255, comment =
                    "Infused netherrack minimum generation height.")
            public static int minY;

            @ConfigurationProperty(defaultInt = 128, minInt = 0, maxInt = 255, comment =
                    "Infused netherrack maximum generation height.")
            public static int maxY;

            @ConfigurationProperty(defaultInt = 15, minInt = 1, maxInt = 30, comment =
                    "Maximum amount of infused netherrack veins on chunk.")
            public static int maxOnChunk;

            @ConfigurationProperty(defaultInt = 4, minInt = 1, maxInt = 10, comment =
                    "Maximum vein size of infused netherrack.")
            public static int veinSize;
        }

        @ConfigurationCategory(name = "bedrock", comment =
                "Infused bedrock generation settings.")
        public static class CategoryBedrock {
            @ConfigurationProperty(defaultBoolean = true, comment =
                    "Enable infused bedrock generation.")
            public static boolean enable;

            @ConfigurationProperty(minInt = 0, maxInt = 255, comment =
                    "Infused bedrock minimum generation height.")
            public static int minY;

            @ConfigurationProperty(defaultInt = 128, minInt = 0, maxInt = 255, comment =
                    "Infused bedrock maximum generation height.")
            public static int maxY;

            @ConfigurationProperty(defaultInt = 15, minInt = 1, maxInt = 30, comment =
                    "Maximum amount of infused bedrock veins on chunk.")
            public static int maxOnChunk;

            @ConfigurationProperty(defaultInt = 4, minInt = 1, maxInt = 10, comment =
                    "Maximum vein size of infused bedrock.")
            public static int veinSize;
        }
    }

    @ConfigurationCategory(name = "debug", comment =
            "Debug options. Not recommended for usage on the server.")
    public static class CategoryDebug {
        @ConfigurationProperty(comment =
                "Enable debug mode.")
        public static boolean enable;

        @ConfigurationProperty(defaultBoolean = true, comment =
                "Log ThaumOres researches")
        public static boolean researches;

        @ConfigurationProperty(defaultBoolean = true, comment =
                "Block remove. When the left click using a stick, all blocks in the specified radius will be " +
                        "deleted, excluding InfusedOre.")
        public static boolean remover;

        @ConfigurationProperty(defaultBoolean = true, comment =
                "InfusedOre scanner. With the right click using a stick, displays the amount of InfusedOre in the " +
                        "specified radius.")
        public static boolean scanner;

        @ConfigurationProperty(defaultInt = 8, minInt = 1, maxInt = 30, comment =
                "Scanner and block remover work radius (square area).")
        public static int radius;
    }
}
