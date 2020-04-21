package com.mushroom.midnight.common.config.ifc;

import com.mushroom.midnight.client.gui.config.ConfigListScreen;
import com.mushroom.midnight.client.gui.config.ListConfigOptionList;
import com.mushroom.midnight.client.gui.config.widget.IConfigWidget;
import com.mushroom.midnight.common.config.provider.IConfigValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class StringListControl implements IConfigControlType<List<String>> {
    private final String header;

    public StringListControl(String header) {
        this.header = header;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IConfigWidget<List<String>> createConfigWidget(IConfigValue<List<String>> configValue) {
        return new CfgWidget(configValue, header);
    }

    @OnlyIn(Dist.CLIENT)
    private static class CfgWidget implements IConfigWidget<List<String>> {
        private final IConfigValue<List<String>> configValue;
        private final Button button;
        private final String header;

        private CfgWidget(IConfigValue<List<String>> configValue, String header) {
            this.configValue = configValue;
            button = new Button(0, 0, 150, 20, I18n.format("config.midnight.format.entries", configValue.get().size()), this::click);
            this.header = header;
        }

        private void click(Button btn) {
            List<String> strings = configValue.get();
            ConfigListScreen<String> listScreen = new ConfigListScreen<>(
                    Minecraft.getInstance().currentScreen,
                    new TranslationTextComponent(header),
                    strings,
                    this::createTextBox,
                    () -> "",
                    list -> {
                        configValue.set(list);
                        btn.setMessage(I18n.format("config.midnight.format.entries", list.size()));
                    }
            );
            Minecraft.getInstance().displayGuiScreen(listScreen);
        }

        private Widget createTextBox(ListConfigOptionList.Value<String> value) {
            TextFieldWidget tf = new TextFieldWidget(Minecraft.getInstance().fontRenderer, 0, 0, 260, 20, "");
            tf.setText(value.get());
            tf.setMaxStringLength(Integer.MAX_VALUE);
            tf.setResponder(value::set);
            return tf;
        }

        @Override
        public IConfigValue<List<String>> getConfigValue() {
            return configValue;
        }

        @Override
        public Widget asWidget() {
            return button;
        }
    }
}
