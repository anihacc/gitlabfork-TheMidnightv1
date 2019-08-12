package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.registry.MidnightCavernousBiomes;
import com.mushroom.midnight.common.util.SessionLocal;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.function.IntPredicate;

// TODO: Initialize along with GenLayers to avoid need for SessionLocal?
public final class BiomeSpawnEntry {
    private final SessionLocal<Integer> biomeId;
    private IntPredicate canReplace;

    private final int value;

    public BiomeSpawnEntry(Biome biome, int value) {
        this.biomeId = SessionLocal.register(() -> Registry.BIOME.getId(biome));
        this.value = value;
    }

    public BiomeSpawnEntry(CavernousBiome biome, int value) {
        this.biomeId = SessionLocal.register(() -> MidnightCavernousBiomes.getId(biome));
        this.value = value;
    }

    public BiomeSpawnEntry canReplace(IntPredicate predicate) {
        this.canReplace = predicate;
        return this;
    }

    public BiomeSpawnEntry canReplace(Biome... biomes) {
        SessionLocal<IntSet> biomeIds = SessionLocal.register(() -> {
            IntSet ids = new IntOpenHashSet();
            for (Biome biome : biomes) {
                ids.add(Registry.BIOME.getId(biome));
            }
            return ids;
        });

        this.canReplace = id -> biomeIds.get().contains(id);

        return this;
    }

    public BiomeSpawnEntry canReplace(CavernousBiome... biomes) {
        SessionLocal<IntSet> biomeIds = SessionLocal.register(() -> {
            IntSet ids = new IntOpenHashSet();
            for (CavernousBiome biome : biomes) {
                ids.add(MidnightCavernousBiomes.getId(biome));
            }
            return ids;
        });

        this.canReplace = id -> biomeIds.get().contains(id);

        return this;
    }

    public int getBiomeId() {
        return this.biomeId.get();
    }

    public boolean canReplace(int biome) {
        if (this.canReplace == null) {
            return true;
        }
        return this.canReplace.test(biome);
    }

    public int getValue() {
        return this.value;
    }
}
