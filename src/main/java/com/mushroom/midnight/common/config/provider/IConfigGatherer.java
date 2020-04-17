package com.mushroom.midnight.common.config.provider;

import net.minecraftforge.common.ForgeConfigSpec;

@FunctionalInterface
public interface IConfigGatherer {
    <T> IConfigGatherer add(ForgeConfigSpec.ConfigValue<T> configValue, T tempDefault);

    default IConfigGatherer add(ForgeConfigSpec.ConfigValue<?> configValue) {
        return add(configValue, null);
    }
}
