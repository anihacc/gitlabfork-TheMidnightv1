package com.mushroom.midnight.common.world.layer;

import com.mushroom.midnight.common.biome.BiomeSpawnEntry;
import com.mushroom.midnight.common.biome.MidnightBiomeGroup;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public class AddHillsLayer implements ICastleTransformer {
    private final MidnightBiomeGroup group;
    private final int chance;

    public AddHillsLayer(MidnightBiomeGroup group, int chance) {
        this.group = group;
        this.chance = chance;
    }

    @Override
    public int apply(INoiseRandom random, int top, int right, int bottom, int left, int value) {
        if (value == top && value == right && value == bottom && value == left) {
            if (random.random(this.chance) == 0) {
                MidnightBiomeGroup.Pool pool = this.group.getPoolForBiome(value);
                BiomeSpawnEntry entry = pool.selectEntry(random::random);
                if (entry != null) {
                    return entry.getBiomeId();
                }
            }
        }
        return value;
    }
}
