package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.world.layer.VoronoiZoomLayer;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;

import java.util.function.LongFunction;

public final class BiomeProcedure<A extends IArea> {
    public final A noise;
    public final A block;

    private BiomeProcedure(A noise, A block) {
        this.noise = noise;
        this.block = block;
    }

    public static <A extends IArea, C extends IExtendedNoiseRandom<A>> BiomeProcedure<A> of(IAreaFactory<A> noise, LongFunction<C> contextFactory) {
        //TODO
        IAreaFactory<A> block = VoronoiZoomLayer.INSTANCE.apply(contextFactory.apply(1234), noise);
        return new BiomeProcedure<>(noise.make(), block.make());
    }
}
