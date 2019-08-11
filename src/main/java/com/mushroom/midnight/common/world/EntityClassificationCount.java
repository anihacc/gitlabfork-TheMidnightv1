package com.mushroom.midnight.common.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.server.ServerWorld;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public class EntityClassificationCount {
    private final int[] counts;

    private EntityClassificationCount(int[] counts) {
        this.counts = counts;
    }

    public int getCount(EntityClassification classification) {
        int ordinal = classification.ordinal();
        if (ordinal >= this.counts.length) {
            return 0;
        }
        return this.counts[ordinal];
    }

    public static EntityClassificationCount count(ServerWorld world) {
        return count(world, Arrays.asList(EntityClassification.values()));
    }

    public static EntityClassificationCount count(ServerWorld world, Collection<EntityClassification> classifications) {
        return count(world, classifications, entity -> true);
    }

    public static EntityClassificationCount count(ServerWorld world, Collection<EntityClassification> classifications, Predicate<Entity> predicate) {
        int[] counts = new int[EntityClassification.values().length];

        world.getEntities().forEach(entity -> {
            EntityClassification currentClassification = entity.getClassification(true);
            for (EntityClassification entityClassification : classifications) {
                if (currentClassification == entityClassification) {
                    counts[entityClassification.ordinal()] += 1;
                    break;
                }
            }
        });

        return new EntityClassificationCount(counts);
    }
}
