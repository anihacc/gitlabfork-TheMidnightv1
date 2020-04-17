package com.mushroom.midnight.client.gui.config.widget;

import com.mushroom.midnight.common.config.provider.IConfigValue;
import net.minecraft.client.gui.widget.Widget;

public interface IConfigWidget<T> {
    IConfigValue<T> getConfigValue();

    Widget asWidget();
}
