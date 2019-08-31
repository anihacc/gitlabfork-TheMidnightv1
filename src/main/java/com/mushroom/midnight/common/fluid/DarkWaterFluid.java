package com.mushroom.midnight.common.fluid;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightFluids;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidAttributes;

import java.util.Random;

public abstract class DarkWaterFluid extends WaterFluid {
    private static final ResourceLocation STILL_TEXTURE = new ResourceLocation(Midnight.MODID, "blocks/dark_water_still");
    private static final ResourceLocation FLOW_TEXTURE = new ResourceLocation(Midnight.MODID, "blocks/dark_water_flow");

    private static final FluidAttributes ATTRIBUTES = FluidAttributes.builder("midnight.dark_water", STILL_TEXTURE, FLOW_TEXTURE)
            .build();

    @Override
    public Fluid getFlowingFluid() {
        return MidnightFluids.FLOWING_DARK_WATER;
    }

    @Override
    public Fluid getStillFluid() {
        return MidnightFluids.DARK_WATER;
    }

    @Override
    public Item getFilledBucket() {
        return MidnightItems.DARK_WATER_BUCKET;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(World world, BlockPos pos, IFluidState state, Random random) {
    }

    @Override
    public BlockState getBlockState(IFluidState state) {
        return MidnightBlocks.DARK_WATER.getDefaultState().with(FlowingFluidBlock.LEVEL, getLevelFromState(state));
    }

    @Override
    public boolean isEquivalentTo(Fluid fluid) {
        return fluid.isIn(MidnightTags.Fluids.DARK_WATER);
    }

    @Override
    protected void flowInto(IWorld world, BlockPos intoPos, BlockState intoBlock, Direction direction, IFluidState state) {
        if (direction == Direction.DOWN) {
            IFluidState intoFluid = world.getFluidState(intoPos);
            if (intoFluid.isTagged(FluidTags.LAVA) || intoFluid.isTagged(MidnightTags.Fluids.MIASMA)) {
                this.mixInto(world, intoPos, MidnightBlocks.TRENCHSTONE.getDefaultState());
                return;
            }
        }

        super.flowInto(world, intoPos, intoBlock, direction, state);
    }

    private void mixInto(IWorld world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state, Constants.BlockFlags.NOTIFY_NEIGHBORS | Constants.BlockFlags.BLOCK_UPDATE);
        world.playEvent(1501, pos, 0);
    }

    @Override
    protected FluidAttributes createAttributes(Fluid p_createAttributes_1_) {
        return ATTRIBUTES;
    }

    public static class Flowing extends DarkWaterFluid {
        public Flowing() {
        }

        @Override
        protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> container) {
            super.fillStateContainer(container);
            container.add(LEVEL_1_8);
        }

        @Override
        public int getLevel(IFluidState state) {
            return state.get(LEVEL_1_8);
        }

        @Override
        public boolean isSource(IFluidState state) {
            return false;
        }
    }

    public static class Source extends DarkWaterFluid {
        public Source() {
        }

        @Override
        public int getLevel(IFluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(IFluidState state) {
            return true;
        }
    }
}
