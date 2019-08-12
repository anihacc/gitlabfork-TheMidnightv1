package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class CrystalotusBlock extends BushBlock {

    private static final VoxelShape BOUNDS = makeCuboidShape(1.0, 0.0, 1.0, 15.0, 12.0, 15.0);

    public CrystalotusBlock() {
        super(Properties.create(Material.PLANTS).hardnessAndResistance(2f, 0f).sound(SoundType.GLASS).tickRandomly().lightValue(3));
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getBlock().isIn(MidnightTags.Blocks.PLANTABLE_GROUNDS);
    }

    @Override
    public boolean isSolid(BlockState state) {
        return true;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 1;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return BOUNDS;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public int getPackedLightmapCoords(BlockState state, IEnviromentBlockReader world, BlockPos pos) {
        return world.getCombinedLight(pos, 13);
    }
}
