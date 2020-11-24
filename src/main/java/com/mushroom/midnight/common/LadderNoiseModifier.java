package com.mushroom.midnight.common;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

public final class LadderNoiseModifier {
    public static boolean modifyLadderNoises(BlockState block, BlockPos pos, Entity e) {
        return block.getBlock().isLadder(block, e.world, pos, e instanceof LivingEntity ? (LivingEntity) e : null);
    }
}
