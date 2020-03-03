package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.registry.MidnightCavernousBiomes;
import com.mushroom.midnight.common.registry.MidnightSurfaceBiomes;
import com.mushroom.midnight.common.world.layer.AddHillsLayer;
import com.mushroom.midnight.common.world.layer.AddOutlineLayer;
import com.mushroom.midnight.common.world.layer.CavernSeedLayer;
import com.mushroom.midnight.common.world.layer.CellSeedLayer;
import com.mushroom.midnight.common.world.layer.CreateGroupPocketsLayer;
import com.mushroom.midnight.common.world.layer.EdgeMergeLayer;
import com.mushroom.midnight.common.world.layer.ProduceOutlineLayer;
import com.mushroom.midnight.common.world.layer.ReplaceRandomLayer;
import com.mushroom.midnight.common.world.layer.SeedGroupLayer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.SmoothLayer;
import net.minecraft.world.gen.layer.ZoomLayer;

import java.util.function.IntFunction;
import java.util.function.LongFunction;

public final class BiomeLayerType<T> {
    public static final BiomeLayerType<Biome> SURFACE = BiomeLayerType.create(Biome.class, BiomeLayerType::buildSurface, Registry.BIOME::getByValue, Biomes.DEFAULT);
    public static final BiomeLayerType<CavernousBiome> UNDERGROUND = BiomeLayerType.create(CavernousBiome.class, BiomeLayerType::buildUnderground, MidnightCavernousBiomes::byId, MidnightCavernousBiomes.CLOSED_CAVERN);

    private static final int MAX_CACHE_SIZE = 25;

    private final Class<T> type;
    private final ProcedureFactory procedureFactory;
    private final IntFunction<T> function;
    private final T defaultValue;

    private BiomeLayerType(Class<T> type, ProcedureFactory procedureFactory, IntFunction<T> function, T defaultValue) {
        this.type = type;
        this.procedureFactory = procedureFactory;
        this.function = function;
        this.defaultValue = defaultValue;
    }

    public static <T> BiomeLayerType<T> create(Class<T> type, ProcedureFactory procedureFactory, IntFunction<T> function, T defaultValue) {
        return new BiomeLayerType<>(type, procedureFactory, function, defaultValue);
    }

    public BiomeLayers<T> make(long worldSeed) {
        BiomeProcedure<LazyArea> procedure = this.procedureFactory.create(value -> new LazyAreaLayerContext(MAX_CACHE_SIZE, worldSeed, value));
        return new BiomeLayers<>(
                new BiomeLayer<>(this.type, procedure.noise, this.function, this.defaultValue),
                new BiomeLayer<>(this.type, procedure.block, this.function, this.defaultValue)
        );
    }

    public interface ProcedureFactory {
        <A extends IArea, C extends IExtendedNoiseRandom<A>> BiomeProcedure<A> create(LongFunction<C> contextFactory);
    }

