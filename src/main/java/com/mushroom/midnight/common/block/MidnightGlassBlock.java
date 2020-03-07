package com.mushroom.midnight.common.block;

import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MidnightGlassBlock extends GlassBlock {
    public MidnightGlassBlock() {
        super(Properties.create(Material.GLASS).hardnessAndResistance(0.3f, 0f).sound(SoundType.GLASS).notSolid());
    }
}
