package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.StingerModel;
import com.mushroom.midnight.common.entity.creature.StingerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class StingerRenderer extends MobRenderer<StingerEntity, StingerModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/stinger.png");

    public StingerRenderer(EntityRendererManager manager) {
        super(manager, new StingerModel(), 0.15f);
    }

    @Override
    public void render(StingerEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {

        matrixStackIn.push();
        RenderSystem.enableCull();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        RenderSystem.disableCull();
        matrixStackIn.pop();
    }

    @Override
    @Nullable
    public ResourceLocation getEntityTexture(StingerEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(StingerEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        super.preRenderCallback(entitylivingbaseIn, matrixStackIn, partialTickTime);
        float scale = 0.3f + (entitylivingbaseIn.getGrowingAge() * 0.1f);
        matrixStackIn.scale(scale, scale, scale);
    }

    @Override
    protected float getDeathMaxRotation(StingerEntity entity) {
        return 180f;
    }
}
