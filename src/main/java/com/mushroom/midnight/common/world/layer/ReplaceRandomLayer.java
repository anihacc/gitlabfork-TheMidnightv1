package com.mushroom.midnight.common.world.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

public class ReplaceRandomLayer implements IC1Transformer {
    private final int value;
    private final int chance;

    public ReplaceRandomLayer(int value, int chance) {
        this.value = value;
        this.chance = chance;
    }

    @Override
    public int apply(INoiseRandom random, int parent) {
        return random.random(this.chance) == 0 ? this.value : parent;
    }
}
