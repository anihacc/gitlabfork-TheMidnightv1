package com.mushroom.midnight.common.config.provider;

public class SimpleConfigValue<T> implements IConfigValue<T> {
    private T value;
    private T lastCheckpoint;

    public SimpleConfigValue(T initial) {
        value = initial;
        lastCheckpoint = initial;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void save() {
        lastCheckpoint = value;
    }

    @Override
    public void discard() {
        value = lastCheckpoint;
    }
}
