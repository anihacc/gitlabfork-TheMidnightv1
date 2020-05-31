package com.mushroom.midnight.common.world.feature.structure;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.config.MidnightConfig;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.ScatteredStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Function;

public class WellStructure extends ScatteredStructure<NoFeatureConfig> {
    public static final String NAME = "midnight:well";

    public WellStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> deserializer) {
        super(deserializer);
    }

    @Override
    protected int getSeedModifier() {
        return 499831872;
    }

    @Override
    public IStartFactory getStartFactory() {
        return Start::new;
    }

    @Override
    public String getStructureName() {
        return NAME;
    }

    @Override
    public int getSize() {
        return 2;
    }


    @Override
    public boolean canBeGenerated(BiomeManager biomeMgr, ChunkGenerator<?> chunkGen, Random rand, int cx, int cz, Biome biome) {
        int config = MidnightConfig.worldgen.wellStructureRarity.get();
        if (config == 0) return false;
        ChunkPos chunkPos = this.getStartPositionForPosition(chunkGen, rand, cx, cz, 0, 0);
        rand.setSeed((long) (chunkPos.x ^ chunkPos.z << 4) ^ chunkGen.getSeed());
        return cx == chunkPos.x && cz == chunkPos.z && chunkGen.hasStructure(biome, this) && rand.nextInt(config) != 0;
    }

    @Nullable
    @Override
    public BlockPos findNearest(World worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, BlockPos pos, int radius, boolean skipExistingChunks) {
        int config = MidnightConfig.worldgen.wellStructureRarity.get();
        if (config == 0) return null;
        return super.findNearest(worldIn, chunkGenerator, pos, radius, skipExistingChunks);
    }

    public static class Start extends StructureStart {

        public Start(Structure<?> structure, int x, int z, MutableBoundingBox box, int refCount, long seed) {
            super(structure, x, z, box, refCount, seed);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManager, int cx, int cz, Biome biome) {
            int x = cx * 16;
            int z = cz * 16;
            BlockPos blockpos = new BlockPos(x, 90, z);
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
            WellPieces.addWellPieces(templateManager, blockpos, rotation, this.components, rand);
            this.recalculateStructureSize();
        }
    }
}
