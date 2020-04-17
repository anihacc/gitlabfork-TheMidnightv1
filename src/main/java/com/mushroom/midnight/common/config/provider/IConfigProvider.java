package com.mushroom.midnight.common.config.provider;

public interface IConfigProvider {
    <T> IConfigValue<T> configValue(String path);
}
