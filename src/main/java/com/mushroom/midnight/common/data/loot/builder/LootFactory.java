package com.mushroom.midnight.common.data.loot.builder;

import net.minecraft.block.Block;

public interface LootFactory {
    void apply(Block... blocks);
}
