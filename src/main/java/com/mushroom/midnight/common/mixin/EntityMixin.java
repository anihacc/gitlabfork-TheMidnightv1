package com.mushroom.midnight.common.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin
{
    @Shadow
    protected BlockPos getOnPosition() {
        throw new IllegalStateException("Mixin failed to shadow method getOnPosition()");
    }

    @Shadow
    private Vec3d getAllowedMovement(Vec3d vec)
    {
        throw new IllegalStateException("Mixin failed to shadow method getAllowedMovement()");
    }

    @Inject(at = @At(value = "INVOKE_ASSIGN", target = "net/minecraft/util/math/MathHelper.sqrt(D)F"), method = "move(Lnet/minecraft/entity/MoverType;Lnet/minecraft/util/math/Vec3d;)V")
    private void move(MoverType typeIn, Vec3d pos, CallbackInfo callback) {
        Entity entity = (Entity) (Object) this;
        Vec3d vec3d = this.getAllowedMovement(pos);
        BlockPos blockPos = this.getOnPosition();
        BlockState blockState = entity.world.getBlockState(blockPos);
        Block block = blockState.getBlock();

        double d1 = this.getAllowedMovement(pos).y;
        if (!modifyLadderNoises(blockState, blockPos, entity) && block != Blocks.LADDER && block != Blocks.SCAFFOLDING) {
            d1 = 0.0;
        }

        entity.distanceWalkedOnStepModified = (float)((double)entity.distanceWalkedOnStepModified + (double)MathHelper.sqrt(vec3d.x * vec3d.x + d1 * d1 + vec3d.z * vec3d.z) * 0.6D);

    }

    private static boolean modifyLadderNoises(BlockState block, BlockPos pos, Entity e) {
        LivingEntity le = null;
        if (e instanceof LivingEntity) {
            le = (LivingEntity) e;
        }

        return block.getBlock().isLadder(block, e.world, pos, le);
    }
}
