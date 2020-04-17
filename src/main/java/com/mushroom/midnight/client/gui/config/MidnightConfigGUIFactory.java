package com.mushroom.midnight.client.gui.config;

import com.mushroom.midnight.common.config.MidnightConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

import java.util.function.BiFunction;

public class MidnightConfigGUIFactory implements BiFunction<Minecraft, Screen, Screen> {
    @Override
    public Screen apply(Minecraft mc, Screen previousScreen) {
        return new ConfigInterfaceScreen(previousScreen, MidnightConfig.MAIN_IFC.makeInterface(MidnightConfig.PROFILE));
    }
}
