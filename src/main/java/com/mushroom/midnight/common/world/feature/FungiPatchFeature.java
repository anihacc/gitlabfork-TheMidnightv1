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

    public boolean func_225559_a_(IWorld p_225559_1_, BlockPos p_225559_2_, NoFeatureConfig p_225559_3_) {
        return this.fungi == (p_225559_1_.getBlockState(p_225559_2_));
    }

    public int func_225560_a_(NoFeatureConfig p_225560_1_) {
        return 7;
    }

    public BlockPos getNearbyPos(Random p_225561_1_, BlockPos p_225561_2_, NoFeatureConfig p_225561_3_) {
        return p_225561_2_.add(p_225561_1_.nextInt(3) - p_225561_1_.nextInt(3), p_225561_1_.nextInt(7) - p_225561_1_.nextInt(7), p_225561_1_.nextInt(3) - p_225561_1_.nextInt(3));
    }


    @Override
    public BlockState getFlowerToPlace(Random p_225562_1_, BlockPos p_225562_2_, NoFeatureConfig p_225562_3_) {
        return this.fungi;
    }
}
