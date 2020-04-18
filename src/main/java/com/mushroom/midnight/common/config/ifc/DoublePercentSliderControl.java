package com.mushroom.midnight.common.config.ifc;

import com.mushroom.midnight.client.gui.config.widget.IConfigWidget;
import com.mushroom.midnight.common.config.provider.IConfigValue;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;

public class DoublePercentSliderControl implements IConfigControlType<Double> {
    private final Function<Integer, String> format;

    public DoublePercentSliderControl(String format, String minName, String maxName) {
        this.format = i -> i == 0 ? minName : i == 100 ? maxName : format;
    }

    public DoublePercentSliderControl(String format) {
        this.format = i -> format;
    }

    public DoublePercentSliderControl() {
        this("config.midnight.format.number_basic");
    }

    @Override
    public IConfigWidget<Double> createConfigWidget(IConfigValue<Double> configValue) {
        return new CfgWidget(configValue, format);
    }

    @OnlyIn(Dist.CLIENT)
    private static class CfgWidget implements IConfigWidget<Double> {
        private final IConfigValue<Double> configValue;
        private final Slider slider;
        private final Function<Integer, String> format;

        private CfgWidget(IConfigValue<Double> configValue, Function<Integer, String> format) {
            this.configValue = configValue;
            this.format = format;

            slider = new Slider(0, 0, 150, 20, configValue.get());
            slider.updateMessage(); // Update message because it's empty when we create it
        }

        private int toRange(double pct) {
            return (int) (pct * 100);
        }

        @Override
        public IConfigValue<Double> getConfigValue() {
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
                configValue.set(value);
            }
        }
    }
}
