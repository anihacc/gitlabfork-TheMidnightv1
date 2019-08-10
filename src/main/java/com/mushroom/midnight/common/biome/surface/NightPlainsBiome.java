package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.MidnightSurfaceConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightEntities;

public class NightPlainsBiome extends SurfaceBiome {
    public NightPlainsBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.PLAINS)
                .grassColor(0xAFA6B5)
                .depth(0.12F)
                .scale(0.26F)
                .ridgeWeight(0.0F)
        );

        MidnightSurfaceConfigurator.addStructureFeatures(this);
        MidnightSurfaceConfigurator.addMoltenCrater(this);

        MidnightSurfaceConfigurator.addGlobalOres(this);

        MidnightSurfaceConfigurator.addSparseShadowrootTrees(this);
        MidnightSurfaceConfigurator.addGrasses(this);
        MidnightSurfaceConfigurator.addFingeredGrass(this);

        MidnightSurfaceConfigurator.addNightstoneSpikesAndBoulders(this);

        MidnightSurfaceConfigurator.addGlobalFeatures(this);
        MidnightSurfaceConfigurator.addStandardMonsterSpawns(this);
        MidnightSurfaceConfigurator.addStandardCreatureSpawns(this);
        MidnightSurfaceConfigurator.addWell(this);

        add(Midnight.MIDNIGHT_MOB, new SpawnListEntry(MidnightEntities.HUNTER, 5, 1, 2));
    }
}
