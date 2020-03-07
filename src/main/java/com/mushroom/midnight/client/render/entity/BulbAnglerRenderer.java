package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.BulbAnglerModel;
import com.mushroom.midnight.common.entity.creature.BulbAnglerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class BulbAnglerRenderer extends MobRenderer<BulbAnglerEntity, BulbAnglerModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/bulb_angler.png");
    private static final ResourceLocation EMISSIVE_TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/bulb_angler_emissive.png");

    private static final RenderType eye_renderType = RenderType.eyes(EMISSIVE_TEXTURE);


    public BulbAnglerRenderer(EntityRendererManager manager) {
        super(manager, new BulbAnglerModel(), 0f);
        this.addLayer(new AbstractEyesLayer<BulbAnglerEntity, BulbAnglerModel>(this) {
            @Override
            public RenderType getRenderType() {
                return eye_renderType;
            }
        });
    }


    private static int computeColor(BulbAnglerEntity entity, float partialTicks) {
        return 0xebe579;
    }

    private static int computeBrightness(BulbAnglerEntity entity, float partialTicks) {
        return 0xf0;
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(BulbAnglerEntity entity) {
        return TEXTURE;
    }


    @Override
    protected void preRenderCallback(BulbAnglerEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.translate(0f, 1.2f, 0.0f);
        super.preRenderCallback(entitylivingbaseIn, matrixStackIn, partialTickTime);
    }

    @Override
    protected void applyRotations(BulbAnglerEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        if (!entityLiving.isInWater()) {
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(90.0F));

        }
    }


    @Override
    protected float getDeathMaxRotation(BulbAnglerEntity entity) {
        return 180f;
    }
}
