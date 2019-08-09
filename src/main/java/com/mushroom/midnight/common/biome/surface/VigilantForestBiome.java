package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class VigilantForestBiome extends SurfaceBiome {
    public VigilantForestBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.FOREST)
                .grassColor(0x695F8C)
                .depth(0.155F)
                .scale(0.07F)
                .fog(0.1F, 80.0F)
        );

        MidnightBiomeConfigurator.addStructureFeatures(this);
        MidnightBiomeConfigurator.addMoltenCrater(this);

        MidnightBiomeConfigurator.addGlobalOres(this);

        MidnightBiomeConfigurator.addShadowRootGuardTower(this);

        MidnightBiomeConfigurator.addVigilantForestTrees(this);
        MidnightBiomeConfigurator.addDeadLogs(this);

        MidnightBiomeConfigurator.addSparseSuavis(this);
        MidnightBiomeConfigurator.addSparseDeadTrees(this);
        MidnightBiomeConfigurator.addVioleafs(this);

        MidnightBiomeConfigurator.addLumen(this);
        MidnightBiomeConfigurator.addNightshroomFlowers(this);

        MidnightBiomeConfigurator.addGrasses(this);

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addForestSpawns(this);
    }
}
