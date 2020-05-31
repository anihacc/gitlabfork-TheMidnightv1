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

public class ShadowrootGuardTowerStructure extends ScatteredStructure<NoFeatureConfig> {
    public ShadowrootGuardTowerStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51491_1_) {
        super(p_i51491_1_);
    }

    @Override
    public String getStructureName() {
        return "midnight:shadowroot_guardtower";
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public Structure.IStartFactory getStartFactory() {
        return ShadowrootGuardTowerStructure.Start::new;
    }

    @Override
    protected int getSeedModifier() {
        return 14357618;
    }

    @Override
    protected int getBiomeFeatureDistance(ChunkGenerator<?> generator) {
        return 16;
    }

    @Override
    protected int getBiomeFeatureSeparation(ChunkGenerator<?> generator) {
        return 8;
    }


    @Override
    public boolean canBeGenerated(BiomeManager biomeMgr, ChunkGenerator<?> chunkGen, Random rand, int cx, int cz, Biome biome) {
        int config = MidnightConfig.worldgen.guardtowerStructureRarity.get();
        if (config == 0) return false;
        ChunkPos chunkPos = this.getStartPositionForPosition(chunkGen, rand, cx, cz, 0, 0);
        rand.setSeed((long) (chunkPos.x ^ chunkPos.z << 4) ^ chunkGen.getSeed());
        return cx == chunkPos.x && cz == chunkPos.z && chunkGen.hasStructure(biome, this) && rand.nextInt(config) != 0;
    }

    @Nullable
    @Override
    public BlockPos findNearest(World worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, BlockPos pos, int radius, boolean skipExistingChunks) {
        int config = MidnightConfig.worldgen.guardtowerStructureRarity.get();
        if (config == 0) return null;
        return super.findNearest(worldIn, chunkGenerator, pos, radius, skipExistingChunks);
    }

    public static class Start extends StructureStart {
        public Start(Structure<?> p_i50678_1_, int p_i50678_2_, int p_i50678_3_, MutableBoundingBox p_i50678_5_, int p_i50678_6_, long p_i50678_7_) {
            super(p_i50678_1_, p_i50678_2_, p_i50678_3_, p_i50678_5_, p_i50678_6_, p_i50678_7_);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            int i = chunkX * 16;
            int j = chunkZ * 16;
            BlockPos blockpos = new BlockPos(i, 90, j);
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
            ShadowrootGuardTowerPieces.addTowerPieces(templateManagerIn, blockpos, rotation, this.components);
            this.recalculateStructureSize();
        }
    }
}
