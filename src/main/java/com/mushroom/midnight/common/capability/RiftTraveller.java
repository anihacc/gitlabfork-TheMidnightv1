package com.mushroom.midnight.common.capability;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.MidnightTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RiftTraveller implements ICapabilityProvider {
    private int cooldown;
    private boolean inRift;

    public void update(Entity entity) {
        if (this.cooldown > 0 && !this.inRift) {
            this.cooldown--;
        }

        if (entity.world instanceof ServerWorld) {
            if (this.inRift && this.isReady()) {
                MidnightTeleporter.INSTANCE.teleport(entity);
                this.cooldown = 80;
            }
        }

        this.inRift = false;
    }

    public void setInRift() {
        this.inRift = true;
    }

    public boolean isReady() {
        return this.cooldown <= 0;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return capability == Midnight.RIFT_TRAVELLER_CAP ? LazyOptional.of(() -> this).cast() : LazyOptional.empty();
    }
}
