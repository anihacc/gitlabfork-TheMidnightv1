package com.mushroom.midnight.common.json;

import com.google.gson.JsonObject;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.nio.file.Path;
import java.util.function.Consumer;

// TODO: Split up
public final class MidnightRecipeProvider extends RecipeProvider {
    public MidnightRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        this.addWoodRecipes(consumer);
        this.addStoneRecipes(consumer);
        this.addMaterialRecipes(consumer);
        this.addDecorativeRecipes(consumer);
        this.addSporeBombRecipes(consumer);
        this.addPlantRecipes(consumer);
        this.addMushroomPowderRecipes(consumer);
        this.addFoodRecipes(consumer);
        this.addStonecuttingRecipes(consumer);
    }

    private void addMaterialRecipes(Consumer<IFinishedRecipe> consumer) {
        new CommonRecipes(MidnightItems.ROCKSHROOM_CLUMP, consumer)
                .addHelmet(MidnightItems.ROCKSHROOM_HELMET)
                .addChestPlate(MidnightItems.ROCKSHROOM_CHESTPLATE)
                .addLeggings(MidnightItems.ROCKSHROOM_LEGGINGS)
                .addBoots(MidnightItems.ROCKSHROOM_BOOTS);

        new CommonRecipes(MidnightItems.NAGRILITE_INGOT, consumer)
                .addPressurePlate(MidnightBlocks.NAGRILITE_PRESSURE_PLATE)
                .addPickaxe(MidnightItems.NAGRILITE_PICKAXE)
                .addAxe(MidnightItems.NAGRILITE_AXE)
                .addSword(MidnightItems.NAGRILITE_SWORD)
                .addShovel(MidnightItems.NAGRILITE_SHOVEL)
                .addHoe(MidnightItems.NAGRILITE_HOE)
                .addStorageBlock(MidnightBlocks.NAGRILITE_BLOCK)
                .addNugget(MidnightItems.NAGRILITE_NUGGET);

        new CommonRecipes(MidnightItems.TENEBRUM_INGOT, consumer)
                .addDoor(MidnightBlocks.TENEBRUM_DOOR)
                .addPressurePlate(MidnightBlocks.TENEBRUM_PRESSURE_PLATE)
                .addTrapDoor(MidnightBlocks.TENEBRUM_TRAPDOOR)
                .addPickaxe(MidnightItems.TENEBRUM_PICKAXE)
                .addAxe(MidnightItems.TENEBRUM_AXE)
                .addSword(MidnightItems.TENEBRUM_SWORD)
                .addShovel(MidnightItems.TENEBRUM_SHOVEL)
                .addHoe(MidnightItems.TENEBRUM_HOE)
                .addHelmet(MidnightItems.TENEBRUM_HELMET)
                .addChestPlate(MidnightItems.TENEBRUM_CHESTPLATE)
                .addLeggings(MidnightItems.TENEBRUM_LEGGINGS)
                .addBoots(MidnightItems.TENEBRUM_BOOTS)
                .addStorageBlock(MidnightBlocks.TENEBRUM_BLOCK)
                .addNugget(MidnightItems.TENEBRUM_NUGGET);

        new CommonRecipes(MidnightItems.EBONITE, consumer)
                .addPickaxe(MidnightItems.EBONITE_PICKAXE)
                .addAxe(MidnightItems.EBONITE_AXE)
                .addSword(MidnightItems.EBONITE_SWORD)
                .addShovel(MidnightItems.EBONITE_SHOVEL)
                .addHoe(MidnightItems.EBONITE_HOE)
                .addStorageBlock(MidnightBlocks.EBONITE_BLOCK);

        new CommonRecipes(MidnightItems.DARK_PEARL, consumer)
                .addStorageBlock(MidnightBlocks.DARK_PEARL_BLOCK);

        new CommonCookingRecipes(consumer)
                .addIngot(MidnightBlocks.NAGRILITE_ORE, MidnightItems.NAGRILITE_INGOT)
                .addIngot(MidnightBlocks.TENEBRUM_ORE, MidnightItems.TENEBRUM_INGOT)
                .addIngot(MidnightBlocks.EBONITE_ORE, MidnightItems.EBONITE);
    }

    private void addStoneRecipes(Consumer<IFinishedRecipe> consumer) {
        new CommonRecipes(MidnightBlocks.NIGHTSTONE, consumer)
                .addButton(MidnightBlocks.NIGHTSTONE_BUTTON)
                .addPressurePlate(MidnightBlocks.NIGHTSTONE_PRESSURE_PLATE)
                .addStoneSlab(MidnightBlocks.NIGHTSTONE_SLAB)
                .addStoneStairs(MidnightBlocks.NIGHTSTONE_STAIRS)
                .addWall(MidnightBlocks.NIGHTSTONE_WALL)
                .addPickaxe(MidnightItems.NIGHTSTONE_PICKAXE)
                .addAxe(MidnightItems.NIGHTSTONE_AXE)
                .addSword(MidnightItems.NIGHTSTONE_SWORD)
                .addShovel(MidnightItems.NIGHTSTONE_SHOVEL)
                .addHoe(MidnightItems.NIGHTSTONE_HOE);

        new CommonRecipes(MidnightBlocks.NIGHTSTONE_BRICKS, consumer)
                .addStoneSlab(MidnightBlocks.NIGHTSTONE_BRICK_SLAB)
                .addStoneStairs(MidnightBlocks.NIGHTSTONE_BRICK_STAIRS)
                .addWall(MidnightBlocks.NIGHTSTONE_BRICK_WALL);

        new CommonRecipes(MidnightBlocks.TRENCHSTONE, consumer)
                .addButton(MidnightBlocks.TRENCHSTONE_BUTTON)
                .addPressurePlate(MidnightBlocks.TRENCHSTONE_PRESSURE_PLATE)
                .addStoneSlab(MidnightBlocks.TRENCHSTONE_SLAB)
                .addStoneStairs(MidnightBlocks.TRENCHSTONE_STAIRS)
                .addWall(MidnightBlocks.TRENCHSTONE_WALL);

        new CommonRecipes(MidnightBlocks.TRENCHSTONE_BRICKS, consumer)
                .addStoneSlab(MidnightBlocks.TRENCHSTONE_BRICK_SLAB)
                .addStoneStairs(MidnightBlocks.TRENCHSTONE_BRICK_STAIRS)
                .addWall(MidnightBlocks.TRENCHSTONE_BRICK_WALL);

        new CommonRecipes(MidnightBlocks.ROCKSHROOM_BRICKS, consumer)
                .addButton(MidnightBlocks.ROCKSHROOM_BRICK_BUTTON)
                .addPressurePlate(MidnightBlocks.ROCKSHROOM_BRICK_PRESSURE_PLATE)
                .addStoneSlab(MidnightBlocks.ROCKSHROOM_BRICK_SLAB)
                .addStoneStairs(MidnightBlocks.ROCKSHROOM_BRICK_STAIRS)
                .addWall(MidnightBlocks.ROCKSHROOM_BRICK_WALL);
    }

    private void addWoodRecipes(Consumer<IFinishedRecipe> consumer) {
        new CommonRecipes(MidnightBlocks.DARK_WILLOW_PLANKS, consumer)
                .addButton(MidnightBlocks.DARK_WILLOW_BUTTON)
                .addChest(MidnightBlocks.DARK_WILLOW_CHEST)
                .addCraftingTable(MidnightBlocks.DARK_WILLOW_CRAFTING_TABLE)
                .addDoor(MidnightBlocks.DARK_WILLOW_DOOR)
                .addFence(MidnightBlocks.DARK_WILLOW_FENCE)
                .addFenceGate(MidnightBlocks.DARK_WILLOW_FENCE_GATE)
                .addLadder(MidnightBlocks.DARK_WILLOW_LADDER)
                .addPressurePlate(MidnightBlocks.DARK_WILLOW_PRESSURE_PLATE)
                .addSlab(MidnightBlocks.DARK_WILLOW_SLAB)
                .addStairs(MidnightBlocks.DARK_WILLOW_STAIRS)
                .addTrapDoor(MidnightBlocks.DARK_WILLOW_TRAPDOOR);

        new CommonRecipes(MidnightBlocks.SHADOWROOT_PLANKS, consumer)
                .addButton(MidnightBlocks.SHADOWROOT_BUTTON)
                .addChest(MidnightBlocks.SHADOWROOT_CHEST)
                .addCraftingTable(MidnightBlocks.SHADOWROOT_CRAFTING_TABLE)
                .addDoor(MidnightBlocks.SHADOWROOT_DOOR)
                .addFence(MidnightBlocks.SHADOWROOT_FENCE)
                .addFenceGate(MidnightBlocks.SHADOWROOT_FENCE_GATE)
                .addLadder(MidnightBlocks.SHADOWROOT_LADDER)
                .addPressurePlate(MidnightBlocks.SHADOWROOT_PRESSURE_PLATE)
                .addSlab(MidnightBlocks.SHADOWROOT_SLAB)
                .addStairs(MidnightBlocks.SHADOWROOT_STAIRS)
                .addTrapDoor(MidnightBlocks.SHADOWROOT_TRAPDOOR)
                .addPickaxe(MidnightItems.SHADOWROOT_PICKAXE)
                .addAxe(MidnightItems.SHADOWROOT_AXE)
                .addSword(MidnightItems.SHADOWROOT_SWORD)
                .addShovel(MidnightItems.SHADOWROOT_SHOVEL)
                .addHoe(MidnightItems.SHADOWROOT_HOE);

        new CommonRecipes(MidnightBlocks.DEAD_WOOD_PLANKS, consumer)
                .addButton(MidnightBlocks.DEAD_WOOD_BUTTON)
                .addChest(MidnightBlocks.DEAD_WOOD_CHEST)
                .addCraftingTable(MidnightBlocks.DEAD_WOOD_CRAFTING_TABLE)
                .addDoor(MidnightBlocks.DEAD_WOOD_DOOR)
                .addFence(MidnightBlocks.DEAD_WOOD_FENCE)
                .addFenceGate(MidnightBlocks.DEAD_WOOD_FENCE_GATE)
                .addLadder(MidnightBlocks.DEAD_WOOD_LADDER)
                .addPressurePlate(MidnightBlocks.DEAD_WOOD_PRESSURE_PLATE)
                .addSlab(MidnightBlocks.DEAD_WOOD_SLAB)
                .addStairs(MidnightBlocks.DEAD_WOOD_STAIRS)
                .addTrapDoor(MidnightBlocks.DEAD_WOOD_TRAPDOOR);

        new CommonRecipes(MidnightBlocks.NIGHTSHROOM_PLANKS, consumer)
                .addButton(MidnightBlocks.NIGHTSHROOM_BUTTON)
                .addChest(MidnightBlocks.NIGHTSHROOM_CHEST)
                .addCraftingTable(MidnightBlocks.NIGHTSHROOM_CRAFTING_TABLE)
                .addDoor(MidnightBlocks.NIGHTSHROOM_DOOR)
                .addFence(MidnightBlocks.NIGHTSHROOM_FENCE)
                .addFenceGate(MidnightBlocks.NIGHTSHROOM_FENCE_GATE)
                .addLadder(MidnightBlocks.NIGHTSHROOM_LADDER)
                .addPressurePlate(MidnightBlocks.NIGHTSHROOM_PRESSURE_PLATE)
                .addSlab(MidnightBlocks.NIGHTSHROOM_SLAB)
                .addStairs(MidnightBlocks.NIGHTSHROOM_STAIRS)
                .addTrapDoor(MidnightBlocks.NIGHTSHROOM_TRAPDOOR);

        new CommonRecipes(MidnightBlocks.DEWSHROOM_PLANKS, consumer)
                .addButton(MidnightBlocks.DEWSHROOM_BUTTON)
                .addChest(MidnightBlocks.DEWSHROOM_CHEST)
                .addCraftingTable(MidnightBlocks.DEWSHROOM_CRAFTING_TABLE)
                .addDoor(MidnightBlocks.DEWSHROOM_DOOR)
                .addFence(MidnightBlocks.DEWSHROOM_FENCE)
                .addFenceGate(MidnightBlocks.DEWSHROOM_FENCE_GATE)
                .addLadder(MidnightBlocks.DEWSHROOM_LADDER)
                .addPressurePlate(MidnightBlocks.DEWSHROOM_PRESSURE_PLATE)
                .addSlab(MidnightBlocks.DEWSHROOM_SLAB)
                .addStairs(MidnightBlocks.DEWSHROOM_STAIRS)
                .addTrapDoor(MidnightBlocks.DEWSHROOM_TRAPDOOR);

        new CommonRecipes(MidnightBlocks.VIRIDSHROOM_PLANKS, consumer)
                .addButton(MidnightBlocks.VIRIDSHROOM_BUTTON)
                .addChest(MidnightBlocks.VIRIDSHROOM_CHEST)
                .addCraftingTable(MidnightBlocks.VIRIDSHROOM_CRAFTING_TABLE)
                .addDoor(MidnightBlocks.VIRIDSHROOM_DOOR)
                .addFence(MidnightBlocks.VIRIDSHROOM_FENCE)
                .addFenceGate(MidnightBlocks.VIRIDSHROOM_FENCE_GATE)
                .addLadder(MidnightBlocks.VIRIDSHROOM_LADDER)
                .addPressurePlate(MidnightBlocks.VIRIDSHROOM_PRESSURE_PLATE)
                .addSlab(MidnightBlocks.VIRIDSHROOM_SLAB)
                .addStairs(MidnightBlocks.VIRIDSHROOM_STAIRS)
                .addTrapDoor(MidnightBlocks.VIRIDSHROOM_TRAPDOOR);

        new CommonRecipes(MidnightBlocks.DARK_WILLOW_LOG, consumer)
                .addPlanks(MidnightBlocks.DARK_WILLOW_PLANKS);

        new CommonRecipes(MidnightBlocks.SHADOWROOT_LOG, consumer)
                .addPlanks(MidnightBlocks.SHADOWROOT_PLANKS);

        new CommonRecipes(MidnightBlocks.DEAD_WOOD_LOG, consumer)
                .addPlanks(MidnightBlocks.DEAD_WOOD_PLANKS);

        new CommonRecipes(MidnightBlocks.NIGHTSHROOM_STEM, consumer)
                .addPlanks(MidnightBlocks.NIGHTSHROOM_PLANKS);

        new CommonRecipes(MidnightBlocks.DEWSHROOM_STEM, consumer)
                .addPlanks(MidnightBlocks.DEWSHROOM_PLANKS);

        new CommonRecipes(MidnightBlocks.VIRIDSHROOM_STEM, consumer)
                .addPlanks(MidnightBlocks.VIRIDSHROOM_PLANKS);
    }

    private void addDecorativeRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.ARCHAIC_GLASS, 8)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightItems.ARCHAIC_SHARD)
                .addCriterion("has_shards", CommonTriggers.hasItem(MidnightItems.ARCHAIC_SHARD))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.ARCHAIC_GLASS_PANE, 16)
                .patternLine("###")
                .patternLine("###")
                .key('#', MidnightBlocks.ARCHAIC_GLASS)
                .addCriterion("has_shards", CommonTriggers.hasItem(MidnightItems.ARCHAIC_SHARD))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.ROCKSHROOM)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightItems.ROCKSHROOM_CLUMP)
                .addCriterion("has_rockshroom_clump", CommonTriggers.hasItems(MidnightItems.ROCKSHROOM_CLUMP, MidnightBlocks.ROCKSHROOM))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.COARSE_DIRT, 4)
                .patternLine("#*")
                .patternLine("*#")
                .key('#', MidnightBlocks.DIRT)
                .key('*', MidnightBlocks.DECEITFUL_PEAT)
                .addCriterion("has_peat", CommonTriggers.hasItem(MidnightBlocks.DECEITFUL_PEAT))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.MIDNIGHT_LEVER)
                .patternLine("|")
                .patternLine("#")
                .key('#', MidnightBlocks.NIGHTSTONE)
                .key('|', MidnightTags.Items.STICKS)
                .addCriterion("has_nightstone", CommonTriggers.hasItem(MidnightBlocks.NIGHTSTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.NIGHTSTONE_FURNACE)
                .patternLine("###")
                .patternLine("# #")
                .patternLine("###")
                .key('#', MidnightBlocks.NIGHTSTONE)
                .addCriterion("has_nightstone", CommonTriggers.hasItem(MidnightBlocks.NIGHTSTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.BOGSHROOM_SPORCH, 2)
                .key('*', MidnightItems.BOGSHROOM_POWDER).key('|', MidnightTags.Items.STICKS)
                .patternLine("*").patternLine("|").setGroup("sporches")
                .addCriterion("has_powder", CommonTriggers.hasItem(MidnightItems.BOGSHROOM_POWDER))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.VIRIDSHROOM_SPORCH, 2)
                .key('*', MidnightItems.VIRIDSHROOM_POWDER).key('|', MidnightTags.Items.STICKS)
                .patternLine("*").patternLine("|").setGroup("sporches")
                .addCriterion("has_powder", CommonTriggers.hasItem(MidnightItems.VIRIDSHROOM_POWDER))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.DEWSHROOM_SPORCH, 2)
                .key('*', MidnightItems.DEWSHROOM_POWDER).key('|', MidnightTags.Items.STICKS)
                .patternLine("*").patternLine("|").setGroup("sporches")
                .addCriterion("has_powder", CommonTriggers.hasItem(MidnightItems.DEWSHROOM_POWDER))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.NIGHTSHROOM_SPORCH, 2)
                .key('*', MidnightItems.NIGHTSHROOM_POWDER).key('|', MidnightTags.Items.STICKS)
                .patternLine("*").patternLine("|").setGroup("sporches")
                .addCriterion("has_powder", CommonTriggers.hasItem(MidnightItems.NIGHTSHROOM_POWDER))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.NIGHTSTONE_BRICKS, 4)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightBlocks.NIGHTSTONE)
                .addCriterion("has_item", CommonTriggers.hasItem(MidnightBlocks.NIGHTSTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.CHISELED_NIGHTSTONE_BRICKS, 4)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightBlocks.NIGHTSTONE_BRICKS)
                .addCriterion("has_item", CommonTriggers.hasItem(MidnightBlocks.NIGHTSTONE_BRICKS))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.TRENCHSTONE_BRICKS, 4)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightBlocks.TRENCHSTONE)
                .addCriterion("has_item", CommonTriggers.hasItem(MidnightBlocks.TRENCHSTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.ROCKSHROOM_BRICKS, 4)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightBlocks.ROCKSHROOM)
                .addCriterion("has_item", CommonTriggers.hasItem(MidnightBlocks.ROCKSHROOM))
                .build(consumer);
    }

    private void addSporeBombRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(MidnightItems.BOGSHROOM_SPORE_BOMB)
                .patternLine(" # ")
                .patternLine("#o#")
                .patternLine(" # ")
                .key('#', MidnightItems.BOGSHROOM_POWDER)
                .key('o', MidnightItems.DARK_PEARL)
                .setGroup("spore_bombs")
                .addCriterion("has_powder", CommonTriggers.hasItem(MidnightItems.BOGSHROOM_POWDER))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightItems.VIRIDSHROOM_SPORE_BOMB)
                .patternLine(" # ")
                .patternLine("#o#")
                .patternLine(" # ")
                .key('#', MidnightItems.VIRIDSHROOM_POWDER)
                .key('o', MidnightItems.DARK_PEARL)
                .setGroup("spore_bombs")
                .addCriterion("has_powder", CommonTriggers.hasItem(MidnightItems.VIRIDSHROOM_POWDER))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightItems.DEWSHROOM_SPORE_BOMB)
                .patternLine(" # ")
                .patternLine("#o#")
                .patternLine(" # ")
                .key('#', MidnightItems.DEWSHROOM_POWDER)
                .key('o', MidnightItems.DARK_PEARL)
                .setGroup("spore_bombs")
                .addCriterion("has_powder", CommonTriggers.hasItem(MidnightItems.DEWSHROOM_POWDER))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightItems.NIGHTSHROOM_SPORE_BOMB)
                .patternLine(" # ")
                .patternLine("#o#")
                .patternLine(" # ")
                .key('#', MidnightItems.NIGHTSHROOM_POWDER)
                .key('o', MidnightItems.DARK_PEARL)
                .setGroup("spore_bombs")
                .addCriterion("has_powder", CommonTriggers.hasItem(MidnightItems.NIGHTSHROOM_POWDER))
                .build(consumer);
    }

    private void addMushroomPowderRecipes(Consumer<IFinishedRecipe> consumer) {
        // bogshroom

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.BOGSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.BOGSHROOM_HAT)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.BOGSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "bogshroom_powder_from_hat"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.BOGSHROOM_POWDER, 2)
                .addIngredient(MidnightBlocks.BOGSHROOM)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.BOGSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "bogshroom_powder_from_single"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.BOGSHROOM_POWDER, 3)
                .addIngredient(MidnightBlocks.BOGSHROOM_SHELF)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.BOGSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "bogshroom_powder_from_shelf"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.BOGSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.DOUBLE_BOGSHROOM)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.BOGSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "bogshroom_powder_from_double"));

        // dewshroom

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.DEWSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.DEWSHROOM_HAT)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.DEWSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "dewshroom_powder_from_hat"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.DEWSHROOM_POWDER, 2)
                .addIngredient(MidnightBlocks.DEWSHROOM)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.DEWSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "dewshroom_powder_from_single"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.DEWSHROOM_POWDER, 3)
                .addIngredient(MidnightBlocks.DEWSHROOM_SHELF)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.DEWSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "dewshroom_powder_from_shelf"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.DEWSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.DOUBLE_DEWSHROOM)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.DEWSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "dewshroom_powder_from_double"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.DEWSHROOM_POWDER, 2)
                .addIngredient(MidnightBlocks.DEWSHROOM_FLOWERING_ROOTS)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.DEWSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "dewshroom_powder_from_flowering_roots"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.DEWSHROOM_POWDER, 1)
                .addIngredient(MidnightBlocks.DEWSHROOM_ROOTS)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.DEWSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "dewshroom_powder_from_roots"));

        // nightshroom

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.NIGHTSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.NIGHTSHROOM_HAT)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.NIGHTSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "nightshroom_powder_from_hat"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.NIGHTSHROOM_POWDER, 2)
                .addIngredient(MidnightBlocks.NIGHTSHROOM)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.NIGHTSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "nightshroom_powder_from_single"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.NIGHTSHROOM_POWDER, 3)
                .addIngredient(MidnightBlocks.NIGHTSHROOM_SHELF)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.NIGHTSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "nightshroom_powder_from_shelf"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.NIGHTSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.DOUBLE_NIGHTSHROOM)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.NIGHTSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "nightshroom_powder_from_double"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.NIGHTSHROOM_POWDER, 2)
                .addIngredient(MidnightBlocks.NIGHTSHROOM_FLOWERING_ROOTS)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.NIGHTSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "nightshroom_powder_from_flowering_roots"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.NIGHTSHROOM_POWDER, 1)
                .addIngredient(MidnightBlocks.NIGHTSHROOM_ROOTS)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.NIGHTSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "nightshroom_powder_from_roots"));

        // viridshroom

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.VIRIDSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.VIRIDSHROOM_HAT)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.VIRIDSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "viridshroom_powder_from_hat"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.VIRIDSHROOM_POWDER, 2)
                .addIngredient(MidnightBlocks.VIRIDSHROOM)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.VIRIDSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "viridshroom_powder_from_single"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.VIRIDSHROOM_POWDER, 3)
                .addIngredient(MidnightBlocks.VIRIDSHROOM_SHELF)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.VIRIDSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "viridshroom_powder_from_shelf"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.VIRIDSHROOM_POWDER, 4)
                .addIngredient(MidnightBlocks.DOUBLE_VIRIDSHROOM)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.VIRIDSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "viridshroom_powder_from_double"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.VIRIDSHROOM_POWDER, 2)
                .addIngredient(MidnightBlocks.VIRIDSHROOM_FLOWERING_ROOTS)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.VIRIDSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "viridshroom_powder_from_flowering_roots"));

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.VIRIDSHROOM_POWDER, 1)
                .addIngredient(MidnightBlocks.VIRIDSHROOM_ROOTS)
                .addCriterion("has_fungi", CommonTriggers.hasItem(MidnightTags.Items.VIRIDSHROOM))
                .build(consumer, new ResourceLocation(Midnight.MODID, "viridshroom_powder_from_roots"));
    }

    private void addPlantRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.GLOB_FUNGUS_STEM)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightItems.GLOB_FUNGUS_HAND)
                .addCriterion("has_hand", CommonTriggers.hasItem(MidnightItems.GLOB_FUNGUS_HAND))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MidnightBlocks.SUAVIS)
                .patternLine("##")
                .patternLine("##")
                .key('#', MidnightItems.RAW_SUAVIS)
                .addCriterion("has_suavis", CommonTriggers.hasItem(MidnightItems.RAW_SUAVIS))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.BLADESHROOM_SPORES)
                .addIngredient(MidnightItems.BLADESHROOM_CAP)
                .addCriterion("has_cap", CommonTriggers.hasItem(MidnightItems.BLADESHROOM_CAP))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.UNSTABLE_SEEDS)
                .addIngredient(MidnightTags.Items.UNSTABLE_FRUITS)
                .addCriterion("has_fruit", CommonTriggers.hasItem(MidnightTags.Items.UNSTABLE_FRUITS))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(MidnightItems.RAW_SUAVIS, 4)
                .addIngredient(MidnightBlocks.SUAVIS)
                .addCriterion("has_suavis", CommonTriggers.hasItem(MidnightBlocks.SUAVIS))
                .build(consumer);
    }

    private void addFoodRecipes(Consumer<IFinishedRecipe> consumer) {
        new CommonCookingRecipes(consumer)
                .addFood(MidnightItems.HUNTER_WING, MidnightItems.COOKED_HUNTER_WING)
                .addFood(MidnightItems.RAW_STAG_FLANK, MidnightItems.COOKED_STAG_FLANK)
                .addFood(MidnightBlocks.STINGER_EGG, MidnightItems.COOKED_STINGER_EGG)
                .addFood(MidnightItems.RAW_SUAVIS, MidnightItems.COOKED_SUAVIS);
    }

    private void addStonecuttingRecipes(Consumer<IFinishedRecipe> consumer) {
        SingleItemRecipeBuilder.func_218648_a(Ingredient.fromItems(MidnightBlocks.NIGHTSTONE), MidnightBlocks.NIGHTSTONE_BRICKS)
                .func_218643_a("has_item", CommonTriggers.hasItem(MidnightBlocks.NIGHTSTONE))
                .func_218647_a(consumer, new ResourceLocation(Midnight.MODID, "nightstone_to_bricks"));

        SingleItemRecipeBuilder.func_218648_a(Ingredient.fromItems(MidnightBlocks.NIGHTSTONE), MidnightBlocks.CHISELED_NIGHTSTONE_BRICKS)
                .func_218643_a("has_item", CommonTriggers.hasItem(MidnightBlocks.NIGHTSTONE))
                .func_218647_a(consumer, new ResourceLocation(Midnight.MODID, "nightstone_to_chiseled_bricks"));

        SingleItemRecipeBuilder.func_218648_a(Ingredient.fromItems(MidnightBlocks.NIGHTSTONE_BRICKS), MidnightBlocks.CHISELED_NIGHTSTONE_BRICKS)
                .func_218643_a("has_item", CommonTriggers.hasItem(MidnightBlocks.NIGHTSTONE_BRICKS))
                .func_218647_a(consumer, new ResourceLocation(Midnight.MODID, "nightstone_bricks_to_chiseled_bricks"));

        SingleItemRecipeBuilder.func_218648_a(Ingredient.fromItems(MidnightBlocks.TRENCHSTONE), MidnightBlocks.TRENCHSTONE_BRICKS)
                .func_218643_a("has_item", CommonTriggers.hasItem(MidnightBlocks.TRENCHSTONE))
                .func_218647_a(consumer, new ResourceLocation(Midnight.MODID, "trenchstone_to_bricks"));

        SingleItemRecipeBuilder.func_218648_a(Ingredient.fromItems(MidnightBlocks.ROCKSHROOM), MidnightBlocks.ROCKSHROOM_BRICKS)
                .func_218643_a("has_item", CommonTriggers.hasItem(MidnightBlocks.ROCKSHROOM))
                .func_218647_a(consumer, new ResourceLocation(Midnight.MODID, "rockshroom_to_bricks"));
    }

    @Override
    protected void saveRecipeAdvancement(DirectoryCache cache, JsonObject advancementJson, Path path) {
        if (path.getFileName().toString().equals("root.json")) return;
        super.saveRecipeAdvancement(cache, advancementJson, path);
    }

    @Override
    public String getName() {
        return "Midnight Recipes";
    }
}
