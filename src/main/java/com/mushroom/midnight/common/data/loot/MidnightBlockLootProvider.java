package com.mushroom.midnight.common.data.loot;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.IRandomRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.conditions.MatchTool;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public final class MidnightBlockLootProvider extends MidnightLootTableProvider {
    private final Map<Block, LootTable.Builder> lootTables = new HashMap<>();

    public MidnightBlockLootProvider(DataGenerator generator) {
        super(generator, LootParameterSets.BLOCK);
    }

    private static final ILootCondition.IBuilder SURVIVES_EXPLOSION = SurvivesExplosion.builder();

    private static final ILootCondition.IBuilder HAS_SILK_TOUCH = MatchTool.builder(
            ItemPredicate.Builder.create()
                    .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))
    );

    private static final ILootCondition.IBuilder HAS_SHEARS = MatchTool.builder(
            ItemPredicate.Builder.create().item(Items.SHEARS)
    );

    private static final TablePattern DROP_IF_SILK_TOUCHED = new TablePattern()
            .pool(p -> p
                    .dropsSelf()
                    .when(HAS_SILK_TOUCH)
            );

    private static final TablePattern DROP_IF_SHEARED = new TablePattern()
            .pool(p -> p
                    .dropsSelf()
                    .when(HAS_SHEARS)
            );

    private static final TablePattern DROP_IF_SURVIVES_EXPLOSION = new TablePattern()
            .pool(p -> p
                    .dropsSelf()
                    .when(SURVIVES_EXPLOSION)
            );

    @Override
    protected void addTables(BiConsumer<String, LootTable.Builder> consumer) {
        this.addAll(DROP_IF_SURVIVES_EXPLOSION,
                MidnightBlocks.SHADOWROOT_LOG,
                MidnightBlocks.SHADOWROOT_STRIPPED_LOG,
                MidnightBlocks.SHADOWROOT_PLANKS,
                MidnightBlocks.DEAD_WOOD_LOG,
                MidnightBlocks.DEAD_WOOD_STRIPPED_LOG,
                MidnightBlocks.DEAD_WOOD_PLANKS,
                MidnightBlocks.DARK_WILLOW_LOG,
                MidnightBlocks.DARK_WILLOW_STRIPPED_LOG,
                MidnightBlocks.DARK_WILLOW_PLANKS,
                MidnightBlocks.NIGHTSTONE,
                MidnightBlocks.NIGHTSTONE_BRICKS,
                MidnightBlocks.CHISELED_NIGHTSTONE_BRICKS,
                MidnightBlocks.TRENCHSTONE,
                MidnightBlocks.TRENCHSTONE_BRICKS,
                MidnightBlocks.DARK_PEARL_BLOCK,
                MidnightBlocks.TENEBRUM_BLOCK,
                MidnightBlocks.NAGRILITE_ORE,
                MidnightBlocks.NAGRILITE_BLOCK,
                MidnightBlocks.EBONITE_BLOCK,
                MidnightBlocks.SHADOWROOT_CRAFTING_TABLE,
                MidnightBlocks.DARK_WILLOW_CRAFTING_TABLE,
                MidnightBlocks.DEAD_WOOD_CRAFTING_TABLE,
                MidnightBlocks.NIGHTSHROOM_CRAFTING_TABLE,
                MidnightBlocks.DEWSHROOM_CRAFTING_TABLE,
                MidnightBlocks.VIRIDSHROOM_CRAFTING_TABLE,
                MidnightBlocks.COARSE_DIRT,
                MidnightBlocks.DIRT,
                MidnightBlocks.NIGHTSHROOM,
                MidnightBlocks.DOUBLE_NIGHTSHROOM,
                MidnightBlocks.NIGHTSHROOM_SHELF,
                MidnightBlocks.DEWSHROOM,
                MidnightBlocks.DOUBLE_DEWSHROOM,
                MidnightBlocks.DEWSHROOM_SHELF,
                MidnightBlocks.DEWSHROOM_PLANKS,
                MidnightBlocks.VIRIDSHROOM,
                MidnightBlocks.DOUBLE_VIRIDSHROOM,
                MidnightBlocks.VIRIDSHROOM_SHELF,
                MidnightBlocks.VIRIDSHROOM_PLANKS,
                MidnightBlocks.VIRIDSHROOM_STEM,
                MidnightBlocks.VIRIDSHROOM_ROOTS,
                MidnightBlocks.VIRIDSHROOM_FLOWERING_ROOTS,
                MidnightBlocks.NIGHTSHROOM_STEM,
                MidnightBlocks.NIGHTSHROOM_ROOTS,
                MidnightBlocks.NIGHTSHROOM_FLOWERING_ROOTS,
                MidnightBlocks.NIGHTSHROOM_PLANKS,
                MidnightBlocks.DEWSHROOM_STEM,
                MidnightBlocks.DEWSHROOM_ROOTS,
                MidnightBlocks.DEWSHROOM_FLOWERING_ROOTS,
                MidnightBlocks.BOGSHROOM,
                MidnightBlocks.DOUBLE_BOGSHROOM,
                MidnightBlocks.BOGSHROOM_SHELF,
                MidnightBlocks.BOGSHROOM_STEM,
                MidnightBlocks.GLOB_FUNGUS,
                MidnightBlocks.GLOB_FUNGUS_STEM,
                MidnightBlocks.ROCKSHROOM,
                MidnightBlocks.ROCKSHROOM_BRICKS,
                MidnightBlocks.LUMEN_BUD,
                MidnightBlocks.DOUBLE_LUMEN_BUD,
                MidnightBlocks.TENDRILWEED,
                MidnightBlocks.RUNEBUSH,
                MidnightBlocks.DRAGON_NEST,
                MidnightBlocks.VIOLEAF,
                MidnightBlocks.CRYSTAL_FLOWER,
                MidnightBlocks.SHADOWROOT_SAPLING,
                MidnightBlocks.DARK_WILLOW_SAPLING,
                MidnightBlocks.SHADOWROOT_DOOR,
                MidnightBlocks.DEAD_WOOD_DOOR,
                MidnightBlocks.DARK_WILLOW_DOOR,
                MidnightBlocks.TENEBRUM_DOOR,
                MidnightBlocks.NIGHTSHROOM_DOOR,
                MidnightBlocks.DEWSHROOM_DOOR,
                MidnightBlocks.VIRIDSHROOM_DOOR,
                MidnightBlocks.SHADOWROOT_TRAPDOOR,
                MidnightBlocks.DEAD_WOOD_TRAPDOOR,
                MidnightBlocks.DARK_WILLOW_TRAPDOOR,
                MidnightBlocks.TENEBRUM_TRAPDOOR,
                MidnightBlocks.NIGHTSHROOM_TRAPDOOR,
                MidnightBlocks.DEWSHROOM_TRAPDOOR,
                MidnightBlocks.VIRIDSHROOM_TRAPDOOR,
                MidnightBlocks.BLOOMCRYSTAL,
                MidnightBlocks.BLOOMCRYSTAL_ROCK,
                MidnightBlocks.ROUXE,
                MidnightBlocks.ROUXE_ROCK,
                MidnightBlocks.MIASMA_SURFACE,
                MidnightBlocks.DECEITFUL_PEAT,
                MidnightBlocks.DECEITFUL_MUD,
                MidnightBlocks.DECEITFUL_ALGAE,
                MidnightBlocks.DECEITFUL_MOSS,
                MidnightBlocks.SHADOWROOT_STAIRS,
                MidnightBlocks.DEAD_WOOD_STAIRS,
                MidnightBlocks.DARK_WILLOW_STAIRS,
                MidnightBlocks.NIGHTSTONE_STAIRS,
                MidnightBlocks.NIGHTSTONE_BRICK_STAIRS,
                MidnightBlocks.TRENCHSTONE_STAIRS,
                MidnightBlocks.TRENCHSTONE_BRICK_STAIRS,
                MidnightBlocks.DEWSHROOM_STAIRS,
                MidnightBlocks.VIRIDSHROOM_STAIRS,
                MidnightBlocks.NIGHTSHROOM_STAIRS,
                MidnightBlocks.ROCKSHROOM_BRICK_STAIRS,
                MidnightBlocks.SHADOWROOT_FENCE,
                MidnightBlocks.DEAD_WOOD_FENCE,
                MidnightBlocks.DARK_WILLOW_FENCE,
                MidnightBlocks.NIGHTSTONE_WALL,
                MidnightBlocks.NIGHTSTONE_BRICK_WALL,
                MidnightBlocks.TRENCHSTONE_WALL,
                MidnightBlocks.TRENCHSTONE_BRICK_WALL,
                MidnightBlocks.ROCKSHROOM_BRICK_WALL,
                MidnightBlocks.DEWSHROOM_FENCE,
                MidnightBlocks.VIRIDSHROOM_FENCE,
                MidnightBlocks.NIGHTSHROOM_FENCE,
                MidnightBlocks.SHADOWROOT_FENCE_GATE,
                MidnightBlocks.DEAD_WOOD_FENCE_GATE,
                MidnightBlocks.DARK_WILLOW_FENCE_GATE,
                MidnightBlocks.DEWSHROOM_FENCE_GATE,
                MidnightBlocks.VIRIDSHROOM_FENCE_GATE,
                MidnightBlocks.NIGHTSHROOM_FENCE_GATE,
                MidnightBlocks.SHADOWROOT_LADDER,
                MidnightBlocks.DEAD_WOOD_LADDER,
                MidnightBlocks.DARK_WILLOW_LADDER,
                MidnightBlocks.DEWSHROOM_LADDER,
                MidnightBlocks.VIRIDSHROOM_LADDER,
                MidnightBlocks.NIGHTSHROOM_LADDER,
                MidnightBlocks.CRYSTALOTUS,
                MidnightBlocks.SHADOWROOT_BUTTON,
                MidnightBlocks.DEAD_WOOD_BUTTON,
                MidnightBlocks.DARK_WILLOW_BUTTON,
                MidnightBlocks.DEWSHROOM_BUTTON,
                MidnightBlocks.VIRIDSHROOM_BUTTON,
                MidnightBlocks.NIGHTSHROOM_BUTTON,
                MidnightBlocks.NIGHTSTONE_BUTTON,
                MidnightBlocks.TRENCHSTONE_BUTTON,
                MidnightBlocks.ROCKSHROOM_BRICK_BUTTON,
                MidnightBlocks.SHADOWROOT_PRESSURE_PLATE,
                MidnightBlocks.DEAD_WOOD_PRESSURE_PLATE,
                MidnightBlocks.DARK_WILLOW_PRESSURE_PLATE,
                MidnightBlocks.DEWSHROOM_PRESSURE_PLATE,
                MidnightBlocks.VIRIDSHROOM_PRESSURE_PLATE,
                MidnightBlocks.NIGHTSHROOM_PRESSURE_PLATE,
                MidnightBlocks.NIGHTSTONE_PRESSURE_PLATE,
                MidnightBlocks.TRENCHSTONE_PRESSURE_PLATE,
                MidnightBlocks.ROCKSHROOM_BRICK_PRESSURE_PLATE,
                MidnightBlocks.NAGRILITE_PRESSURE_PLATE,
                MidnightBlocks.TENEBRUM_PRESSURE_PLATE,
                MidnightBlocks.MIDNIGHT_LEVER,
                MidnightBlocks.SHADOWROOT_CHEST,
                MidnightBlocks.DARK_WILLOW_CHEST,
                MidnightBlocks.DEAD_WOOD_CHEST,
                MidnightBlocks.NIGHTSHROOM_CHEST,
                MidnightBlocks.DEWSHROOM_CHEST,
                MidnightBlocks.VIRIDSHROOM_CHEST,
                MidnightBlocks.NIGHTSTONE_FURNACE,
                MidnightBlocks.BOGSHROOM_SPORCH,
                MidnightBlocks.NIGHTSHROOM_SPORCH,
                MidnightBlocks.DEWSHROOM_SPORCH,
                MidnightBlocks.VIRIDSHROOM_SPORCH,
                MidnightBlocks.TENEBRUM_ORE,
                MidnightBlocks.EBONITE_ORE
        );

        this.addAll(DROP_IF_SHEARED,
                MidnightBlocks.BOGWEED,
                MidnightBlocks.GHOST_PLANT,
                MidnightBlocks.FINGERED_GRASS,
                MidnightBlocks.GRASS,
                MidnightBlocks.TALL_GRASS
        );

        this.addAll(DROP_IF_SILK_TOUCHED,
                MidnightBlocks.ARCHAIC_GLASS,
                MidnightBlocks.ARCHAIC_GLASS_PANE
        );

        // MidnightBlocks.VIRIDSHROOM_HAT,
        // MidnightBlocks.NIGHTSHROOM_HAT,
        // MidnightBlocks.DEWSHROOM_HAT,
        // MidnightBlocks.BOGSHROOM_HAT,
        // MidnightBlocks.GLOB_FUNGUS_HAT,
        // MidnightBlocks.VIRIDSHROOM_STEM_CACHE,
        // MidnightBlocks.SHADOWROOT_LEAVES,
        // MidnightBlocks.DARK_WILLOW_LEAVES,
        // MidnightBlocks.DARK_PEARL_ORE,
        // MidnightBlocks.ARCHAIC_ORE,
        // MidnightBlocks.GRASS_BLOCK,
        // MidnightBlocks.MYCELIUM,
        // MidnightBlocks.BLADESHROOM,
        // MidnightBlocks.SHADOWROOT_SLAB,
        // MidnightBlocks.DEAD_WOOD_SLAB,
        // MidnightBlocks.DARK_WILLOW_SLAB,
        // MidnightBlocks.NIGHTSTONE_SLAB,
        // MidnightBlocks.NIGHTSTONE_BRICK_SLAB,
        // MidnightBlocks.TRENCHSTONE_SLAB,
        // MidnightBlocks.TRENCHSTONE_BRICK_SLAB,
        // MidnightBlocks.DEWSHROOM_SLAB,
        // MidnightBlocks.VIRIDSHROOM_SLAB,
        // MidnightBlocks.NIGHTSHROOM_SLAB,
        // MidnightBlocks.ROCKSHROOM_BRICK_SLAB,
        // MidnightBlocks.SUAVIS,
        // MidnightBlocks.STINGER_EGG,
        // MidnightBlocks.UNSTABLE_BUSH,
        // MidnightBlocks.UNSTABLE_BUSH_BLUE_BLOOMED,
        // MidnightBlocks.UNSTABLE_BUSH_GREEN_BLOOMED,
        // MidnightBlocks.UNSTABLE_BUSH_LIME_BLOOMED,

        this.lootTables.forEach((block, builder) -> {
            consumer.accept(block.getRegistryName().getPath(), builder);
        });
    }

    private void add(Block block, TablePattern pattern) {
        this.lootTables.put(block, pattern.build(block));
    }

    private void addAll(TablePattern pattern, Block... blocks) {
        for (Block block : blocks) {
            this.lootTables.put(block, pattern.build(block));
        }
    }

    private static class TablePattern {
        private final LinkedList<PoolPattern> pools = new LinkedList<>();

        TablePattern pool(Consumer<PoolPattern> builder) {
            PoolPattern pattern = new PoolPattern();
            builder.accept(pattern);

            this.pools.add(pattern);
            return this;
        }

        LootTable.Builder build(Block block) {
            LootTable.Builder table = LootTable.builder();

            Stream<LootPool.Builder> pools = this.pools.stream()
                    .map(pattern -> pattern.build(block));

            pools.forEach(table::addLootPool);

            return table;
        }
    }

    private static class PoolPattern {
        private final LinkedList<Function<Block, LootEntry.Builder<?>>> entries = new LinkedList<>();
        private final LinkedList<ILootCondition.IBuilder> conditions = new LinkedList<>();

        private IRandomRange rolls = ConstantRange.of(1);

        PoolPattern dropsSelf() {
            return this.entry(ItemLootEntry::builder);
        }

        PoolPattern drops(IItemProvider item) {
            return this.entry(block -> ItemLootEntry.builder(item));
        }

        PoolPattern entry(Function<Block, LootEntry.Builder<?>> entry) {
            this.entries.add(entry);
            return this;
        }

        PoolPattern when(ILootCondition.IBuilder condition) {
            this.conditions.add(condition);
            return this;
        }

        PoolPattern rolls(IRandomRange rolls) {
            this.rolls = rolls;
            return this;
        }

        LootPool.Builder build(Block block) {
            LootPool.Builder pool = LootPool.builder()
                    .rolls(this.rolls);

            Stream<LootEntry.Builder<?>> entries = this.entries.stream()
                    .map(entry -> entry.apply(block));
            entries.forEach(pool::addEntry);

            this.conditions.forEach(pool::acceptCondition);

            return pool;
        }
    }
}
