package com.mushroom.midnight.common.world.feature.placement;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.world.feature.structure.MoltenCraterStructure;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DragonNestPlacement extends Placement<NoPlacementConfig> {
    public DragonNestPlacement(Function<Dynamic<?>, ? extends NoPlacementConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, NoPlacementConfig config, BlockPos origin) {
        int chunkX = origin.getX() >> 4;
        int chunkZ = origin.getZ() >> 4;

        IChunk chunk = world.getChunk(chunkX, chunkZ, ChunkStatus.STRUCTURE_REFERENCES);
        LongSet references = chunk.getStructureReferences(MoltenCraterStructure.NAME);
        if (references.isEmpty()) {
            return Stream.empty();
        }

        return IntStream.range(0, 64).mapToObj(i -> {
            int x = random.nextInt(16);
            int z = random.nextInt(16);

            int maxY = SurfacePlacementLevel.INSTANCE.getSurfacePos(world, Heightmap.Type.MOTION_BLOCKING, origin.add(x, 0, z)).getY() + 32;

            int y = random.nextInt(maxY);
            if (!SurfacePlacementLevel.INSTANCE.containsY(world, y)) return null;

            return origin.add(x, y, z);
        }).filter(Objects::nonNull);
    }
}
