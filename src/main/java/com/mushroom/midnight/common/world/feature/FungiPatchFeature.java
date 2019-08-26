package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.block.MidnightDoublePlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.util.Constants;

import java.util.Random;
import java.util.function.Function;

public class FungiPatchFeature extends FlowersFeature {
    private final BlockState fungi;
    private final BlockState tallFungi;
    private final AbstractTreeFeature<NoFeatureConfig> tree;

    public FungiPatchFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize, BlockState fungi, BlockState tallFungi, AbstractTreeFeature<NoFeatureConfig> tree) {
        super(deserialize);
        this.fungi = fungi;
        this.tallFungi = tallFungi;
        this.tree = tree;
    }

    public FungiPatchFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize, BlockState fungi, BlockState tallFungi) {
        this(deserialize, fungi, tallFungi, null);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos origin, NoFeatureConfig config) {
        boolean result = false;

        if (rand.nextFloat() < 0.05F && this.tree != null) {
            result |= this.tree.place(world, generator, rand, origin, config);
        }

        float tallRatio = 0.3F;
        if (rand.nextFloat() < 0.2F) {
            tallRatio = 0.8F;
        }

        for (int i = 0; i < 20; i++) {
            BlockPos pos = origin.add(
                    rand.nextInt(8) - rand.nextInt(8),
                    rand.nextInt(4) - rand.nextInt(4),
                    rand.nextInt(8) - rand.nextInt(8)
            );
            if (world.isAirBlock(pos) && this.fungi.isValidPosition(world, pos)) {
                if (rand.nextFloat() < tallRatio) {
                    MidnightDoublePlantBlock.placeAt(world, pos, this.tallFungi, Constants.BlockFlags.NOTIFY_LISTENERS);
                } else {
                    world.setBlockState(pos, this.fungi, Constants.BlockFlags.NOTIFY_LISTENERS);
                }
                result = true;
            }
        }

        return result;
    }

    @Override
    public BlockState getRandomFlower(Random random, BlockPos pos) {
        return this.fungi;
    }
}
