package com.mushroom.midnight.common.data.loot.builder;

import com.mushroom.midnight.common.data.loot.LootConsumer;
import net.minecraft.block.Block;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.storage.loot.AlternativesLootEntry;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.IRandomRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.functions.ExplosionDecay;

public final class FungiHatLootBuilder {
    private static final IRandomRange ONE = ConstantRange.of(1);

    private final IItemProvider powder;

    private FungiHatLootBuilder(IItemProvider powder) {
        this.powder = powder;
    }

    public static FungiHatLootBuilder ofPowder(IItemProvider powder) {
        return new FungiHatLootBuilder(powder);
    }

    public LootFactory into(LootConsumer consumer) {
        return blocks -> {
            for (Block block : blocks) {
                LootPool.Builder pool = LootPool.builder()
                        .addEntry(AlternativesLootEntry.func_216149_a(
                                ItemLootEntry.builder(block).acceptCondition(RandomChance.builder(0.5F)),
                                ItemLootEntry.builder(this.powder)
                        ))
                        .acceptFunction(ExplosionDecay.func_215863_b())
                        .rolls(ONE);

                consumer.accept(block, LootTable.builder().addLootPool(pool));
            }
        };
    }
}
