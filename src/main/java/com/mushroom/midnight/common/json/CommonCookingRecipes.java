package com.mushroom.midnight.common.json;

import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public final class CommonCookingRecipes {
    private final Consumer<IFinishedRecipe> consumer;

    public CommonCookingRecipes(Consumer<IFinishedRecipe> consumer) {
        this.consumer = consumer;
    }

    public CommonCookingRecipes addFood(IItemProvider raw, IItemProvider cooked) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(cooked.asItem());

        CookingRecipeBuilder.func_218629_c(Ingredient.fromItems(raw), cooked, 0.35F, 200)
                .func_218628_a("has_raw", CommonTriggers.hasItem(raw))
                .func_218630_a(this.consumer);

        CookingRecipeBuilder.func_218631_a(Ingredient.fromItems(raw), cooked, 0.35F, 100, IRecipeSerializer.SMOKING)
                .func_218628_a("has_raw", CommonTriggers.hasItem(raw))
                .func_218635_a(this.consumer, new ResourceLocation(id.getNamespace(), id.getPath() + "_from_smoking"));

        CookingRecipeBuilder.func_218631_a(Ingredient.fromItems(raw), cooked, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING)
                .func_218628_a("has_raw", CommonTriggers.hasItem(raw))
                .func_218635_a(this.consumer, new ResourceLocation(id.getNamespace(), id.getPath() + "_from_campfire_cooking"));

        return this;
    }

    public CommonCookingRecipes addIngot(IItemProvider ore, IItemProvider ingot) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(ingot.asItem());

        CookingRecipeBuilder.func_218629_c(Ingredient.fromItems(ore), ingot, 1.0F, 200)
                .func_218628_a("has_ore", CommonTriggers.hasItem(ore))
                .func_218630_a(this.consumer);

        CookingRecipeBuilder.func_218633_b(Ingredient.fromItems(ore), ingot, 1.0F, 100)
                .func_218628_a("has_ore", CommonTriggers.hasItem(ore))
                .func_218635_a(this.consumer, new ResourceLocation(id.getNamespace(), id.getPath() + "_from_blasting"));

        return this;
    }
}
