package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;

public class BlockDeceitfulPeat extends Block implements IModelProvider {
    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.875, 1.0);

    public BlockDeceitfulPeat() {
        super(Material.GROUND);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.GROUND);
        this.setCreativeTab(Midnight.BUILDING_TAB);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return true;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return BOUNDS;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        entity.motionX *= 0.6;
        entity.motionZ *= 0.6;
    }
}