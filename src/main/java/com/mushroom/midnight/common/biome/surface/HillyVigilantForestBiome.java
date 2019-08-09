package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class HillyVigilantForestBiome extends SurfaceBiome {
    public HillyVigilantForestBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.FOREST)
                .grassColor(0x695F8C)
                .depth(2.25F)
                .scale(0.4F)
                .fog(0.1F, 80.0F)
        );

        MidnightBiomeConfigurator.addStructureFeatures(this);
        MidnightBiomeConfigurator.addGlobalOres(this);

        MidnightBiomeConfigurator.addShadowRootGuardTower(this);

        MidnightBiomeConfigurator.addLumen(this);
        MidnightBiomeConfigurator.addNightshroomFlowers(this);

        MidnightBiomeConfigurator.addGrasses(this);

        MidnightBiomeConfigurator.addVigilantForestTrees(this);
        MidnightBiomeConfigurator.addDeadLogs(this);

        MidnightBiomeConfigurator.addSparseSuavis(this);
        MidnightBiomeConfigurator.addSparseDeadTrees(this);
        MidnightBiomeConfigurator.addVioleafs(this);

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addForestSpawns(this);
    }
}
