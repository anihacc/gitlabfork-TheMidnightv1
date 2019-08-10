package com.mushroom.midnight.common.biome.cavern;

import com.mushroom.midnight.common.biome.MidnightCavernConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class FungalCavernBiome extends CavernousBiome {
    public FungalCavernBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.CAVERN, MidnightSurfaceBuilders.MYCELIUM_CONFIG)
                .cavernDensity(-15.0F)
                .pillarWeight(0.0F)
                .floorHeight(0.1F)
                .ceilingHeight(0.8F)
                .heightScale(0.6F)
        );

        MidnightCavernConfigurator.addCaves(this);

        MidnightCavernConfigurator.addCrystalotus(this);
        MidnightCavernConfigurator.addBulbFungi(this);

        MidnightCavernConfigurator.addStandardSpawns(this);
    }
}
