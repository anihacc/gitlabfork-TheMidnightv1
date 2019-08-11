package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightSurfaceConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class FungiForestBiome extends SurfaceBiome {
    public FungiForestBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.PATCHED_COARSE_DIRT, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.FOREST)
                .grassColor(0x8489B5)
                .skyColor(0x15292D)
                .depth(0.155F)
                .scale(0.1F)
        );

        MidnightSurfaceConfigurator.addStructureFeatures(this);
        MidnightSurfaceConfigurator.addMoltenCrater(this);

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
