package com.mushroom.midnight.common.world;

import com.google.common.collect.ImmutableSet;
import com.mushroom.midnight.common.biome.BiomeLayers;
import com.mushroom.midnight.common.registry.MidnightSurfaceBiomes;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.feature.structure.Structure;

import java.util.Set;

public class MidnightBiomeProvider extends BiomeProvider {
    private final BiomeLayers<Biome> layers;

    private static final Set<Biome> biomes = ImmutableSet.of(MidnightSurfaceBiomes.BLACK_RIDGE, MidnightSurfaceBiomes.VIGILANT_FOREST, MidnightSurfaceBiomes.DECEITFUL_BOG, MidnightSurfaceBiomes.FUNGI_FOREST, MidnightSurfaceBiomes.WARPED_FIELDS
            , MidnightSurfaceBiomes.CRYSTAL_SPIRES, MidnightSurfaceBiomes.NIGHT_PLAINS, MidnightSurfaceBiomes.OBSCURED_PLATEAU, MidnightSurfaceBiomes.PHANTASMAL_VALLEY, MidnightSurfaceBiomes.RUNEBUSH_GROVE, MidnightSurfaceBiomes.HILLY_VIGILANT_FOREST, MidnightSurfaceBiomes.HILLY_FUNGI_FOREST);


    public MidnightBiomeProvider(BiomeLayers<Biome> layers) {
        super(biomes);
        this.layers = layers;
    }


    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return this.layers.block.sample(x, y);
    }

/*
    @Override
    public Biome func_222365_c(int x, int y) {
        return this.layers.noise.sample(x, y);
    }
*/


    @Override
    public boolean hasStructure(Structure<?> structure) {
        return this.hasStructureCache.computeIfAbsent(structure, s -> {
            return MidnightSurfaceBiomes.allBiomes()
                    .anyMatch(biome -> biome.hasStructure(s));
        });
    }

    @Override
    public Set<BlockState> getSurfaceBlocks() {
        if (this.topBlocksCache.isEmpty()) {
            MidnightSurfaceBiomes.allBiomes().forEach(biome -> {
                this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
            });
        }
        return this.topBlocksCache;
    }
}
