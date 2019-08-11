package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightSurfaceConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class HillyFungiForestBiome extends SurfaceBiome {
    public HillyFungiForestBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.PATCHED_COARSE_DIRT, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.FOREST)
                .grassColor(0x8489B5)
                .skyColor(0x15292D)
                .depth(2.25F)
                .scale(0.4F)
        );

        MidnightSurfaceConfigurator.addStructureFeatures(this);
        MidnightSurfaceConfigurator.addGlobalOres(this);

        MidnightSurfaceConfigurator.addLargeFungis(this);
        MidnightSurfaceConfigurator.addLumen(this);

        MidnightSurfaceConfigurator.addNightshroomFlowers(this);
        MidnightSurfaceConfigurator.addDewshroomFlowers(this);
        MidnightSurfaceConfigurator.addViridshroomFlowers(this);

        MidnightSurfaceConfigurator.addBladeshrooms(this);
        MidnightSurfaceConfigurator.addDeadViridShroomAndCache(this);
        MidnightSurfaceConfigurator.addGrasses(this);

        MidnightSurfaceConfigurator.addGlobalFeatures(this);

        MidnightSurfaceConfigurator.addStandardCreatureSpawns(this);
        MidnightSurfaceConfigurator.addStandardMonsterSpawns(this);
        MidnightSurfaceConfigurator.addForestSpawns(this);
    }
}
