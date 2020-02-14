// TODO: Reevaluate if this is needed
/*package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.biome.ConfigurableBiome;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.util.WeightedPool;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ChunkManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mushroom.midnight.Midnight.MIDNIGHT_MOB;

public final class MidnightEntitySpawner<T extends ConfigurableBiome> {
    private static final int MOB_COUNT_DIV = (int) Math.pow(17d, 2d);

    private static final long ANIMAL_SPAWN_INTERVAL = 400;

    private final Function<BlockPos, T> biomeFunction;
    private final PlacementLevel placementLevel;

    private final EntityClassification[] classifications;

    public MidnightEntitySpawner(
            Function<BlockPos, T> biomeFunction,
            PlacementLevel placementLevel,
            EntityClassification[] classifications
    ) {
        this.biomeFunction = biomeFunction;
        this.placementLevel = placementLevel;
        this.classifications = classifications;
    }

    public void spawnAroundPlayer(ServerWorld world, boolean spawnHostiles, boolean spawnPassives) {
        if (!world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) return;
        if (!spawnHostiles && !spawnPassives) return;

        int spawningChunks = world.getChunkProvider().ticketManager.func_219358_b();

        Collection<EntityClassification> validClassifications = Arrays.stream(this.classifications)
                .filter(classification -> this.shouldSpawnCreatureType(world, spawnHostiles, spawnPassives, classification))
                .collect(Collectors.toList());

        if (validClassifications.isEmpty()) return;

        EntityClassificationCount entityCount = EntityClassificationCount.count(world, validClassifications, entity -> {
            return this.placementLevel.containsY(world, MathHelper.floor(entity.posY));
        });

        ChunkManager chunkManager = world.getChunkProvider().chunkManager;
        chunkManager.func_223491_f().forEach(holder -> {
            ChunkPos chunkPos = holder.getPosition();
            if (chunkManager.isOutsideSpawningRadius(chunkPos) || !world.getWorldBorder().contains(chunkPos)) {
                return;
            }

            holder.func_219297_b().getNow(ChunkHolder.UNLOADED_CHUNK).left().ifPresent(chunk -> {
                for (EntityClassification classification : validClassifications) {
                    int maxCount = classification.getMaxNumberOfCreature() * spawningChunks / MOB_COUNT_DIV;
                    if (entityCount.getCount(classification) <= maxCount) {
                        this.spawnEntitiesInChunk(world, classification, chunkPos);
                    }
                }
            });
        });
    }

    private boolean shouldSpawnCreatureType(World world, boolean spawnHostiles, boolean spawnPassives, EntityClassification classification) {
        if (classification.getPeacefulCreature() && !spawnPassives) return false;
        if (!classification.getPeacefulCreature() && spawnHostiles) return false;

        // TODO: redo
        if (classification == MIDNIGHT_MOB) {
            return world.getGameTime() % MidnightConfig.general.monsterSpawnRate.get() == 0;
        }

        return !classification.getAnimal() || world.getGameTime() % ANIMAL_SPAWN_INTERVAL == 0;
    }

    public void populateChunk(World world, int chunkX, int chunkZ, Random random) {
        int globalX = chunkX << 4;
        int globalZ = chunkZ << 4;

        int originX = globalX + 8;
        int originZ = globalZ + 8;

        BlockPos centerPos = new BlockPos(globalX + 16, 0, globalZ + 16);
        T biome = this.biomeFunction.apply(centerPos);

        // TODO Spawner
        SpawnerConfig spawnerConfig = biome.getSpawnerConfig();
        if (spawnerConfig.isEmpty()) {
            return;
        }

        while (random.nextFloat() < spawnerConfig.getSpawnChance()) {
            WeightedPool<Biome.SpawnListEntry> pool = spawnerConfig.getPool(EntityClassification.CREATURE);
            Biome.SpawnListEntry entry = pool.pick(world.rand);
            if (entry == null) {
                continue;
            }

            this.spawnGroupInChunk(world, originX, originZ, random, entry);
        }
    }

    private void spawnEntitiesInChunk(ServerWorld world, EntityClassification classification, ChunkPos chunkPos) {
        BlockPos pos = this.getRandomPositionInChunk(world, chunkPos);
        if (pos == null) return;

        BlockState state = world.getBlockState(pos);
        if (state.isNormalCube(world, pos)) return;

        this.spawnEntitiesAround(world, pos, classification);
    }

    private void spawnEntitiesAround(ServerWorld world, BlockPos pos, EntityClassification classification) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();

        int spawnedEntities = 0;

        for (int i = 0; i < 3; i++) {
            int range = 6;
            Biome.SpawnListEntry selectedEntry = null;
            ILivingEntityData livingData = null;

            mutablePos.setPos(pos);

            int spawnGroupSize = MathHelper.ceil(Math.random() * 4.0);
            for (int groupIndex = 0; groupIndex < spawnGroupSize; groupIndex++) {
                mutablePos.setPos(
                        mutablePos.getX() + world.rand.nextInt(range) - world.rand.nextInt(range),
                        mutablePos.getY() + world.rand.nextInt(1) - world.rand.nextInt(1),
                        mutablePos.getZ() + world.rand.nextInt(range) - world.rand.nextInt(range)
                );

                if (world.isPlayerWithin(mutablePos.getX() + 0.5, mutablePos.getY(), mutablePos.getZ() + 0.5, 24)) {
                    continue;
                }

                if (selectedEntry == null) {
                    T biome = this.biomeFunction.apply(pos);
                    WeightedPool<Biome.SpawnListEntry> pool = biome. ().getPool(classification);

                    selectedEntry = pool.pick(world.rand);
                    if (selectedEntry == null) {
                        break;
                    }
                }

                if (this.canSpawnAt(world, classification, mutablePos, selectedEntry)) {
                    MobEntity creature = this.createEntity(world, mutablePos, selectedEntry);

                    float spawnX = mutablePos.getX() + 0.5F;
                    float spawnY = mutablePos.getY();
                    float spawnZ = mutablePos.getZ() + 0.5F;

                    Event.Result spawnResult = ForgeEventFactory.canEntitySpawn(creature, world, spawnX, spawnY, spawnZ, null);
                    if (spawnResult != Event.Result.ALLOW && spawnResult != Event.Result.DEFAULT) {
                        continue;
                    }

                    if (creature.canSpawn(world, SpawnReason.NATURAL) && creature.isNotColliding(world)) {
                        if (!ForgeEventFactory.doSpecialSpawn(creature, world, spawnX, spawnY, spawnZ, null)) {
                            livingData = creature.onInitialSpawn(world, world.getDifficultyForLocation(new BlockPos(creature)), SpawnReason.NATURAL, livingData, null);
                        }

                        if (creature.isNotColliding(world)) {
                            spawnedEntities++;
                            world.addEntity(creature);
                        } else {
                            creature.remove();
                        }

                        if (spawnedEntities >= ForgeEventFactory.getMaxSpawnPackSize(creature)) {
                            return;
                        }
                    }
                }
            }
        }
    }

    @Nullable
    private static Biome.SpawnListEntry pickSpawnEntry(ChunkGenerator<?> generator, EntityClassification classification, Random random, BlockPos pos, World world) {
        List<Biome.SpawnListEntry> possibleSpawns = generator.getPossibleCreatures(classification, pos);
        possibleSpawns = ForgeEventFactory.getPotentialSpawns(world, classification, pos, possibleSpawns);

        if (possibleSpawns == null || possibleSpawns.isEmpty()) return null;

        return WeightedRandom.getRandomItem(random, possibleSpawns);
    }

    private void spawnGroupInChunk(World world, int originX, int originZ, Random random, Biome.SpawnListEntry spawnEntry) {
        int groupSize = spawnEntry.minGroupCount + random.nextInt(spawnEntry.maxGroupCount - spawnEntry.minGroupCount + 1);
        int centerX = originX + random.nextInt(16);
        int centerZ = originZ + random.nextInt(16);

        int x = centerX;
        int z = centerZ;

        ILivingEntityData livingData = null;

        for (int i = 0; i < groupSize; i++) {
            for (int attempt = 0; attempt < 4; attempt++) {
                boolean spawnedEntity = false;

                BlockPos pos = this.placementLevel.getSurfacePos(world, Heightmap.Type.MOTION_BLOCKING, new BlockPos(x, 0, z));
                EntityType<?> entityType = spawnEntry.entityType;
                if (canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.getPlacementType(entityType), world, pos, entityType)) {
                    MobEntity creature = this.createEntity(world, pos, spawnEntry);
                    if (ForgeEventFactory.canEntitySpawn(creature, world, x + 0.5F, pos.getY(), z + 0.5F, null) == Event.Result.DENY) {
                        creature.remove();
                        continue;
                    }

                    world.addEntity(creature);
                    livingData = creature.onInitialSpawn(world, world.getDifficultyForLocation(new BlockPos(creature)), SpawnReason.NATURAL, livingData, null);
                    spawnedEntity = true;
                }

                x += random.nextInt(5) - random.nextInt(5);
                z += random.nextInt(5) - random.nextInt(5);

                while (x < originX || x >= originX + 16 || z < originZ || z >= originZ + 16) {
                    x = centerX + random.nextInt(5) - random.nextInt(5);
                    z = centerZ + random.nextInt(5) - random.nextInt(5);
                }

                if (spawnedEntity) {
                    break;
                }
            }
        }
    }

    private MobEntity createEntity(World world, BlockPos pos, Biome.SpawnListEntry selectedEntry) {
        MobEntity entity = (MobEntity) selectedEntry.entityType.create(world);
        float yaw = world.rand.nextFloat() * 360.0F;
        entity.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, yaw, 0.0F);
        return entity;
    }

    private BlockPos getRandomPositionInChunk(World world, ChunkPos chunkPos) {
        int x = chunkPos.getXStart() + world.rand.nextInt(16);
        int z = chunkPos.getZStart() + world.rand.nextInt(16);

        BlockPos pos = new BlockPos(x, 0, z);
        BlockPos surfacePos = this.placementLevel.getSurfacePos(world, Heightmap.Type.MOTION_BLOCKING, pos);

        int surfaceY = MathHelper.roundUp(surfacePos.getY() + 1, 16);

        int y = world.rand.nextInt(surfaceY);
        if (!this.placementLevel.containsY(world, y)) return null;

        return new BlockPos(x, y, z);
    }

    private static boolean canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType placeType, IWorldReader world, BlockPos pos, @Nullable EntityType<?> entityType) {
        if (placeType == EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS) {
            return true;
        } else if (entityType != null && world.getWorldBorder().contains(pos)) {
            return placeType.canSpawnAt(world, pos, entityType);
        }
        return false;
    }
}*/
