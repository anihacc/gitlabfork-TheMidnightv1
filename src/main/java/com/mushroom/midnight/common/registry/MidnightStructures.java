package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.feature.structure.MoltenCraterStructure;
import com.mushroom.midnight.common.world.feature.structure.ShadowrootGuardTowerStructure;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Midnight.MODID)
public class MidnightStructures {
    public static final Structure<NoFeatureConfig> SHADOWROOT_GUARDTOWER = new ShadowrootGuardTowerStructure(NoFeatureConfig::deserialize);
    public static final Structure<NoFeatureConfig> MOLTEN_CRATER = new MoltenCraterStructure(NoFeatureConfig::deserialize);

    @SubscribeEvent
    public static void registerStructures(IForgeRegistry<Feature<?>> event) {
        RegUtil.generic(event)
                .add("shadowroot_guardtower", SHADOWROOT_GUARDTOWER)
                .add("molten_crater", MOLTEN_CRATER);
    }
}
