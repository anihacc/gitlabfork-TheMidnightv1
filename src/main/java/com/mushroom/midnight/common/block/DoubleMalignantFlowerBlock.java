package com.mushroom.midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DoubleMalignantFlowerBlock extends MidnightDoublePlantBlock {
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.VERTICAL);

    public DoubleMalignantFlowerBlock(Properties properties) {
        super(properties, true);
        this.setDefaultState(this.stateContainer.getBaseState().with(HALF, DoubleBlockHalf.LOWER).with(FACING, Direction.UP));
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return Block.isOpaque(state.getCollisionShape(world, pos));
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

        BlockPos pos = context.getPos();
        BlockPos abovePos = pos.offset(facing);
        if (pos.getY() >= context.getWorld().getDimension().getHeight() - 1 || !context.getWorld().getBlockState(abovePos).isReplaceable(context)) {
            return null;
        }

        return this.getDefaultState().with(FACING, facing);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        world.setBlockState(pos.offset(state.get(FACING)), state.with(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.fillStateContainer(builder);
    }


    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}
