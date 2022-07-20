package com.mushroom.midnight.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.gui.config.ConfigInterfaceScreen;
import com.mushroom.midnight.common.capability.RifterCapturable;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.registry.MidnightEffects;
import com.mushroom.midnight.common.registry.MidnightParticleTypes;
import com.mushroom.midnight.common.registry.MidnightSounds;
import com.mushroom.midnight.common.registry.MidnightSurfaceBiomes;
import com.mushroom.midnight.common.util.EntityUtil;
import com.mushroom.midnight.common.util.MidnightUtil;
import com.mushroom.midnight.common.util.ResetHookHandler;
import com.mushroom.midnight.common.world.MidnightAtmosphereController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;

import static com.mushroom.midnight.client.ClientProxy.worldSetupConfig;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Dist.CLIENT)
public class ClientEventHandler {
    private static final Minecraft CLIENT = Minecraft.getInstance();

    private static final double HOOK_SENSITIVITY = 0.2;
    private static final ResetHookHandler<Double> SENSITIVITY_HOOK = new ResetHookHandler<>(HOOK_SENSITIVITY)
            .getValue(() -> CLIENT.gameSettings.mouseSensitivity)
            .setValue(sensitivity -> CLIENT.gameSettings.mouseSensitivity = sensitivity);

    private static final long AMBIENT_SOUND_INTERVAL = 140;
    private static final int AMBIENT_SOUND_CHANCE = 120;

    private static boolean hasGivenRewriteNotification = false;

    private static long lastAmbientSoundTime;

    private static ISound playingMusic;
    public static float flicker;
    public static float prevFlicker;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            prevFlicker = flicker;

            double targetFlicker = Math.random();
            targetFlicker *= targetFlicker;

