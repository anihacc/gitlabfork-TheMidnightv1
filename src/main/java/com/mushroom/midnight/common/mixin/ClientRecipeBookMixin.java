package com.mushroom.midnight.common.mixin;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mushroom.midnight.common.recipe.MidnightRecipeBookCategories;
import com.mushroom.midnight.common.registry.MidnightRecipeTypes;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.crafting.IRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin
{
    @SuppressWarnings("FieldMayBeFinal")
    @Shadow
    @Final
    private Map<RecipeBookCategories, List<RecipeList>> recipesByCategory = Maps.newHashMap();

    @SuppressWarnings("FieldMayBeFinal")
    @Shadow
    @Final
    private List<RecipeList> allRecipes = Lists.newArrayList();

    @Inject(at = @At("HEAD"), method = "newRecipeList(Lnet/minecraft/client/util/RecipeBookCategories;)Lnet/minecraft/client/gui/recipebook/RecipeList;", cancellable = true)
    private void newRecipeList(RecipeBookCategories categories, CallbackInfoReturnable<RecipeList> callback) {
        RecipeList list = newRecipeList(categories, allRecipes, recipesByCategory);
        if (list != null) {
            this.allRecipes.add(list);
            callback.setReturnValue(list);
        }
    }

    @Inject(at = @At("HEAD"), method = "getCategory(Lnet/minecraft/item/crafting/IRecipe;)Lnet/minecraft/client/util/RecipeBookCategories;", cancellable = true)
    private static void getCategory(IRecipe<?> recipe, CallbackInfoReturnable<RecipeBookCategories> callback) {
        RecipeBookCategories category = getRecipeCategory(recipe);
        if (category != null) {
            callback.setReturnValue(category);
        }
    }

    private static RecipeBookCategories getRecipeCategory(IRecipe<?> recipe) {
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

    private static RecipeList newRecipeList(RecipeBookCategories category, List<RecipeList> allRecipes, Map<RecipeBookCategories, List<RecipeList>> recsByCategory) {
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
