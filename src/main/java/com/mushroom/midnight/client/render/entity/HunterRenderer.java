package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.HunterModel;
import com.mushroom.midnight.common.entity.creature.HunterEntity;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class HunterRenderer extends MobRenderer<HunterEntity, HunterModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/hunter.png");

    public HunterRenderer(EntityRendererManager manager) {
        super(manager, new HunterModel(), 0.5F);
    }

    @Override
    protected void applyRotations(HunterEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        float pitch = entityLiving.prevRotationPitch + (entityLiving.rotationPitch - entityLiving.prevRotationPitch) * partialTicks;
        float roll = entityLiving.prevRoll + (entityLiving.roll - entityLiving.prevRoll) * partialTicks;

        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(roll));

        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(-pitch));
    }

    @Override
    public ResourceLocation getEntityTexture(HunterEntity entity) {
        return TEXTURE;
    }
}
