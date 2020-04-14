package com.mushroom.midnight.common.config;

public enum ShaderMode {
    ALWAYS(true, true),
    MIDNIGHT_OR_NEEDED(true, true),
    MIDNIGHT_ONLY(false, true),
    NEEDED_ONLY(true, false),
    OFF(false, false);

    private final boolean needed;
    private final boolean dimens;

    ShaderMode(boolean needed, boolean dimens) {
        this.needed = needed;
        this.dimens = dimens;
    }

    public boolean isDimens() {
        return dimens;
    }

    public boolean isNeeded() {
        return needed;
    }
}
