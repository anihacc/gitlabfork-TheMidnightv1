package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.biome.cavern.CavernStructureConfig;
import com.mushroom.midnight.common.biome.cavern.CavernousBiomeConfig;
import com.mushroom.midnight.common.biome.config.FeatureConfig;
import com.mushroom.midnight.common.biome.config.SpawnerConfig;
import com.mushroom.midnight.common.biome.config.SurfaceConfig;
import com.mushroom.midnight.common.biome.surface.SurfaceBiomeConfig;
import com.mushroom.midnight.common.biome.surface.SurfaceTerrainConfig;
import com.mushroom.midnight.common.block.BlockSuavis;
import com.mushroom.midnight.common.entity.creature.EntityCrystalBug;
import com.mushroom.midnight.common.entity.creature.EntityDeceitfulSnapper;
import com.mushroom.midnight.common.entity.creature.EntityHunter;
import com.mushroom.midnight.common.entity.creature.EntityNightStag;
import com.mushroom.midnight.common.entity.creature.EntityNova;
import com.mushroom.midnight.common.entity.creature.EntitySkulk;
import com.mushroom.midnight.common.entity.creature.EntityRifter;
import com.mushroom.midnight.common.entity.creature.EntityStinger;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.SurfacePlacementLevel;
import com.mushroom.midnight.common.world.feature.FeatureSorting;
import com.mushroom.midnight.common.world.feature.IMidnightFeature;
import com.mushroom.midnight.common.world.feature.config.DragonNestPlacementConfig;
import com.mushroom.midnight.common.world.feature.config.ParcelPlacementConfig;
import com.mushroom.midnight.common.world.feature.config.ScatterPlacementConfig;
import com.mushroom.midnight.common.world.feature.config.SurfacePlacementConfig;
import com.mushroom.midnight.common.world.feature.config.UndergroundPlacementConfig;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.FlowerEntry;

import java.util.Random;
import java.util.function.Consumer;

import static com.mushroom.midnight.common.biome.MidnightBiomeFeatures.*;

public class MidnightBiomeConfigs {
    public static final SurfaceConfig ROCKY_SURFACE_CONFIG = new SurfaceConfig()
            .withTopState(ModBlocks.NIGHTSTONE.getDefaultState())
            .withFillerState(ModBlocks.NIGHTSTONE.getDefaultState())
            .withWetState(ModBlocks.NIGHTSTONE.getDefaultState());

    public static final FeatureConfig GLOBAL_FEATURE_CONFIG = FeatureConfig.builder()
            .withFeature(FeatureSorting.LAST, GHOST_PLANT_FEATURE, new ParcelPlacementConfig(3, 6, 0.3f))
            .withFeature(FeatureSorting.LAST, DRAGON_NEST_FEATURE, new DragonNestPlacementConfig(32, 32))
            .withFeature(FeatureSorting.LAST, UNSTABLE_BUSH_FEATURE, new ParcelPlacementConfig(6, 10, 0.2f))
            .withFeature(FeatureSorting.LAST, UNDERGROUND_FEATURES, new UndergroundPlacementConfig(1, 3, 10, 50))
            .withFlower(new FlowerEntry(ModBlocks.GHOST_PLANT.getDefaultState(), 20), new FlowerEntry(ModBlocks.LUMEN_BUD.getDefaultState(), 20), new FlowerEntry(ModBlocks.NIGHTSHROOM.getDefaultState(), 5), new FlowerEntry(ModBlocks.VIRIDSHROOM.getDefaultState(), 5), new FlowerEntry(ModBlocks.DEWSHROOM.getDefaultState(), 5), new FlowerEntry(ModBlocks.BOGSHROOM.getDefaultState(), 5))
            .build();

    public static final FeatureConfig VEGETATED_FEATURE_CONFIG = FeatureConfig.builder()
            .extendsFrom(GLOBAL_FEATURE_CONFIG)
            .withFeature(ROCKSHROOM_HEAP_FEATURE, new SurfacePlacementConfig(-99, 1))
            .withFeature(TALL_GRASS_FEATURE, new ScatterPlacementConfig(6, 64))
            .withFeature(DOUBLE_GRASS_FEATURE, new ScatterPlacementConfig(3, 32))
            .withFeature(LUMEN_FEATURE, new ScatterPlacementConfig(1, 32))
            .withFeature(DOUBLE_LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(1, 16))
            .build();

