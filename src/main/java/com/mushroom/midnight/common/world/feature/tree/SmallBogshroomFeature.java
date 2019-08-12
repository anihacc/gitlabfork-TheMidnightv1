package com.mushroom.midnight.common.world.feature.tree;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.IPlantable;

import java.util.function.Function;

public class SmallBogshroomFeature extends TemplateFungiFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "mushroom/bogshroom_small"),
    };

    private static final BlockState STEM = MidnightBlocks.BOGSHROOM_STEM.getDefaultState();
    private static final BlockState HAT = MidnightBlocks.BOGSHROOM_HAT.getDefaultState();

    public SmallBogshroomFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize, TEMPLATES, STEM, HAT, new BlockState[0]);
        this.setSapling((IPlantable) MidnightBlocks.BOGSHROOM);
    }
}
