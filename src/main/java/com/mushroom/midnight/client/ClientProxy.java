package com.mushroom.midnight.client;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.render.block.BlockRenderLayer;
import com.mushroom.midnight.common.util.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.mushroom.midnight.Midnight.MODID;


@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientProxy implements IProxy {
    private static final Minecraft MC = Minecraft.getInstance();

    @Override
    public boolean isClientPlayer(Entity entity) {
        return entity == MC.player;
    }

    public static void setup(final FMLCommonSetupEvent event) {
        BlockRenderLayer.renderBlock();
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (event.getMap().getBasePath().equals(Atlases.CHEST_ATLAS)) {
            event.addSprite(new ResourceLocation(MODID, "entities/chest/shadowroot_chest_double"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/shadowroot_chest"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dark_willow_chest_double"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dark_willow_chest"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dead_wood_chest_double"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dead_wood_chest"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/nightshroom_chest_double"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/nightshroom_chest"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dewshroom_chest_double"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dewshroom_chest"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/viridshroom_chest_double"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/viridshroom_chest"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/bogshroom_chest_double"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/bogshroom_chest"));
        }
    }
}
