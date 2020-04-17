package com.mushroom.midnight.client;

import com.google.common.collect.Lists;
import com.mushroom.midnight.common.recipe.MidnightRecipeBookCategories;
import com.mushroom.midnight.common.registry.MidnightRecipeTypes;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.crafting.IRecipe;

import java.util.List;
import java.util.Map;

public class RecipeHandler {
    public static RecipeBookCategories getRecipeCategory(IRecipe<?> recipe) {
        if (recipe.getType() == MidnightRecipeTypes.SMELTING) {
            if (recipe.getRecipeOutput().getItem().isFood()) {
                return MidnightRecipeBookCategories.FURNACE_FOOD;
            } else {
                return recipe.getRecipeOutput().getItem() instanceof BlockItem
                        ? MidnightRecipeBookCategories.FURNACE_BLOCKS
                        : MidnightRecipeBookCategories.FURNACE_MISC;
            }
        }

        return null;
    }

    public static RecipeList newRecipeList(RecipeBookCategories category, List<RecipeList> allRecipes, Map<RecipeBookCategories, List<RecipeList>> recsByCategory) {
        if (category == MidnightRecipeBookCategories.FURNACE_BLOCKS || category == MidnightRecipeBookCategories.FURNACE_MISC || category == MidnightRecipeBookCategories.FURNACE_FOOD) {
            RecipeList list = new RecipeList();
            allRecipes.add(list);
            recsByCategory.computeIfAbsent(category, cgr -> Lists.newArrayList()).add(list);
            recsByCategory.computeIfAbsent(MidnightRecipeBookCategories.FURNACE_SEARCH, cgr -> Lists.newArrayList()).add(list);
            return list;
        }

        return null;
    }
}
