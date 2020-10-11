package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.block.MidnightDoublePlantBlock;
import com.mushroom.midnight.common.world.feature.tree.AbstractMidnightTreeFeature;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.util.Constants;

import java.util.Random;
import java.util.function.Function;

public class FungiPatchFeature extends FlowersFeature<NoFeatureConfig> {
    private final BlockState fungi;
    private final BlockState tallFungi;
    private final AbstractMidnightTreeFeature<NoFeatureConfig> tree;

    public FungiPatchFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize, BlockState fungi, BlockState tallFungi, AbstractMidnightTreeFeature<NoFeatureConfig> tree) {
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
                    MidnightDoublePlantBlock.placeAt(world, pos, this.tallFungi, Constants.BlockFlags.BLOCK_UPDATE);
                } else {
                    world.setBlockState(pos, this.fungi, Constants.BlockFlags.BLOCK_UPDATE);
                }
                result = true;
            }
        }

        return result;
    }

    public boolean isValidPosition(IWorld worldIn, BlockPos pos, NoFeatureConfig config) {
        return this.fungi == (worldIn.getBlockState(pos));
    }

    public int getFlowerCount(NoFeatureConfig config) {
        return 7;
    }

    public BlockPos getNearbyPos(Random rand, BlockPos pos, NoFeatureConfig config) {
        return pos.add(rand.nextInt(3) - rand.nextInt(3), rand.nextInt(7) - rand.nextInt(7), rand.nextInt(3) - rand.nextInt(3));
    }


    @Override
    public BlockState getFlowerToPlace(Random rand, BlockPos pos, NoFeatureConfig config) {
        return this.fungi;
    }
}
