package com.mushroom.midnight.common.biome;

import com.google.common.collect.ImmutableList;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.block.VioleafBlock;
import com.mushroom.midnight.common.registry.*;
import com.mushroom.midnight.common.world.feature.config.CrystalClusterConfig;
import com.mushroom.midnight.common.world.feature.config.MidnightOreConfig;
import com.mushroom.midnight.common.world.feature.config.UniformCompositionConfig;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;

public class MidnightSurfaceConfigurator {
    public static final BlockClusterFeatureConfig GHOST_PLANT_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MidnightBlocks.GHOST_PLANT.getDefaultState()), new SimpleBlockPlacer()).tries(32).build();
    public static final BlockClusterFeatureConfig DRAGONNEST_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MidnightBlocks.DRAGON_NEST.getDefaultState()), new SimpleBlockPlacer()).tries(32).build();
    public static final BlockClusterFeatureConfig CRYSTALFLOWER_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MidnightBlocks.CRYSTAL_FLOWER.getDefaultState()), new SimpleBlockPlacer()).tries(64).build();

    public static final BlockClusterFeatureConfig TALL_GRASS_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MidnightBlocks.TALL_GRASS.getDefaultState()), new SimpleBlockPlacer()).tries(64).build();
    public static final BlockClusterFeatureConfig GRASS_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MidnightBlocks.GRASS.getDefaultState()), new SimpleBlockPlacer()).tries(64).build();

    public static final BlockClusterFeatureConfig BIOLEAF_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MidnightBlocks.VIOLEAF.getDefaultState().with(VioleafBlock.IS_GROWN, true)), new SimpleBlockPlacer()).tries(64).build();
    public static final BlockClusterFeatureConfig RUNEBUSH_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MidnightBlocks.RUNEBUSH.getDefaultState()), new SimpleBlockPlacer()).tries(64).build();
    public static final BlockClusterFeatureConfig BOGWEED_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MidnightBlocks.BOGWEED.getDefaultState()), new SimpleBlockPlacer()).tries(64).build();


    public static void addStructureFeatures(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.SURFACE_STRUCTURES, MidnightStructures.MOLTEN_CRATER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG))
        );

        biome.add(GenerationStage.Decoration.SURFACE_STRUCTURES, MidnightStructures.SHADOWROOT_GUARDTOWER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG))
        );

        biome.add(GenerationStage.Decoration.SURFACE_STRUCTURES, MidnightStructures.WELL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG))
        );

    }

    public static void addMoltenCrater(ConfigurableBiome biome) {
        biome.add(MidnightStructures.MOLTEN_CRATER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    }

    public static void addGlobalOres(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.UNDERGROUND_ORES,
                MidnightFeatures.ORE.withConfiguration(new MidnightOreConfig(MidnightBlocks.DARK_PEARL_ORE.getDefaultState(), 14)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 56)))
        );

        biome.add(GenerationStage.Decoration.UNDERGROUND_ORES,
                MidnightFeatures.ORE.withConfiguration(new MidnightOreConfig(MidnightBlocks.TENEBRUM_ORE.getDefaultState(), 4)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(6, 0, 0, 56)))
        );

        biome.add(GenerationStage.Decoration.UNDERGROUND_ORES,
                MidnightFeatures.ORE.withConfiguration(new MidnightOreConfig(MidnightBlocks.NAGRILITE_ORE.getDefaultState(), 4)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 0, 0, 24)))
        );

        biome.add(GenerationStage.Decoration.UNDERGROUND_ORES,
                MidnightFeatures.ORE.withConfiguration(new MidnightOreConfig(MidnightBlocks.EBONITE_ORE.getDefaultState(), 6)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 0, 0, 24)))
        );
    }

    public static void addGlobalFeatures(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.LOCAL_MODIFICATIONS,
                MidnightFeatures.HEAP.withConfiguration(new UniformCompositionConfig(MidnightBlocks.ROCKSHROOM.getDefaultState())).withPlacement(MidnightPlacements.CHANCE_SURFACE.configure(new ChanceConfig(100)))
        );

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(GHOST_PLANT_CONFIG).withPlacement(MidnightPlacements.COUNT_CHANCE_SURFACE_DOUBLE.configure(new HeightWithChanceConfig(4, 0.3F))));


        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DRAGONNEST_CONFIG).withPlacement(MidnightPlacements.DRAGON_NEST.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));


        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.UNSTABLE_BUSH.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.CHANCE_SURFACE_DOUBLE.configure(new ChanceConfig(2)))
        );
    }

    public static void addLumen(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.LUMEN_FLOWERS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_DOUBLE.configure(new FrequencyConfig(1)))
        );
    }

    public static void addNightshroomFlowers(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.NIGHTSHROOM_FLOWERS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_32.configure(new FrequencyConfig(10)))
        );
    }

    public static void addDewshroomFlowers(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.DEWSHROOM_FLOWERS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_32.configure(new FrequencyConfig(10)))
        );
    }

    public static void addViridshroomFlowers(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.VIRIDSHROOM_FLOWERS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_32.configure(new FrequencyConfig(10)))
        );
    }

    public static void addBogshroomFlowers(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.BOGSHROOM_FLOWERS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_32.configure(new FrequencyConfig(10)))
        );
    }

    public static void addMistshroomFlowers(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.MISTSHROOM_FLOWERS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_32.configure(new FrequencyConfig(10))
        ));
    }

    public static void addCrystalFlowers(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(CRYSTALFLOWER_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_32.configure(new FrequencyConfig(8))));
    }

    public static void addCrystalClusters(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.SURFACE_STRUCTURES,
                MidnightFeatures.CRYSTAL_CLUSTER.withConfiguration(new CrystalClusterConfig(MidnightBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(), MidnightBlocks.BLOOMCRYSTAL.getDefaultState())).withPlacement(MidnightPlacements.COUNT_SURFACE.configure(new FrequencyConfig(3)))
        );

        biome.add(GenerationStage.Decoration.SURFACE_STRUCTURES,
                MidnightFeatures.CRYSTAL_SPIRE.withConfiguration(new CrystalClusterConfig(MidnightBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(), MidnightBlocks.BLOOMCRYSTAL.getDefaultState())).withPlacement(MidnightPlacements.COUNT_SURFACE.configure(new FrequencyConfig(2)))
        );
    }

    public static void addGrasses(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(GRASS_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_DOUBLE.configure(new FrequencyConfig(3))));

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(TALL_GRASS_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_32.configure(new FrequencyConfig(3))));
    }

    public static void addFingeredGrass(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.FINGERED_GRASS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_32.configure(new FrequencyConfig(1)))
        );
    }

    public static void addTrenchstoneBoulders(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.LOCAL_MODIFICATIONS,
                MidnightFeatures.TRENCHSTONE_BOULDER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.CHANCE_SURFACE.configure(new ChanceConfig(3)))
        );
    }

    public static void addNightstoneSpikesAndBoulders(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.LOCAL_MODIFICATIONS,
                Feature.RANDOM_BOOLEAN_SELECTOR.withConfiguration(new TwoFeatureChoiceConfig(
                        MidnightFeatures.NIGHTSTONE_BOULDER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG),
                        MidnightFeatures.SPIKE.withConfiguration(new UniformCompositionConfig(MidnightBlocks.NIGHTSTONE.getDefaultState()))
                )).withPlacement(MidnightPlacements.CHANCE_SURFACE.configure(new ChanceConfig(4)))
        );
    }

    public static void addSparseShadowrootTrees(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.LOCAL_MODIFICATIONS,
                Feature.RANDOM_BOOLEAN_SELECTOR.withConfiguration(new TwoFeatureChoiceConfig(
                        MidnightFeatures.SHADOWROOT_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG),
                        MidnightFeatures.DEAD_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                )).withPlacement(MidnightPlacements.CHANCE_SURFACE.configure(new ChanceConfig(5))));
    }

    public static void addSparseSuavis(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.SUAVIS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.CHANCE_SURFACE_DOUBLE.configure(new ChanceConfig(2)))
        );
    }

    public static void addCommonSuavis(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.SUAVIS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_DOUBLE.configure(new FrequencyConfig(8))
        ));
    }

    public static void addSparseDeadTrees(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.DEAD_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.CHANCE_SURFACE.configure(new ChanceConfig(6)))
        );
    }

    public static void addVioleafs(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(BIOLEAF_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_DOUBLE.configure(new FrequencyConfig(2))));
    }

    public static void addRunebushes(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(RUNEBUSH_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_DOUBLE.configure(new FrequencyConfig(32))));
    }

    public static void addBogweed(ConfigurableBiome biome) {

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(BOGWEED_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_32.configure(new FrequencyConfig(1))));

    }

    public static void addFallenDeadLogs(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.FALLEN_DEAD_LOG.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE.configure(new FrequencyConfig(2)))
        );
    }

    public static void addVigilantForestTrees(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                Feature.RANDOM_BOOLEAN_SELECTOR.withConfiguration(new TwoFeatureChoiceConfig(
                        MidnightFeatures.SHADOWROOT_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG),
                        MidnightFeatures.DARK_WILLOW_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                )).withPlacement(MidnightPlacements.COUNT_SURFACE.configure(new FrequencyConfig(8)))
        );
    }

    public static void addBogTrees(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.DARK_WILLOW_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE.configure(new FrequencyConfig(3)))
        );

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.SHADOWROOT_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE.configure(new FrequencyConfig(1)))
        );
    }

    public static void addBogDeadTrees(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.BOG_DEAD_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.CHANCE_SURFACE.configure(new ChanceConfig(3)))
        );
    }

    public static void addLargeBogshrooms(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                Feature.RANDOM_BOOLEAN_SELECTOR.withConfiguration(new TwoFeatureChoiceConfig(
                        MidnightFeatures.LARGE_BOGSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG),
                        MidnightFeatures.SMALL_BOGSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                )).withPlacement(MidnightPlacements.CHANCE_SURFACE.configure(new ChanceConfig(3)))
        );
    }

    public static void addLargeFungis(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SIMPLE_RANDOM_SELECTOR.withConfiguration(new SingleRandomFeature(ImmutableList.of(MidnightFeatures.LARGE_NIGHTSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG), MidnightFeatures.LARGE_DEWSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG), MidnightFeatures.LARGE_VIRIDSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)))).withPlacement(MidnightPlacements.CHANCE_SURFACE.configure(new ChanceConfig(2))));

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SIMPLE_RANDOM_SELECTOR.withConfiguration(new SingleRandomFeature(ImmutableList.of(MidnightFeatures.MEDIUM_NIGHTSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG), MidnightFeatures.MEDIUM_DEWSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG), MidnightFeatures.MEDIUM_VIRIDSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)))).withPlacement(MidnightPlacements.COUNT_SURFACE.configure(new FrequencyConfig(1))));


        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SIMPLE_RANDOM_SELECTOR.withConfiguration(new SingleRandomFeature(ImmutableList.of(MidnightFeatures.SMALL_NIGHTSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG), MidnightFeatures.SMALL_DEWSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG), MidnightFeatures.SMALL_VIRIDSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)))).withPlacement(MidnightPlacements.COUNT_SURFACE.configure(new FrequencyConfig(3))));

    }

    public static void addBladeshrooms(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.BLADESHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_32.configure(new FrequencyConfig(2)))
        );
    }

    public static void addAlgaeAndMoss(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.DECEITFUL_ALGAE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_DOUBLE.configure(new FrequencyConfig(10)))
        );

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.DECEITFUL_MOSS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_SURFACE_DOUBLE.configure(new FrequencyConfig(16)))
        );
    }

    public static void addWell(ConfigurableBiome biome) {
        biome.add(MidnightStructures.WELL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    }

    public static void addShadowRootGuardTower(ConfigurableBiome biome) {
        biome.add(MidnightStructures.SHADOWROOT_GUARDTOWER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    }

    public static void addDeadViridShroomAndCache(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.SURFACE_STRUCTURES,
                MidnightFeatures.DEAD_VIRIDSHROOM_AND_CACHE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.CHANCE_SURFACE.configure(new ChanceConfig(45)))
        );
    }

    public static void addStandardCreatureSpawns(ConfigurableBiome biome) {
        addCreature(biome, MidnightEntities.NIGHTSTAG, 100, 1, 3);
    }

    public static void addStandardMonsterSpawns(ConfigurableBiome biome) {
        addMonster(biome, MidnightEntities.RIFTER, 100, 1, 2);
        addMonster(biome, EntityType.ENDERMAN, 10, 4, 4);
    }

    public static void addRockySpawns(ConfigurableBiome biome) {
        addMonster(biome, MidnightEntities.HUNTER, 5, 1, 2);
    }

    public static void addForestSpawns(ConfigurableBiome biome) {
        addMonster(biome, MidnightEntities.SKULK, 100, 1, 2);
//        addCreature(biome, MidnightEntities.SHADE_SQUIRREL, 65, 2, 3);
    }

    private static void addCreature(ConfigurableBiome biome, EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        biome.add(EntityClassification.CREATURE, new Biome.SpawnListEntry(type, weight, minGroupSize, maxGroupSize));
    }

    private static void addMonster(ConfigurableBiome biome, EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        biome.add(Midnight.MIDNIGHT_MOB, new Biome.SpawnListEntry(type, weight, minGroupSize, maxGroupSize));
    }
}
