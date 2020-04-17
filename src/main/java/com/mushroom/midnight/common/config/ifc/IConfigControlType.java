package com.mushroom.midnight.common.config.ifc;

import com.mushroom.midnight.client.gui.config.widget.IConfigWidget;
import com.mushroom.midnight.common.config.provider.IConfigValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IConfigControlType<T> {
    @OnlyIn(Dist.CLIENT)
    IConfigWidget<T> createConfigWidget(IConfigValue<T> configValue);
}
