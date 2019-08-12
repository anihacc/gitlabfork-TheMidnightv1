package com.mushroom.midnight.common.world.feature.structure;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightStructurePieces;
import com.mushroom.midnight.common.world.MidnightChunkGenerator;
import com.mushroom.midnight.common.world.noise.INoiseSampler;
import com.mushroom.midnight.common.world.noise.PerlinNoiseSampler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Function;

public final class MoltenCraterStructure extends Structure<NoFeatureConfig> {
    public static final String NAME = "molten_crater";

    private static final int SEED = 0x9C55CF;

    private static final int GRID_SIZE = 20;
    private static final int SEPARATION = 8;

    private static final int MIN_RADIUS = 16;
    private static final int MAX_RADIUS = 42;

    private static final int MAX_GENERATION_Y = MidnightChunkGenerator.SURFACE_LEVEL + 8;
    private static final int SCALE_Y = 2;

    private static final int EDGE_DEPTH = 12;
    private static final int POOL_DEPTH = 3;

    private static final int FULL_RADIUS = MAX_RADIUS + EDGE_DEPTH;
    private static final int CHUNK_RADIUS = MathHelper.ceil(FULL_RADIUS / 16.0);

    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private static final BlockState MIASMA = MidnightBlocks.MIASMA.getDefaultState();
    private static final BlockState MIASMA_SURFACE = MidnightBlocks.MIASMA_SURFACE.getDefaultState();
    private static final BlockState SURFACE = MidnightBlocks.TRENCHSTONE.getDefaultState();

    private static final INoiseSampler NOISE_SAMPLER = new PerlinNoiseSampler(new Random(SEED));

    static {
        NOISE_SAMPLER.setFrequency(0.001);
    }

