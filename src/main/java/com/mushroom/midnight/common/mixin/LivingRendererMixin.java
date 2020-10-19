package com.mushroom.midnight.common.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mushroom.midnight.client.ClientEventHandler;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingRenderer.class)
public class LivingRendererMixin
{
    @Inject(at = @At("HEAD"), method = "applyRotations(Lnet/minecraft/entity/LivingEntity;Lcom/mojang/blaze3d/matrix/MatrixStack;FFF)V")
    private void applyRotations(LivingEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks, CallbackInfo callback) {
        ClientEventHandler.onApplyRotations(entityLiving, matrixStackIn);
    }
}
