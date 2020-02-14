package com.mushroom.midnight.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RiftPortalBlockModel extends Model {
    private final ModelRenderer shape;

    public RiftPortalBlockModel() {
        super(RenderType::entityCutoutNoCull);
        this.textureWidth = 16;
        this.textureHeight = 16;
        this.shape = new ModelRenderer(this, 0, 0);
        this.shape.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape.addBox(-8.0F, -8.0F, -8.0F, 16, 15, 16, 0.0F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.shape).forEach((p_228272_8_) -> {
            p_228272_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
}
