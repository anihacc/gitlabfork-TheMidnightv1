package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.RifterModel;
import com.mushroom.midnight.common.entity.creature.RifterEntity;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RifterRenderer extends MobRenderer<RifterEntity, RifterModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/rifter.png");

    public RifterRenderer(EntityRendererManager manager) {
        super(manager, new RifterModel(), 0.5F);
    }

    @Override
    protected void preRenderCallback(RifterEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        super.preRenderCallback(entitylivingbaseIn, matrixStackIn, partialTickTime);
        if (MidnightUtil.isMidnightDimension(entitylivingbaseIn.world)) {
            float scaleFactor = RifterEntity.HOME_SCALE_MODIFIER;
            GlStateManager.scalef(scaleFactor, scaleFactor, scaleFactor);
        }
    }

    @Override
    public ResourceLocation getEntityTexture(RifterEntity entity) {
        return TEXTURE;
    }
}
