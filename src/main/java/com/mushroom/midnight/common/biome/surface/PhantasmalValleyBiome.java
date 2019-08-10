package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.MidnightSurfaceConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightEntities;

public class PhantasmalValleyBiome extends SurfaceBiome {
    public PhantasmalValleyBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.RIVER)
                .depth(-1.7F)
                .scale(0.07F)
                .ridgeWeight(0.0F)
        );

        MidnightSurfaceConfigurator.addStructureFeatures(this);
        MidnightSurfaceConfigurator.addGlobalOres(this);
        MidnightSurfaceConfigurator.addGlobalFeatures(this);

        MidnightSurfaceConfigurator.addDewshroomFlowers(this);
        MidnightSurfaceConfigurator.addViridshroomFlowers(this);

        MidnightSurfaceConfigurator.addStandardMonsterSpawns(this);
        MidnightSurfaceConfigurator.addStandardCreatureSpawns(this);

        this.add(Midnight.MIDNIGHT_MOB, new SpawnListEntry(MidnightEntities.HUNTER, 5, 1, 2));
    }
}
