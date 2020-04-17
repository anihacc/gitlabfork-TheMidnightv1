package com.mushroom.midnight.common.config.provider;

import net.minecraftforge.common.ForgeConfigSpec;

public class ForgeConfigValue<T> implements IConfigValue<T> {
    private final ForgeConfigSpec.ConfigValue<T> configValue;
    private T value;
    private boolean initalized;

    public ForgeConfigValue(ForgeConfigSpec.ConfigValue<T> configValue) {
        this.configValue = configValue;
    }

    private void init() {
        if (initalized) return;
        value = configValue.get();
        initalized = true;
    }

    @Override
    public void set(T value) {
        init();
        this.value = value;
    }

    @Override
    public T get() {
        init();
        return value;
    }

    @Override
    public void save() {
        if (!initalized) return;
        if (value != configValue.get()) {
            configValue.set(value);
            configValue.save();
        }
    }

    @Override
    public void discard() {
        if (!initalized) return;
        value = configValue.get();
    }
}
