package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.client.gui.CacheScreen;
import com.mushroom.midnight.client.gui.MidnightFurnaceScreen;
import com.mushroom.midnight.common.inventory.CacheContainer;
import com.mushroom.midnight.common.inventory.MidnightFurnaceContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static com.mushroom.midnight.Midnight.MODID;

@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MidnightContainers {
    public static ContainerType<CacheContainer> CACHE = new ContainerType<>(CacheContainer::new);
    public static ContainerType<MidnightFurnaceContainer> FURNACE = new ContainerType<>(MidnightFurnaceContainer::new);

    @SubscribeEvent
    public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(
                CACHE.setRegistryName(MODID, "cache"),
                FURNACE.setRegistryName(MODID, "furnace")
        );

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            ScreenManager.registerFactory(MidnightContainers.CACHE, CacheScreen::new);
            ScreenManager.registerFactory(MidnightContainers.FURNACE, MidnightFurnaceScreen::new);
        });
    }
}
