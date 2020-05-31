package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.block.PileOfEggsBlock;
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

public class StingerEggFeature extends Feature<NoFeatureConfig> {
    private static final BlockState BLOCK = MidnightBlocks.STINGER_EGG.getDefaultState();

    public StingerEggFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos origin, NoFeatureConfig config) {
        boolean result = false;

        for (int i = 0; i < 8; ++i) {
            BlockPos pos = origin.add(
                    rand.nextInt(4) - rand.nextInt(4),
                    rand.nextInt(4) - rand.nextInt(4),
                    rand.nextInt(4) - rand.nextInt(4)
            );

            if (world.isAirBlock(pos) && BLOCK.isValidPosition(world, pos) && (pos.getY() > 1) &&
                    (world.getBlockState(pos.down()).isSolid())) {
                int count = rand.nextInt(4) + 1;
                world.setBlockState(origin, BLOCK.with(PileOfEggsBlock.EGGS, count), Constants.BlockFlags.BLOCK_UPDATE);
                result = true;
//                System.out.println("world.getBlockState(pos.down()).getBlock() = " + world.getBlockState(pos.down()).getBlock());
            }
        }

        return result;
    }
}
