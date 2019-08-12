package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.util.Constants;

import java.util.Random;
import java.util.function.Function;

public class FingeredGrassFeature extends Feature<NoFeatureConfig> {
    private static final BlockState BLOCK = MidnightBlocks.FINGERED_GRASS.getDefaultState();

    public FingeredGrassFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos origin, NoFeatureConfig config) {
        boolean result = false;

        for (int i = 0; i < 256; ++i) {
            BlockPos pos = origin.add(
                    rand.nextInt(10) - rand.nextInt(10),
                    rand.nextInt(4) - rand.nextInt(4),
                    rand.nextInt(10) - rand.nextInt(10)
            );

            if (world.isAirBlock(pos) && BLOCK.isValidPosition(world, pos)) {
                world.setBlockState(pos, BLOCK, Constants.BlockFlags.NOTIFY_LISTENERS);
                result = true;
            }
        }

        return result;
    }
}
