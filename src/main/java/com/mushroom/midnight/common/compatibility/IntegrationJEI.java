package com.mushroom.midnight.common.compatibility;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.compatibility.jei.FurnaceSmeltingCategory;
import com.mushroom.midnight.common.recipe.MidnightFurnaceRecipe;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class IntegrationJEI implements IModPlugin {
    private final ResourceLocation rl = new ResourceLocation(Midnight.MODID);

    static {
        Midnight.LOGGER.info("Initializing The Midnight's JEI Integration.");
    }

    @Override
    public ResourceLocation getPluginUid() {
        return rl;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        registry.addRecipes(getNightstoneFurnaceRecipeList(), FurnaceSmeltingCategory.ID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
                new FurnaceSmeltingCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry)
    {
        registry.addRecipeCatalyst(new ItemStack(MidnightBlocks.NIGHTSTONE_FURNACE), FurnaceSmeltingCategory.ID);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
    }

    private static List<MidnightFurnaceRecipe> getNightstoneFurnaceRecipeList() {
        List<MidnightFurnaceRecipe> recipeList = new ArrayList<>();
        recipeList.add(new MidnightFurnaceRecipe(new ResourceLocation(MidnightItems.COOKED_HUNTER_WING.getItem().getRegistryName().getPath()),
                "midnight:furnace", Ingredient.fromItems(MidnightItems.HUNTER_WING), MidnightItems.COOKED_HUNTER_WING.getDefaultInstance(), 0.35F, 200));
        recipeList.add(new MidnightFurnaceRecipe(new ResourceLocation(MidnightItems.COOKED_STAG_FLANK.getItem().getRegistryName().getPath()),
                "midnight:furnace", Ingredient.fromItems(MidnightItems.RAW_STAG_FLANK), MidnightItems.COOKED_STAG_FLANK.getDefaultInstance(), 0.35F, 200));
        recipeList.add(new MidnightFurnaceRecipe(new ResourceLocation(MidnightItems.COOKED_STINGER_EGG.getItem().getRegistryName().getPath()),
                "midnight:furnace", Ingredient.fromItems(MidnightBlocks.STINGER_EGG), MidnightItems.COOKED_STINGER_EGG.getDefaultInstance(), 0.35F, 200));
        recipeList.add(new MidnightFurnaceRecipe(new ResourceLocation(MidnightItems.COOKED_SUAVIS.getItem().getRegistryName().getPath()),
                "midnight:furnace", Ingredient.fromItems(MidnightItems.RAW_SUAVIS), MidnightItems.COOKED_SUAVIS.getDefaultInstance(), 0.35F, 200));
        return recipeList;
    }
}
