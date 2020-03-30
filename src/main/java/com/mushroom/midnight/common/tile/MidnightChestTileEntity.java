package com.mushroom.midnight.common.tile;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

public class MidnightChestTileEntity extends ChestTileEntity {
    private Block block = Blocks.AIR;

    protected MidnightChestTileEntity(TileEntityType<?> entityType) {
        super(entityType);
    }

    public MidnightChestTileEntity() {
        super(MidnightTileEntities.MIDNIGHT_CHEST);
    }

    public MidnightChestTileEntity(Block chest) {
        this();
        this.setChestModel(chest);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(
                this.pos.add(-1, 0, -1),
                this.pos.add(2, 2, 2)
        );
    }

    public void setChestModel(Block block) {
        this.block = block;
    }

    public Block getChestModel() {
        return this.block != Blocks.AIR ? this.block : MidnightBlocks.SHADOWROOT_CHEST;
    }
}
