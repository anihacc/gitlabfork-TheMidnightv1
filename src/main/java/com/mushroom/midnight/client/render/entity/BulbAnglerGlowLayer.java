package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mushroom.midnight.client.model.BulbAnglerModel;
import com.mushroom.midnight.client.shader.postfx.LightSource;
import com.mushroom.midnight.client.shader.postfx.ShaderManager;
import com.mushroom.midnight.common.entity.creature.BulbAnglerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.math.MathHelper;

public class BulbAnglerGlowLayer extends LayerRenderer<BulbAnglerEntity, BulbAnglerModel> {
    public BulbAnglerGlowLayer(IEntityRenderer<BulbAnglerEntity, BulbAnglerModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(MatrixStack stack, IRenderTypeBuffer buff, int light, BulbAnglerEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Vector4f location = new Vector4f(0, 0.2F, 0.6F, 0);
        stack.push();
        stack.getLast().getMatrix().setIdentity();
        float yaw = MathHelper.lerp(partialTicks, entity.prevRotationYaw, entity.rotationYaw);
        stack.rotate(Vector3f.YP.rotationDegrees(-yaw));

        stack.translate(0, 1.2F, 0);
        if (!entity.isInWater()) {
            stack.rotate(Vector3f.ZP.rotationDegrees(-90));
        }

        location.transform(stack.getLast().getMatrix());
        float x = (float) MathHelper.lerp(partialTicks, entity.lastTickPosX, entity.getPosX());
        float y = (float) MathHelper.lerp(partialTicks, entity.lastTickPosY, entity.getPosY());
        float z = (float) MathHelper.lerp(partialTicks, entity.lastTickPosZ, entity.getPosZ());

        LightSource src = new LightSource(
                location.getX() + x,
                location.getY() + y,
                location.getZ() + z,
                255 / 255F,
                246 / 255F,
                222 / 255F,
                2.4F,
                3,
                LightSource.LIGHT
        );
        ShaderManager.addLight(src);

        stack.pop();
    }
}
