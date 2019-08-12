package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class HangingLeavesBlock extends MidnightPlantBlock {
    private static final IntegerProperty LENGTH = IntegerProperty.create("length", 0, 3);
    private static final VoxelShape BOUNDS = makeCuboidShape(3.0, 3.0, 3.0, 13.0, 16.0, 13.0);

    public HangingLeavesBlock(Properties properties) {
        super(properties, false);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction face, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        int length = 0;
        if (facingState.getBlock() == this) {
            length = Math.min(facingState.get(LENGTH) + 1, 3);
        }
        return state.with(LENGTH, length);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return BOUNDS;
    }

    @Override
    public boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.isIn(BlockTags.LEAVES) || state.getBlock() == this;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos abovePos = pos.up();
        return this.isValidGround(world.getBlockState(abovePos), world, abovePos);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LENGTH);
    }
}