    public static final FeatureConfig ROCKY_FEATURE_CONFIG = FeatureConfig.builder()
            .extendsFrom(GLOBAL_FEATURE_CONFIG)
            .withFeature(ROCKSHROOM_HEAP_FEATURE, new SurfacePlacementConfig(-99, 1))
            .withFeature(LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(DOUBLE_LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(TRENCHSTONE_BOULDER_FEATURE, new SurfacePlacementConfig(-3, 1))
            .build();

    public static final FeatureConfig VIGILANT_FOREST_FEATURE_CONFIG = FeatureConfig.builder()
            .extendsFrom(VEGETATED_FEATURE_CONFIG)
            .withFeature(new IMidnightFeature[] {
                    SHADOWROOT_TREE_FEATURE,
                    DARK_WILLOW_TREE_FEATURE
            }, new SurfacePlacementConfig(8))
            .withFeature(SUAVIS_FEATURE, new ScatterPlacementConfig(1, 8))
            .withFeature(DEAD_LOG_FEATURE, new SurfacePlacementConfig(6))
            .withFeature(DEAD_TREE_FEATURE, new SurfacePlacementConfig(-5, 1))
            .withFeature(VIOLEAF_FEATURE, new ParcelPlacementConfig(2, 5, 0.3f))
            .withFlower(new FlowerEntry(ModBlocks.VIOLEAF.getDefaultState(), 40), new FlowerEntry(ModBlocks.SUAVIS.getDefaultState().withProperty(BlockSuavis.STAGE, 0), 40))
            .build();

    public static final FeatureConfig RUNEBUSH_GROVE_FEATURE_CONFIG = FeatureConfig.builder()
            .extendsFrom(GLOBAL_FEATURE_CONFIG)
            .withFeature(LUMEN_FEATURE, new ScatterPlacementConfig(1, 32))
            .withFeature(DOUBLE_LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(new IMidnightFeature[] {
                    SHADOWROOT_TREE_FEATURE,
                    DARK_WILLOW_TREE_FEATURE
            }, new SurfacePlacementConfig(8))
            .withFeature(DEAD_LOG_FEATURE, new SurfacePlacementConfig(6))
            .withFeature(COMMON_SUAVIS_FEATURE, new ScatterPlacementConfig(8, 8))
            .withFeature(RUNEBUSH_FEATURE, new ScatterPlacementConfig(16, 128))
            .withFeature(TALL_GRASS_FEATURE, new ScatterPlacementConfig(4, 16))
            .withFlower(new FlowerEntry(ModBlocks.RUNEBUSH.getDefaultState(), 40), new FlowerEntry(ModBlocks.SUAVIS.getDefaultState().withProperty(BlockSuavis.STAGE, 0), 40))
            .build();

    public static final FeatureConfig FUNGI_FOREST_FEATURE_CONFIG = FeatureConfig.builder()
            .extendsFrom(VEGETATED_FEATURE_CONFIG)
            .withFeature(ROCKSHROOM_HEAP_FEATURE, new SurfacePlacementConfig(-8, 2))
            .withFeature(LARGE_FUNGI_FEATURES, new SurfacePlacementConfig(6))
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(6, 16))
            .withFeature(DOUBLE_FUNGI_FEATURE, new ScatterPlacementConfig(4, 8))
            .withFeature(BLADESHROOM_FEATURE, new ScatterPlacementConfig(1, 32))
            .withFlower(new FlowerEntry(ModBlocks.BLADESHROOM.getDefaultState(), 40))
            .build();

    public static final FeatureConfig CRYSTAL_SPIRES_FEATURE_CONFIG = FeatureConfig.builder()
            .extendsFrom(GLOBAL_FEATURE_CONFIG)
            .withFeature(ROCKSHROOM_HEAP_FEATURE, new SurfacePlacementConfig(-99, 1))
            .withFeature(SHADOWROOT_TREE_FEATURE, new SurfacePlacementConfig(-3, 1))
            .withFeature(BLOOMCRYSTAL_FEATURE, new SurfacePlacementConfig(3))
            .withFeature(BLOOMCRYSTAL_SPIRE_FEATURE, new SurfacePlacementConfig(2, 3))
            .withFeature(LUMEN_FEATURE, new ScatterPlacementConfig(1, 32))
            .withFeature(DOUBLE_LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(CRYSTAL_FLOWER_FEATURE, new ScatterPlacementConfig(5, 12))
            .withFlower(new FlowerEntry(ModBlocks.CRYSTAL_FLOWER.getDefaultState(), 60))
            .build();

    public static final FeatureConfig WARPED_FIELDS_FEATURE_CONFIG = FeatureConfig.builder()
            .extendsFrom(VEGETATED_FEATURE_CONFIG)
            .withFeature(SHADOWROOT_TREE_FEATURE, new SurfacePlacementConfig(-3, 1))
            .build();

    public static final FeatureConfig DECEITFUL_BOG_FEATURE_CONFIG = FeatureConfig.builder()
            .extendsFrom(GLOBAL_FEATURE_CONFIG)
            .withFeature(ROCKSHROOM_HEAP_FEATURE, new SurfacePlacementConfig(-99, 1))
            .withFeature(BOG_DEAD_TREE_FEATURE, new SurfacePlacementConfig(-1, 1))
            .withFeature(new IMidnightFeature[] {
                    SHADOWROOT_TREE_FEATURE,
                    DARK_WILLOW_TREE_FEATURE
            }, new SurfacePlacementConfig(4))
            .withFeature(LARGE_BOGSHROOM_FEATURE, new SurfacePlacementConfig(1))
            .withFeature(BOGWEED_FEATURE, new ScatterPlacementConfig(1, 24))
            .withFeature(TALL_GRASS_FEATURE, new ScatterPlacementConfig(5, 32))
            .withFeature(DOUBLE_GRASS_FEATURE, new ScatterPlacementConfig(3, 16))
            .withFeature(DECEITFUL_MOSS_FEATURE, new ScatterPlacementConfig(16, 32))
            .withFeature(BOG_FUNGI_FEATURE, new ScatterPlacementConfig(2, 16))
            .withFeature(DOUBLE_BOG_FUNGI_FEATURE, new ScatterPlacementConfig(1, 8))
            .withFeature(DEAD_LOG_FEATURE, new SurfacePlacementConfig(5))
            .withFeature(DECEITFUL_ALGAE_FEATURE, new ScatterPlacementConfig(10, 20))
            .build();

    public static final FeatureConfig NIGHT_PLAINS_FEATURE_CONFIG = FeatureConfig.builder()
            .extendsFrom(GLOBAL_FEATURE_CONFIG)
            .withFeature(ROCKSHROOM_HEAP_FEATURE, new SurfacePlacementConfig(-99, 1))
            .withFeature(TALL_GRASS_FEATURE, new ScatterPlacementConfig(2, 32))
            .withFeature(FINGERED_GRASS_FEATURE, new ScatterPlacementConfig(8, 16) {
                @Override
                public void apply(World world, SurfacePlacementLevel placementLevel, Random random, BlockPos chunkOrigin, Consumer<BlockPos> generator) {
                    if (world.rand.nextFloat() < 0.2f) {
                        super.apply(world, placementLevel, random, chunkOrigin, generator);
                    }
                }
            })
            .withFlower(new FlowerEntry(ModBlocks.FINGERED_GRASS.getDefaultState(), 40))
            .withFeature(new IMidnightFeature[] {
                    SHADOWROOT_TREE_FEATURE,
                    DEAD_TREE_FEATURE
            }, new SurfacePlacementConfig(-5, 1))
            .withFeature(new IMidnightFeature[] {
                    NIGHTSTONE_BOULDER_FEATURE,
                    NIGHTSTONE_SPIKE_FEATURE
            }, new SurfacePlacementConfig(-3, 1))
            .build();

    public static final FeatureConfig VALLEY_FEATURE_CONFIG = FeatureConfig.builder()
            .extendsFrom(GLOBAL_FEATURE_CONFIG)
            .withFeature(ROCKSHROOM_HEAP_FEATURE, new SurfacePlacementConfig(-99, 1))
            .build();

    public static final FeatureConfig GREAT_CAVERN_FEATURE_CONFIG = FeatureConfig.builder()
            .withFlower(new FlowerEntry(ModBlocks.LUMEN_BUD.getDefaultState(), 10), new FlowerEntry(ModBlocks.NIGHTSHROOM.getDefaultState(), 10), new FlowerEntry(ModBlocks.VIRIDSHROOM.getDefaultState(), 10), new FlowerEntry(ModBlocks.DEWSHROOM.getDefaultState(), 10), new FlowerEntry(ModBlocks.BOGSHROOM.getDefaultState(), 10))
            .build();

    public static final FeatureConfig CRYSTAL_CAVERN_FEATURE_CONFIG = FeatureConfig.builder()
            .withFeature(ROUXE_FEATURE, new SurfacePlacementConfig(5))
            .withFlower(new FlowerEntry(ModBlocks.LUMEN_BUD.getDefaultState(), 10), new FlowerEntry(ModBlocks.NIGHTSHROOM.getDefaultState(), 10), new FlowerEntry(ModBlocks.VIRIDSHROOM.getDefaultState(), 10), new FlowerEntry(ModBlocks.DEWSHROOM.getDefaultState(), 10), new FlowerEntry(ModBlocks.BOGSHROOM.getDefaultState(), 10))
            .build();

    public static final FeatureConfig FUNGAL_CAVERN_FEATURE_CONFIG = FeatureConfig.builder()
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(6, 16))
            .withFeature(DOUBLE_FUNGI_FEATURE, new ScatterPlacementConfig(4, 8))
            .withFeature(LARGE_BULB_FUNGUS_FEATURE, new SurfacePlacementConfig(3))
            .withFeature(BULB_FUNGUS_FEATURE, new SurfacePlacementConfig(5, 8))
            .withFlower(new FlowerEntry(ModBlocks.BULB_FUNGUS.getDefaultState(), 10), new FlowerEntry(ModBlocks.LUMEN_BUD.getDefaultState(), 10), new FlowerEntry(ModBlocks.NIGHTSHROOM.getDefaultState(), 10), new FlowerEntry(ModBlocks.VIRIDSHROOM.getDefaultState(), 10), new FlowerEntry(ModBlocks.DEWSHROOM.getDefaultState(), 10), new FlowerEntry(ModBlocks.BOGSHROOM.getDefaultState(), 10))
            .build();

    public static final SpawnerConfig VEGETATED_SPAWN_CONFIG = SpawnerConfig.builder()
            .withCreature(new Biome.SpawnListEntry(EntityNightStag.class, 100, 1, 3))
            .withMonster(new Biome.SpawnListEntry(EntityRifter.class, 100, 1, 2))
            .withMonster(new Biome.SpawnListEntry(EntityEnderman.class, 10, 4, 4))
            .build();

    public static final SpawnerConfig ROCKY_SPAWN_CONFIG = SpawnerConfig.builder()
            .withCreature(new Biome.SpawnListEntry(EntityNightStag.class, 100, 1, 3))
            .withMonster(new Biome.SpawnListEntry(EntityRifter.class, 100, 1, 2))
            .withMonster(new Biome.SpawnListEntry(EntityEnderman.class, 10, 4, 4))
            .withMonster(new Biome.SpawnListEntry(EntityHunter.class, 5, 1, 2))
            .build();

    public static final SpawnerConfig UNDERGROUND_SPAWN_CONFIG = SpawnerConfig.builder()
            .withMonster(new Biome.SpawnListEntry(EntityStinger.class, 100, 2, 4))
            .withMonster(new Biome.SpawnListEntry(EntityEnderman.class, 10, 4, 4))
            .build();

    public static final SpawnerConfig CRYSTAL_SPIRES_SPAWN_CONFIG = SpawnerConfig.builder()
            .withCreature(new Biome.SpawnListEntry(EntityNightStag.class, 100, 1, 3))
            .withAmbientCreature(new Biome.SpawnListEntry(EntityCrystalBug.class, 100, 7, 10))
            .withMonster(new Biome.SpawnListEntry(EntityStinger.class, 100, 1, 2))
            .build();

    public static final SpawnerConfig WARPED_FIELDS_SPAWN_CONFIG = SpawnerConfig.builder()
            .extendsFrom(VEGETATED_SPAWN_CONFIG)
            .withMonster(new Biome.SpawnListEntry(EntityHunter.class, 5, 0, 2))
            .build();

    public static final SpawnerConfig VIGILANT_FOREST_SPAWN_CONFIG = SpawnerConfig.builder()
            .extendsFrom(VEGETATED_SPAWN_CONFIG)
            .withCreature(new Biome.SpawnListEntry(EntitySkulk.class, 100, 1, 2))
            .build();

    public static final SpawnerConfig FUNGI_FOREST_SPAWN_CONFIG = SpawnerConfig.builder()
            .extendsFrom(VEGETATED_SPAWN_CONFIG)
            .withCreature(new Biome.SpawnListEntry(EntitySkulk.class, 100, 1, 2))
            .build();

    public static final SpawnerConfig DECEITFUL_BOG_SPAWN_CONFIG = SpawnerConfig.builder()
            .extendsFrom(VEGETATED_SPAWN_CONFIG)
            .withWaterCreature(new Biome.SpawnListEntry(EntityDeceitfulSnapper.class, 100, 5, 10))
            .build();

    public static final SpawnerConfig NIGHT_PLAINS_SPAWN_CONFIG = SpawnerConfig.builder()
            .extendsFrom(VEGETATED_SPAWN_CONFIG)
            .withMonster(new Biome.SpawnListEntry(EntityHunter.class, 5, 1, 2))
            .build();

    public static final SpawnerConfig PLATEAU_SPAWN_CONFIG = SpawnerConfig.builder()
            .extendsFrom(ROCKY_SPAWN_CONFIG)
            .withMonster(new Biome.SpawnListEntry(EntityHunter.class, 5, 0, 2))
            .build();

    public static final SpawnerConfig VALLEY_SPAWN_CONFIG = SpawnerConfig.builder()
            .extendsFrom(VEGETATED_SPAWN_CONFIG)
            .withMonster(new Biome.SpawnListEntry(EntityHunter.class, 5, 1, 2))
            .build();

    public static final SpawnerConfig GREAT_CAVERN_SPAWN_CONFIG = SpawnerConfig.builder()
            .extendsFrom(UNDERGROUND_SPAWN_CONFIG)
            .build();

    public static final SpawnerConfig CRYSTAL_CAVERN_SPAWN_CONFIG = SpawnerConfig.builder()
            .extendsFrom(UNDERGROUND_SPAWN_CONFIG)
            .withMonster(new Biome.SpawnListEntry(EntityNova.class, 100, 1, 2))
            .build();

    public static final SpawnerConfig FUNGAL_CAVERN_SPAWN_CONFIG = SpawnerConfig.builder()
            .extendsFrom(UNDERGROUND_SPAWN_CONFIG)
            .build();

    public static final SurfaceTerrainConfig WARPED_TERRAIN_CONFIG = new SurfaceTerrainConfig()
            .withBaseHeight(0.1F)
            .withHeightVariation(0.8F)
            .withDensityScale(0.5F)
            .withRidgeWeight(0.0F)
            .wet();

    public static final SurfaceTerrainConfig BOG_TERRAIN_CONFIG = new SurfaceTerrainConfig()
            .withBaseHeight(-0.9F)
            .withHeightVariation(0.2F)
            .withRidgeWeight(0.0F)
            .wet();

    public static final SurfaceTerrainConfig PLAINS_TERRAIN_CONFIG = new SurfaceTerrainConfig()
            .withBaseHeight(0.12F)
            .withHeightVariation(0.26F)
            .withRidgeWeight(0.0F);

    public static final SurfaceTerrainConfig CRYSTAL_SPIRES_TERRAIN_CONFIG = new SurfaceTerrainConfig()
            .withBaseHeight(0.6F)
            .withHeightVariation(0.26F)
            .withRidgeWeight(0.0F);

    public static final SurfaceTerrainConfig FOREST_TERRAIN_CONFIG = new SurfaceTerrainConfig()
            .withBaseHeight(0.155F)
            .withHeightVariation(0.07F);

    public static final SurfaceTerrainConfig VALLEY_TERRAIN_CONFIG = new SurfaceTerrainConfig()
            .withBaseHeight(-1.2F)
            .withHeightVariation(0.05F)
            .withRidgeWeight(0.0F)
            .wet();

    public static final SurfaceTerrainConfig PLATEAU_TERRAIN_CONFIG = new SurfaceTerrainConfig()
            .withBaseHeight(5.0F)
            .withHeightVariation(0.01F);

    public static final SurfaceTerrainConfig PEAK_TERRAIN_CONFIG = new SurfaceTerrainConfig()
            .withBaseHeight(6.0F)
            .withHeightVariation(0.5F);

    public static final SurfaceTerrainConfig RIDGE_TERRAIN_CONFIG = new SurfaceTerrainConfig()
            .withBaseHeight(4.5F)
            .withHeightVariation(0.1F);

    public static final SurfaceTerrainConfig HILL_TERRAIN_CONFIG = new SurfaceTerrainConfig()
            .withBaseHeight(2.25F)
            .withHeightVariation(0.4F);

    public static final SurfaceBiomeConfig OBSCURED_PLATEAU_CONFIG = SurfaceBiomeConfig.builder()
            .withTerrain(PLATEAU_TERRAIN_CONFIG)
            .withSurface(ROCKY_SURFACE_CONFIG)
            .withFeatures(ROCKY_FEATURE_CONFIG)
            .withSpawner(PLATEAU_SPAWN_CONFIG)
            .build();

    public static final SurfaceBiomeConfig OBSCURED_PEAK_CONFIG = SurfaceBiomeConfig.builder()
            .withTerrain(PEAK_TERRAIN_CONFIG)
            .withSurface(ROCKY_SURFACE_CONFIG)
            .withFeatures(ROCKY_FEATURE_CONFIG)
            .withSpawner(ROCKY_SPAWN_CONFIG)
            .build();

    public static final SurfaceBiomeConfig BLACK_RIDGE_CONFIG = SurfaceBiomeConfig.builder()
            .withTerrain(RIDGE_TERRAIN_CONFIG)
            .withSurface(ROCKY_SURFACE_CONFIG)
            .withFeatures(ROCKY_FEATURE_CONFIG)
            .withSpawner(ROCKY_SPAWN_CONFIG)
            .build();

    public static final SurfaceBiomeConfig PHANTASMAL_VALLEY_CONFIG = SurfaceBiomeConfig.builder()
            .withTerrain(VALLEY_TERRAIN_CONFIG)
            .withFeatures(VALLEY_FEATURE_CONFIG)
            .withSpawner(VALLEY_SPAWN_CONFIG)
            .build();

    public static final SurfaceBiomeConfig VIGILANT_FOREST_CONFIG = SurfaceBiomeConfig.builder()
            .withTerrain(FOREST_TERRAIN_CONFIG)
            .withFeatures(VIGILANT_FOREST_FEATURE_CONFIG)
            .withSpawner(VIGILANT_FOREST_SPAWN_CONFIG)
            .build();

    public static final SurfaceBiomeConfig HILLY_VIGILANT_FOREST_CONFIG = SurfaceBiomeConfig.builder()
            .withTerrain(HILL_TERRAIN_CONFIG)
            .withFeatures(VIGILANT_FOREST_FEATURE_CONFIG)
            .withSpawner(VIGILANT_FOREST_SPAWN_CONFIG)
            .build();

    public static final SurfaceBiomeConfig RUNEBUSH_GROVE_CONFIG = SurfaceBiomeConfig.builder()
            .withTerrain(FOREST_TERRAIN_CONFIG)
            .withFeatures(RUNEBUSH_GROVE_FEATURE_CONFIG)
            .withSpawner(VEGETATED_SPAWN_CONFIG)
            .withGrassColor(0x8C84BC)
            .build();

    public static final SurfaceBiomeConfig FUNGI_FOREST_CONFIG = SurfaceBiomeConfig.builder()
            .withTerrain(FOREST_TERRAIN_CONFIG)
            .withFeatures(FUNGI_FOREST_FEATURE_CONFIG)
            .withSpawner(FUNGI_FOREST_SPAWN_CONFIG)
            .withGrassColor(0x8489B5)
            .build();

    public static final SurfaceBiomeConfig HILLY_FUNGI_FOREST_CONFIG = SurfaceBiomeConfig.builder()
            .withTerrain(HILL_TERRAIN_CONFIG)
            .withFeatures(FUNGI_FOREST_FEATURE_CONFIG)
            .withSpawner(FUNGI_FOREST_SPAWN_CONFIG)
            .withGrassColor(0x8489B5)
            .build();

    public static final SurfaceBiomeConfig CRYSTAL_SPIRES_CONFIG = SurfaceBiomeConfig.builder()
            .withTerrain(CRYSTAL_SPIRES_TERRAIN_CONFIG)
            .withFeatures(CRYSTAL_SPIRES_FEATURE_CONFIG)
            .withSpawner(CRYSTAL_SPIRES_SPAWN_CONFIG)
            .withGrassColor(0xD184BC)
            .build();

    public static final SurfaceBiomeConfig WARPED_FIELDS_CONFIG = SurfaceBiomeConfig.builder()
            .withTerrain(WARPED_TERRAIN_CONFIG)
            .withFeatures(WARPED_FIELDS_FEATURE_CONFIG)
            .withSpawner(WARPED_FIELDS_SPAWN_CONFIG)
            .build();

    public static final SurfaceBiomeConfig DECEITFUL_BOG_CONFIG = SurfaceBiomeConfig.builder()
            .withTerrain(BOG_TERRAIN_CONFIG)
            .withFeatures(DECEITFUL_BOG_FEATURE_CONFIG)
            .withSpawner(DECEITFUL_BOG_SPAWN_CONFIG)
            .withGrassColor(0x8893AD)
            .build();

    public static final SurfaceBiomeConfig NIGHT_PLAINS_CONFIG = SurfaceBiomeConfig.builder()
            .withTerrain(PLAINS_TERRAIN_CONFIG)
            .withFeatures(NIGHT_PLAINS_FEATURE_CONFIG)
            .withSpawner(NIGHT_PLAINS_SPAWN_CONFIG)
            .withGrassColor(0xBAA3C6)
            .build();

    public static final CavernStructureConfig GREAT_CAVERN_STRUCTURE_CONFIG = new CavernStructureConfig()
            .withCaveRadiusScale(0.0F)
            .withHeightVariation(0.4F);

    public static final CavernStructureConfig FUNGAL_CAVERN_STRUCTURE_CONFIG = new CavernStructureConfig()
            .withCavernDensity(-15.0F)
            .withPillarWeight(0.0F)
            .withFloorHeight(0.1F)
            .withCeilingHeight(0.8F)
            .withHeightVariation(0.6F);

    public static final CavernousBiomeConfig GREAT_CAVERN_CONFIG = CavernousBiomeConfig.builder()
            .withSpawner(GREAT_CAVERN_SPAWN_CONFIG)
            .withFeatures(GREAT_CAVERN_FEATURE_CONFIG)
            .withStructure(GREAT_CAVERN_STRUCTURE_CONFIG)
            .build();

    public static final CavernousBiomeConfig CRYSTAL_CAVERN_CONFIG = CavernousBiomeConfig.builder()
            .withSpawner(CRYSTAL_CAVERN_SPAWN_CONFIG)
            .withFeatures(CRYSTAL_CAVERN_FEATURE_CONFIG)
            .withStructure(GREAT_CAVERN_STRUCTURE_CONFIG)
            .build();

    public static final CavernousBiomeConfig FUNGAL_CAVERN_CONFIG = CavernousBiomeConfig.builder()
            .withSurface(new SurfaceConfig().withTopState(ModBlocks.MIDNIGHT_MYCELIUM.getDefaultState()).withFillerState(ModBlocks.NIGHTSTONE.getDefaultState()).withWetState(ModBlocks.NIGHTSTONE.getDefaultState()))
            .withSpawner(FUNGAL_CAVERN_SPAWN_CONFIG)
            .withFeatures(FUNGAL_CAVERN_FEATURE_CONFIG)
            .withStructure(FUNGAL_CAVERN_STRUCTURE_CONFIG)
            .build();
}
