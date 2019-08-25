package com.mushroom.midnight.common.tile;

import com.mushroom.midnight.common.registry.MidnightTileEntities;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class RiftPortalTileEntity extends TileEntity {
    public RiftPortalTileEntity(TileEntityType<?> type) {
        super(type);
    }

    public RiftPortalTileEntity() {
        this(MidnightTileEntities.RIFT_PORTAL);
    }
}
