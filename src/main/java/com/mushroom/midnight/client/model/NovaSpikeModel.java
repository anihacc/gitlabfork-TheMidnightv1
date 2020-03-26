package com.mushroom.midnight.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class NovaSpikeModel<T extends Entity> extends SegmentedModel<T> {
    public ModelRenderer spike;
    public ModelRenderer spike2;

    public NovaSpikeModel() {
        this.textureWidth = 32;
        this.textureHeight = 16;
        this.spike2 = new ModelRenderer(this, 14, 0);
        this.spike2.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.spike2.addBox(-0.5F, -0.5F, -4.0F, 1, 1, 4, 0.0F);
        this.spike = new ModelRenderer(this, 0, 0);
        this.spike.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.spike.addBox(-1.0F, -1.0F, -1.5F, 2, 2, 5, 0.0F);
        this.setRotateAngle(spike, 1.5707963267948966F, 0.0F, 0.0F);
        this.spike.addChild(this.spike2);
    }

    // TODO: NEEDS ATTENTION!
    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.spike);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
