package com.mushroom.midnight.client;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.render.block.BlockRenderLayer;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.config.provider.IConfigProvider;
import com.mushroom.midnight.common.util.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static com.mushroom.midnight.Midnight.MODID;


@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientProxy implements IProxy {
    private static final Minecraft MC = Minecraft.getInstance();
    public static boolean enqueuedServerConfigUpdate;
    public static IConfigProvider worldSetupConfig;

    @Override
    public boolean isClientPlayer(Entity entity) {
        return entity == MC.player;
    }

    public static void setup(FMLCommonSetupEvent event) {
        BlockRenderLayer.renderBlock();
    }

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        MidnightKeybinds.registerKeybinds();
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (event.getMap().getTextureLocation().equals(Atlases.CHEST_ATLAS)) {
            event.addSprite(new ResourceLocation(MODID, "entities/chest/shadowroot_chest_right"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/shadowroot_chest_left"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/shadowroot_chest"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dark_willow_chest_right"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dark_willow_chest_left"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dark_willow_chest"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dead_wood_chest_right"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dead_wood_chest_left"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dead_wood_chest"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/nightshroom_chest_right"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/nightshroom_chest_left"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/nightshroom_chest"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dewshroom_chest_right"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dewshroom_chest_left"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/dewshroom_chest"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/viridshroom_chest_right"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/viridshroom_chest_left"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/viridshroom_chest"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/bogshroom_chest_right"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/bogshroom_chest_left"));
            event.addSprite(new ResourceLocation(MODID, "entities/chest/bogshroom_chest"));
        }
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfig.Loading event) {
        if (event.getConfig().getType() == ModConfig.Type.SERVER) {
            MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
            if (server instanceof IntegratedServer) {
                File file = server.getActiveAnvilConverter().getFile(server.getFolderName(), "midnight_configured.txt");
                if (!file.exists()) {
                    System.out.println("----------____________________-------------------Importing");
                    MidnightConfig.SERVER_PROFILE.importFromProvider(worldSetupConfig, true);
                    file.getParentFile().mkdirs();
                    try {
                        file.createNewFile();
                        PrintStream stream = new PrintStream(file);
                        stream.println("Midnight configured!");
                        stream.println("This file is used by the Midnight mod to ensure that server configs aren't overwritten when loading the world.");
                        stream.println("DO NOT DELETE THIS FILE, OTHERWISE YOUR SERVER CONFIG WILL RESET UPON WORLD LOAD!!!");
                    } catch (IOException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            }
        }
    }
}
