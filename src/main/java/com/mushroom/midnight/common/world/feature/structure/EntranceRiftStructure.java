package com.mushroom.midnight.common.world.feature.structure;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.OverworldChunkGenerator;
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
        return 3;
    }

    @Override
    public Structure.IStartFactory getStartFactory() {
        return EntranceRiftStructure.Start::new;
    }

    @Override
    protected int getSeedModifier() {
        return 14357618;
    }

    public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
        ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosX, 0, 0);
        if (chunkGen instanceof OverworldChunkGenerator || chunkGen instanceof MidnightChunkGenerator) {
            if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
                int i = chunkPosX >> 4;
                int j = chunkPosZ >> 4;
                rand.setSeed((long) (i ^ j << 4) ^ chunkGen.getSeed());
                rand.nextInt();
                if (rand.nextInt(5) != 0) {
                    return false;
                }

                if (chunkGen.hasStructure(chunkGen.getBiomeProvider().getBiome(chunkPosX, chunkPosZ), this)) {
                    for (int k = chunkPosX - 10; k <= chunkPosX + 10; ++k) {
                        for (int l = chunkPosZ - 10; l <= chunkPosZ + 10; ++l) {
                            if (Feature.VILLAGE.hasStartAt(chunkGen, rand, k, l)) {
                                return false;
                            }
                        }
                    }

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected int getBiomeFeatureDistance(ChunkGenerator<?> generator) {
        return 24;
    }

    @Override
    protected int getBiomeFeatureSeparation(ChunkGenerator<?> generator) {
        return 3;
    }

    public static class Start extends StructureStart {
        public Start(Structure<?> structureIn, int chunkX, int chunkZ, Biome biomeIn, MutableBoundingBox boundsIn, int referenceIn, long seed) {
            super(structureIn, chunkX, chunkZ, biomeIn, boundsIn, referenceIn, seed);
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
