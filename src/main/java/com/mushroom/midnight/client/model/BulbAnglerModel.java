package com.mushroom.midnight.client.model;

import com.google.common.collect.ImmutableList;
import com.mushroom.midnight.common.entity.creature.BulbAnglerEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BulbAnglerModel extends SegmentedModel<BulbAnglerEntity> {
    private final ModelPartAnimator animator = new ModelPartAnimator();
    public ModelRenderer body;
    public ModelRenderer mouseUp;
    public ModelRenderer mouseDown;
    public ModelRenderer body2;
    public ModelRenderer FinL;
    public ModelRenderer FinR;
    public ModelRenderer shape12;
    public ModelRenderer light;
    public ModelRenderer tail;

    public BulbAnglerModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.tail = new ModelRenderer(this, 38, 0);
        this.tail.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tail.addBox(-0.5F, -2.0F, 0.0F, 1, 4, 4, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 20.3F, 2.0F);
        this.body.addBox(-3.0F, -3.5F, -5.0F, 6, 7, 6, 0.0F);
        this.light = new ModelRenderer(this, 24, 13);
        this.light.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.light.addBox(-1.5F, -1.5F, -1.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(light, 0.6373942428283291F, 0.0F, 0.0F);
        this.body2 = new ModelRenderer(this, 24, 0);
        this.body2.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.body2.addBox(-2.0F, -2.5F, -3.0F, 4, 5, 3, 0.0F);
        this.FinR = new ModelRenderer(this, 48, 6);
        this.FinR.setRotationPoint(-3.0F, 1.0F, -3.0F);
        this.FinR.addBox(-1.0F, -1.5F, 0.0F, 1, 3, 3, 0.0F);
        this.mouseDown = new ModelRenderer(this, 0, 23);
        this.mouseDown.setRotationPoint(0.0F, 5.0F, -5.0F);
        this.mouseDown.addBox(-3.0F, -3.0F, -5.0F, 6, 1, 5, 0.0F);
        this.shape12 = new ModelRenderer(this, 24, 8);
        this.shape12.setRotationPoint(0.0F, -4.0F, -4.0F);
        this.shape12.addBox(-0.5F, -0.5F, -4.0F, 1, 1, 4, 0.0F);
        this.setRotateAngle(shape12, -0.6373942428283291F, 0.0F, 0.0F);
        this.mouseUp = new ModelRenderer(this, 0, 13);
        this.mouseUp.setRotationPoint(0.0F, 2.0F, -5.0F);
        this.mouseUp.addBox(-3.0F, -5.0F, -5.0F, 6, 5, 5, 0.0F);
        this.FinL = new ModelRenderer(this, 48, 0);
        this.FinL.setRotationPoint(3.0F, 1.0F, -3.0F);
        this.FinL.addBox(0.0F, -1.5F, 0.0F, 1, 3, 3, 0.0F);
        this.body2.addChild(this.tail);
        this.shape12.addChild(this.light);
        this.body.addChild(this.body2);
        this.body.addChild(this.FinR);
        this.body.addChild(this.mouseDown);
        this.mouseUp.addChild(this.shape12);
        this.body.addChild(this.mouseUp);
        this.body.addChild(this.FinL);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.body);
    }

    // TODO: NEEDS ATTENTION!
    @Override
    public void render(BulbAnglerEntity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch) {
        this.animator.bob(this.body, 0.125F, 0.25F, false, 0.0F, 0.0F, age, 1.0F);
        this.animator.swing(this.FinR, 0.125F, 0.125F, true, 0.0F, 0.5F, age, 1.0F);
        this.animator.swing(this.FinL, 0.125F, 0.125F, false, 0.0F, 0.5F, age, 1.0F);

        this.animator.swing(this.body2, 0.125F, 0.125F, true, 0.0F, 0.0F, age, 1.0F);
        this.animator.swing(this.tail, 0.125F, 0.15F, true, 0.0F, 0.0F, age, 1.0F);

    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
