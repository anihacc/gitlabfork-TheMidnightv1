package com.mushroom.midnight.common.config.provider;

import java.util.HashMap;

public class TempConfigProvider implements IConfigProvider {
    private final HashMap<String, IConfigValue<?>> configValues = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> IConfigValue<T> configValue(String path) {
        return (IConfigValue<T>) configValues.get(path);
    }

    public TempConfigProvider withValue(String path, IConfigValue<?> value) {
        configValues.put(path, value);
        return this;
    }
}
