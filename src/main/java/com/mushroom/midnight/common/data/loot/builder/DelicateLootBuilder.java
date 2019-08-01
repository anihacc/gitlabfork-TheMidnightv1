package com.mushroom.midnight.common.data.loot.builder;

import com.mushroom.midnight.common.data.loot.Conditions;
import com.mushroom.midnight.common.data.loot.LootConsumer;
import net.minecraft.block.Block;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.storage.loot.AlternativesLootEntry;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.IRandomRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;

public final class DelicateLootBuilder {
    private static final IRandomRange ONE = ConstantRange.of(1);

    private final IItemProvider withSilk;
    private final IItemProvider withoutSilk;

    private DelicateLootBuilder(IItemProvider withSilk, IItemProvider withoutSilk) {
        this.withSilk = withSilk;
        this.withoutSilk = withoutSilk;
    }

    public static DelicateLootBuilder of(IItemProvider withSilk, IItemProvider withoutSilk) {
        return new DelicateLootBuilder(withSilk, withoutSilk);
    }

    public LootFactory into(LootConsumer consumer) {
        return blocks -> {
            for (Block block : blocks) {
                LootPool.Builder pool = LootPool.builder()
                        .addEntry(AlternativesLootEntry.func_216149_a(
                                ItemLootEntry.builder(this.withSilk).acceptCondition(Conditions.HAS_SILK_TOUCH),
                                ItemLootEntry.builder(this.withoutSilk).acceptCondition(Conditions.SURVIVES_EXPLOSION)
                        ))
                        .rolls(ONE);

                consumer.accept(block, LootTable.builder().addLootPool(pool));
            }
        };
    }
}
