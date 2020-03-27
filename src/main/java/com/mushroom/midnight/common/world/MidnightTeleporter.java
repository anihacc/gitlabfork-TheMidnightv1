package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.registry.MidnightDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public class MidnightTeleporter {
    public static final MidnightTeleporter INSTANCE = new MidnightTeleporter();

    private MidnightTeleporter() {
    }

    public void teleport(Entity entity) {
        MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        if (server == null) {
            return;
        }

        DimensionType endpointDimension = this.getEndpointDimension(entity.dimension);
        ServerWorld endpointWorld = server.getWorld(endpointDimension);

        BlockPos pos = entity.getPosition();
        IChunk chunk = endpointWorld.getChunk(pos);
        int surfaceY = chunk.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ()) + 1;

        Vec3d endpointPos = new Vec3d(pos.getX() + 0.5, surfaceY, pos.getZ() + 0.5);
        Entity teleportedEntity = this.teleportEntity(entity, endpointWorld, endpointPos);
        teleportedEntity.fallDistance = 0.0F;
    }

    private Entity teleportEntity(Entity entity, ServerWorld endpointWorld, Vec3d endpoint) {
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            player.teleport(endpointWorld, endpoint.x, endpoint.y, endpoint.z, entity.rotationYaw, entity.rotationPitch);
            return player;
        }

        entity.detach();
        entity.dimension = endpointWorld.dimension.getType();

        Entity teleportedEntity = entity.getType().create(endpointWorld);
        if (teleportedEntity == null) {
            return entity;
        }

        teleportedEntity.copyDataFromOld(entity);
        teleportedEntity.setLocationAndAngles(endpoint.x, endpoint.y, endpoint.z, entity.rotationYaw, entity.rotationPitch);
        teleportedEntity.setRotationYawHead(entity.rotationYaw);
        endpointWorld.addFromAnotherDimension(teleportedEntity);

        return teleportedEntity;
    }

    private DimensionType getEndpointDimension(DimensionType source) {
        return source.getModType() == MidnightDimensions.MIDNIGHT ? DimensionType.OVERWORLD : MidnightDimensions.midnight();
    }
}
