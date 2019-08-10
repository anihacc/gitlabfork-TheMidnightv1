package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightSurfaceConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class HillyVigilantForestBiome extends SurfaceBiome {
    public HillyVigilantForestBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.FOREST)
                .grassColor(0x695F8C)
                .depth(2.25F)
                .scale(0.4F)
                .fog(20.0F, 110.0F)
        );

        MidnightSurfaceConfigurator.addStructureFeatures(this);
        MidnightSurfaceConfigurator.addGlobalOres(this);

        MidnightSurfaceConfigurator.addShadowRootGuardTower(this);

        MidnightSurfaceConfigurator.addLumen(this);
        MidnightSurfaceConfigurator.addNightshroomFlowers(this);

        MidnightSurfaceConfigurator.addGrasses(this);

        MidnightSurfaceConfigurator.addVigilantForestTrees(this);
        MidnightSurfaceConfigurator.addDeadLogs(this);

        MidnightSurfaceConfigurator.addSparseSuavis(this);
        MidnightSurfaceConfigurator.addSparseDeadTrees(this);
        MidnightSurfaceConfigurator.addVioleafs(this);

        MidnightSurfaceConfigurator.addGlobalFeatures(this);

        MidnightSurfaceConfigurator.addStandardCreatureSpawns(this);
        MidnightSurfaceConfigurator.addStandardMonsterSpawns(this);
        MidnightSurfaceConfigurator.addForestSpawns(this);
    }
}
