package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.RiftTraveller;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RiftPortalBlock extends Block {
    public RiftPortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        entity.getCapability(Midnight.RIFT_TRAVELLER_CAP).ifPresent(RiftTraveller::setInRift);
    }
}
