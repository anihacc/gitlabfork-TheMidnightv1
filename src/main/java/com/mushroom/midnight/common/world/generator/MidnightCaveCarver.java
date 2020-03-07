package com.mushroom.midnight.common.world.generator;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightFluids;
import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class MidnightCaveCarver extends CaveWorldCarver {
    private final float radiusScale;

    public MidnightCaveCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> deserialize, float radiusScale) {
        super(deserialize, 256);
        this.radiusScale = radiusScale;
        this.carvableFluids = ImmutableSet.of(MidnightFluids.FLOWING_MIASMA);
    }

    @Override
    protected int generateCaveStartY(Random random) {
        return random.nextInt(random.nextInt(MidnightChunkGenerator.SEA_LEVEL) + 8);
    }

    @Override
    protected boolean func_225556_a_(IChunk p_225556_1_, Function<BlockPos, Biome> p_225556_2_, BitSet p_225556_3_, Random p_225556_4_, BlockPos.Mutable p_225556_5_, BlockPos.Mutable p_225556_6_, BlockPos.Mutable p_225556_7_, int p_225556_8_, int p_225556_9_, int p_225556_10_, int p_225556_11_, int p_225556_12_, int p_225556_13_, int p_225556_14_, int p_225556_15_, AtomicBoolean p_225556_16_) {
        int i = p_225556_13_ | p_225556_15_ << 4 | p_225556_14_ << 8;
        if (p_225556_3_.get(i)) {
            return false;
        } else {
            p_225556_3_.set(i);
            p_225556_5_.setPos(p_225556_11_, p_225556_14_, p_225556_12_);
            if (this.func_222706_a(p_225556_1_.getBlockState(p_225556_5_))) {
                BlockState blockstate;
                if (p_225556_14_ <= 10) {
                    blockstate = MidnightBlocks.MIASMA.getDefaultState();
                } else {
                    blockstate = CAVE_AIR;
                }

                p_225556_1_.setBlockState(p_225556_5_, blockstate, false);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    protected boolean canCarveBlock(BlockState state, BlockState aboveState) {
        if (state.getBlock() == Blocks.BEDROCK) return false;

        Material material = state.getMaterial();
        Material aboveMaterial = aboveState.getMaterial();
        return (material == Material.ROCK || material == Material.EARTH || material == Material.ORGANIC)
                && material != Material.WATER && material != Material.LAVA
                && aboveMaterial != Material.WATER && aboveMaterial != Material.LAVA;
    }

    @Override
    protected float generateCaveRadius(Random random) {
        return super.generateCaveRadius(random) * this.radiusScale;
    }
}
