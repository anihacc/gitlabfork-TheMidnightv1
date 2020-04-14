/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   03 - 23 - 2020
 * Author: rgsw
 */

package com.mushroom.midnight.client.shader.postfx;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.ClientProxy;
import com.mushroom.midnight.common.util.IShaderDimension;
import com.mushroom.midnight.common.util.reflect.MethodAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.*;

import java.util.function.Predicate;

// TODO: Configurations for shaders
public class ShaderManager implements ISelectiveResourceReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int MIN_REQUIRED_TEX_UNITS = 6;

    //    private static final MethodAccessor<GameRenderer, Void> setupCameraTransformMethod = new MethodAccessor<>(GameRenderer.class, "func_195460_g", float.class);
    private static final MethodAccessor<GameRenderer, Void> renderHandMethod = new MethodAccessor<>(GameRenderer.class, "func_228381_a_", MatrixStack.class, ActiveRenderInfo.class, float.class);

    private final Minecraft mc = Minecraft.getInstance();

    private MainShader mainShader = new MainShader("midnight:main");

    private final BlitBuffer blit = new BlitBuffer();

    private boolean renderingOverlays;

    private boolean shaderError;
    private boolean required;

    private static boolean capabilitiesChecked;
    private static boolean supportsShaders;
    private static boolean supportsFloatbuffer;
    private static boolean supportsARBFloatbuffer;


    public boolean canUseShaders() {
        return areShadersSupported() && !shaderError;
    }

    public boolean renderingOverlays() {
        return renderingOverlays;
    }

    public boolean shouldUseShaders() {
        return required || mc.world != null && mc.world.dimension instanceof IShaderDimension;
    }

    public void updateShaders(float partialTicks, MatrixStack mvStack) {
        if (useShaders()) {
            mainShader.updateMatrices(mvStack);
            mainShader.updateInputFBO(mc.getFramebuffer());
        }
    }

    public void renderShaders(float partialTicks, MatrixStack stack) {
        if (useShaders()) {
            blit.setSize(mc.getMainWindow().getFramebufferWidth(), mc.getMainWindow().getFramebufferHeight());
            mainShader.updateHandFBO(mc.getFramebuffer());
            mainShader.setMirrorY(true);
            mainShader.setOutputFBO(blit.getFBO());
            mainShader.render(partialTicks);
            blit.render();
            renderOverlays(partialTicks, stack);
        }
        required = false;
    }

    private void renderOverlays(float partialTicks, MatrixStack stack) {
        renderingOverlays = true;

        RenderSystem.matrixMode(GL11.GL_PROJECTION);
        RenderSystem.pushMatrix();
        RenderSystem.matrixMode(GL11.GL_MODELVIEW);
        RenderSystem.pushMatrix();
        RenderSystem.disableAlphaTest();
        RenderSystem.enableCull();
        RenderSystem.color4f(1, 1, 1, 1);
        RenderSystem.depthMask(true);
        renderHandMethod.call(mc.gameRenderer, stack, mc.gameRenderer.getActiveRenderInfo(), partialTicks);
        RenderSystem.enableAlphaTest();
        RenderSystem.matrixMode(GL11.GL_PROJECTION);
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(GL11.GL_MODELVIEW);
        RenderSystem.popMatrix();

        renderingOverlays = false;
    }

    public boolean useShaders() {
        return canUseShaders() && shouldUseShaders();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        if (resourcePredicate.test(VanillaResourceType.SHADERS)) {
            shaderError = false;
            try {
                mainShader.reload();
            } catch (ShaderException exc) {
                LOGGER.error("Error loading shaders: " + exc.getMessage(), exc);
                shaderError = true;
            }
        }
    }

    private static void checkGLCaps() {
        if (!capabilitiesChecked) {
            capabilitiesChecked = true;
            GLCapabilities caps = GL.getCapabilities();
            boolean supported = caps.OpenGL21 || caps.GL_ARB_vertex_shader && caps.GL_ARB_fragment_shader && caps.GL_ARB_shader_objects;
            boolean arbMultitexture = caps.GL_ARB_multitexture && !caps.OpenGL13;
            int maxTextureUnits = arbMultitexture
                    ? GL11.glGetInteger(ARBMultitexture.GL_MAX_TEXTURE_UNITS_ARB)
                    : !caps.OpenGL20
                    ? GL11.glGetInteger(GL13.GL_MAX_TEXTURE_UNITS)
                    : GL11.glGetInteger(GL20.GL_MAX_TEXTURE_IMAGE_UNITS);
            boolean textureUnitsSupported = maxTextureUnits >= MIN_REQUIRED_TEX_UNITS;
            supportsShaders = supported && textureUnitsSupported;
            supportsFloatbuffer = caps.OpenGL30;
            supportsARBFloatbuffer = caps.GL_ARB_texture_float;
        }
    }

    public static boolean areShadersSupported() {
        checkGLCaps();
        return supportsShaders;
    }

    public static boolean isARBFloatBuffSupported() {
        checkGLCaps();
        return supportsARBFloatbuffer;
    }

    public static boolean isFloatBuffSupported() {
        checkGLCaps();
        return supportsFloatbuffer;
    }

    public static void require() {
        get().required = true;
    }

    public static void addLight(LightSource src) {
        get().mainShader.addLight(src);
    }

    public static ShaderManager get() {
        ClientProxy clientProxy = (ClientProxy) Midnight.PROXY;
        return clientProxy.getShaderManager();
    }


    // Hooked in coremod
    public static void renderShadersHook(float partialTicks, MatrixStack stack) {
        get().renderShaders(partialTicks, stack);
        MinecraftForge.EVENT_BUS.post(new RenderShadersEvent(partialTicks));
    }
}
