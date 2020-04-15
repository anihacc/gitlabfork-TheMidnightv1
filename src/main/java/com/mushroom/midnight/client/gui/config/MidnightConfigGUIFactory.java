package com.mushroom.midnight.client.gui.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

import java.util.function.BiFunction;

public class MidnightConfigGUIFactory implements BiFunction<Minecraft, Screen, Screen> {
    @Override
    public Screen apply(Minecraft mc, Screen previousScreen) {
        return new MainConfigScreen(new StringTextComponent("Midnight Config"), previousScreen);
    }
}
