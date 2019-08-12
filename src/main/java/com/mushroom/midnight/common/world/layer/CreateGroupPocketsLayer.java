package com.mushroom.midnight.common.world.layer;

import com.mushroom.midnight.common.biome.BiomeSpawnEntry;
import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

public class CreateGroupPocketsLayer implements IC1Transformer {
    private final MidnightBiomeGroup group;

    public CreateGroupPocketsLayer(MidnightBiomeGroup group) {
        this.group = group;
    }

    @Override
    public int apply(INoiseRandom random, int parent) {
        MidnightBiomeGroup.Pool pool = this.group.getPoolForBiome(parent);
        BiomeSpawnEntry entry = pool.selectChance(random::random);
        if (entry != null) {
            return entry.getBiomeId();
        }
        return parent;
    }
}
