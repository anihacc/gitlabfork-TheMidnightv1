package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightEntities;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;

public class DeceitfulBogBiome extends SurfaceBiome {
    public DeceitfulBogBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.BOG, MidnightSurfaceBuilders.PEAT_CONFIG)
                .category(Category.SWAMP)
                .grassColor(0x554E70)
                .skyColor(0x1E1716)
                .depth(-1F)
                .scale(0.2F)
                .ridgeWeight(0.0F)
                .fog(0.1F, 80.0F)
        );

        MidnightBiomeConfigurator.addStructureFeatures(this);
        MidnightBiomeConfigurator.addGlobalOres(this);

        MidnightBiomeConfigurator.addLargeBogshrooms(this);

        MidnightBiomeConfigurator.addBogDeadTrees(this);

        MidnightBiomeConfigurator.addBogTrees(this);
        MidnightBiomeConfigurator.addDeadLogs(this);

        MidnightBiomeConfigurator.addBogweed(this);
        MidnightBiomeConfigurator.addBogshroomFlowers(this);
        MidnightBiomeConfigurator.addDewshroomFlowers(this);

        MidnightBiomeConfigurator.addAlgaeAndMoss(this);
        MidnightBiomeConfigurator.addGrasses(this);

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);

        this.add(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(MidnightEntities.DECEITFUL_SNAPPER, 100, 5, 10));
    }
}
