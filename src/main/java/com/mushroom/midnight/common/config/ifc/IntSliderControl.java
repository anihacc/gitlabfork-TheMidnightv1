package com.mushroom.midnight.common.config.ifc;

import com.mushroom.midnight.client.gui.config.widget.IConfigWidget;
import com.mushroom.midnight.common.config.provider.IConfigValue;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;

public class IntSliderControl implements IConfigControlType<Integer> {
    private final int min;
    private final int max;
    private final Function<Integer, String> format;

    public IntSliderControl(int min, int max, String format, String minName, String maxName) {
        this.min = min;
        this.max = max;
        this.format = i -> i == min ? minName : i == max ? maxName : format;
    }

    public IntSliderControl(int min, int max, String format) {
        this.min = min;
        this.max = max;
        this.format = i -> format;
    }

    public IntSliderControl(int min, int max) {
        this(min, max, "config.midnight.format.number_basic");
    }

    @Override
    public IConfigWidget<Integer> createConfigWidget(IConfigValue<Integer> configValue) {
        return new CfgWidget(configValue, format, min, max);
    }

    @OnlyIn(Dist.CLIENT)
    private static class CfgWidget implements IConfigWidget<Integer> {
        private final IConfigValue<Integer> configValue;
        private final Slider slider;
        private final Function<Integer, String> format;
        private final int min;
        private final int max;

        private CfgWidget(IConfigValue<Integer> configValue, Function<Integer, String> format, int min, int max) {
            this.configValue = configValue;
            this.format = format;
            this.min = min;
            this.max = max;

            slider = new Slider(0, 0, 150, 20, fromRange(configValue.get()));
            slider.updateMessage(); // Update message because it's empty when we create it
        }

        private int toRange(double pct) {
            return (int) (pct * (max - min) + min);
        }

        private double fromRange(int val) {
            return (double) (val - min) / (max - min);
        }

        @Override
        public IConfigValue<Integer> getConfigValue() {
            return configValue;
        }

        @Override
        public Widget asWidget() {
            return slider;
        }

        @OnlyIn(Dist.CLIENT)
        private class Slider extends AbstractSlider {
            protected Slider(int x, int y, int width, int height, double value) {
                super(x, y, width, height, value);
            }

            @Override
            protected void updateMessage() {
                int val = toRange(value);
                setMessage(I18n.format(format.apply(val), val));
            }

            @Override
            protected void applyValue() {
                configValue.set(toRange(value));
            }
        }
    }
}
