package com.mushroom.midnight.common.tile;

import com.mushroom.midnight.common.registry.MidnightTileEntities;
import com.mushroom.midnight.common.util.SlidingToggle;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class RiftPortalTileEntity extends TileEntity implements ITickableTileEntity {
    private final SlidingToggle closed = new SlidingToggle(30).initialize(true);

    public RiftPortalTileEntity(TileEntityType<?> type) {
        super(type);
    }

    public RiftPortalTileEntity() {
        this(MidnightTileEntities.RIFT_PORTAL);
    }

    @Override
    public void tick() {
        if (this.world.rand.nextInt(3) == 0) {
            long time = this.world.getDayTime() % 24000;
            this.closed.set(time >= 0 && time <= 13000);
        }

        this.closed.update();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound = super.write(compound);
        compound.put("closed", this.closed.serialize(new CompoundNBT()));

        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);

        if (compound.contains("closed")) {
            this.closed.deserialize(compound.getCompound("closed"));
        }
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public float getCloseAnimation(float partialTicks) {
        return this.closed.getScale(partialTicks);
    }

    public boolean isClosed() {
        return this.closed.get() || this.closed.getTimer() > 0;
    }
}
