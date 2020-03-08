package com.mushroom.midnight.common.world.spawner;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class MidnightAnimalSpawner {
    private int field_222698_b;

    public int tick(ServerWorld worldIn, boolean spawnHostileMobs, boolean spawnPeacefulMobs) {
        if (!spawnPeacefulMobs) {
            return 0;
        } else if (!worldIn.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            return 0;
        } else {
            Random random = worldIn.rand;
            --this.field_222698_b;
            if (this.field_222698_b > 0) {
                return 0;
            } else {
                this.field_222698_b += 600 + random.nextInt(200);

                int j = worldIn.getPlayers().size();
                if (j < 1) {
                    return 0;
                } else {
                    PlayerEntity playerentity = worldIn.getPlayers().get(random.nextInt(j));
                    if (playerentity.isSpectator()) {
                        return 0;
                    } else {
                        int k = (42 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                        int l = (42 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                        BlockPos.Mutable blockpos$mutable = (new BlockPos.Mutable(playerentity)).move(k, 0, l);
                        if (!worldIn.isAreaLoaded(blockpos$mutable.getX() - 10, blockpos$mutable.getY() - 10, blockpos$mutable.getZ() - 10, blockpos$mutable.getX() + 10, blockpos$mutable.getY() + 10, blockpos$mutable.getZ() + 10)) {
                            return 0;
                        } else {
                            Biome biome = worldIn.getBiome(blockpos$mutable);
                            Biome.Category biome$category = biome.getCategory();

                            int i1 = 0;
                            int j1 = (int) Math.ceil((double) worldIn.getDifficultyForLocation(blockpos$mutable).getAdditionalDifficulty()) + 1;

                            for (int k1 = 0; k1 < j1; ++k1) {
                                ++i1;
                                blockpos$mutable.setY(worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockpos$mutable).getY());
                                if (isAnimalLess(worldIn, blockpos$mutable)) {
                                    if (k1 == 0) {
                                        if (!this.spawnAnimal(worldIn, blockpos$mutable, random, true)) {
                                            break;
                                        }
                                    } else {
                                        this.spawnAnimal(worldIn, blockpos$mutable, random, false);
                                    }
                                } else {
                                    return 0;
                                }

                                blockpos$mutable.setX(blockpos$mutable.getX() + random.nextInt(5) - random.nextInt(5));
                                blockpos$mutable.setZ(blockpos$mutable.getZ() + random.nextInt(5) - random.nextInt(5));
                            }

                            return i1;
                        }
                    }

                }
            }
        }
    }

    private boolean isAnimalLess(ServerWorld worldIn, BlockPos p_221121_2_) {
        int i = 48;
        List<LivingEntity> list = worldIn.getEntitiesWithinAABB(LivingEntity.class, (new AxisAlignedBB(p_221121_2_)).grow(48.0D, 28.0D, 48.0D));
        if (list.size() < 2) {
            return true;
        }


        return false;
    }

    private boolean spawnAnimal(ServerWorld worldIn, BlockPos p_222695_2_, Random random, boolean p_222695_4_) {
        BlockState blockstate = worldIn.getBlockState(p_222695_2_);
        if (!WorldEntitySpawner.isSpawnableSpace(worldIn, p_222695_2_, blockstate, blockstate.getFluidState())) {
            return false;
        } else {
            ChunkGenerator<?> chunkgenerator = worldIn.getChunkProvider().getChunkGenerator();

            List<Biome.SpawnListEntry> list = worldIn.getBiome(p_222695_2_).getSpawns(EntityClassification.CREATURE);
            Biome.SpawnListEntry biome$spawnlistentry = null;

            int i2;
            ILivingEntityData ilivingentitydata = null;

            if (biome$spawnlistentry == null) {
                biome$spawnlistentry = WeightedRandom.getRandomItem(random, list);
            }

            if (biome$spawnlistentry != null) {
                i2 = biome$spawnlistentry.minGroupCount + worldIn.rand.nextInt(1 + biome$spawnlistentry.maxGroupCount - biome$spawnlistentry.minGroupCount);
                EntitySpawnPlacementRegistry.PlacementType entityspawnplacementregistry$placementtype = EntitySpawnPlacementRegistry.getPlacementType(biome$spawnlistentry.entityType);
                if (!canCreatureTypeSpawnAtLocation(entityspawnplacementregistry$placementtype, worldIn, p_222695_2_, biome$spawnlistentry.entityType) || !EntitySpawnPlacementRegistry.func_223515_a(biome$spawnlistentry.entityType, worldIn, SpawnReason.NATURAL, p_222695_2_, worldIn.rand) || !worldIn.func_226664_a_(biome$spawnlistentry.entityType.func_220328_a((double) p_222695_2_.getX(), (double) p_222695_2_.getY(), (double) p_222695_2_.getZ()))) {
                    return false;
                }

                for (int i = 0; i < i2; i++) {
                    Entity entity = biome$spawnlistentry.entityType.create(worldIn);

                    if (entity instanceof MobEntity) {
                        MobEntity mobentity = (MobEntity) entity;
                        if (mobentity.canSpawn(worldIn, SpawnReason.CHUNK_GENERATION) && mobentity.isNotColliding(worldIn)) {
                            ilivingentitydata = mobentity.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(new BlockPos(mobentity)), SpawnReason.CHUNK_GENERATION, ilivingentitydata, (CompoundNBT) null);
                        }
                    }
                    entity.setLocationAndAngles((double) p_222695_2_.getX(), (double) p_222695_2_.getY(), (double) p_222695_2_.getZ(), worldIn.rand.nextFloat() * 360.0F, 0.0F);
                    worldIn.addEntity(entity);
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType placeType, IWorldReader worldIn, BlockPos pos, @Nullable EntityType<?> entityTypeIn) {
        if (placeType == EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS) {
            return true;
        } else if (entityTypeIn != null && worldIn.getWorldBorder().contains(pos)) {
            return placeType.canSpawnAt(worldIn, pos, entityTypeIn);
        }
        return false;
    }
}