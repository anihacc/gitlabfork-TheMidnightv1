package com.mushroom.midnight.common.mixin;

import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LightTexture.class)
public class LightTextureMixin
{
//    @ModifyVariable(at = @At(value = "FIELD", target = "net/minecraft/client/GameSettings.gamma:D", ordinal = 1), method = "updateLightmap(F)V")
//    private float updateLightmap(float f11, CallbackInfoReturnable<Float> callback) {
//        if (MidnightUtil.isMidnightDimension(Minecraft.getInstance().world)) {
//            return 0.0f;
//        }
//        return (float) f11;
//    }

    @ModifyVariable(at = @At("LOAD"), method = "updateLightmap(F)V", name = "f11", remap = false)
    public float modify$f11(float f11) {
        if (MidnightUtil.isMidnightDimension(Minecraft.getInstance().world)) {
            return 0.0f;
        }
        return f11;
    }
}
