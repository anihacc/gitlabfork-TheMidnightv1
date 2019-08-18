package com.mushroom.midnight.common.world.template;

import com.mushroom.midnight.common.block.HangingLeavesBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class HangingLeavesAttachProcessor implements TemplatePostProcessor {
    private final int attachChance;
    private final BlockState baseState;
    private final BlockState tipState;

    public HangingLeavesAttachProcessor(int attachChance, BlockState state) {
        this.attachChance = attachChance;
        this.baseState = state
                .with(HangingLeavesBlock.IS_BASE, true)
                .with(HangingLeavesBlock.IS_TIP, false);
        this.tipState = state
                .with(HangingLeavesBlock.IS_BASE, false)
                .with(HangingLeavesBlock.IS_TIP, true);
    }

    @Override
    public void process(CompiledTemplate template, IWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.isIn(BlockTags.LEAVES) && random.nextInt(this.attachChance) == 0) {
            BlockPos attachPos = pos.down();

            if (world.isAirBlock(attachPos)) {
                int flags = Constants.BlockFlags.NOTIFY_LISTENERS;

                world.setBlockState(attachPos, this.baseState, flags);
                if (random.nextInt(2) == 0) {
                    world.setBlockState(attachPos.down(), this.tipState, flags);
                }
            }
        }
    }
}
