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
    private final BlockState state;

    public HangingLeavesAttachProcessor(int attachChance, BlockState state) {
        this.attachChance = attachChance;
        this.state = state;
    }

    @Override
    public void process(CompiledTemplate template, IWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.isIn(BlockTags.LEAVES) && random.nextInt(this.attachChance) == 0) {
            BlockPos attachPos = pos.down();

            if (world.isAirBlock(attachPos)) {
                int flags = Constants.BlockFlags.NOTIFY_LISTENERS | Constants.BlockFlags.UPDATE_NEIGHBORS;

                world.setBlockState(attachPos, this.state.with(HangingLeavesBlock.IS_BASE, true), flags);
                if (random.nextInt(2) == 0) {
                    world.setBlockState(attachPos.down(), this.state.with(HangingLeavesBlock.IS_TIP, true), flags);
                }
            }
        }
    }
}
