package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.MidnightDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.BiFunction;

@Mod.EventBusSubscriber(modid = Midnight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MidnightDimensions {
    public static final ModDimension MIDNIGHT = new ModDimension() {
        @Override
        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return MidnightDimension::new;
        }
    };

    private static final ResourceLocation MIDNIGHT_ID = new ResourceLocation(Midnight.MODID, "midnight");

    @SubscribeEvent
    public static void registerModDimensions(RegistryEvent.Register<ModDimension> event) {
        RegUtil.generic(event.getRegistry()).add("midnight", MIDNIGHT);
    }

    public static void registerDimensions(RegisterDimensionsEvent event) {
        if (DimensionType.byName(MIDNIGHT_ID) == null) {
            DimensionManager.registerDimension(MIDNIGHT_ID, MIDNIGHT, null, false);
        }
    }

    public static DimensionType midnight() {
        return DimensionType.byName(MIDNIGHT_ID);
    }
}
