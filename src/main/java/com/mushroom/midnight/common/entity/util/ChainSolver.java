package com.mushroom.midnight.common.entity.util;

import com.mushroom.midnight.common.util.MatrixStack;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class ChainSolver<E extends LivingEntity> {
    private final Quaternion rootNode;

    private final Quaternion[] restingPoints;
    private final Quaternion[] transformedRestingPoints;

    private final Quaternion[] solvedNodes;
    private final Quaternion[] previousNodes;

    private final Vector3f[] nodeVelocity;

    private final float tightness;
    private final float dampening;

    private final TransformationMethod<E> transformationMethod;

    private final MatrixStack matrix = new MatrixStack(4);

    public ChainSolver(Quaternion rootNode, Quaternion[] restingPoints, float tightness, float dampening, TransformationMethod<E> transformationMethod) {
        this.rootNode = rootNode;
        this.restingPoints = this.relativize(restingPoints);
        this.tightness = tightness;
        this.dampening = dampening;

        this.transformedRestingPoints = new Quaternion[restingPoints.length];
        this.solvedNodes = new Quaternion[restingPoints.length];
        this.previousNodes = new Quaternion[restingPoints.length];
        this.nodeVelocity = new Vector3f[restingPoints.length];

        for (int i = 0; i < this.restingPoints.length; i++) {
            this.transformedRestingPoints[i] = new Quaternion(this.restingPoints[i]);
            this.solvedNodes[i] = Quaternion.ONE;
            this.previousNodes[i] = Quaternion.ONE;
            this.nodeVelocity[i] = new Vector3f();
        }

        this.transformationMethod = transformationMethod;
    }

    private Quaternion[] relativize(Quaternion[] restingPoints) {
        for (int i = restingPoints.length - 1; i >= 1; i--) {
            Quaternion point = restingPoints[i];
            Quaternion previousPoint = restingPoints[i - 1];
            point.set(point.getX() - previousPoint.getX(), point.getY() - previousPoint.getY(), point.getZ() - previousPoint.getZ(), point.getW());
        }
        return restingPoints;
    }

    public void update(E entity) {
        this.computeTransforms(entity);

        this.storePreviousState();

        for (int i = 0; i < this.restingPoints.length; i++) {
            Quaternion restingPoint = this.transformedRestingPoints[i];
            Quaternion previousPoint = i > 0 ? this.transformedRestingPoints[i - 1] : this.rootNode;

            Quaternion currentPoint = this.solvedNodes[i];
            float localX = currentPoint.getX() - previousPoint.getX();
            float localY = currentPoint.getY() - previousPoint.getY();
            float localZ = currentPoint.getZ() - previousPoint.getZ();

            Vector3f nodeVelocity = this.nodeVelocity[i];

            nodeVelocity.set(nodeVelocity.getX() * this.dampening, nodeVelocity.getY() * this.dampening, nodeVelocity.getZ() * this.dampening);


            nodeVelocity.add(this.tightness * (restingPoint.getX() - localX), this.tightness * (restingPoint.getY() - localY), this.tightness * (restingPoint.getZ() - localZ));


            currentPoint.set(currentPoint.getX() + nodeVelocity.getX(), currentPoint.getY() + nodeVelocity.getY(), currentPoint.getZ() + nodeVelocity.getZ(), currentPoint.getW());
        }
    }

    private void computeTransforms(E entity) {
        //TODO
        this.matrix.getLast().getNormalMatrix().setIdentity();
        this.transformationMethod.transform(entity, this.matrix);

        for (int i = 0; i < this.restingPoints.length; i++) {
            Quaternion restingPoint = this.restingPoints[i];
            Quaternion transformedPoint = this.transformedRestingPoints[i];
            transformedPoint.set(restingPoint.getX(), restingPoint.getY(), restingPoint.getZ(), restingPoint.getW());

            this.matrix.transform(transformedPoint);
        }
    }

    private void storePreviousState() {
        for (int i = 0; i < this.solvedNodes.length; i++) {
            this.previousNodes[i].set(this.solvedNodes[i].getX(), this.solvedNodes[i].getY(), this.solvedNodes[i].getZ(), this.solvedNodes[i].getW());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void apply(ModelRenderer[] parts, float partialTicks) {
        if (parts.length != this.solvedNodes.length) {
            throw new IllegalArgumentException("Expected " + this.solvedNodes.length + " parts but got " + parts.length);
        }

        // TODO: compute angles on update tick (keep solvedNodes global)
        for (int i = 0; i < this.solvedNodes.length; i++) {
            Quaternion solvedNode = this.solvedNodes[i];
            Quaternion connectedNode = i > 1 ? this.solvedNodes[i] : this.rootNode;

            float deltaX = solvedNode.getX() - connectedNode.getX();
            float deltaY = solvedNode.getY() - connectedNode.getY();
            float deltaZ = solvedNode.getZ() - connectedNode.getZ();

            float distanceHorizontal = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);

            ModelRenderer part = parts[i];
            part.rotateAngleY = (float) (Math.atan2(deltaZ, deltaX) - Math.PI / 2.0F);
            part.rotateAngleX = (float) Math.atan2(deltaY, distanceHorizontal);
        }
    }
}
