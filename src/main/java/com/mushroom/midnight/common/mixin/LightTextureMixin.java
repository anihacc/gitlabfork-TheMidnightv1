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
    @ModifyVariable(at = @At("LOAD"), method = "updateLightmap(F)V", index = 16)
    private float modify$f11(float f11) {
        if (MidnightUtil.isMidnightDimension(Minecraft.getInstance().world)) {
            return 0.0f;
        }
        return f11;
    }
}
