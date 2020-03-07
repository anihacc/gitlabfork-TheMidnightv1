package com.mushroom.midnight.common.block;

import net.minecraft.block.PaneBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MidnightGlassPaneBlock extends PaneBlock {
    public MidnightGlassPaneBlock() {
        super(Properties.create(Material.GLASS).sound(SoundType.GLASS).notSolid());
    }

    /*@Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }*/
}
