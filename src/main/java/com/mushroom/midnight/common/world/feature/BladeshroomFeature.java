package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.block.BladeshroomBlock;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class BladeshroomFeature extends Feature<NoFeatureConfig> {
    private static final BlockState BLADESHROOM = MidnightBlocks.BLADESHROOM.getDefaultState().with(BladeshroomBlock.STAGE, BladeshroomBlock.Stage.CAPPED);

    public BladeshroomFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos origin, NoFeatureConfig config) {
        boolean generated = false;

        for (int i = 0; i < 96; i++) {
            BlockPos pos = origin.add(
                    rand.nextInt(8) - rand.nextInt(8),
                    rand.nextInt(4) - rand.nextInt(4),
                    rand.nextInt(8) - rand.nextInt(8)
            );

            Direction direction = Direction.random(rand);
            BlockState state = BLADESHROOM.with(BladeshroomBlock.FACING, direction);

            if (world.isAirBlock(pos) && state.isValidPosition(world, pos)) {
                this.setBlockState(world, pos, state);
                generated = true;
            }
        }

        return generated;
    }
}
