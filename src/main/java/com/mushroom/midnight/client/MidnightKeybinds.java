package com.mushroom.midnight.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class MidnightKeybinds {
    public static final KeyBinding MIDNIGHT_CONFIG = new KeyBinding("key.midnight.config", KeyConflictContext.UNIVERSAL, KeyModifier.CONTROL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_BACKSLASH, "key.categories.midnight");

    public static void registerKeybinds() {
        ClientRegistry.registerKeyBinding(MIDNIGHT_CONFIG);
    }
}
