package com.mushroom.midnight.common.util;

import net.minecraft.nbt.CompoundNBT;

public class SlidingToggle {
    private final int length;

    private int rate = 1;

    private boolean state;
    private int timer;
    private int prevTimer;

    public SlidingToggle(int length) {
        this.length = length;
    }

    public SlidingToggle initialize(boolean state) {
        this.state = state;
        this.timer = this.prevTimer = state ? this.length : 0;
        return this;
    }

    public void update() {
        this.prevTimer = this.timer;
        if (this.state) {
            this.timer = Math.min(this.timer + this.rate, this.length);
        } else {
            this.timer = Math.max(this.timer - this.rate, 0);
        }
    }

    public void set(boolean state) {
        this.state = state;
    }

    public boolean get() {
        return this.state;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return this.timer;
    }

    public float getScale() {
        return this.timer / (float) this.length;
    }

    public float getScale(float partialTicks) {
        float timer = this.prevTimer + (this.timer - this.prevTimer) * partialTicks;
        return timer / this.length;
    }

    public CompoundNBT serialize(CompoundNBT compound) {
        compound.putBoolean("state", this.state);
        compound.putShort("timer", (short) this.timer);
        compound.putByte("rate", (byte) this.rate);
        return compound;
    }

    public void deserialize(CompoundNBT compound) {
        this.state = compound.getBoolean("state");
        this.timer = compound.getShort("timer");
        this.rate = compound.getByte("rate");
    }

    @Override
    public String toString() {
        return "SlidingToggle{state=" + this.state + ", timer=" + this.timer + '}';
    }
}
