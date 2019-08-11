package com.mushroom.midnight.common.effect;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class PollinatedEffect extends Effect {
    private static final BlockState TENDRILWEED = MidnightBlocks.TENDRILWEED.getDefaultState();

    public PollinatedEffect() {
        super(EffectType.BENEFICIAL, 0);
        // TODO provide an effect of glowing red trail
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        World world = entity.world;
        if (world.isRemote) return;

        BlockPos pos = entity.getPosition().add(
                world.rand.nextInt(3) - 1,
                world.rand.nextInt(3) - 1,
                world.rand.nextInt(3) - 1
        );

        BlockState state = world.getBlockState(pos);
        if (state.getBlock().getMaterial(state).isReplaceable() && TENDRILWEED.isValidPosition(world, pos)) {
            world.playEvent(Constants.WorldEvents.BONEMEAL_PARTICLES, pos, 0);
            world.setBlockState(pos, TENDRILWEED, Constants.BlockFlags.NOTIFY_NEIGHBORS | Constants.BlockFlags.NOTIFY_LISTENERS);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 30 == 0;
    }
}
