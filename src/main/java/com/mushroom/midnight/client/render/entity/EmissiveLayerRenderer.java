package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
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
        GlStateManager.depthMask(true);
        GlStateManager.enableBlend();

        int brightness = this.brightnessFunction.apply(entitylivingbaseIn, partialTicks);
        RenderSystem.glMultiTexCoord2f(33986, brightness, brightness);
        float[] rgbF = MidnightUtil.getRGBColorF(colorFunction.getColor(entitylivingbaseIn, partialTicks));
        GlStateManager.color4f(rgbF[0], rgbF[1], rgbF[2], 1f);
        GlStateManager.disableLighting();

        this.getEntityModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        GlStateManager.enableLighting();
        float i = entitylivingbaseIn.getBrightness();
        float j = i % 65536;
        float k = i / 65536;
        RenderSystem.glMultiTexCoord2f(33986, (float) j, (float) k);
        //func_215334_a(entitylivingbaseIn);

        GlStateManager.disableBlend();
    }

    public interface BrightnessFunction<T extends LivingEntity> {
        int apply(T entity, float partialTicks);
    }

    public interface ColorFunction<T extends LivingEntity> {
        int getColor(T entity, float partialTicks);
    }
}
