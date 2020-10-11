package com.mushroom.midnight.common.world.feature.structure;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.ScatteredStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
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

    @Override
    public boolean canBeGenerated(BiomeManager biomeMgr, ChunkGenerator<?> chunkGen, Random rand, int cx, int cz, Biome biome) {
        ChunkPos start = getStartPositionForPosition(chunkGen, rand, cx, cz, 0, 0);
        // Minecraft won't give us access to the world: force that access via reflection...        "world"
        IWorld world = ObfuscationReflectionHelper.getPrivateValue(ChunkGenerator.class, chunkGen, "field_222540_a");

        if (world != null && ((world.getDimension().getType() == DimensionType.OVERWORLD) || chunkGen instanceof MidnightChunkGenerator)) {
            if (cx == start.x && cz == start.z) {
                int rx = cx >> 4;
                int rz = cz >> 4;

                rand.setSeed((long) (rx ^ rz << 4) ^ chunkGen.getSeed());
                rand.nextInt();
                int config = MidnightConfig.worldgen.riftStructureRarity.get();
                if (config <= 0 || rand.nextInt(config) != 0) {
                    return false;
                }

                if (MidnightConfig.getRiftBiomes().contains(biome) && MidnightConfig.getRiftDims().contains(world.getDimension().getType())) {
                    for (int k = cx - 10; k <= cx + 10; ++k) {
                        for (int l = cz - 10; l <= cz + 10; ++l) {
                            if (Feature.VILLAGE.canBeGenerated(biomeMgr, chunkGen, rand, k, l, biomeMgr.getBiome(new BlockPos((k << 4) + 9, 0, (l << 4) + 9)))) {
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

    @Nullable
    @Override
    public BlockPos findNearest(World world, ChunkGenerator<? extends GenerationSettings> chunkGen, BlockPos pos, int radius, boolean skipExistingChunks) {
        int config = MidnightConfig.worldgen.riftStructureRarity.get();
        if (config == 0) return null;
        if (!MidnightConfig.getRiftDims().contains(world.getDimension().getType())) return null;
        if (!chunkGen.getBiomeProvider().hasStructure(this)) {
            return null;
        } else {
            int cx = pos.getX() >> 4;
            int cz = pos.getZ() >> 4;
            int rad = 0;

            for (SharedSeedRandom rand = new SharedSeedRandom(); rad <= radius; ++rad) {
                for (int xOff = -rad; xOff <= rad; ++xOff) {
                    boolean xEdge = xOff == -rad || xOff == rad;

                    for (int zOff = -rad; zOff <= rad; ++zOff) {
                        boolean zEdge = zOff == -rad || zOff == rad;
                        if (xEdge || zEdge) {
                            ChunkPos cpos = getStartPositionForPosition(chunkGen, rand, cx, cz, xOff, zOff);
                            boolean startAtPos = canBeGenerated(world.getBiomeManager(), chunkGen, rand, cpos.x, cpos.z, world.getBiome(new BlockPos((cpos.x << 4) + 9, 64, (cpos.z << 4) + 9)));
//                            StructureStart start = world.getChunk(cpos.x, cpos.z, ChunkStatus.STRUCTURE_STARTS).getStructureStart(this.getStructureName());
//                            if (start != null && start.isValid()) {
//                                if (skipExistingChunks && start.isRefCountBelowMax()) {
//                                    start.incrementRefCount();
//                                    return start.getPos();
//                                }
//
//                                if (!skipExistingChunks) {
//                                    return start.getPos();
//                                }
//                            }
                            // Prevent minecraft from loading chunks: if canBeGenerated returns true then there is a valid rift there
                            if (startAtPos) {
                                return new BlockPos((cpos.x << 4) + 9, 64, (cpos.z << 4) + 9);
                            }

                            if (rad == 0) {
                                break;
                            }
                        }
                    }

                    if (rad == 0) {
                        break;
                    }
                }
            }

            return null;
        }
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
        public Start(Structure<?> p_i50678_1_, int p_i50678_2_, int p_i50678_3_, MutableBoundingBox p_i50678_5_, int p_i50678_6_, long p_i50678_7_) {
            super(p_i50678_1_, p_i50678_2_, p_i50678_3_, p_i50678_5_, p_i50678_6_, p_i50678_7_);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            int i = chunkX * 16;
            int j = chunkZ * 16;

            int originY = generator.getNoiseHeightMinusOne(i, j, Heightmap.Type.WORLD_SURFACE_WG);
            BlockPos blockpos = new BlockPos(i, originY, j);

            EntranceRiftPieces.addPieces(blockpos, this.components);
            this.recalculateStructureSize();
        }

    }
}
