package com.mushroom.midnight.common.config.provider;

import com.google.common.base.Joiner;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.Map;

public class ConfigProfile implements IConfigGatherer, IConfigProvider {
    private static final Joiner PATH_JOINER = Joiner.on('.');

    private final HashMap<String, ForgeConfigSpec.ConfigValue<?>> configs = new HashMap<>();
    private final HashMap<String, ForgeConfigValue<?>> values = new HashMap<>();
    private final HashMap<String, Object> tempDefaults = new HashMap<>();

    @Override
    public <T> IConfigGatherer add(ForgeConfigSpec.ConfigValue<T> configValue, T tempDefault) {
        String path = PATH_JOINER.join(configValue.getPath());
        configs.put(path, configValue);
        values.put(path, new ForgeConfigValue<>(configValue));
        if (tempDefault != null)
            tempDefaults.put(path, tempDefault);
        return this;
    }

    public IConfigProvider makeTempProvider() {
        TempConfigProvider provider = new TempConfigProvider();
        for (Map.Entry<String, ForgeConfigSpec.ConfigValue<?>> entry : configs.entrySet()) {
            provider.withValue(entry.getKey(), new SimpleConfigValue<>(
                    tempDefaults.containsKey(entry.getKey())
                            ? tempDefaults.get(entry.getKey())
                            : entry.getValue().get()
            ));
        }
        return provider;
    }

    @SuppressWarnings("unchecked")
    public void importFromProvider(IConfigProvider provider, boolean save) {
        for (Map.Entry<String, ForgeConfigSpec.ConfigValue<?>> entry : configs.entrySet()) {
            ((ForgeConfigSpec.ConfigValue) entry.getValue())
                    .set(provider.configValue(entry.getKey()).get());

            if (save)
                entry.getValue().save();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> IConfigValue<T> configValue(String path) {
        return (IConfigValue<T>) values.get(path);
    }
}
