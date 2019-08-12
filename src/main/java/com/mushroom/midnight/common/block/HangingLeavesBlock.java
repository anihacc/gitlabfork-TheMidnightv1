package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class HangingLeavesBlock extends MidnightPlantBlock {
    private static final BooleanProperty IS_TIP = BooleanProperty.create("is_tip");
    private static final BooleanProperty IS_BASE = BooleanProperty.create("is_base");

    private static final VoxelShape BOUNDS = makeCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public HangingLeavesBlock(Properties properties) {
        super(properties, false);
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
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return BOUNDS;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader world, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        if (state.getBlock() == this) {
            return state.get(IS_BASE);
        }
        return Block.doesSideFillSquare(state.getCollisionShape(world, pos), Direction.DOWN);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos abovePos = pos.up();
        return this.isValidGround(world.getBlockState(abovePos), world, abovePos);
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(IS_TIP, IS_BASE);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}
