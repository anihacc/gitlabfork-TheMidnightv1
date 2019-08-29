package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class FungiInsideBlock extends AirBlock {
    public FungiInsideBlock(Properties properties) {
        super(properties.lightValue(14));
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (!this.hasHatNeighbor(world, currentPos)) {
            return Blocks.AIR.getDefaultState();
        }
        return state;
    }

    private boolean hasHatNeighbor(IWorld world, BlockPos pos) {
        for (Direction facing : Direction.values()) {
            if (world.getBlockState(pos.offset(facing)).isIn(MidnightTags.Blocks.FUNGI_HATS)) {
                return true;
            }
        }
        return false;
    }
}
