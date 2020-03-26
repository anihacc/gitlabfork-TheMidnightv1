package com.mushroom.midnight.common.entity.util;

import com.mushroom.midnight.common.entity.creature.RifterEntity;
import com.mushroom.midnight.common.util.EntityUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class DragSolver {
    private final RifterEntity owner;
    private final AttachmentSolver attachmentSolver;

    private LivingEntity dragged;

    private DragVector3d attachmentPoint;
    private DragVector3d prevAttachmentPoint;

    public DragSolver(RifterEntity owner) {
        this.owner = owner;
        this.attachmentSolver = new AttachmentSolver(this.owner);
    }

    public void setDragged(LivingEntity dragged) {
        if (this.dragged != null) {
            this.resetDragged(this.dragged);
        }
        this.dragged = dragged;
        if (dragged != null) {
            this.initDragged(dragged);
        }
    }

    private void initDragged(LivingEntity entity) {
        entity.noClip = true;
    }

    private void resetDragged(LivingEntity entity) {
        entity.noClip = false;
        entity.setMotion(new Vec3d(entity.getPosX() - entity.prevPosX, entity.getPosY() - entity.prevPosY, entity.getPosZ() - entity.prevPosZ));
    }

    public void solveDrag() {
        if (this.dragged == null) {
            this.attachmentPoint = null;
            return;
        }

        this.prevAttachmentPoint = this.attachmentPoint;

        float entityWidth = this.getTransformedWidth();

        float dragOffset = (this.owner.getWidth() + entityWidth) / 2.0F + 0.2F;

        float theta = (float) Math.toRadians(this.owner.renderYawOffset);
        double dragOriginX = MathHelper.sin(theta) * dragOffset;
        double dragOriginZ = -MathHelper.cos(theta) * dragOffset;

        this.attachmentSolver.getAttachmentPoint().moveTo(dragOriginX, 0.0, dragOriginZ);

        this.solveRotation(this.dragged);

        this.dragged.noClip = false;
        try {
            AttachmentSolver.Result result = this.attachmentSolver.solveAttachment(this.dragged);
            this.attachmentPoint = result.getSnappedPoint();
        } finally {
            this.dragged.noClip = true;
            this.dragged.setMotion(Vec3d.ZERO);
        }
    }

    private float getTransformedWidth() {
        EntityUtil.Stance stance = EntityUtil.getStance(this.dragged);
        return stance == EntityUtil.Stance.QUADRUPEDAL ? this.dragged.getWidth() : this.dragged.getHeight();
    }

    private void solveRotation(LivingEntity entity) {
        entity.setRenderYawOffset(this.owner.rotationYaw);

        float deltaYaw = this.owner.rotationYaw - this.owner.prevRotationYaw;

        entity.rotationYaw += deltaYaw;
        entity.setRotationYawHead(entity.getRotationYawHead() + deltaYaw);
    }

    @Nullable
    public DragVector3d getAttachmentPoint() {
        return this.attachmentPoint;
    }

    @Nullable
    public DragVector3d lerpAttachmentPoint(float partialTicks) {
        if (this.attachmentPoint == null) {
            return null;
        } else if (this.prevAttachmentPoint == null) {
            this.prevAttachmentPoint = this.attachmentPoint;
        }

        DragVector3d lerp = new DragVector3d(0, 0, 0);
        //TODO better interpolate?
        this.interpolate(lerp, this.prevAttachmentPoint, this.attachmentPoint, (double) partialTicks);
        return lerp;
    }

    protected void interpolate(DragVector3d DragVector3d, DragVector3d a, DragVector3d b, double x) {
        double t = x;
        DragVector3d.x = (1.0 - t) * a.x + t * b.x;
        DragVector3d.y = (1.0 - t) * a.y + t * b.y;
        DragVector3d.z = (1.0 - t) * a.z + t * b.z;
    }
}
