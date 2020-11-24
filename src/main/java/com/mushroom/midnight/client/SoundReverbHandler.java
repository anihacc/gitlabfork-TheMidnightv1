package com.mushroom.midnight.client;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.world.World;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.EXTEfx;

public final class SoundReverbHandler {
    private static final Minecraft MC = Minecraft.getInstance();

    private static boolean available;
    private static boolean setup;

    private static int auxEffectSlot;

    static {
        ((IReloadableResourceManager) MC.getResourceManager()).addReloadListener((ISelectiveResourceReloadListener) (manager, predicate) -> setup = false);
    }

    public static void onPlaySound(int soundId) {
        if (!setup) {
            setupEffects();
            setup = true;
        }

        if (MidnightConfig.client.echoVolume.get() > 0 && available && shouldEcho(MC.world)) {
            AL11.alSource3i(soundId, EXTEfx.AL_AUXILIARY_SEND_FILTER, auxEffectSlot, 0, EXTEfx.AL_FILTER_NULL);
        }
    }

    private static void setupEffects() {
        available = AL.getCapabilities().ALC_EXT_EFX;
        if (!available) {
            Midnight.LOGGER.warn("Unable to setup reverb effects, AL EFX not supported!");
            return;
        }

        auxEffectSlot = EXTEfx.alGenAuxiliaryEffectSlots();
        EXTEfx.alAuxiliaryEffectSloti(auxEffectSlot, EXTEfx.AL_EFFECTSLOT_AUXILIARY_SEND_AUTO, AL10.AL_TRUE);
        EXTEfx.alAuxiliaryEffectSlotf(auxEffectSlot, EXTEfx.AL_EFFECTSLOT_GAIN, MidnightConfig.client.echoVolume.get().floatValue());

        int reverbEffectSlot = EXTEfx.alGenEffects();

        EXTEfx.alEffecti(reverbEffectSlot, EXTEfx.AL_EFFECT_TYPE, EXTEfx.AL_EFFECT_EAXREVERB);
        EXTEfx.alEffectf(reverbEffectSlot, EXTEfx.AL_EAXREVERB_DECAY_TIME, 6.0F);

        EXTEfx.alAuxiliaryEffectSloti(auxEffectSlot, EXTEfx.AL_EFFECTSLOT_EFFECT, reverbEffectSlot);
    }

    private static boolean shouldEcho(World world) {
        return MidnightUtil.isMidnightDimension(world);
    }
}
