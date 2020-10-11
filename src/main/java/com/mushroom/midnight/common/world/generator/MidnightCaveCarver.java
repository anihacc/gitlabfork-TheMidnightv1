package com.mushroom.midnight.common.world.generator;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightFluids;
import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
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
        this.carvableBlocks = ImmutableSet.of(MidnightBlocks.NIGHTSTONE, MidnightBlocks.DIRT, MidnightBlocks.GRASS_BLOCK);
        this.carvableFluids = ImmutableSet.of(MidnightFluids.FLOWING_MIASMA);
    }

    @Override
    protected int generateCaveStartY(Random random) {
        return random.nextInt(random.nextInt(MidnightChunkGenerator.SEA_LEVEL) + 8);
    }

    @Override
    protected boolean carveBlock(IChunk chunk, Function<BlockPos, Biome> biomeFunc, BitSet carvingMask, Random rand, BlockPos.Mutable mutable, BlockPos.Mutable p_225556_6_, BlockPos.Mutable p_225556_7_, int p_225556_8_, int p_225556_9_, int p_225556_10_, int x, int z, int p_225556_13_, int y, int p_225556_15_, AtomicBoolean p_225556_16_) {
        int i = p_225556_13_ | p_225556_15_ << 4 | y << 8;
        if (carvingMask.get(i)) {
            return false;
        } else {
            carvingMask.set(i);
            mutable.setPos(x, y, z);
            boolean noAdjacentBlock = true;
            for (Direction dir : Direction.values()) {
                if (dir == Direction.DOWN) continue;
                mutable.move(dir);
                if (this.doesAdjacentBlockPreventCarving(chunk.getBlockState(mutable), mutable.getY() <= 10)) {
                    noAdjacentBlock = false;
                }
                mutable.move(dir, -1);
                if (!noAdjacentBlock) {
                    break;
                }
            }
            if (this.isCarvable(chunk.getBlockState(mutable)) && noAdjacentBlock) {
                BlockState blockstate;
                if (y <= 10) {
                    blockstate = MidnightBlocks.MIASMA.getDefaultState();
                } else {
                    blockstate = CAVE_AIR;
                }

                chunk.setBlockState(mutable, blockstate, false);
                return true;
            } else {
                return false;
            }
        }
    }

    protected boolean doesAdjacentBlockPreventCarving(BlockState state, boolean miasmaHeight) {
        if (miasmaHeight && state.getBlock() == MidnightBlocks.MIASMA) return false;
        Material material = state.getMaterial();
        return material == Material.WATER || material == Material.LAVA || miasmaHeight && material == Material.AIR;
    }

    @Override
    protected boolean isCarvable(BlockState state) {
        if (state.getBlock() == Blocks.BEDROCK) return false;

        Material material = state.getMaterial();
        return (material == Material.ROCK || material == Material.EARTH || material == Material.ORGANIC)
                && material != Material.WATER && material != Material.LAVA;
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
