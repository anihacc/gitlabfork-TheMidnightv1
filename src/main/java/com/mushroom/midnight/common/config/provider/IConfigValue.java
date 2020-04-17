package com.mushroom.midnight.common.config.provider;

public interface IConfigValue<T> {
    void set(T value);

    T get();

    void save();

    void discard();
}
