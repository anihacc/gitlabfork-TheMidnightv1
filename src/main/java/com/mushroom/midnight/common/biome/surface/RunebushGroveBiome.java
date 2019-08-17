package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightSurfaceConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class RunebushGroveBiome extends SurfaceBiome {
    public RunebushGroveBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.FOREST)
                .grassColor(0x8C84BC)
                .skyColor(0x1F3B4A)
                .depth(0.155F)
                .scale(0.07F)
                .fog(0.1F, 80.0F)
        );

        MidnightSurfaceConfigurator.addStructureFeatures(this);
        MidnightSurfaceConfigurator.addGlobalOres(this);

        MidnightSurfaceConfigurator.addVigilantForestTrees(this);
        MidnightSurfaceConfigurator.addFallenDeadLogs(this);

        MidnightSurfaceConfigurator.addCommonSuavis(this);
        MidnightSurfaceConfigurator.addLumen(this);
        MidnightSurfaceConfigurator.addRunebushes(this);

        MidnightSurfaceConfigurator.addGlobalFeatures(this);

        MidnightSurfaceConfigurator.addStandardCreatureSpawns(this);
        MidnightSurfaceConfigurator.addStandardMonsterSpawns(this);
        MidnightSurfaceConfigurator.addForestSpawns(this);
    }
}
