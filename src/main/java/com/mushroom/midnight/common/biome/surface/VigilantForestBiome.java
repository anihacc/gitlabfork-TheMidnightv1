package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightSurfaceConfigurator;
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

        MidnightSurfaceConfigurator.addStructureFeatures(this);
        MidnightSurfaceConfigurator.addMoltenCrater(this);

        MidnightSurfaceConfigurator.addGlobalOres(this);

        MidnightSurfaceConfigurator.addShadowRootGuardTower(this);

        MidnightSurfaceConfigurator.addVigilantForestTrees(this);
        MidnightSurfaceConfigurator.addDeadLogs(this);

        MidnightSurfaceConfigurator.addSparseSuavis(this);
        MidnightSurfaceConfigurator.addSparseDeadTrees(this);
        MidnightSurfaceConfigurator.addVioleafs(this);

        MidnightSurfaceConfigurator.addLumen(this);
        MidnightSurfaceConfigurator.addNightshroomFlowers(this);

        MidnightSurfaceConfigurator.addGrasses(this);

        MidnightSurfaceConfigurator.addGlobalFeatures(this);

        MidnightSurfaceConfigurator.addStandardCreatureSpawns(this);
        MidnightSurfaceConfigurator.addStandardMonsterSpawns(this);
        MidnightSurfaceConfigurator.addForestSpawns(this);
    }
}
