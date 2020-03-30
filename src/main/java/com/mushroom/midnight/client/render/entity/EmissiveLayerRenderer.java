package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class EmissiveLayerRenderer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    private static final Minecraft CLIENT = Minecraft.getInstance();

    private final ResourceLocation texture;
    private final BrightnessFunction<T> brightnessFunction;
    private final ColorFunction<T> colorFunction;

    public EmissiveLayerRenderer(IEntityRenderer<T, M> renderer, ResourceLocation texture, BrightnessFunction<T> brightnessFunction, ColorFunction<T> colorFunction) {
        super(renderer);
        this.texture = texture;
        this.brightnessFunction = brightnessFunction;
        this.colorFunction = colorFunction;
    }

    public EmissiveLayerRenderer(IEntityRenderer<T, M> renderer, ResourceLocation texture, BrightnessFunction<T> brightnessFunction) {
        this(renderer, texture, brightnessFunction, (entity, partialTicks) -> 0xffffff);
    }

    public EmissiveLayerRenderer(IEntityRenderer<T, M> renderer, ResourceLocation texture) {
        this(renderer, texture, (entity, partialTicks) -> 240);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entitylivingbaseIn.isInvisible()) {
            return;
        }
        CLIENT.getTextureManager().bindTexture(this.texture);
        int brightness = this.brightnessFunction.apply(entitylivingbaseIn, partialTicks);
        float[] rgbF = MidnightUtil.getRGBColorF(colorFunction.getColor(entitylivingbaseIn, partialTicks));

        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(texture));

        this.getEntityModel().render(matrixStackIn, ivertexbuilder, brightness, OverlayTexture.NO_OVERLAY, rgbF[0], rgbF[1], rgbF[2], 1f);
    }

    public interface BrightnessFunction<T extends LivingEntity> {
        int apply(T entity, float partialTicks);
    }

    public interface ColorFunction<T extends LivingEntity> {
        int getColor(T entity, float partialTicks);
    }
}
