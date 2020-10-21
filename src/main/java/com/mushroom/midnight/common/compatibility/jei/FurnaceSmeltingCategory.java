package com.mushroom.midnight.common.compatibility.jei;

import com.mushroom.midnight.common.recipe.MidnightFurnaceRecipe;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.util.ResourceLocation;

public class FurnaceSmeltingCategory extends AbstractCookingCategory<MidnightFurnaceRecipe> {
    public static final ResourceLocation ID = new ResourceLocation("midnight", "furnace");

    public FurnaceSmeltingCategory(IGuiHelper guiHelper) {
        super(guiHelper, MidnightBlocks.NIGHTSTONE_FURNACE, "gui.midnight.category.smelting", 200);
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public Class<? extends MidnightFurnaceRecipe> getRecipeClass() {
        return MidnightFurnaceRecipe.class;
    }
}
