package com.mushroom.midnight.common.json;

import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

// TODO: Possibly split into different types
public final class CommonRecipes {
    private final IItemProvider ingredient;

    private final Consumer<IFinishedRecipe> consumer;

    public CommonRecipes(IItemProvider ingredient, Consumer<IFinishedRecipe> consumer) {
        this.ingredient = ingredient;
        this.consumer = consumer;
    }

    public CommonRecipes addButton(IItemProvider button) {
        ShapelessRecipeBuilder.shapelessRecipe(button)
                .addIngredient(this.ingredient)
                .setGroup("midnight_buttons")
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addChest(IItemProvider chest) {
        ShapedRecipeBuilder.shapedRecipe(chest)
                .patternLine("###")
                .patternLine("# #")
                .patternLine("###")
                .key('#', this.ingredient)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addCraftingTable(IItemProvider craftingTable) {
        ShapedRecipeBuilder.shapedRecipe(craftingTable)
                .patternLine("##")
                .patternLine("##")
                .key('#', this.ingredient)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addDoor(IItemProvider door) {
        ShapedRecipeBuilder.shapedRecipe(door, 3)
                .patternLine("##")
                .patternLine("##")
                .patternLine("##")
                .key('#', this.ingredient)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .setGroup("midnight_doors")
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addFence(IItemProvider fence) {
        ShapedRecipeBuilder.shapedRecipe(fence, 3)
                .patternLine("#|#")
                .patternLine("#|#")
                .key('#', this.ingredient)
                .key('|', MidnightTags.Items.STICKS)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .setGroup("midnight_fences")
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addFenceGate(IItemProvider fenceGate) {
        ShapedRecipeBuilder.shapedRecipe(fenceGate)
                .patternLine("|#|")
                .patternLine("|#|")
                .key('#', this.ingredient)
                .key('|', MidnightTags.Items.STICKS)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .setGroup("midnight_fence_gates")
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addLadder(IItemProvider ladder) {
        ShapedRecipeBuilder.shapedRecipe(ladder, 3)
                .patternLine("| |")
                .patternLine("|#|")
                .patternLine("| |")
                .key('#', this.ingredient)
                .key('|', MidnightTags.Items.STICKS)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addPressurePlate(IItemProvider pressurePlate) {
        ShapedRecipeBuilder.shapedRecipe(pressurePlate)
                .patternLine("##")
                .key('#', this.ingredient)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addSlab(IItemProvider slab) {
        ShapedRecipeBuilder.shapedRecipe(slab, 6)
                .patternLine("###")
                .key('#', this.ingredient)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addStoneSlab(IItemProvider slab) {
        this.addSlab(slab);

        ResourceLocation id = ForgeRegistries.ITEMS.getKey(slab.asItem());
        SingleItemRecipeBuilder.func_218644_a(Ingredient.fromItems(this.ingredient), slab, 2)
                .func_218643_a("has_item", CommonTriggers.hasItem(this.ingredient))
                .func_218647_a(this.consumer, new ResourceLocation(id.getNamespace(), id.getPath() + "_stonecutting"));

        return this;
    }

    public CommonRecipes addStairs(IItemProvider stairs) {
        ShapedRecipeBuilder.shapedRecipe(stairs, 4)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .key('#', this.ingredient)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);

        return this;
    }

    public CommonRecipes addStoneStairs(IItemProvider stairs) {
        this.addStairs(stairs);

        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stairs.asItem());
        SingleItemRecipeBuilder.func_218648_a(Ingredient.fromItems(this.ingredient), stairs)
                .func_218643_a("has_item", CommonTriggers.hasItem(this.ingredient))
                .func_218647_a(this.consumer, new ResourceLocation(id.getNamespace(), id.getPath() + "_stonecutting"));

        return this;
    }

    public CommonRecipes addTrapDoor(IItemProvider trapDoor) {
        ShapedRecipeBuilder.shapedRecipe(trapDoor, 6)
                .patternLine("###")
                .patternLine("###")
                .key('#', this.ingredient)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addWall(IItemProvider wall) {
        ShapedRecipeBuilder.shapedRecipe(wall, 6)
                .patternLine("###")
                .patternLine("###")
                .key('#', this.ingredient)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);

        ResourceLocation id = ForgeRegistries.ITEMS.getKey(wall.asItem());

        SingleItemRecipeBuilder.func_218648_a(Ingredient.fromItems(this.ingredient), wall)
                .func_218643_a("has_item", CommonTriggers.hasItem(this.ingredient))
                .func_218647_a(this.consumer, new ResourceLocation(id.getNamespace(), id.getPath() + "_stonecutting"));

        return this;
    }

    public CommonRecipes addPickaxe(IItemProvider pickaxe) {
        ShapedRecipeBuilder.shapedRecipe(pickaxe)
                .patternLine("###")
                .patternLine(" | ")
                .patternLine(" | ")
                .key('#', this.ingredient)
                .key('|', MidnightTags.Items.STICKS)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addAxe(IItemProvider axe) {
        ShapedRecipeBuilder.shapedRecipe(axe)
                .patternLine("##")
                .patternLine("|#")
                .patternLine("| ")
                .key('#', this.ingredient)
                .key('|', MidnightTags.Items.STICKS)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addSword(IItemProvider sword) {
        ShapedRecipeBuilder.shapedRecipe(sword)
                .patternLine("#")
                .patternLine("#")
                .patternLine("|")
                .key('#', this.ingredient)
                .key('|', MidnightTags.Items.STICKS)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addShovel(IItemProvider shovel) {
        ShapedRecipeBuilder.shapedRecipe(shovel)
                .patternLine("#")
                .patternLine("|")
                .patternLine("|")
                .key('#', this.ingredient)
                .key('|', MidnightTags.Items.STICKS)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addHoe(IItemProvider hoe) {
        ShapedRecipeBuilder.shapedRecipe(hoe)
                .patternLine("##")
                .patternLine("| ")
                .patternLine("| ")
                .key('#', this.ingredient)
                .key('|', MidnightTags.Items.STICKS)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addHelmet(IItemProvider helmet) {
        ShapedRecipeBuilder.shapedRecipe(helmet)
                .patternLine("###")
                .patternLine("# #")
                .key('#', this.ingredient)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addChestPlate(IItemProvider chestPlate) {
        ShapedRecipeBuilder.shapedRecipe(chestPlate)
                .patternLine("# #")
                .patternLine("###")
                .patternLine("###")
                .key('#', this.ingredient)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addLeggings(IItemProvider leggings) {
        ShapedRecipeBuilder.shapedRecipe(leggings)
                .patternLine("###")
                .patternLine("# #")
                .patternLine("# #")
                .key('#', this.ingredient)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addBoots(IItemProvider boots) {
        ShapedRecipeBuilder.shapedRecipe(boots)
                .patternLine("# #")
                .patternLine("# #")
                .key('#', this.ingredient)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }

    public CommonRecipes addStorageBlock(IItemProvider block) {
        ShapedRecipeBuilder.shapedRecipe(block)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .key('#', this.ingredient)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);

        ResourceLocation id = ForgeRegistries.ITEMS.getKey(this.ingredient.asItem());

        ShapelessRecipeBuilder.shapelessRecipe(this.ingredient, 9)
                .addIngredient(block)
                .addCriterion("has_item", CommonTriggers.hasItem(block))
                .build(this.consumer, new ResourceLocation(id.getNamespace(), id.getPath() + "_from_block"));

        return this;
    }

    public CommonRecipes addNugget(IItemProvider nugget) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(nugget.asItem());

        ShapedRecipeBuilder.shapedRecipe(this.ingredient)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .key('#', nugget)
                .addCriterion("has_nugget", CommonTriggers.hasItem(nugget))
                .build(this.consumer, new ResourceLocation(id.getNamespace(), id.getPath() + "_from_nuggets"));

        ShapelessRecipeBuilder.shapelessRecipe(nugget, 9)
                .addIngredient(this.ingredient)
                .addCriterion("has_item", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);

        return this;
    }

    public CommonRecipes addPlanks(IItemProvider planks) {
        ShapelessRecipeBuilder.shapelessRecipe(planks, 4)
                .addIngredient(this.ingredient)
                .addCriterion("has_log", CommonTriggers.hasItem(this.ingredient))
                .build(this.consumer);
        return this;
    }
}
