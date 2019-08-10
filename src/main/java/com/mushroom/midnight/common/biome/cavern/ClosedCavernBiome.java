package com.mushroom.midnight.common.biome.cavern;

import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.biome.MidnightCavernConfigurator;

public class ClosedCavernBiome extends CavernousBiome {
    public ClosedCavernBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.CAVERN, MidnightSurfaceBuilders.NIGHTSTONE_CONFIG)
                .cavernDensity(5.0F)
        );

        MidnightCavernConfigurator.addCaves(this);
    }
}
