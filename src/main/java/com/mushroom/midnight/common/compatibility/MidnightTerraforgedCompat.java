package com.mushroom.midnight.common.compatibility;

import com.mushroom.midnight.common.registry.MidnightStructures;
import com.terraforged.api.event.SetupEvent;
import com.terraforged.feature.matcher.biome.BiomeMatcher;
import com.terraforged.feature.matcher.feature.FeatureMatcher;
import com.terraforged.feature.transformer.FeatureInjector;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MidnightTerraforgedCompat {

    @SubscribeEvent
    public static void onTerraFeaturesSetup(SetupEvent.Features event) {

        FeatureInjector injector = new FeatureInjector(MidnightStructures.ENTRANCE_RIFT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)), FeatureInjector.Type.AFTER);

        event.getManager().getInjectors().add(BiomeMatcher.ANY, FeatureMatcher.ANY, injector);

    }
}