    private static <A extends IArea, C extends IExtendedNoiseRandom<A>> BiomeProcedure<A> buildSurface(LongFunction<C> contextFactory) {
        int ridgeId = Registry.BIOME.getId(MidnightSurfaceBiomes.BLACK_RIDGE);
        int plateauId = Registry.BIOME.getId(MidnightSurfaceBiomes.OBSCURED_PLATEAU);
        int peakId = Registry.BIOME.getId(MidnightSurfaceBiomes.OBSCURED_PEAKS);
        int valleyId = Registry.BIOME.getId(MidnightSurfaceBiomes.PHANTASMAL_VALLEY);

        IAreaFactory<A> ridgeLayer = buildEdgeHighlightLayer(contextFactory, 100);
        ridgeLayer = new ReplaceRandomLayer(0, 11).apply(contextFactory.apply(110), ridgeLayer);

        IAreaFactory<A> valleyLayer = buildEdgeHighlightLayer(contextFactory, 200);

        IAreaFactory<A> layer = new SeedGroupLayer(MidnightBiomeGroup.SURFACE).apply(contextFactory.apply(0));
        layer = ZoomLayer.NORMAL.apply(contextFactory.apply(1000), layer);

        layer = new AddHillsLayer(MidnightBiomeGroup.SURFACE_HILLS, 5).apply(contextFactory.apply(2000), layer);

        layer = ZoomLayer.NORMAL.apply(contextFactory.apply(3000), layer);

        layer = new CreateGroupPocketsLayer(MidnightBiomeGroup.SURFACE_POCKET).apply(contextFactory.apply(5000), layer);
        layer = ZoomLayer.FUZZY.apply(contextFactory.apply(6000), layer);
        layer = SmoothLayer.INSTANCE.apply(contextFactory.apply(7000), layer);

        layer = new EdgeMergeLayer(id -> id != plateauId && id != peakId, ridgeId).apply(contextFactory.apply(8000), layer, ridgeLayer);

        layer = LayerUtil.repeat(9000, ZoomLayer.NORMAL, layer, 2, contextFactory);

        layer = new EdgeMergeLayer(id -> id == plateauId, valleyId).apply(contextFactory.apply(10000), layer, valleyLayer);

        layer = ZoomLayer.NORMAL.apply(contextFactory.apply(11000), layer);
        layer = SmoothLayer.INSTANCE.apply(contextFactory.apply(12000), layer);

        return BiomeProcedure.of(layer, contextFactory);
    }

    private static <A extends IArea, C extends IExtendedNoiseRandom<A>> BiomeProcedure<A> buildUnderground(LongFunction<C> contextFactory) {
        int closedCavernId = MidnightCavernousBiomes.getId(MidnightCavernousBiomes.CLOSED_CAVERN);
        int fungalCavernId = MidnightCavernousBiomes.getId(MidnightCavernousBiomes.FUNGAL_CAVERN);

        IAreaFactory<A> passageLayer = buildEdgeHighlightLayer(contextFactory, 300);

        IAreaFactory<A> layer = new CavernSeedLayer(MidnightBiomeGroup.UNDERGROUND).apply(contextFactory.apply(0));
        layer = ZoomLayer.NORMAL.apply(contextFactory.apply(1000), layer);

        layer = new AddOutlineLayer(closedCavernId).apply(contextFactory.apply(2000), layer);
        layer = new CreateGroupPocketsLayer(MidnightBiomeGroup.UNDERGROUND_POCKET).apply(contextFactory.apply(3000), layer);
        layer = ZoomLayer.FUZZY.apply(contextFactory.apply(4000), layer);

        layer = LayerUtil.repeat(5000, ZoomLayer.NORMAL, layer, 2, contextFactory);

        layer = new EdgeMergeLayer(id -> id == closedCavernId, fungalCavernId).apply(contextFactory.apply(6000), layer, passageLayer);

        layer = ZoomLayer.NORMAL.apply(contextFactory.apply(7000), layer);
        layer = SmoothLayer.INSTANCE.apply(contextFactory.apply(8000), layer);

        return BiomeProcedure.of(layer, contextFactory);
    }

    private static <A extends IArea, C extends IExtendedNoiseRandom<A>> IAreaFactory<A> buildEdgeHighlightLayer(LongFunction<C> contextFactory, long seed) {
        IAreaFactory<A> valleyLayer = CellSeedLayer.INSTANCE.apply(contextFactory.apply(10 + seed));
        valleyLayer = ZoomLayer.NORMAL.apply(contextFactory.apply(20 + seed), valleyLayer);
        valleyLayer = LayerUtil.repeat(30 + seed, ZoomLayer.NORMAL, valleyLayer, 2, contextFactory);
        valleyLayer = ProduceOutlineLayer.INSTANCE.apply(contextFactory.apply(40 + seed), valleyLayer);

        return valleyLayer;
    }
}
