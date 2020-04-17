package com.mushroom.midnight.common.config.ifc;

import com.mushroom.midnight.client.gui.config.widget.IConfigWidget;
import com.mushroom.midnight.common.config.provider.IConfigValue;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ToggleButtonControl implements IConfigControlType<Boolean> {
    @Override
    public IConfigWidget<Boolean> createConfigWidget(IConfigValue<Boolean> configValue) {
        return new CfgWidget(configValue);
    }

    @OnlyIn(Dist.CLIENT)
    private static class CfgWidget implements IConfigWidget<Boolean> {
        private final IConfigValue<Boolean> configValue;
        private final Button button;

        private CfgWidget(IConfigValue<Boolean> configValue) {
            this.configValue = configValue;
            button = new Button(0, 0, 150, 20, text(), btn -> {
                configValue.set(!configValue.get());
                btn.setMessage(text());
            });
        }

        private String text() {
            return I18n.format(configValue.get() ? "options.on" : "options.off");
        }

        @Override
        public IConfigValue<Boolean> getConfigValue() {
            return configValue;
        }

        @Override
        public Widget asWidget() {
            return button;
        }
    }
}
