package com.mushroom.midnight.common.mixin;

import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Entity.class)
public abstract class EntityMixin
{
    @Shadow
    protected Vec3d maybeBackOffFromEdge(Vec3d p_225514_1_, MoverType p_225514_2_) {
        throw new IllegalStateException("Mixin failed to shadow method maybeBackOffFromEdge()");
    }

    @Shadow
    private Vec3d getAllowedMovement(Vec3d vec)
    {
        throw new IllegalStateException("Mixin failed to shadow method getAllowedMovement()");
    }

    @Shadow
    protected BlockPos getOnPosition() {
        throw new IllegalStateException("Mixin failed to shadow method getOnPosition()");
    }

    @ModifyVariable(at = @At("STORE"), method = "move(Lnet/minecraft/entity/MoverType;Lnet/minecraft/util/math/Vec3d;)V", index = 10)
    private double modify$d1(double d1, MoverType typeIn, Vec3d pos) {
        Entity entity = (Entity) (Object) this;
        World world = entity.world;

        pos = this.maybeBackOffFromEdge(pos, typeIn);
        Vec3d vec3d = this.getAllowedMovement(pos);

        BlockPos blockPos = this.getOnPosition();
        BlockState blockState = world.getBlockState(blockPos);

        if (!blockState.getBlock().isLadder(blockState, world, blockPos, getLivingEntity(entity))) {
            return 0.0D;
        }

        return vec3d.y;
    }

    @Nullable
    private static LivingEntity getLivingEntity(Entity entity) {
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }
}
