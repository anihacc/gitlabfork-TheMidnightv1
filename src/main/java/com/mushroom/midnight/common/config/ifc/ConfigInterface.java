package com.mushroom.midnight.common.config.ifc;

import com.mushroom.midnight.client.gui.config.ConfigInterfaceScreen;
import com.mushroom.midnight.client.gui.config.ConfigOptionList;
import com.mushroom.midnight.common.config.provider.IConfigProvider;
import com.mushroom.midnight.common.config.provider.IConfigValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigInterface {
    private final ITextComponent title;
    private final IConfigProvider provider;
    private final boolean canCancel;
    private final List<Entry> entries = new ArrayList<>();
    private final List<IConfigValue<?>> values = new ArrayList<>();
    private final EditAccess access;

    public ConfigInterface(ITextComponent title, IConfigProvider provider, boolean canCancel, EditAccess access) {
        this.title = title;
        this.provider = provider;
        this.canCancel = canCancel;
        this.access = access;
    }

    public ConfigInterface header(String headerTK) {
        entries.add(new HeaderEntry(headerTK));
        return this;
    }

    public ConfigInterface category(String categoryTK, ConfigInterface cfgInterface) {
        entries.add(new CategoryEntry(categoryTK, categoryTK + ".tooltip", cfgInterface));
        return this;
    }

    public ConfigInterface category(String categoryTK, String descTK, ConfigInterface cfgInterface) {
        entries.add(new CategoryEntry(categoryTK, descTK, cfgInterface));
        return this;
    }

    public <T> ConfigInterface setting(String settingTK, IConfigControlType<T> type, String path) {
        IConfigValue<T> config = provider.configValue(path);
        entries.add(new ConfigEntry<>(settingTK, settingTK + ".tooltip", type, config));
        values.add(config);
        return this;
    }

    public <T> ConfigInterface setting(String settingTK, String descTK, IConfigControlType<T> type, String path) {
        IConfigValue<T> config = provider.configValue(path);
        entries.add(new ConfigEntry<>(settingTK, descTK, type, config));
        values.add(config);
        return this;
    }

    public ITextComponent getTitle() {
        return title;
    }

    public boolean canCancel() {
        return canCancel;
    }

    public EditAccess getAccess() {
        return access;
    }

    public void saveAll() {
        for (IConfigValue<?> value : values) {
            value.save();
        }
    }

    public void discardAll() {
        for (IConfigValue<?> value : values) {
            value.discard();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void fillOptionsScreen(ConfigInterfaceScreen screen, ConfigOptionList scrollView) {
        for (Entry entry : entries) {
            scrollView.addEntry(entry.createRow(screen));
        }
    }

    public Snapshot createSnapshot() {
        return new Snapshot(values);
    }

    @FunctionalInterface
    private interface Entry {
        @OnlyIn(Dist.CLIENT)
        ConfigOptionList.Row createRow(ConfigInterfaceScreen screen);
    }

    private static class HeaderEntry implements Entry {
        private final String headerTK;

        private HeaderEntry(String headerTK) {
            this.headerTK = headerTK;
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ConfigOptionList.Row createRow(ConfigInterfaceScreen screen) {
            return new ConfigOptionList.HeaderRow(I18n.format(headerTK), screen);
        }
    }

    private static class CategoryEntry implements Entry {
        private final String buttonTK;
        private final String tooltipTK;
        private final ConfigInterface cfgInterface;

        private CategoryEntry(String buttonTK, String tooltipTK, ConfigInterface cfgInterface) {
            this.buttonTK = buttonTK;
            this.tooltipTK = tooltipTK;
            this.cfgInterface = cfgInterface;
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ConfigOptionList.Row createRow(ConfigInterfaceScreen screen) {
            Button button = new Button(0, 0, 200, 20, I18n.format(buttonTK), btn -> screen.openCategory(cfgInterface));
            button.active = cfgInterface.getAccess().canEdit(Minecraft.getInstance());
            ConfigOptionList.Row row = new ConfigOptionList.ButtonOnlyRow(button, screen);
            if (I18n.hasKey(tooltipTK)) {
                row.withTooltip(I18n.format(tooltipTK));
            }
            return row;
        }
    }

    private static class ConfigEntry<T> implements Entry {
        private final String nameTK;
        private final String tooltipTK;
        private final IConfigControlType<T> type;
        private final IConfigValue<T> cfg;

        private ConfigEntry(String nameTK, String tooltipTK, IConfigControlType<T> type, IConfigValue<T> cfg) {
            this.nameTK = nameTK;
            this.tooltipTK = tooltipTK;
            this.type = type;
            this.cfg = cfg;
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ConfigOptionList.Row createRow(ConfigInterfaceScreen screen) {
            ConfigOptionList.Row row = new ConfigOptionList.Row(I18n.format(nameTK), type.createConfigWidget(cfg).asWidget(), screen);
            if (I18n.hasKey(tooltipTK)) {
                row.withTooltip(I18n.format(tooltipTK));
            }
            return row;
        }
    }

    public static class Snapshot {
        private final Map<IConfigValue<?>, Object> snapshotValues = new HashMap<>();

        private Snapshot(List<IConfigValue<?>> values) {
            for (IConfigValue<?> value : values) {
                snapshotValues.put(value, value.get());
            }
        }

        @SuppressWarnings("unchecked")
        public void revert() {
            for (Map.Entry<IConfigValue<?>, Object> entry : snapshotValues.entrySet()) {
                ((IConfigValue) entry.getKey()).set(entry.getValue());
            }
        }
    }

    @FunctionalInterface
    public interface Factory {
        ConfigInterface makeInterface(IConfigProvider provider);
    }
}
