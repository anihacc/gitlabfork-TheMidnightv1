package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.common.recipe.MidnightCookingRecipeSerializer;
import com.mushroom.midnight.common.recipe.MidnightFurnaceRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static com.mushroom.midnight.Midnight.MODID;

@ObjectHolder("midnight")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MidnightRecipeSerializers {
    public static final MidnightCookingRecipeSerializer<MidnightFurnaceRecipe> SMELTING = RegUtil.injected();

    @SubscribeEvent
    public static void register(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        RegUtil.generic(event.getRegistry()).add("smelting", new MidnightCookingRecipeSerializer<>(MidnightFurnaceRecipe::new, 200));
    }
}
