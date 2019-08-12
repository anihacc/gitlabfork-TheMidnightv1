package com.mushroom.midnight.common.biome.cavern;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.biome.MidnightCavernConfigurator;
import com.mushroom.midnight.common.registry.MidnightEntities;
import net.minecraft.world.biome.Biome;

public class CrystalCavernBiome extends CavernousBiome {
    public CrystalCavernBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.CAVERN, MidnightSurfaceBuilders.NIGHTSTONE_CONFIG)
                .heightScale(0.4F)
        );

        MidnightCavernConfigurator.addCaves(this);

        MidnightCavernConfigurator.addStandardFeatures(this);
        MidnightCavernConfigurator.addRouxeClusters(this);
        MidnightCavernConfigurator.addTendrilweed(this);

        MidnightCavernConfigurator.addStandardSpawns(this);

        this.add(Midnight.MIDNIGHT_MOB, new Biome.SpawnListEntry(MidnightEntities.NOVA, 100, 1, 2));
    }
}
