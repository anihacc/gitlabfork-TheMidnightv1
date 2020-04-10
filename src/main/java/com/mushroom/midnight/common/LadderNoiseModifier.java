package com.mushroom.midnight.common;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

public final class LadderNoiseModifier {
    private LadderNoiseModifier() {
    }

    public static boolean modifyLadderNoises(BlockState block, BlockPos pos, Entity e) {
        LivingEntity le = null;
        if (e instanceof LivingEntity) {
            le = (LivingEntity) e;
        }

        return block.getBlock().isLadder(block, e.world, pos, le);
    }
}
