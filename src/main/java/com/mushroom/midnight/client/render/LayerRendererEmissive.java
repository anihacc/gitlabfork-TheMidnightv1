package com.mushroom.midnight.client.render;

import com.mushroom.midnight.common.helper.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LayerRendererEmissive<T extends EntityLivingBase> implements LayerRenderer<T> {
    private static final Minecraft CLIENT = Minecraft.getMinecraft();

    private final ModelBase model;
    private final ResourceLocation texture;
    private final BrightnessFunction<T> brightnessFunction;
    private final ColorFunction<T> colorFunction;

    public LayerRendererEmissive(ModelBase model, ResourceLocation texture, BrightnessFunction<T> brightnessFunction, ColorFunction<T> colorFunction) {
        this.model = model;
        this.texture = texture;
        this.brightnessFunction = brightnessFunction;
        this.colorFunction = colorFunction;
    }

    public LayerRendererEmissive(ModelBase model, ResourceLocation texture, BrightnessFunction<T> brightnessFunction) {
        this(model, texture, brightnessFunction, (entity, partialTicks) -> 0xffffff);
    }

    public LayerRendererEmissive(ModelBase model, ResourceLocation texture) {
        this(model, texture, (entity, partialTicks) -> 240);
    }

    @Override
    public void doRenderLayer(T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entity.isInvisible()) {
            return;
        }
        CLIENT.getTextureManager().bindTexture(this.texture);
        GlStateManager.depthMask(true);
        GlStateManager.enableBlend();

        int brightness = this.brightnessFunction.apply(entity, partialTicks);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightness, brightness);
        float[] rgbF = Helper.getRGBColorF(colorFunction.getColor(entity, partialTicks));
        GlStateManager.color(rgbF[0], rgbF[1], rgbF[2], 1f);
        GlStateManager.disableLighting();

        this.model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        GlStateManager.enableLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, OpenGlHelper.lastBrightnessX, OpenGlHelper.lastBrightnessY);

        GlStateManager.disableBlend();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    public interface BrightnessFunction<T extends EntityLivingBase> {
        int apply(T entity, float partialTicks);
    }

    public interface ColorFunction<T extends EntityLivingBase> {
        int getColor(T entity, float partialTicks);
    }
}
