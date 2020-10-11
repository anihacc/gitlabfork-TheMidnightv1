package com.mushroom.midnight.common.world.generator;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightFluids;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public abstract class MidnightCarver<C extends ICarverConfig> extends WorldCarver<C> {
    protected MidnightCarver(Function<Dynamic<?>, ? extends C> deserialize, int maxHeight) {
        super(deserialize, maxHeight);
        this.carvableFluids = ImmutableSet.of(MidnightFluids.FLOWING_MIASMA);
    }


    @Override
    protected boolean carveBlock(IChunk chunkIn, Function<BlockPos, Biome> biomePos, BitSet carvingMask, Random rand, BlockPos.Mutable p_225556_5_, BlockPos.Mutable p_225556_6_, BlockPos.Mutable p_225556_7_, int p_225556_8_, int p_225556_9_, int p_225556_10_, int p_225556_11_, int p_225556_12_, int p_225556_13_, int p_225556_14_, int p_225556_15_, AtomicBoolean p_225556_16_) {
        int i = p_225556_13_ | p_225556_15_ << 4 | p_225556_14_ << 8;
        if (carvingMask.get(i)) {
            return false;
        } else {
            carvingMask.set(i);
            p_225556_5_.setPos(p_225556_11_, p_225556_14_, p_225556_12_);
            if (this.isCarvable(chunkIn.getBlockState(p_225556_5_))) {
                BlockState blockstate;
                if (p_225556_14_ <= 10) {
                    blockstate = MidnightBlocks.MIASMA.getDefaultState();
                } else {
                    blockstate = CAVE_AIR;
                }

                chunkIn.setBlockState(p_225556_5_, blockstate, false);
                return true;
            } else {
                return false;
            }
        }
    }
}
