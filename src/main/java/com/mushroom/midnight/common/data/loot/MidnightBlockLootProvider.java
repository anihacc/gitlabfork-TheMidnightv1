package com.mushroom.midnight.common.data.loot;

import com.mushroom.midnight.common.data.loot.builder.DelicateLootBuilder;
import com.mushroom.midnight.common.data.loot.builder.FungiHatLootBuilder;
import com.mushroom.midnight.common.data.loot.builder.LootFactory;
import com.mushroom.midnight.common.data.loot.builder.SimpleLootBuilder;
import com.mushroom.midnight.common.data.loot.builder.SlabLootBuilder;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.storage.loot.LootParameterSets;

public final class MidnightBlockLootProvider extends MidnightLootTableProvider {
    public MidnightBlockLootProvider(DataGenerator generator) {
        super(generator, LootParameterSets.BLOCK);
    }

    @Override
    protected void addTables(LootConsumer consumer) {
        LootFactory dropSimple = SimpleLootBuilder.dropsSelf()
                .when(Conditions.SURVIVES_EXPLOSION)
                .into(consumer);

        dropSimple.apply(
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
                MidnightBlocks.EBONITE_ORE,
                MidnightBlocks.GLOB_FUNGUS_HAT
        );

        LootFactory dropIfSheared = SimpleLootBuilder.dropsSelf()
                .when(Conditions.HAS_SHEARS)
                .into(consumer);

        dropIfSheared.apply(
                MidnightBlocks.BOGWEED,
                MidnightBlocks.GHOST_PLANT,
                MidnightBlocks.FINGERED_GRASS,
                MidnightBlocks.GRASS,
                MidnightBlocks.TALL_GRASS
        );

        LootFactory dropIfSilkTouched = SimpleLootBuilder.dropsSelf()
                .when(Conditions.HAS_SILK_TOUCH)
                .into(consumer);

        dropIfSilkTouched.apply(
                MidnightBlocks.ARCHAIC_GLASS,
                MidnightBlocks.ARCHAIC_GLASS_PANE
        );

        LootFactory dropSlabs = SlabLootBuilder.INSTANCE.into(consumer);
        dropSlabs.apply(
                MidnightBlocks.SHADOWROOT_SLAB,
                MidnightBlocks.DEAD_WOOD_SLAB,
                MidnightBlocks.DARK_WILLOW_SLAB,
                MidnightBlocks.NIGHTSTONE_SLAB,
                MidnightBlocks.NIGHTSTONE_BRICK_SLAB,
                MidnightBlocks.TRENCHSTONE_SLAB,
                MidnightBlocks.TRENCHSTONE_BRICK_SLAB,
                MidnightBlocks.DEWSHROOM_SLAB,
                MidnightBlocks.VIRIDSHROOM_SLAB,
                MidnightBlocks.NIGHTSHROOM_SLAB,
                MidnightBlocks.ROCKSHROOM_BRICK_SLAB
        );

        FungiHatLootBuilder.ofPowder(MidnightItems.VIRIDSHROOM_POWDER).into(consumer)
                .apply(MidnightBlocks.VIRIDSHROOM_HAT);

        FungiHatLootBuilder.ofPowder(MidnightItems.NIGHTSHROOM_POWDER).into(consumer)
                .apply(MidnightBlocks.NIGHTSHROOM_HAT);

        FungiHatLootBuilder.ofPowder(MidnightItems.DEWSHROOM_POWDER).into(consumer)
                .apply(MidnightBlocks.DEWSHROOM_HAT);

        FungiHatLootBuilder.ofPowder(MidnightItems.BOGSHROOM_POWDER).into(consumer)
                .apply(MidnightBlocks.BOGSHROOM_HAT);

        DelicateLootBuilder.of(MidnightBlocks.GRASS_BLOCK, MidnightBlocks.DIRT).into(consumer)
                .apply(MidnightBlocks.GRASS_BLOCK);

        DelicateLootBuilder.of(MidnightBlocks.MYCELIUM, MidnightBlocks.NIGHTSTONE).into(consumer)
                .apply(MidnightBlocks.MYCELIUM);

        // MidnightBlocks.VIRIDSHROOM_STEM_CACHE,
        // MidnightBlocks.SHADOWROOT_LEAVES,
        // MidnightBlocks.DARK_WILLOW_LEAVES,
        // MidnightBlocks.DARK_PEARL_ORE,
        // MidnightBlocks.ARCHAIC_ORE,
        // MidnightBlocks.BLADESHROOM,
        // MidnightBlocks.SUAVIS,
        // MidnightBlocks.STINGER_EGG,
        // MidnightBlocks.UNSTABLE_BUSH,
        // MidnightBlocks.UNSTABLE_BUSH_BLUE_BLOOMED,
        // MidnightBlocks.UNSTABLE_BUSH_GREEN_BLOOMED,
        // MidnightBlocks.UNSTABLE_BUSH_LIME_BLOOMED,
    }
}
