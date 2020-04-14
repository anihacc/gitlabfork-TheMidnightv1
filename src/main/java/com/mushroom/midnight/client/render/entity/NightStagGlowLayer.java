package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mushroom.midnight.client.model.NightStagModel;
import com.mushroom.midnight.client.shader.postfx.LightSource;
import com.mushroom.midnight.client.shader.postfx.ShaderManager;
import com.mushroom.midnight.common.entity.creature.NightStagEntity;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.math.MathHelper;

public class NightStagGlowLayer extends LayerRenderer<NightStagEntity, NightStagModel> {
    public NightStagGlowLayer(IEntityRenderer<NightStagEntity, NightStagModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(MatrixStack stack, IRenderTypeBuffer buff, int light, NightStagEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        float x = (float) MathHelper.lerp(partialTicks, entity.lastTickPosX, entity.getPosX());
        float y = (float) MathHelper.lerp(partialTicks, entity.lastTickPosY, entity.getPosY());
        float z = (float) MathHelper.lerp(partialTicks, entity.lastTickPosZ, entity.getPosZ());

        int color = NightStagRenderer.computeColor(entity, partialTicks);
        float[] rgb = MidnightUtil.getRGBColorF(color);
        float brightness = NightStagRenderer.computeBrightness(entity, partialTicks) / 255F;

        LightSource src = new LightSource(
                0 + x, entity.getEyeHeight() + y, 0 + z,
                rgb[0], rgb[1], rgb[2],
                1.2F * brightness,
                6, LightSource.LIGHT
        );
        ShaderManager.addLight(src);
    }
}