    public MoltenCraterStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> generator, Random random, int chunkX, int chunkZ, int offsetX, int offsetZ) {
        int ox = chunkX + GRID_SIZE * offsetX;
        int oz = chunkZ + GRID_SIZE * offsetZ;

        int gridX = (ox < 0 ? ox - GRID_SIZE + 1 : ox) / GRID_SIZE;
        int gridZ = (oz < 0 ? oz - GRID_SIZE + 1 : oz) / GRID_SIZE;

        ((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(generator.getSeed(), gridX, gridZ, SEED);

        int startX = gridX * GRID_SIZE;
        int startZ = gridZ * GRID_SIZE;
        startX += random.nextInt(GRID_SIZE - SEPARATION);
        startZ += random.nextInt(GRID_SIZE - SEPARATION);

        return new ChunkPos(startX, startZ);
    }

    @Override
    public boolean hasStartAt(ChunkGenerator<?> generator, Random random, int chunkX, int chunkZ) {
        ChunkPos startPos = this.getStartPositionForPosition(generator, random, chunkX, chunkZ, 0, 0);
        if (chunkX == startPos.x && chunkZ == startPos.z) {
            SharedSeedRandom seedRandom = (SharedSeedRandom) random;
            seedRandom.setLargeFeatureSeed(generator.getSeed(), chunkX, chunkZ);

            Metadata metadata = Metadata.generate(seedRandom, generator, chunkX, chunkZ);
            if (metadata.isValid()) {
                BiomeProvider biomeProvider = generator.getBiomeProvider();
                return biomeProvider.getBiomesInSquare((chunkX << 4) + 9, (chunkZ << 4) + 9, metadata.radius)
                        .stream()
                        .allMatch(biome -> generator.hasStructure(biome, this));
            }
        }

        return false;
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
        return CHUNK_RADIUS;
    }

    private static class Metadata {
        final int originX;
        final int originY;
        final int originZ;
        final int radius;

        Metadata(int originX, int originY, int originZ, int radius) {
            this.originX = originX;
            this.originY = originY;
            this.originZ = originZ;
            this.radius = radius;
        }

        static Metadata generate(SharedSeedRandom random, ChunkGenerator<?> generator, int chunkX, int chunkZ) {
            int originX = (chunkX << 4) + random.nextInt(16);
            int originZ = (chunkZ << 4) + random.nextInt(16);
            int originY = generator.func_222531_c(originX, originZ, Heightmap.Type.WORLD_SURFACE_WG);

            int radius = random.nextInt(MAX_RADIUS - MIN_RADIUS + 1) + MIN_RADIUS;

            return new Metadata(originX, originY, originZ, radius);
        }

        boolean isValid() {
            return this.originY < MAX_GENERATION_Y;
        }

        CompoundNBT serialize(CompoundNBT compound) {
            compound.putInt("origin_x", this.originX);
            compound.putInt("origin_y", this.originY);
            compound.putInt("origin_z", this.originZ);
            compound.putInt("radius", this.radius);
            return compound;
        }

        static Metadata deserialize(CompoundNBT compound) {
            int originX = compound.getInt("origin_x");
            int originY = compound.getInt("origin_y");
            int originZ = compound.getInt("origin_z");
            int radius = compound.getInt("radius");
            return new MoltenCraterStructure.Metadata(originX, originY, originZ, radius);
        }
    }

    public static class Start extends StructureStart {
        Start(Structure<?> structure, int chunkX, int chunkZ, Biome biome, MutableBoundingBox bounds, int reference, long seed) {
            super(structure, chunkX, chunkZ, biome, bounds, reference, seed);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManager, int chunkX, int chunkZ, Biome biome) {
            Metadata metadata = Metadata.generate(this.rand, generator, chunkX, chunkZ);
            this.components.add(new Piece(metadata));
            this.recalculateStructureSize();
        }
    }

    public static class Piece extends StructurePiece {
        private Metadata metadata;

        Piece(Metadata metadata) {
            super(MidnightStructurePieces.MOLTEN_CRATER, 0);
            this.metadata = metadata;
            this.initBounds();
        }

        public Piece(TemplateManager templateManager, CompoundNBT compound) {
            super(MidnightStructurePieces.MOLTEN_CRATER, compound);
            this.metadata = Metadata.deserialize(compound);
            this.initBounds();
        }

        private void initBounds() {
            int fullRadius = this.metadata.radius + EDGE_DEPTH;

            this.boundingBox = new MutableBoundingBox(
                    this.metadata.originX - fullRadius,
                    this.metadata.originY - fullRadius / SCALE_Y,
                    this.metadata.originZ - fullRadius,
                    this.metadata.originX + fullRadius,
                    this.metadata.originY + fullRadius / SCALE_Y,
                    this.metadata.originZ + fullRadius
            );
        }

        @Override
        protected void readAdditional(CompoundNBT compound) {
            this.metadata.serialize(compound);
        }

        @Override
        public boolean addComponentParts(IWorld world, Random random, MutableBoundingBox bounds, ChunkPos chunkPos) {
            int minX = Math.max(this.boundingBox.minX, bounds.minX);
            int maxX = Math.min(this.boundingBox.maxX, bounds.maxX);

            int minZ = Math.max(this.boundingBox.minZ, bounds.minZ);
            int maxZ = Math.min(this.boundingBox.maxZ, bounds.maxZ);

            int minY = Math.max(this.boundingBox.minY, 0);
            int maxY = Math.min(this.boundingBox.maxY, 255);

            if (minX >= maxX || minZ >= maxZ || minY >= maxY) {
                return true;
            }

            BlockPos minPos = new BlockPos(minX, minY, minZ);
            BlockPos maxPos = new BlockPos(maxX, maxY, maxZ);

            this.carveCrater(world, minPos, maxPos);
            this.decorateSurface(world, random, minPos, maxPos);

            return true;
        }

        private void carveCrater(IWorld world, BlockPos minPos, BlockPos maxPos) {
            int edgeRadius = this.metadata.radius + EDGE_DEPTH;

            int radiusSquared = this.metadata.radius * this.metadata.radius;
            int edgeRadiusSquared = edgeRadius * edgeRadius;
            int poolLevel = this.metadata.originY - (this.metadata.radius / SCALE_Y) + POOL_DEPTH;

            for (BlockPos pos : BlockPos.getAllInBoxMutable(minPos, maxPos)) {
                double noise = (NOISE_SAMPLER.get(pos.getX(), pos.getY(), pos.getZ()) + 1.0) * 8.0;
                double distanceSquared = this.computeDistanceToCenterSquared(pos.getX(), pos.getY(), pos.getZ()) + noise * noise;
                if (distanceSquared <= edgeRadiusSquared) {
                    if (distanceSquared < radiusSquared) {
                        this.carveCraterBlock(world, poolLevel, pos);
                    } else {
                        this.hardenEdgeBlock(world, pos);
                    }
                }
            }
        }

        private void carveCraterBlock(IWorld world, int poolLevel, BlockPos pos) {
            if (world.getBlockState(pos) == AIR) {
                return;
            }
            if (pos.getY() <= poolLevel) {
                world.setBlockState(pos, MIASMA, Constants.BlockFlags.NOTIFY_LISTENERS);
            } else {
                world.setBlockState(pos, AIR, Constants.BlockFlags.NOTIFY_LISTENERS);
            }
        }

        private void hardenEdgeBlock(IWorld world, BlockPos pos) {
            BlockState currentState = world.getBlockState(pos);
            if (currentState.isSolid()) {
                world.setBlockState(pos, SURFACE, Constants.BlockFlags.NOTIFY_LISTENERS);
            }
        }

        private void decorateSurface(IWorld world, Random random, BlockPos minPos, BlockPos maxPos) {
            int edgeRadius = this.metadata.radius + EDGE_DEPTH;
            int edgeRadiusSquared = edgeRadius * edgeRadius;

            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

            for (int z = minPos.getZ(); z <= maxPos.getZ(); z++) {
                for (int x = minPos.getX(); x <= maxPos.getX(); x++) {
                    int deltaX = x - this.metadata.originX;
                    int deltaZ = z - this.metadata.originZ;
                    int distanceSquared = deltaX * deltaX + deltaZ * deltaZ;

                    if (distanceSquared <= edgeRadiusSquared) {
                        int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING, x, z) - 1;
                        mutablePos.setPos(x, y, z);

                        if (world.getBlockState(mutablePos) == SURFACE) {
                            BlockState state = this.selectSurfaceState(random);
                            if (state != null) {
                                world.setBlockState(mutablePos, state, Constants.BlockFlags.NOTIFY_LISTENERS);
                            }
                        }
                    }
                }
            }
        }

        @Nullable
        private BlockState selectSurfaceState(Random random) {
            float value = random.nextFloat();
            if (value > 0.95F) {
                return MIASMA;
            } else if (value > 0.7F) {
                return MIASMA_SURFACE;
            }
            return null;
        }

        private double computeDistanceToCenterSquared(int x, int y, int z) {
            int deltaX = x - this.metadata.originX;
            int deltaY = Math.min(y - this.metadata.originY, 0) * SCALE_Y;
            int deltaZ = z - this.metadata.originZ;
            return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
        }
    }
}
