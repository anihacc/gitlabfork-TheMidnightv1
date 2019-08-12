package com.mushroom.midnight.common.world.layer;

import com.mushroom.midnight.common.biome.BiomeSpawnEntry;
import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

public class AddHillsLayer implements IC1Transformer {
    private final MidnightBiomeGroup group;
    private final int chance;

    public AddHillsLayer(MidnightBiomeGroup group, int chance) {
        this.group = group;
        this.chance = chance;
    }

    @Override
    public int apply(INoiseRandom random, int value) {
        if (random.random(this.chance) == 0) {
            MidnightBiomeGroup.Pool pool = this.group.getPoolForBiome(value);
            BiomeSpawnEntry entry = pool.selectWeight(random::random);
            if (entry != null) {
                return entry.getBiomeId();
            }
        }
        return value;
    }
}
