package com.mushroom.midnight.common.config.ifc;

import com.mushroom.midnight.client.gui.config.widget.IConfigWidget;
import com.mushroom.midnight.common.config.provider.IConfigValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IntInputControl implements IConfigControlType<Integer> {
    private final int min;
    private final int max;

    public IntInputControl(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IConfigWidget<Integer> createConfigWidget(IConfigValue<Integer> configValue) {
        return new CfgWidget(configValue, min, max);
    }

    @OnlyIn(Dist.CLIENT)
    private static class CfgWidget implements IConfigWidget<Integer> {
        private final IConfigValue<Integer> configValue;
        private final TextFieldWidget input;
        private final int min;
        private final int max;

        private CfgWidget(IConfigValue<Integer> configValue, int min, int max) {
            this.configValue = configValue;
            this.min = min;
            this.max = max;

            input = new TextFieldWidget(Minecraft.getInstance().fontRenderer, 0, 0, 150, 20, "");
            input.setText(configValue.get() + "");
            input.setResponder(this::handleInput);
        }

        private static final int RED_COLOR = 0xFF5555;
        private static final int AQUA_COLOR = 0x55FFFF;

        private void handleInput(String value) {
            Integer parsed = lazyParse(value);
            input.setTextColor(AQUA_COLOR);
            if (parsed == null) {
                input.setTextColor(RED_COLOR);
            } else {
                configValue.set(parsed);
            }
        }

        private Integer lazyParse(String value) {
            try {
                int i = Integer.parseInt(value);
                return i >= min && i <= max ? i : null;
            } catch (NumberFormatException exc) {
                return null;
            }
        }

        @Override
        public IConfigValue<Integer> getConfigValue() {
            return configValue;
        }

        @Override
        public Widget asWidget() {
            return input;
        }
    }
}
