package com.mushroom.midnight.common.biome.cavern;

import com.mushroom.midnight.common.biome.MidnightCavernConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class GreatCavernBiome extends CavernousBiome {
    public GreatCavernBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.CAVERN, MidnightSurfaceBuilders.NIGHTSTONE_CONFIG)
                .heightScale(0.4F)
        );

        MidnightCavernConfigurator.addCaves(this);

        MidnightCavernConfigurator.addStandardFeatures(this);
        MidnightCavernConfigurator.addStandardSpawns(this);
    }
}
