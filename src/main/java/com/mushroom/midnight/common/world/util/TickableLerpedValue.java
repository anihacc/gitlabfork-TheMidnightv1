package com.mushroom.midnight.common.world.util;

public final class TickableLerpedValue {
    private static final double EPS = 1e-6;

    private final double speed;

    private double value;
    private double lastValue;

    private boolean initialized;

    private TickableLerpedValue(double speed) {
        this.speed = speed;
    }

    public static TickableLerpedValue atSpeed(double speed) {
        return new TickableLerpedValue(speed);
    }

    public void update(double value) {
        if (!this.initialized) {
            this.value = this.lastValue = value;
            this.initialized = true;
            return;
        }

        this.lastValue = this.value;
        this.value = this.updateValue(this.value, value);
    }

    private double updateValue(double value, double target) {
        double delta = target - value;
        if (Math.abs(delta) < EPS) return target;

        if (delta > 0.0) {
            return Math.min(value + this.speed, target);
        } else {
            return Math.max(value - this.speed, target);
        }
    }

    public double get(double partialTicks) {
        return this.lastValue + (this.value - this.lastValue) * partialTicks;
    }
}
