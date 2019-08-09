package com.mushroom.midnight.common.world;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.surface.SurfaceBiome;
import com.mushroom.midnight.common.util.MidnightUtil;
import com.mushroom.midnight.common.world.util.TickableLerpedValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Dist.CLIENT)
public final class MidnightAtmosphereController {
    public static final MidnightAtmosphereController INSTANCE = new MidnightAtmosphereController();

    private static final Minecraft CLIENT = Minecraft.getInstance();

    private static final double COLOR_LERP_SPEED = 0.002;
    private static final double FOG_LERP_SPEED = 3.0;

    private static final Vec3d LIGHTING_SKY_COLOR = new Vec3d(1.0, 0.35, 0.25);
    private static final Vec3d UNDERGROUND_SKY_COLOR = new Vec3d(0.1, 0.1, 0.2);

    private final TickableLerpedValue surfaceSkyRed = TickableLerpedValue.atSpeed(COLOR_LERP_SPEED);
    private final TickableLerpedValue surfaceSkyGreen = TickableLerpedValue.atSpeed(COLOR_LERP_SPEED);
    private final TickableLerpedValue surfaceSkyBlue = TickableLerpedValue.atSpeed(COLOR_LERP_SPEED);

    private final TickableLerpedValue fogStart = TickableLerpedValue.atSpeed(FOG_LERP_SPEED);
    private final TickableLerpedValue fogEnd = TickableLerpedValue.atSpeed(FOG_LERP_SPEED);

    private MidnightAtmosphereController() {
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;

        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null || !MidnightUtil.isMidnightDimension(player.world)) return;

        INSTANCE.update(player);
    }

    private void update(PlayerEntity player) {
        int playerX = MathHelper.floor(player.posX);
        int playerZ = MathHelper.floor(player.posZ);

        Chunk chunk = player.world.getChunkProvider().func_225313_a(playerX >> 4, playerZ >> 4);
        if (chunk == null) return;

        Biome biome = chunk.getBiomes()[(playerX & 15) | (playerZ & 15) << 4];

        this.updateSkyColor(biome);
        this.updateFog(biome);
    }

    private void updateSkyColor(Biome biome) {
        int skyColor = biome.getSkyColorByTemp(0.0F);

        this.surfaceSkyRed.update((skyColor >> 16 & 0xFF) / 255.0);
        this.surfaceSkyGreen.update((skyColor >> 8 & 0xFF) / 255.0);
        this.surfaceSkyBlue.update((skyColor & 0xFF) / 255.0);
    }

    private void updateFog(Biome biome) {
        float fogStart = 20.0F;
        float fogEnd = 200.0F;

        if (biome instanceof SurfaceBiome) {
            SurfaceBiome surfaceBiome = (SurfaceBiome) biome;
            fogStart = surfaceBiome.getFogStart();
            fogEnd = surfaceBiome.getFogEnd();
        }

        this.fogStart.update(fogStart);
        this.fogEnd.update(fogEnd);
    }

    public Vec3d computeSkyColor() {
        float partialTicks = CLIENT.getRenderPartialTicks();

        double undergroundFactor = this.getUndergroundFactor();
        if (undergroundFactor >= 1.0) return UNDERGROUND_SKY_COLOR;

        Vec3d color = new Vec3d(
                this.surfaceSkyRed.get(partialTicks),
                this.surfaceSkyGreen.get(partialTicks),
                this.surfaceSkyBlue.get(partialTicks)
        );

        color = MidnightUtil.lerp(color, UNDERGROUND_SKY_COLOR, undergroundFactor);

        if (CLIENT.world.getLastLightningBolt() > 0) {
            color = MidnightUtil.lerp(color, LIGHTING_SKY_COLOR, 1.0 - undergroundFactor);
        }

        return color;
    }

    public double getFogStart() {
        double surface = this.fogStart.get(CLIENT.getRenderPartialTicks());
        double underground = 20.0;
        return surface + (underground - surface) * this.getUndergroundFactor();
    }

    public double getFogEnd() {
        double surface = this.fogEnd.get(CLIENT.getRenderPartialTicks());
        double underground = 200.0;
        return surface + (underground - surface) * this.getUndergroundFactor();
    }

    public double getUndergroundFactor() {
        ActiveRenderInfo renderInfo = CLIENT.gameRenderer.getActiveRenderInfo();
        double cameraY = renderInfo.getProjectedView().y;

        return MathHelper.clamp((60.0 - cameraY) / 20.0, 0.0, 1.0);
    }
}
