package com.mushroom.midnight.common.data.loot.builder;

import com.mushroom.midnight.common.data.loot.LootConsumer;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.properties.SlabType;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.IRandomRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.conditions.BlockStateProperty;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.functions.ExplosionDecay;
import net.minecraft.world.storage.loot.functions.SetCount;

public final class SlabLootBuilder {
    public static final SlabLootBuilder INSTANCE = new SlabLootBuilder();

    private static final IRandomRange ONE = ConstantRange.of(1);
    private static final IRandomRange TWO = ConstantRange.of(2);

    private SlabLootBuilder() {
    }

    public LootFactory into(LootConsumer consumer) {
        return blocks -> {
            for (Block block : blocks) {
                ILootCondition.IBuilder isDouble = BlockStateProperty.builder(block).with(SlabBlock.TYPE, SlabType.DOUBLE);

                LootPool.Builder pool = LootPool.builder()
                        .addEntry(ItemLootEntry.builder(block))
                        .acceptFunction(SetCount.func_215932_a(TWO).acceptCondition(isDouble))
                        .acceptFunction(ExplosionDecay.func_215863_b())
                        .rolls(ONE);

                consumer.accept(block, LootTable.builder().addLootPool(pool));
            }
        };
    }
}
