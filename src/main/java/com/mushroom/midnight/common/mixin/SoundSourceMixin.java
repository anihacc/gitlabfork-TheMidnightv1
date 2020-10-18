package com.mushroom.midnight.common.mixin;

import com.mushroom.midnight.client.SoundReverbHandler;
import net.minecraft.client.audio.SoundSource;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundSource.class)
public class SoundSourceMixin
{
    @Final
    @Shadow
    private int id;

    @Inject(at = @At("RETURN"), method = "updateSource(Lnet/minecraft/util/math/Vec3d;)V")
    private void updateSource(Vec3d vec3d, CallbackInfo callback) {
        SoundReverbHandler.onPlaySound(id);
    }
}
