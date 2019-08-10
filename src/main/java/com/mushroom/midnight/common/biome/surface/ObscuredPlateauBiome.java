package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightSurfaceConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class ObscuredPlateauBiome extends SurfaceBiome {
    public ObscuredPlateauBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.NIGHTSTONE_CONFIG)
                .category(Category.EXTREME_HILLS)
                .depth(4.9F)
                .scale(0.2F)
                .ridgeWeight(0.0F)
        );

        MidnightSurfaceConfigurator.addStructureFeatures(this);
        MidnightSurfaceConfigurator.addGlobalOres(this);

        MidnightSurfaceConfigurator.addDewshroomFlowers(this);
        MidnightSurfaceConfigurator.addLumen(this);

        MidnightSurfaceConfigurator.addTrenchstoneBoulders(this);
        MidnightSurfaceConfigurator.addGlobalFeatures(this);

        MidnightSurfaceConfigurator.addStandardMonsterSpawns(this);
        MidnightSurfaceConfigurator.addStandardCreatureSpawns(this);
        MidnightSurfaceConfigurator.addRockySpawns(this);
    }
}
