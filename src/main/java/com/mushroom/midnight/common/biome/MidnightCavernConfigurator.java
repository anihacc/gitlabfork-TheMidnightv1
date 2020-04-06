package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightCarvers;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightFeatures;
import com.mushroom.midnight.common.registry.MidnightPlacements;
import com.mushroom.midnight.common.world.feature.config.CrystalClusterConfig;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;

public class MidnightCavernConfigurator {
    public static final BlockClusterFeatureConfig CRYSTALOTUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MidnightBlocks.CRYSTALOTUS.getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
    public static final BlockClusterFeatureConfig TENDRILWEED_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MidnightBlocks.TENDRILWEED.getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
    public static final BlockClusterFeatureConfig GLOB_FUNGI_FLOWERS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MidnightBlocks.GLOB_FUNGUS.getDefaultState()), new SimpleBlockPlacer())).tries(32).build();


    public static void addCaves(ConfigurableBiome biome) {
        biome.add(GenerationStage.Carving.AIR, Biome.createCarver(
                MidnightCarvers.WIDE_CAVE, new ProbabilityConfig(1.0F / 7.0F)
        ));
    }

    public static void addStandardFeatures(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(CRYSTALOTUS_CONFIG).withPlacement(MidnightPlacements.CHANCE_UNDERGROUND_DOUBLE.configure(new ChanceConfig(20))));

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.STINGER_EGG.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_UNDERGROUND.configure(new FrequencyConfig(1)))
        );
    }

    public static void addTendrilweed(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(TENDRILWEED_CONFIG).withPlacement(MidnightPlacements.COUNT_UNDERGROUND_32.configure(new FrequencyConfig(1))));
    }

    public static void addRouxeClusters(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.SURFACE_STRUCTURES,
                MidnightFeatures.CRYSTAL_CLUSTER.withConfiguration(new CrystalClusterConfig(MidnightBlocks.ROUXE_ROCK.getDefaultState(), MidnightBlocks.ROUXE.getDefaultState())
                ).withPlacement(MidnightPlacements.COUNT_UNDERGROUND.configure(new FrequencyConfig(5))));
    }

    public static void addBulbFungi(ConfigurableBiome biome) {

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(GLOB_FUNGI_FLOWERS_CONFIG).withPlacement(MidnightPlacements.COUNT_UNDERGROUND_32.configure(new FrequencyConfig(7))));


        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION,
                MidnightFeatures.LARGE_GLOB_FUNGUS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MidnightPlacements.COUNT_UNDERGROUND.configure(new FrequencyConfig(3)))
        );
    }

    public static void addStandardSpawns(ConfigurableBiome biome) {
        addMonster(biome, MidnightEntities.STINGER, 100, 2, 4);
        addMonster(biome, EntityType.ENDERMAN, 10, 4, 4);
    }

    private static void addCreature(ConfigurableBiome biome, EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        biome.add(EntityClassification.CREATURE, new Biome.SpawnListEntry(type, weight, minGroupSize, maxGroupSize));
    }

    private static void addMonster(ConfigurableBiome biome, EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        biome.add(Midnight.MIDNIGHT_MOB, new Biome.SpawnListEntry(type, weight, minGroupSize, maxGroupSize));
    }
}
