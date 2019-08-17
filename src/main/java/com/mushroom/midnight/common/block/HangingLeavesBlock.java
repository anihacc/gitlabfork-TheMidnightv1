package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class HangingLeavesBlock extends HangingVinesBlock {
    public static final BooleanProperty IS_TIP = BooleanProperty.create("is_tip");
    public static final BooleanProperty IS_BASE = BooleanProperty.create("is_base");

    public HangingLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction face, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        state = super.updatePostPlacement(state, face, facingState, world, currentPos, facingPos);
        if (state.getBlock() == this) {
            BlockState aboveState = world.getBlockState(currentPos.up());
            BlockState belowState = world.getBlockState(currentPos.down());
            return state
                    .with(IS_TIP, belowState.getBlock() != this)
                    .with(IS_BASE, aboveState.getBlock() != this);
        }

        return state;
    }

    @Override
    public boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        if (state.getBlock() == this) {
            return state.get(IS_BASE);
        }
        return super.isValidGround(state, world, pos);
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(IS_TIP, IS_BASE);
    }
}
