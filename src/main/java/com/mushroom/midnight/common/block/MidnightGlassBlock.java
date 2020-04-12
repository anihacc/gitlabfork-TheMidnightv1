package com.mushroom.midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;

public class MidnightGlassBlock extends GlassBlock {
    public MidnightGlassBlock() {
        super(Properties.create(Material.GLASS).hardnessAndResistance(0.3f, 0f).sound(SoundType.GLASS).notSolid());
    }

    @Override
    public boolean shouldDisplayFluidOverlay(BlockState state, ILightReader world, BlockPos pos, IFluidState fluidState) {
        return true;
    }
}
