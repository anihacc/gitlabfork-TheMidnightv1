package com.mushroom.midnight.common.recipe;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.util.EnumUtil;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class MidnightRecipeBookCategories {
    public static final RecipeBookCategories FURNACE_SEARCH = EnumUtil.addEnum(RecipeBookCategories.class, "MIDNIGHT_FURNACE_SEARCH", new Class[]{ItemStack[].class}, (Object) new ItemStack[]{new ItemStack(Items.COMPASS)});
    public static final RecipeBookCategories FURNACE_FOOD = EnumUtil.addEnum(RecipeBookCategories.class, "MIDNIGHT_FURNACE_FOOD", new Class[]{ItemStack[].class}, (Object) new ItemStack[]{new ItemStack(MidnightItems.RAW_STAG_FLANK)});
    public static final RecipeBookCategories FURNACE_BLOCKS = EnumUtil.addEnum(RecipeBookCategories.class, "MIDNIGHT_FURNACE_BLOCKS", new Class[]{ItemStack[].class}, (Object) new ItemStack[]{new ItemStack(MidnightBlocks.NIGHTSTONE)});
    public static final RecipeBookCategories FURNACE_MISC = EnumUtil.addEnum(RecipeBookCategories.class, "MIDNIGHT_FURNACE_MISC", new Class[]{ItemStack[].class}, (Object) new ItemStack[]{new ItemStack(MidnightItems.MIASMA_BUCKET), new ItemStack(MidnightItems.TENEBRUM_INGOT)});

    public static void justLoadClass() {
    }

}
