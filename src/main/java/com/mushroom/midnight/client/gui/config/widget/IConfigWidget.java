package com.mushroom.midnight.client.gui.config.widget;

import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.common.ForgeConfigSpec;

public interface IConfigWidget<T> {
    ForgeConfigSpec.ConfigValue<T> getConfigValue();

    void updatePos(int left, int top);

    Widget asWidget();
}
