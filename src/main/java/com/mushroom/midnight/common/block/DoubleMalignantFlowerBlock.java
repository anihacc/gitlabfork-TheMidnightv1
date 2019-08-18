package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.util.DirectionalShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class DoubleMalignantFlowerBlock extends MidnightDoublePlantBlock {
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.VERTICAL);
    private static final DirectionalShape SHAPE = new DirectionalShape(2d, 0d, 2d, 14d, 13d, 14d);

    public DoubleMalignantFlowerBlock(Properties properties) {
        super(properties, true);
        this.setDefaultState(this.stateContainer.getBaseState().with(HALF, DoubleBlockHalf.LOWER).with(FACING, Direction.UP));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE.get(state.get(FACING));
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            return this.isValidBasePosition(state, world, pos);
        }

        Direction facing = state.get(FACING);
        BlockState downState = world.getBlockState(pos.offset(facing.getOpposite()));
        if (state.getBlock() != this) return this.isValidBasePosition(state, world, pos);

        return downState.getBlock() == this && downState.get(HALF) == DoubleBlockHalf.LOWER;
    }

    private boolean isValidBasePosition(BlockState state, IWorldReader world, BlockPos pos) {
        Direction facing = state.get(FACING);
        BlockPos attachedPos = pos.offset(facing.getOpposite());
        return this.isValidGround(world.getBlockState(attachedPos), world, attachedPos);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction facing = context.getFace();
        if (facing.getAxis() != Direction.Axis.Y) {
            facing = Direction.UP;
        }

        BlockState state = super.getStateForPlacement(context);
        if (state == null) return null;

        return state.with(FACING, facing);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.fillStateContainer(builder);
    }
}
