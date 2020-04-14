package com.mushroom.midnight.client;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.render.block.BlockRenderLayer;
import com.mushroom.midnight.client.shader.postfx.ShaderManager;
import com.mushroom.midnight.common.util.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.entity.Entity;
import net.minecraft.resources.IReloadableResourceManager;
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

    private final ShaderManager shaderManager = new ShaderManager();

    public ClientProxy() {
        IReloadableResourceManager resMgr = (IReloadableResourceManager) MC.getResourceManager();
        addReloadListeners(resMgr);
    }

    @Override
    public boolean isClientPlayer(Entity entity) {
        return entity == MC.player;
    }

    public static void setup(FMLCommonSetupEvent event) {
        BlockRenderLayer.renderBlock();
    }

    private void addReloadListeners(IReloadableResourceManager resMgr) {
        resMgr.addReloadListener(shaderManager);
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

    public ShaderManager getShaderManager() {
        return shaderManager;
    }
}
