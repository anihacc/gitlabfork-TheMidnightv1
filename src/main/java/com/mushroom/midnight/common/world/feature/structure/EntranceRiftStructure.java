package com.mushroom.midnight.common.world.feature.structure;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.ScatteredStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;
import java.util.function.Function;

public class EntranceRiftStructure extends ScatteredStructure<NoFeatureConfig> {
    public EntranceRiftStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51491_1_) {
        super(p_i51491_1_);
    }

    @Override
    public String getStructureName() {
        return "midnight:entrance_rift";
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public Structure.IStartFactory getStartFactory() {
        return EntranceRiftStructure.Start::new;
    }

    @Override
    protected int getSeedModifier() {
        return 14357618;
    }

    public boolean func_225558_a_(BiomeManager p_225558_1_, ChunkGenerator<?> p_225558_2_, Random p_225558_3_, int p_225558_4_, int p_225558_5_, Biome p_225558_6_) {
        ChunkPos chunkpos = this.getStartPositionForPosition(p_225558_2_, p_225558_3_, p_225558_4_, p_225558_5_, 0, 0);
        if (p_225558_4_ == chunkpos.x && p_225558_5_ == chunkpos.z) {
            int i = p_225558_4_ >> 4;
            int j = p_225558_5_ >> 4;
            p_225558_3_.setSeed((long) (i ^ j << 4) ^ p_225558_2_.getSeed());
            p_225558_3_.nextInt();
            if (p_225558_3_.nextInt(5) != 0) {
                return false;
            }

            if (p_225558_2_.hasStructure(p_225558_6_, this)) {
                for (int k = p_225558_4_ - 10; k <= p_225558_4_ + 10; ++k) {
                    for (int l = p_225558_5_ - 10; l <= p_225558_5_ + 10; ++l) {
                        if (Feature.VILLAGE.func_225558_a_(p_225558_1_, p_225558_2_, p_225558_3_, k, l, p_225558_1_.getBiome(new BlockPos((k << 4) + 9, 0, (l << 4) + 9)))) {
                            return false;
                        }
                    }
                }

                return true;
            }
        }

        return false;
    }

    @Override
    protected int getBiomeFeatureDistance(ChunkGenerator<?> generator) {
        return 26;
    }

    @Override
    protected int getBiomeFeatureSeparation(ChunkGenerator<?> generator) {
        return 4;
    }

    public static class Start extends StructureStart {
        public Start(Structure<?> p_i50678_1_, int p_i50678_2_, int p_i50678_3_, MutableBoundingBox p_i50678_5_, int p_i50678_6_, long p_i50678_7_) {
            super(p_i50678_1_, p_i50678_2_, p_i50678_3_, p_i50678_5_, p_i50678_6_, p_i50678_7_);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            int i = chunkX * 16;
            int j = chunkZ * 16;

            int originY = generator.func_222531_c(i, j, Heightmap.Type.WORLD_SURFACE_WG);
            BlockPos blockpos = new BlockPos(i, originY, j);

            EntranceRiftPieces.addPieces(blockpos, this.components);
            this.recalculateStructureSize();
        }
    }
}
