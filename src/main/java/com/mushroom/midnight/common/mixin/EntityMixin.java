package com.mushroom.midnight.common.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import javax.annotation.Nullable;

@Mixin(Entity.class)
public abstract class EntityMixin
{
    /**
     * @see Entity#maybeBackOffFromEdge(Vec3d, MoverType)
     * @see net.minecraft.entity.player.PlayerEntity#maybeBackOffFromEdge(Vec3d, MoverType)
     */
    @Shadow
    protected Vec3d maybeBackOffFromEdge(Vec3d p_225514_1_, MoverType p_225514_2_) {
        throw new IllegalStateException("Mixin failed to shadow method maybeBackOffFromEdge()");
    }

    /**
     * @see Entity#getAllowedMovement(Vec3d)
     */
    @Shadow
    private Vec3d getAllowedMovement(Vec3d vec) {
        throw new IllegalStateException("Mixin failed to shadow method getAllowedMovement()");
    }

    /**
     * @see Entity#getOnPosition()
     */
    @Shadow
    protected BlockPos getOnPosition() {
        throw new IllegalStateException("Mixin failed to shadow method getOnPosition()");
    }

    /**
     * This mixin method overrides the original {@code d1} double variable by having its own check for whether or not it
     * should use {@code 0.0D} or {@link Vec3d#y}. It modifies the variable after the last time the variable has a new
     * value stored into it (hence the use of {@link org.spongepowered.asm.mixin.injection.modify.AfterStoreLocal} in
     * the {@link At} annotation).
     *
     * @param d1     The original {@code d1} variable that the Mixin reads into this method. Completely ignored since we
     *               return one of our own values.
     * @param typeIn The given {@link MoverType} from the {@link Entity#move(MoverType, Vec3d)} method to be used in the
     *               {@link Entity#maybeBackOffFromEdge(Vec3d, MoverType)} method.
     * @param pos    The given {@link Vec3d} from the {@link Entity#move(MoverType, Vec3d)} method to be used in the
     *               {@link Entity#getAllowedMovement(Vec3d)} method from which a new {@link Vec3d} is created.
     * @return {@link Vec3d#y} of a newly created {@link Vec3d} if the {@link net.minecraft.block.Block} the
     * {@link Entity} is currently moving on is a ladder or {@code 0.0D} otherwise. The
     * {@link net.minecraft.block.Block#isLadder(BlockState, IWorldReader, BlockPos, LivingEntity)} method is used for
     * this reason and DOES account for {@link net.minecraft.block.Blocks#LADDER} and
     * {@link net.minecraft.block.Blocks#SCAFFOLDING}.
     */
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

    /**
     * Attempts to cast a given {@link Entity} to a {@link LivingEntity}. If it fails, it will return {@code null},
     * hence why this method is annotated as {@link Nullable}.
     *
     * @param entity The given {@link Entity} to cast into a {@link LivingEntity}.
     * @return The result of the cast attempt.
     */
    @Nullable
    private static LivingEntity getLivingEntity(Entity entity) {
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }
}
