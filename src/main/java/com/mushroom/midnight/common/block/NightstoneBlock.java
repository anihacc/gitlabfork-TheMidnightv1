package com.mushroom.midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class NightstoneBlock extends GrowableOnBlock {
    public NightstoneBlock(Properties properties) {
        super(properties, true);
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 0;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }
}