            flicker += (targetFlicker - flicker) * 0.5;
        }

        // Access key press action in InputMappings directly as keybinds only respond when the game is not paused...
        if (MidnightKeybinds.MIDNIGHT_CONFIG.getKey().getKeyCode() > -1
                && InputMappings.isKeyDown(CLIENT.getMainWindow().getHandle(), MidnightKeybinds.MIDNIGHT_CONFIG.getKey().getKeyCode())
                && MidnightKeybinds.MIDNIGHT_CONFIG.getKeyModifier().isActive(MidnightKeybinds.MIDNIGHT_CONFIG.getKeyConflictContext())) {
            if (CLIENT.currentScreen instanceof IngameMenuScreen) {
                CLIENT.displayGuiScreen(new ConfigInterfaceScreen(CLIENT.currentScreen, MidnightConfig.MAIN_IFC.makeInterface(MidnightConfig.PROFILE)));
            } else if (CLIENT.currentScreen != null && CLIENT.currentScreen == currentCreateWorldScreen) {
                CLIENT.displayGuiScreen(new ConfigInterfaceScreen(CLIENT.currentScreen, MidnightConfig.SERVER_IFC.makeInterface(worldSetupConfig)));
            }
        }

        if (!CLIENT.isGamePaused()) {
            ClientPlayerEntity player = CLIENT.player;
            if (player == null) {
                return;
            }

            if (event.phase == TickEvent.Phase.END) {
                if (playingMusic != null && !CLIENT.getSoundHandler().isPlaying(playingMusic)) {
                    playingMusic = null;
                }

                if (MidnightUtil.isMidnightDimension(player.world)) {
                    spawnAmbientParticles(player);
                    playAmbientSounds(player);
                }

                SENSITIVITY_HOOK.apply(player.isPotionActive(MidnightEffects.STUNNED));
            }
        }
    }

    private static void cancelSleep(ClientPlayerEntity player) {
        ClientPlayNetHandler handler = player.connection;
        handler.sendPacket(new CEntityActionPacket(player, CEntityActionPacket.Action.STOP_SLEEPING));
    }

    private static void playAmbientSounds(PlayerEntity player) {
        Random rand = player.world.rand;
        long worldTime = player.world.getGameTime();

        if (worldTime - lastAmbientSoundTime > AMBIENT_SOUND_INTERVAL && rand.nextInt(AMBIENT_SOUND_CHANCE) == 0) {
            ResourceLocation ambientSound = MidnightSounds.AMBIENT.getName();

            float volume = (rand.nextFloat() * 0.4F + 0.8F) * MidnightConfig.client.ambientVolume.get().floatValue();
            float pitch = rand.nextFloat() * 0.6F + 0.7F;

            float x = (float) (player.getPosX() + rand.nextFloat() - 0.5F);
            float y = (float) (player.getPosY() + rand.nextFloat() - 0.5F);
            float z = (float) (player.getPosZ() + rand.nextFloat() - 0.5F);

            ISound sound = new SimpleSound(ambientSound, SoundCategory.AMBIENT, volume, pitch, false, 0, ISound.AttenuationType.NONE, x, y, z, false);
            CLIENT.getSoundHandler().play(sound);

            lastAmbientSoundTime = worldTime;
        }
    }

    @SubscribeEvent
    public static void onRenderFog(EntityViewRenderEvent.RenderFogEvent event) {
        Entity entity = event.getInfo().getRenderViewEntity();

        if (MidnightUtil.isMidnightDimension(entity.world)) {
            float farDistance = event.getFarPlaneDistance();

            float fogStart = (float) MidnightAtmosphereController.INSTANCE.getFogStart();
            float fogEnd = (float) MidnightAtmosphereController.INSTANCE.getFogEnd();

            GlStateManager.fogStart(Math.min(fogStart, farDistance));
            GlStateManager.fogEnd(Math.min(fogEnd, farDistance));
        }
    }

    @SubscribeEvent
    public static void onSetupFogDensity(EntityViewRenderEvent.RenderFogEvent.FogDensity event) {
        if (!FluidImmersionRenderer.immersedFluid.isEmpty()) {
            return;
        }

        LivingEntity entity = event.getInfo().getRenderViewEntity() instanceof LivingEntity ? (LivingEntity) event.getInfo().getRenderViewEntity() : null;
        if (entity != null && !entity.isPotionActive(Effects.BLINDNESS)) {
            if (entity.isPotionActive(MidnightEffects.DARKNESS)) {
                RenderSystem.fogMode(GlStateManager.FogMode.EXP);
                event.setCanceled(true);
                event.setDensity(0.15f);
            } else if (entity.isPotionActive(MidnightEffects.STUNNED)) {
                RenderSystem.fogMode(GlStateManager.FogMode.EXP);
                event.setCanceled(true);
                event.setDensity(0.15f);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSetupFogColor(EntityViewRenderEvent.FogColors event) {
        if (event.getInfo().getRenderViewEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getInfo().getRenderViewEntity();
            if (entity.isPotionActive(MidnightEffects.STUNNED)) {
                event.setRed(0.1F);
                event.setGreen(0.1F);
                event.setBlue(0.1F);
            } else if (entity.isPotionActive(MidnightEffects.DARKNESS)) {
                event.setRed(0f);
                event.setGreen(0f);
                event.setBlue(0f);
            }
        }
    }

    private static void spawnAmbientParticles(PlayerEntity player) {
        if (!MidnightConfig.client.ambientSporeParticles.get()) return;
        Random random = player.world.rand;
        double originX = player.getPosX();
        double originY = player.getPosY();
        double originZ = player.getPosZ();
        for (int i = 0; i < 6; i++) {
            double particleX = originX + (random.nextInt(24) - random.nextInt(24));
            double particleY = originY + (random.nextInt(24) - random.nextInt(24));
            double particleZ = originZ + (random.nextInt(24) - random.nextInt(24));
            double velocityX = (random.nextDouble() - 0.5) * 0.04;
            double velocityY = (random.nextDouble() - 0.5) * 0.04;
            double velocityZ = (random.nextDouble() - 0.5) * 0.04;
            player.world.addParticle(MidnightParticleTypes.AMBIENT_SPORE, particleX, particleY, particleZ, velocityX, velocityY, velocityZ);
        }
    }

    public static void onApplyRotations(LivingEntity entity, MatrixStack matrixStack) {
        boolean captured = RifterCapturable.isCaptured(entity);
        if (captured) {
            entity.limbSwing = 0.0F;
            entity.limbSwingAmount = entity.prevLimbSwingAmount = 0.0F;

            EntityUtil.Stance stance = EntityUtil.getStance(entity);
            if (stance == EntityUtil.Stance.QUADRUPEDAL) {
                matrixStack.translate(0.0F, entity.getHeight(), 0.0F);
                matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F));
                matrixStack.rotate(Vector3f.XP.rotationDegrees(180.0F));
            } else {
                matrixStack.translate(0.0F, entity.getWidth() / 2.0F, 0.0F);
                matrixStack.translate(0.0F, 0.0F, entity.getHeight() / 2.0F);
                matrixStack.rotate(Vector3f.XP.rotationDegrees(-90.0F));
            }
        }
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        if (!isMusicSound()) {
            return;
        }

        if (CLIENT.player != null && MidnightUtil.isMidnightDimension(CLIENT.player.world)) {
            SoundEvent sound = getMusicSound(CLIENT.player);
            if (sound == null || playingMusic != null) {
                event.setResultSound(null);
                return;
            }

            playingMusic = SimpleSound.music(sound);

            event.setResultSound(playingMusic);
        }
    }

    @Nullable
    private static SoundEvent getMusicSound(PlayerEntity player) {
        Biome biome = player.world.getBiome(player.getPosition());
        if (biome == MidnightSurfaceBiomes.CRYSTAL_SPIRES) {
            return MidnightSounds.MUSIC_CRYSTAL;
        } else if (biome == MidnightSurfaceBiomes.DECEITFUL_BOG) {
            return MidnightSounds.MUSIC_DARK_WILLOW;
        }
        return MidnightSounds.MUSIC_GENERIC;
    }

    private static boolean isMusicSound() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return Arrays.stream(stackTrace).anyMatch(e -> e.getClassName().equals(MusicTicker.class.getName()));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderVignetteOverLay(RenderGameOverlayEvent.Pre event) {
        if (MidnightConfig.client.hideVignetteEffect.get() && event.getType() == RenderGameOverlayEvent.ElementType.VIGNETTE) {
            if (MidnightUtil.isMidnightDimension(CLIENT.world)) {
                WorldBorder worldborder = CLIENT.world.getWorldBorder();
                float distWarn = Math.max(worldborder.getWarningDistance(), (float) Math.min(worldborder.getResizeSpeed() * worldborder.getWarningTime() * 1000d, Math.abs(worldborder.getTargetSize() - worldborder.getDiameter())));
                if (worldborder.getClosestDistance(CLIENT.player) >= distWarn) {
                    event.setCanceled(true);
                }
            }
        }
    }


    private static CreateWorldScreen currentCreateWorldScreen;

    @SubscribeEvent
    public static void onOpenGUIScreen(GuiOpenEvent event) {
        if (event.getGui() instanceof CreateWorldScreen && event.getGui() != currentCreateWorldScreen) {
            currentCreateWorldScreen = (CreateWorldScreen) event.getGui();
            worldSetupConfig = MidnightConfig.SERVER_PROFILE.makeTempProvider();
        }
    }

    @SubscribeEvent
    public static void serverStartingEvent(FMLServerStartingEvent event) {
        MidnightConfig.update();
    }
}
