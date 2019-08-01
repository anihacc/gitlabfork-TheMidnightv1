package com.mushroom.midnight.common.data.loot.builder;

import com.mushroom.midnight.common.data.loot.LootConsumer;
import net.minecraft.block.Block;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.IRandomRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedList;

public final class SimpleLootBuilder {
    private static final IRandomRange DEFAULT_ROLLS = ConstantRange.of(1);

    @Nullable
    private final IItemProvider drop;

    private final Collection<ILootCondition.IBuilder> conditions = new LinkedList<>();

    private IRandomRange rolls = DEFAULT_ROLLS;

    private SimpleLootBuilder(IItemProvider drop) {
        this.drop = drop;
    }

    public static SimpleLootBuilder dropsSelf() {
        return new SimpleLootBuilder(null);
    }

    public static SimpleLootBuilder drops(IItemProvider item) {
        return new SimpleLootBuilder(item);
    }

    public SimpleLootBuilder rolls(IRandomRange rolls) {
        this.rolls = rolls;
        return this;
    }

    public SimpleLootBuilder when(ILootCondition.IBuilder condition) {
        this.conditions.add(condition);
        return this;
    }

    public LootFactory into(LootConsumer consumer) {
        return blocks -> {
            for (Block block : blocks) {
                IItemProvider drop = SimpleLootBuilder.this.resolveDrop(block);

                LootPool.Builder pool = LootPool.builder()
                        .addEntry(ItemLootEntry.builder(drop))
                        .rolls(SimpleLootBuilder.this.rolls);

                SimpleLootBuilder.this.conditions.forEach(pool::acceptCondition);

                consumer.accept(block, LootTable.builder().addLootPool(pool));
            }
        };
    }

    private IItemProvider resolveDrop(Block block) {
        return this.drop != null ? this.drop : block;
    }
}
