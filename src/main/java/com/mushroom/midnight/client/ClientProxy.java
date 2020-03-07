package com.mushroom.midnight.client;

import com.mushroom.midnight.client.render.block.BlockRenderLayer;
import com.mushroom.midnight.common.util.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy implements IProxy {
    private static final Minecraft MC = Minecraft.getInstance();

    @Override
    public boolean isClientPlayer(Entity entity) {
        return entity == MC.player;
    }

    public static void setup(final FMLCommonSetupEvent event) {
        BlockRenderLayer.renderBlock();
    }
}
