package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.common.recipe.MidnightFurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;

public class MidnightRecipeTypes {
    public static final IRecipeType<MidnightFurnaceRecipe> SMELTING = IRecipeType.register("midnight:smelting");

    public static void init() { // Loads class
    }
}
