package com.mushroom.midnight.common.world.tree;

import com.mushroom.midnight.common.registry.MidnightFeatures;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class ShadowrootTree extends MidnightTree {

    @Nullable
    @Override
    protected ConfiguredFeature<NoFeatureConfig, ?> getNewTreeFeature(Random randomIn, boolean p_225546_2_) {
        return MidnightFeatures.SHADOWROOT_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    }
}
